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

        // Loop through the data from the Constants file
        for (var productData : Constants.PRODUCTS_DATA) {
            var result = products.data.insert(productData);
            createdProductIds.add(result.metadata().uuid());
        }

        // var result = products.data.insertMany(Constants.PRODUCTS_DATA);
        // createdProductIds.addAll(result.uuids());

        return createdProductIds;
    }
}
