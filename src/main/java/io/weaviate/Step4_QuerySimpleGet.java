package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;
import io.weaviate.client6.v1.api.collections.query.Metadata;

import java.io.IOException;

public class Step4_QuerySimpleGet {
    public static void run(WeaviateClient client, String productId) throws IOException {
        var products = client.collections.use(Constants.PRODUCT_COLLECTION_NAME);
        var result = products.query.byId(productId, query -> query.returnProperties("name")
                .returnMetadata(Metadata.ID, Metadata.VECTOR, Metadata.DISTANCE));

        System.out.println("Fetched object properties: " + result.get().properties());
        System.out.println("Fetched object vectors: " + result.get().vectors());
        System.out.println("Fetched object metadata: " + result.get().metadata());
    }
}