package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;
import io.weaviate.client6.v1.api.collections.query.Metadata;

import java.io.IOException;

public class Step4_QuerySimpleGet {
    public static void run(WeaviateClient client, String productId) throws IOException {
        // Get the object with the ID "productId"
        // Also print out the metadata, properties, and vector of the object
        //
        // See Weaviate docs: 
        //      Get object by ID: https://java-client-v6--docs-weaviate-io.netlify.app/weaviate/search/basics#get-object-by-id
    }
}