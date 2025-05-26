package io.weaviate;

import io.weaviate.client6.Config;
import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.Property;
import io.weaviate.client6.v1.collections.Reference;
import io.weaviate.client6.v1.collections.Vectorizer;
import io.weaviate.client6.v1.collections.VectorIndex;
import io.weaviate.client6.v1.collections.aggregate.AggregateGroupByRequest.GroupBy;
import io.weaviate.client6.v1.collections.aggregate.AggregateGroupByResponse;
import io.weaviate.client6.v1.collections.aggregate.Metric;
import io.weaviate.client6.v1.collections.object.WeaviateObject;
import io.weaviate.client6.v1.collections.query.QueryResult;

import java.io.IOException;
import java.util.*;

public class Main {

    private static final String CATEGORY_COLLECTION_NAME = "Category";
    private static final String PRODUCT_COLLECTION_NAME = "Product";

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
                                Property.integer("price"))
                        .references(Property.reference("hasCategory", CATEGORY_COLLECTION_NAME))
                        .vector(
                                new VectorIndex<>(VectorIndex.IndexingStrategy.hnsw(),
                                        Vectorizer.text2vecContextionary())));
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
                        "price", 1000),
                opt -> opt.reference(
                        "hasCategory", Reference.collection(CATEGORY_COLLECTION_NAME, categoryResult.metadata().id())));
        System.out.println(productResult);
        createdProductIds.add(productResult.metadata().id());

        WeaviateObject<Map<String, Object>> phoneProductResult = products.data.insert(
                Map.of("name", "Some phone is coming out",
                        "description", "New features, new hardware, very exciting phone...",
                        "price", 800),
                opt -> opt.reference(
                        "hasCategory", Reference.collection(CATEGORY_COLLECTION_NAME,
                                techCategoryResult.metadata().id())));
        createdProductIds.add(phoneProductResult.metadata().id());

        WeaviateObject<Map<String, Object>> watchProductResult = products.data.insert(
                Map.of("name", "Some watch is coming out",
                        "description", "Need to know what time it is...",
                        "price", 600),
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

        QueryResult<Map<String, Object>> queryResult = products.query.nearText("phone",
                opt -> opt.limit(1));
        System.out.println("NearText query result: " + queryResult.objects);
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
}
