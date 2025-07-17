package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;
import java.io.IOException;
import java.util.List;

public class Step7_DataDelete {
    public static void run(WeaviateClient client, List<String> productIds) throws IOException {
        // Use the list of object IDs to delete all objects and check if they exist after deletion
        //
        // See Weaviate docs: 
        //      Delete objects: https://java-client-v6--docs-weaviate-io.netlify.app/weaviate/manage-objects/delete#delete-object-by-id
    }
}