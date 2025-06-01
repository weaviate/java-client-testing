package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import java.io.IOException;
import java.util.List;

public class DataDelete {
    public static void run(WeaviateClient client, List<String> categoryIds, List<String> productIds) throws IOException {
        var products = client.collections.use(Constants.PRODUCT_COLLECTION_NAME);
        var categories = client.collections.use(Constants.CATEGORY_COLLECTION_NAME);

        for (var productId : productIds) {
            products.data.delete(productId);
        }
        for (var categoryId : categoryIds) {
            categories.data.delete(categoryId);
        }
    }
}