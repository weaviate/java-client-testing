package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Step3_DataInsert {
    public static List<String> run(WeaviateClient client) throws IOException {
        // Create three "Product" objects, values in the Constants.java file
        //
        // Return the created object IDs in the "createdProductIds" list
        //
        // See Weaviate docs: 
        //      Create a new object: https://java-client-v6--docs-weaviate-io.netlify.app/weaviate/manage-objects/create#create-an-object

        List<String> createdProductIds = new ArrayList<>();

        // This is where the object creation code goes
        // createdProductIds.add(...) // Add the objects to the appropriate list once created

        return createdProductIds;
    }
}