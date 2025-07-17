package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;
import io.weaviate.client6.v1.api.collections.aggregate.Aggregation;
import io.weaviate.client6.v1.api.collections.aggregate.GroupBy;

public class Step6_QueryAggregate {
    public static void run(WeaviateClient client) {
        var products = client.collections.use(Constants.PRODUCT_COLLECTION_NAME);

        // Step 1: Define the metrics you want to calculate.
        var priceMetrics = Aggregation.integer(Constants.PROPERTY_PRICE,
                calculate -> calculate.max());

        // Step 2: Define the group by logic.
        var groupBy = new GroupBy(Constants.PROPERTY_NAME);

        // Step 3: Execute the aggregation with the clear, pre-defined parts.
        var response = products.aggregate.overAll(
                with -> with.metrics(priceMetrics).includeTotalCount(true),
                groupBy);

        System.out.println("Aggregate query result: " + response);
    }
}
