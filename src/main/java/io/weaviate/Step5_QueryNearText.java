package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;
import io.weaviate.client6.v1.api.collections.query.Metadata;

public class Step5_QueryNearText {
    public static void run(WeaviateClient client) {
        var products = client.collections.use(Constants.PRODUCT_COLLECTION_NAME);
        var result = products.query.nearText(
                "phone",
                opt -> opt.returnProperties(Constants.PROPERTY_NAME).returnMetadata(Metadata.VECTOR));

        System.out.println("NearText query result properties: " + result.objects().get(0).properties());
        System.out.println("NearText query result metadata: " + result.objects().get(0).metadata());
    }
}
