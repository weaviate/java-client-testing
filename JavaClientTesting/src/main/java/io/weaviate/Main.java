package io.weaviate;

import io.weaviate.client6.Config;
import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.*;
import io.weaviate.client6.v1.collections.aggregate.AggregateGroupByRequest.GroupBy;
import io.weaviate.client6.v1.collections.aggregate.AggregateGroupByResponse;
import io.weaviate.client6.v1.collections.aggregate.Metric;
import io.weaviate.client6.v1.collections.object.WeaviateObject;
import io.weaviate.client6.v1.collections.query.QueryResult;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Main {

    private static final String CATEGORY_COLLECTION_NAME = "Category";
    private static final String PRODUCT_COLLECTION_NAME = "Product";
    private static final String PRODUCT_SHIRT_IMAGE_URL = "https://upload.wikimedia" +
            ".org/wikipedia/commons/0/01/Charvet_shirt.jpg";
    private static final String PRODUCT_PHONE_IMAGE_URL = "https://upload.wikimedia" +
            ".org/wikipedia/commons/d/d2/IPhone_16_Pro_Vector.svg";
    private static final String PRODUCT_WATCH_IMAGE_URL = "https://upload.wikimedia" +
            ".org/wikipedia/commons/c/cd/Casio_OCEANUS_OCW-S1350PC-1AJR_01.JPG";

    private static WeaviateClient client;
    // Store IDs of created objects for later use
    private static final List<String> createdCategoryIds = new ArrayList<>();
    private static final List<String> createdProductIds = new ArrayList<>();


    public static void main(String[] args) {
        try {
            client = connectToWeaviate();
            System.out.println("Successfully connected to Weaviate.");

            deleteCollections();
            System.out.println("\nCreating collections...");
            createCollections();

            System.out.println("\nPopulating collections...");
            populateCollections();

            if (!createdCategoryIds.isEmpty() && !createdProductIds.isEmpty()) {
                System.out.println("\nFetching an object...");
                fetchObject(createdProductIds.get(0));

                System.out.println("\nPerforming a nearText vector search...");
                performNearTextSearch();

                //System.out.println("\nPerforming a nearImage vector search...");
                //performNearImageSearch();

                System.out.println("\nPerforming an aggregate query...");
                performAggregateQuery();

                System.out.println("\nDeleting created objects...");
                deleteCreatedObjects();
            }
            System.out.println("\nDeleting collections (post-run cleanup)...");
            deleteCollections();
        } catch (Exception e) {
            System.err.println("\nAn error occurred during the demo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                    System.out.println("\nWeaviate client closed.");
                } catch (Exception e) {
                    System.err.println("Error closing Weaviate client: " + e.getMessage());
                }
            }
            System.out.println("\nDemo finished.");
        }
    }

    private static WeaviateClient connectToWeaviate() {
        String scheme = "http";
        String httpHost = "localhost:8080";
        String grpcHost = "localhost:50051";

        Config config = new Config(scheme, httpHost, grpcHost);
        return new WeaviateClient(config);
    }

    private static void createCollections() throws IOException {
        // 1. Create `Categories` collection
        client.collections.create(CATEGORY_COLLECTION_NAME,
                collection -> collection
                        .properties(
                                Property.text("name"))
                        .vector(
                                new VectorIndex<>(VectorIndex.IndexingStrategy.hnsw(),
                                        Vectorizer.text2vecContextionary())));

        // 2. Create `Products` collection with a cross-reference to `Categories`
        client.collections.create(PRODUCT_COLLECTION_NAME,
                collection -> collection
                        .properties(
                                Property.text("name"),
                                Property.text("description"),
                                Property.integer("price"),
                                Property.blob("image")
                        ).references(Property.reference("hasCategory", CATEGORY_COLLECTION_NAME))
                        .vector(
                                new VectorIndex<>(VectorIndex.IndexingStrategy.hnsw(),
                                        Vectorizer.text2vecContextionary()))
        );
    }

    private static void populateCollections() throws IOException {
        var categories = client.collections.use(CATEGORY_COLLECTION_NAME);
        var products = client.collections.use(PRODUCT_COLLECTION_NAME);

        // 1. Add a category
        WeaviateObject<Map<String, Object>> categoryResult = categories.data.insert(
                Map.of("name", "Clothes"));
        System.out.println(categoryResult);
        createdCategoryIds.add(categoryResult.metadata().id());

        WeaviateObject<Map<String, Object>> techCategoryResult = categories.data.insert(
                Map.of("name", "Tech"));
        createdCategoryIds.add(techCategoryResult.metadata().id());

        // 2. Add a product linked to the category
        WeaviateObject<Map<String, Object>> productResult = products.data.insert(
                Map.of("name", "Some shirt",
                        "description", "A very nice shirt...",
                        "price", 1000,
                        "image", fetchAndEncodeImage(PRODUCT_SHIRT_IMAGE_URL)),
                opt -> opt.reference(
                        "hasCategory", Reference.collection(CATEGORY_COLLECTION_NAME, categoryResult.metadata().id())));
        System.out.println(productResult);
        createdProductIds.add(productResult.metadata().id());

        WeaviateObject<Map<String, Object>> phoneProductResult = products.data.insert(
                Map.of("name", "Some phone is coming out",
                        "description", "New features, new hardware, very exciting phone...",
                        "price", 800,
                        "image", fetchAndEncodeImage(PRODUCT_PHONE_IMAGE_URL)),
                opt -> opt.reference(
                        "hasCategory", Reference.collection(CATEGORY_COLLECTION_NAME,
                                techCategoryResult.metadata().id())));
        createdProductIds.add(phoneProductResult.metadata().id());

        WeaviateObject<Map<String, Object>> watchProductResult = products.data.insert(
                Map.of("name", "Some watch is coming out",
                        "description", "Need to know what time it is?...",
                        "price", 600,
                        "image", fetchAndEncodeImage(PRODUCT_WATCH_IMAGE_URL)),
                opt -> opt.reference(
                        "hasCategory", Reference.collection(CATEGORY_COLLECTION_NAME,
                                techCategoryResult.metadata().id())));
        createdProductIds.add(watchProductResult.metadata().id());
    }

    private static void fetchObject(String productIdToFetch) throws IOException {
        var products = client.collections.use(PRODUCT_COLLECTION_NAME);

        Optional<WeaviateObject<Map<String, Object>>> fetchResult = products.data.get(productIdToFetch);
        System.out.println("Fetched product: " + fetchResult);
    }

    private static void performNearTextSearch() {
        var products = client.collections.use(PRODUCT_COLLECTION_NAME);

        QueryResult<Map<String, Object>> queryResult = products.query.nearText(
                "phone",
                opt -> opt.returnProperties("name"));
        System.out.println("NearText query result: " + queryResult.objects);
    }

    private static void performNearImageSearch() throws IOException {
        var products = client.collections.use(PRODUCT_COLLECTION_NAME);

        QueryResult<Map<String, Object>> queryResult = products.query.nearImage(
                fetchAndEncodeImage(PRODUCT_PHONE_IMAGE_URL),
                opt -> opt.limit(1));
        System.out.println("NearText query result: " + queryResult);
    }

    private static void performAggregateQuery() {
        var products = client.collections.use(PRODUCT_COLLECTION_NAME);

        AggregateGroupByResponse response = products.aggregate.overAll(
                new GroupBy("name"),
                with -> with.metrics(
                        Metric.integer("price", calculate -> calculate
                                .min().max().count()))
                        .includeTotalCount());
        System.out.println("Aggregate query result: " + response);
    }

    private static void deleteCreatedObjects() throws IOException {
        var products = client.collections.use(PRODUCT_COLLECTION_NAME);
        var categories = client.collections.use(CATEGORY_COLLECTION_NAME);

        for (var productId : createdProductIds) {
            products.data.delete(productId);
        }
        for (var categoryId : createdCategoryIds) {
            categories.data.delete(categoryId);
        }
    }

    private static void deleteCollections() throws IOException {
        client.collections.delete(PRODUCT_COLLECTION_NAME);
        client.collections.delete(CATEGORY_COLLECTION_NAME);
    }

    // Helper method to fetch and encode the image in Weaviate-friendly format
    private static String fetchAndEncodeImage(String imageUrl) throws IOException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(imageUrl))
                    .GET()
                    .build();

            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            if (response.statusCode() == 200) {
                byte[] imageBytes = response.body();
                return Base64.getEncoder().encodeToString(imageBytes);
            } else {
                throw new IOException("Failed to fetch image: HTTP " + response.statusCode());
            }
        } catch (Exception e) {
            throw new IOException("Error fetching or encoding image: " + e.getMessage(), e);
        }
    }
}
