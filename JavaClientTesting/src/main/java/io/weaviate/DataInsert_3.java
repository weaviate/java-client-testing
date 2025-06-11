package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.Reference;
import io.weaviate.client6.v1.collections.object.WeaviateObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataInsert_3 {
    public static Map<String, List<String>> run(WeaviateClient client) throws IOException {
        // Create two "Category" objects:
        //      {name: "Clothes"}
        //      {name: "Technology"}
        // Create two "Product" objects:
        //      {name: "Some shirt", description", "A very nice shirt...", price: 20, hasCategory: "Clothes"}
        //      {name: "Some phone", description", "A very nice phone...", price: 1000, hasCategory: "Tech"}
        //
        // Return the created object IDs in the "createdIds" map
        //
        // See Weaviate docs: 
        //      Create a new object: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/manage-objects/create#create-an-object
        //      Create an object with a cross-reference: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/manage-objects/create#create-an-object

        Map<String, Object> c1 = Map.of("name", "Clothes");
        Map<String, Object>  c2 = Map.of("name", "Technology");

        var category_col = client.collections.use("Category");
        List<String> createdCategoryIds = new ArrayList<>();
        
        createdCategoryIds.add(
            category_col.data.insert(c1).metadata().id()
        );
        createdCategoryIds.add(
            category_col.data.insert(c2).metadata().id()
        );

        var product_col = client.collections.use("Product");
        List<String> createdProductIds = new ArrayList<>();

        Map<String, Object>  p1 = Map.of(
            "name", "Some shirt",
            "description", "A very nice shirt...",
            "price", 20
            // "hasCategory", "Clothes"
        );
        Map<String, Object>  p2 = Map.of(
            "name", "Some phone",
            "description", "A very nice phone...",
            "price", 1000
            // "hasCategory", "Tech"
        );

        createdProductIds.add(
            product_col.data.insert(p1).metadata().id()
        );
        createdProductIds.add(
            product_col.data.insert(p2).metadata().id()
        );

        // This is where the object creation code goes

        // createdCategoryIds.add(...) // Add the objects to the appropriate list once created
        // createdProductIds.add(...) // Add the objects to the appropriate list once created

        Map<String, List<String>> createdIds = new HashMap<>();
        createdIds.put("categoryIds", createdCategoryIds);
        createdIds.put("productIds", createdProductIds);
        return createdIds;
    }
}