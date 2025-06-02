package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.aggregate.AggregateGroupByRequest.GroupBy;
import io.weaviate.client6.v1.collections.aggregate.AggregateGroupByResponse;
import io.weaviate.client6.v1.collections.aggregate.Metric;

public class QueryAggregate {
    public static void run(WeaviateClient client) {
        // See Weaviate docs:
        // Count: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/search/aggregate#retrieve-the-count-meta-property
        // Text properties: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/search/aggregate#aggregate-text-properties
        // Integer properties: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/search/aggregate#aggregate-int-properties
        // Group by: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/search/aggregate#aggregate-groupedby-properties
    }
}