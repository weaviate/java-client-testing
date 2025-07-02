package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.query.QueryResult;
import java.util.Map;

public class Step5_QueryNearText {
    public static void run(WeaviateClient client) {
        var products = client.collections.use(Constants.PRODUCT_COLLECTION_NAME);
        QueryResult<Map<String, Object>> result = products.query.nearText(
                "phone",
                opt -> opt.returnProperties("name")
        );
        System.out.println("NearText query result: " + result.objects);
    }
}