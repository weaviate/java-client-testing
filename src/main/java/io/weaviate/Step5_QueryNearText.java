package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;

public class Step5_QueryNearText {
    public static void run(WeaviateClient client) {
        var products = client.collections.use(Constants.PRODUCT_COLLECTION_NAME);
        var result = products.query.nearText(
                "phone",
                opt -> opt.returnProperties("name"));

        System.out.println("NearText query result: " + result.objects().get(0).properties());
    }
}