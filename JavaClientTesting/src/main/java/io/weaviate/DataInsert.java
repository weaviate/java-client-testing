package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.Reference;
import io.weaviate.client6.v1.collections.object.WeaviateObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataInsert {
    public static Map<String, List<String>> run(WeaviateClient client) throws IOException {
        List<String> createdCategoryIds = new ArrayList<>();
        List<String> createdProductIds = new ArrayList<>();
        var categories = client.collections.use(Constants.CATEGORY_COLLECTION_NAME);
        var products = client.collections.use(Constants.PRODUCT_COLLECTION_NAME);

        // Insert categories
        WeaviateObject<Map<String, Object>> cat1 = categories.data.insert(Map.of("name", "Clothes"));
        createdCategoryIds.add(cat1.metadata().id());
        WeaviateObject<Map<String, Object>> cat2 = categories.data.insert(Map.of("name", "Tech"));
        createdCategoryIds.add(cat2.metadata().id());

        // Insert products
        WeaviateObject<Map<String, Object>> p1 = products.data.insert(
                Map.of("name", "Some shirt", "description", "A very nice shirt...", "price", 1000, "image",
                        QueryNearImage.fetchAndEncodeImage(Constants.PRODUCT_SHIRT_IMAGE_URL)),
                opt -> opt.reference("hasCategory", Reference.collection(Constants.CATEGORY_COLLECTION_NAME, cat1.metadata().id()))
        );
        createdProductIds.add(p1.metadata().id());

        WeaviateObject<Map<String, Object>> p2 = products.data.insert(
                Map.of("name", "Some phone is coming out", "description", "New features, new hardware...", "price", 800, "image", QueryNearImage.fetchAndEncodeImage(Constants.PRODUCT_PHONE_IMAGE_URL)),
                opt -> opt.reference("hasCategory", Reference.collection(Constants.CATEGORY_COLLECTION_NAME, cat2.metadata().id()))
        );
        createdProductIds.add(p2.metadata().id());

        WeaviateObject<Map<String, Object>> p3 = products.data.insert(
                Map.of("name", "Some watch is coming out", "description", "Need to know what time it is?...", "price", 600, "image", QueryNearImage.fetchAndEncodeImage(Constants.PRODUCT_WATCH_IMAGE_URL)),
                opt -> opt.reference("hasCategory", Reference.collection(Constants.CATEGORY_COLLECTION_NAME, cat2.metadata().id()))
        );
        createdProductIds.add(p3.metadata().id());

        Map<String, List<String>> createdIds = new HashMap<>();
        createdIds.put("categoryIds", createdCategoryIds);
        createdIds.put("productIds", createdProductIds);
        return createdIds;
    }
}