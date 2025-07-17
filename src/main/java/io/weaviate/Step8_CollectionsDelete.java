package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;
import java.io.IOException;

public class Step8_CollectionsDelete {
    public static void run(WeaviateClient client) throws IOException {
        client.collections.delete(Constants.PRODUCT_COLLECTION_NAME);

        System.out.println("Collection exists: " + client.collections.exists(Constants.PRODUCT_COLLECTION_NAME));
    }
}
