package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.Property;
import io.weaviate.client6.v1.collections.VectorIndex;
import io.weaviate.client6.v1.collections.Vectorizer;
import java.io.IOException;

public class CollectionsCreate_2 {
    public static void run(WeaviateClient client) throws IOException {
        // Create "Category" collection 
        // The collection has a text property "name" 
        // The collection uses text2vecContextionary vectorizer
        //
        // See Weaviate docs: 
        //      Create a collection with properties: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/manage-collections/collection-operations#create-a-collection-and-define-properties

        // Create "Product" collection 
        // The collection has text properties "name" and "description" and an integer property "price"
        // The collection has a reference "hasCategory" to the "Category" collection 
        // The collection uses  text2vecContextionary vectorizer
        //
        // See Weaviate docs: 
        //      Create a collection with properties: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/manage-collections/collection-operations#create-a-collection-and-define-properties
        //      Define a cross-reference: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/manage-collections/cross-references#define-a-cross-reference-property
    }
}