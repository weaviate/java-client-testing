package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;
import io.weaviate.client6.v1.api.collections.aggregate.Aggregation;
import io.weaviate.client6.v1.api.collections.aggregate.GroupBy;

public class Step6_QueryAggregate {
    public static void run(WeaviateClient client) {
        var products = client.collections.use(Constants.PRODUCT_COLLECTION_NAME);

        var response = products.aggregate.overAll(
                with -> with
                        .metrics(
                                Aggregation.integer("price",
                                        calculate -> calculate.median().max().count()))
                        .includeTotalCount(true),
                new GroupBy("name"));

        System.out.println("Aggregate query result: " + response);
    }
}