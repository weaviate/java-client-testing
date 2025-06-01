package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.aggregate.AggregateGroupByRequest.GroupBy;
import io.weaviate.client6.v1.collections.aggregate.AggregateGroupByResponse;
import io.weaviate.client6.v1.collections.aggregate.Metric;

public class QueryAggregate {
    public static void run(WeaviateClient client) {
        var products = client.collections.use(Constants.PRODUCT_COLLECTION_NAME);
        AggregateGroupByResponse response = products.aggregate.overAll(
                new GroupBy("name"),
                with -> with.metrics(
                        Metric.integer("price", calculate -> calculate.min().max().count())
                ).includeTotalCount()
        );
        System.out.println("Aggregate query result: " + response);
    }
}