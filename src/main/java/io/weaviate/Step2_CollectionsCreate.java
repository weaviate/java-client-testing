package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.Property;
import io.weaviate.client6.v1.collections.VectorIndex;
import io.weaviate.client6.v1.collections.Vectorizer;
import java.io.IOException;

public class Step2_CollectionsCreate {
    public static void run(WeaviateClient client) throws IOException {
        // Create "Product" collection 
        // The collection has text properties "name", "description" and an integer property "price"
        // The collection uses the text2vecContextionary vectorizer and HNSW vector index
        //
        // See Weaviate docs: 
        //      Create a collection with properties: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/manage-collections/collection-operations#create-a-collection-and-define-properties
    }
}