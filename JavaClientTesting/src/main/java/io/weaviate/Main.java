package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        WeaviateClient client = null;
        try {
            // STEP 1
            // client = ConnectToWeaviate_1.run();
            // System.out.println("Successfully connected to Weaviate.");

            // STEP 2
            // System.out.println("\nCreating collections...");
            // CollectionsCreate_2.run(client);

            // STEP 3
            Map<String, List<String>> createdIds = new HashMap<>();
            // System.out.println("\nPopulating collections...");
            // createdIds = DataInsert_3.run(client);
            List<String> createdCategoryIds = createdIds.get("categoryIds");
            List<String> createdProductIds = createdIds.get("productIds");

            if (!createdCategoryIds.isEmpty() && !createdProductIds.isEmpty()) {
                // STEP 4
                // System.out.println("\nFetching an object by ID...");
                // QuerySimpleGet_4.run(client, createdProductIds.get(0));

                // STEP 5
                // System.out.println("\nPerforming a nearText vector search...");
                // QueryNearText_5.run(client);

                // STEP 6
                // System.out.println("\nPerforming an aggregate query...");
                // QueryAggregate_6.run(client);

                // STEP 7
                // System.out.println("\nDeleting created objects...");
                // DataDelete_7.run(client, createdCategoryIds, createdProductIds);
            }
            // STEP 8
            // System.out.println("\nDeleting collections (post-run cleanup)...");
            // CollectionsDelete_8.run(client);

        } catch (Exception e) {
            System.err.println("\nAn error occurred during the demo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                    System.out.println("\nWeaviate client closed.");
                } catch (IOException e) {
                    System.err.println("Error closing Weaviate client: " + e.getMessage());
                }
            }
            System.out.println("\nDemo finished.");
        }
    }
}