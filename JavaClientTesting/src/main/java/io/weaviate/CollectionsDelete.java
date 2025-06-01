package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import java.io.IOException;

public class CollectionsDelete {
    public static void run(WeaviateClient client) throws IOException {
        client.collections.delete(Constants.PRODUCT_COLLECTION_NAME);
        client.collections.delete(Constants.CATEGORY_COLLECTION_NAME);
    }
}