package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.object.WeaviateObject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class QuerySimpleGet_4 {
    public static void run(WeaviateClient client, String productId) throws IOException {
        // Get and print the obect with the ID "productId"
        //
        // See Weaviate docs: 
        //      Get object by ID: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/search/basics#get-object-by-id

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        var collection = client.collections.use("Product");

        var prod =  collection.data.get(productId, 
            opt -> 
                opt.returnProperties("description", "price")
                .includeVector()
        );

        if(prod.isPresent()) {
            System.out.println(prod.get().properties());
            
            System.out.println("**** VECTOR ****");
            var vec = prod.get().metadata().vectors();
            System.out.println(Arrays.toString(prod.get().metadata().vectors().getDefaultSingle()));

            // System.out.println(prod.get().vectors().printVectors());
            // System.out.println(prod.get().vectors().getSingle("vname").printVectors());
            
        }
        else {
            System.out.println("object not found");
        }

        // return prod.orElseGet(null);
    }
}