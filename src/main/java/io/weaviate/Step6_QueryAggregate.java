package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;
import io.weaviate.client6.v1.api.collections.aggregate.Aggregation;
import io.weaviate.client6.v1.api.collections.aggregate.GroupBy;

public class Step6_QueryAggregate {
    public static void run(WeaviateClient client) {
        // Perform the following aggregate query: 
        //      Group all products by their name and for each group find the maximum price.
        //
        // See Weaviate docs:
        //      Integer properties: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/search/aggregate#aggregate-int-properties
        //      Group by: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/search/aggregate#aggregate-groupedby-properties
    }
}