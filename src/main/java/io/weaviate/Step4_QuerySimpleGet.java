package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.object.WeaviateObject;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class Step4_QuerySimpleGet {
    public static void run(WeaviateClient client, String productId) throws IOException {
        var products = client.collections.use(Constants.PRODUCT_COLLECTION_NAME);
        Optional<WeaviateObject<Map<String, Object>>> result = products.data.get(productId);
        System.out.println("Fetched product: " + result);
    }
}