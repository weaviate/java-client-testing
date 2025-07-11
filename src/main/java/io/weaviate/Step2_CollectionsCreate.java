package io.weaviate;

import io.weaviate.client6.v1.api.WeaviateClient;
import io.weaviate.client6.v1.api.collections.Property;
import io.weaviate.client6.v1.api.collections.Vectorizers;
import java.io.IOException;

public class Step2_CollectionsCreate {
        public static void run(WeaviateClient client) throws IOException {
                client.collections.create(Constants.PRODUCT_COLLECTION_NAME, collection -> collection
                                .properties(
                                                Property.text("name"),
                                                Property.text("description"),
                                                Property.integer("price"))
                                .vectors(
                                                Vectorizers.text2vecContextionary("default",
                                                                t2v -> t2v.vectorizeCollectionName(false))));
        }
}