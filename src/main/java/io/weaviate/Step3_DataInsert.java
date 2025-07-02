package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.object.WeaviateObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Step3_DataInsert {
    public static List<String> run(WeaviateClient client) throws IOException {
        // Create two "Product" objects:
        //      {name: "Some shirt", description": "A very nice shirt...", price: 20}
        //      {name: "Some jacket", description": "A very nice jacket...", price: 60}
        //      {name: "Some phone", description": "A very nice phone...", price: 1000}
        //
        // Return the created object IDs in the "createdProductIds" list
        //
        // See Weaviate docs: 
        //      Create a new object: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/manage-objects/create#create-an-object

        List<String> createdProductIds = new ArrayList<>();

        // This is where the object creation code goes
        // createdProductIds.add(...) // Add the objects to the appropriate list once created

        return createdProductIds;
    }
}