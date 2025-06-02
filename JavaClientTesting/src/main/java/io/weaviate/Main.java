package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        WeaviateClient client = null;
        try {
            client = ConnectToWeaviate.run();
            System.out.println("Successfully connected to Weaviate.");

            // Initial cleanup
            CollectionsDelete.run(client);
            System.out.println("\nCreating collections...");
            CollectionsCreate.run(client);

            System.out.println("\nPopulating collections...");
            Map<String, List<String>> createdIds = DataInsert.run(client);
            List<String> createdCategoryIds = createdIds.get("categoryIds");
            List<String> createdProductIds = createdIds.get("productIds");

            if (!createdProductIds.isEmpty()) {
                System.out.println("\nFetching an object by ID...");
                QuerySimpleGet.run(client, createdProductIds.get(0));

                System.out.println("\nPerforming a nearText vector search...");
                QueryNearText.run(client);

                System.out.println("\nPerforming an aggregate query...");
                QueryAggregate.run(client);

                System.out.println("\nDeleting created objects...");
                DataDelete.run(client, createdCategoryIds, createdProductIds);
            }

            System.out.println("\nDeleting collections (post-run cleanup)...");
            CollectionsDelete.run(client);

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