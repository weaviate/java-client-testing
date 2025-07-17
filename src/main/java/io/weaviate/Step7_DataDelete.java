package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;
import java.io.IOException;
import java.util.List;

public class Step7_DataDelete {
    public static void run(WeaviateClient client, List<String> productIds) throws IOException {
        var products = client.collections.use(Constants.PRODUCT_COLLECTION_NAME);

        for (var productId : productIds) {
            products.data.delete(productId);
        }

        for (var productId : productIds) {
            System.out.println("Product exists: " + products.data.exists(productId));
        }
    }
}
