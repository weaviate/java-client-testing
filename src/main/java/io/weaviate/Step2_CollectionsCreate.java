package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;
import io.weaviate.client6.v1.api.collections.Property;
import io.weaviate.client6.v1.api.collections.Vectorizers;
import java.io.IOException;

public class Step2_CollectionsCreate {
    public static void run(WeaviateClient client) throws IOException {
        // Create "Product" collection 
        // The collection has text properties "name", "description" and an integer property "price" (names in Constants.java)
        // The collection uses the text2vecContextionary vectorizer
        //
        // See Weaviate docs: 
        //      Create a collection with properties: https://java-client-v6--docs-weaviate-io.netlify.app/weaviate/manage-collections/collection-operations#create-a-collection-and-define-properties
    }
}