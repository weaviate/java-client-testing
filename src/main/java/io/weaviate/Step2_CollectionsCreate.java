package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.Property;
import io.weaviate.client6.v1.collections.VectorIndex;
import io.weaviate.client6.v1.collections.Vectorizer;
import java.io.IOException;

public class Step2_CollectionsCreate {
    public static void run(WeaviateClient client) throws IOException {
        client.collections.create(Constants.PRODUCT_COLLECTION_NAME,
                collection -> collection
                        .properties(
                                Property.text("name"),
                                Property.text("description"),
                                Property.integer("price"),
                                Property.blob("image"))
                        .vector(new VectorIndex<>(
                                VectorIndex.IndexingStrategy.hnsw(),
                                Vectorizer.text2vecContextionary())));
    }
}