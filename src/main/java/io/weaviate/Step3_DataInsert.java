package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Step3_DataInsert {
    public static List<String> run(WeaviateClient client) throws IOException {
        List<String> createdProductIds = new ArrayList<>();
        var products = client.collections.use(Constants.PRODUCT_COLLECTION_NAME);

        var p1 = products.data.insert(
                Map.of("name", "Some shirt", "description", "A very nice shirt...", "price", 1000));
        createdProductIds.add(p1.metadata().uuid());

        var p2 = products.data.insert(
                Map.of("name", "Some phone is coming out", "description", "New features, new hardware...", "price",
                        800));
        createdProductIds.add(p2.metadata().uuid());

        var p3 = products.data.insert(
                Map.of("name", "Some watch is coming out", "description", "Need to know what time it is?...", "price",
                        600));
        createdProductIds.add(p3.metadata().uuid());

        return createdProductIds;
    }
}