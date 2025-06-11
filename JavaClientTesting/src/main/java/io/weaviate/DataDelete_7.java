package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.aggregate.Metric;

import java.io.IOException;
import java.util.List;

public class DataDelete_7 {
    public static void run(WeaviateClient client, List<String> categoryIds, List<String> productIds) throws IOException {
        // Use the list of object IDs to delete all objects
        //
        // See Weaviate docs: 
        //      Delete objects: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/manage-objects/delete#delete-object-by-id

        var collection = client.collections.use("Product");
        var count = collection.aggregate.overAll(
            opt -> opt.includeTotalCount()
            .metrics(
                // Metric.integer("price", m -> m.min().max())
            )
        ).totalCount();
        System.err.println("Count before: " + count);

        // DELETE HEREEEEEE
        collection.data.delete(productIds.get(0));

        count = collection.aggregate.overAll(
            opt -> opt.includeTotalCount().metrics()
        ).totalCount();
        System.err.println("Count after: " + count);
    }
}