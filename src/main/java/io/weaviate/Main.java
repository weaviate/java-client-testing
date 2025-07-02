package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import java.io.IOException;
import java.util.List;

public class Main {

    // Boolean variables to control execution flow
    private static boolean step1_connect = false;
    private static boolean step2_createCollections = false;
    private static boolean step3_populateCollections = false;
    private static boolean step4_fetchById = false;
    private static boolean step5_nearTextSearch = false;
    private static boolean step6_aggregateQuery = false;
    private static boolean step7_deleteObjects = false;
    private static boolean step8_deleteCollections = false;

    public static void main(String[] args) {
        try {
            execute();
        } catch (Exception e) {
            System.err.println("\nAn error occurred during the demo: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("\nDemo finished.");
    }

    private static void execute() throws Exception {
        WeaviateClient client = null;

        try {
            // STEP 1: Connect to Weaviate
            if (!step1_connect)
                return;
            System.out.println("===============================");
            System.out.println("===== Step 1: Connect to Weaviate =====");
            System.out.println("===============================");
            client = Step1_ConnectToWeaviate.run();

            // STEP 2: Create collections
            if (!step2_createCollections)
                return;
            System.out.println("\n===============================");
            System.out.println("===== Step 2: Create Collections =====");
            System.out.println("===============================");
            cleanup(client); // Ensure previous collections are deleted before creating new ones
            Step2_CollectionsCreate.run(client);
            System.out.println("Collections created successfully.");

            // STEP 3: Populate collections
            if (!step3_populateCollections)
                return;
            System.out.println("\n===============================");
            System.out.println("===== Step 3: Populate Collections =====");
            System.out.println("===============================");
            List<String> createdProductIds = Step3_DataInsert.run(client);

            if (createdProductIds.isEmpty()) {
                System.out.println("No data was created, skipping query steps.");
                return;
            }
            System.out.println("Objects created successfully.");

            // STEP 4: Fetch object by ID
            if (!step4_fetchById)
                return;
            System.out.println("\n===============================");
            System.out.println("===== Step 4: Fetch Object by ID =====");
            System.out.println("===============================");
            Step4_QuerySimpleGet.run(client, createdProductIds.get(0));

            // STEP 5: Perform nearText vector search
            Thread.sleep(1000); // Wait for 1 second
            if (!step5_nearTextSearch)
                return;
            System.out.println("\n===============================");
            System.out.println("===== Step 5: NearText Vector Search =====");
            System.out.println("===============================");
            Step5_QueryNearText.run(client);

            // STEP 6: Perform aggregate query
            if (!step6_aggregateQuery)
                return;
            System.out.println("\n===============================");
            System.out.println("===== Step 6: Aggregate Query =====");
            System.out.println("===============================");
            Step6_QueryAggregate.run(client);

            // STEP 7: Delete created objects
            if (!step7_deleteObjects)
                return;
            System.out.println("\n===============================");
            System.out.println("===== Step 7: Delete Objects =====");
            System.out.println("===============================");
            Step7_DataDelete.run(client, createdProductIds);

            // STEP 8: Delete collections (cleanup)
            if (!step8_deleteCollections)
                return;
            System.out.println("\n===============================");
            System.out.println("===== Step 8: Delete Collections =====");
            System.out.println("===============================");
            Step8_CollectionsDelete.run(client);

        } finally {
            if (client != null) {
                try {
                    client.close();
                    System.out.println("\nWeaviate client closed.");
                } catch (IOException e) {
                    System.err.println("Error closing Weaviate client: " + e.getMessage());
                }
            }
        }
    }

    private static void cleanup(WeaviateClient client) throws IOException {
        client.collections.delete(Constants.PRODUCT_COLLECTION_NAME);
    }
}
