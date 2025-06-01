package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.Property;
import io.weaviate.client6.v1.collections.VectorIndex;
import io.weaviate.client6.v1.collections.Vectorizer;
import java.io.IOException;

public class CollectionsCreate {
    public static void run(WeaviateClient client) throws IOException {
        // Create `Category` collection
        client.collections.create(Constants.CATEGORY_COLLECTION_NAME,
                collection -> collection
                        .properties(Property.text("name"))
                        .vector(new VectorIndex<>(
                                VectorIndex.IndexingStrategy.hnsw(),
                                Vectorizer.text2vecContextionary()
                        ))
        );

        // Create `Product` collection
        client.collections.create(Constants.PRODUCT_COLLECTION_NAME,
                collection -> collection
                        .properties(
                                Property.text("name"),
                                Property.text("description"),
                                Property.integer("price"),
                                Property.blob("image")
                        ).references(Property.reference("hasCategory", Constants.CATEGORY_COLLECTION_NAME))
                        .vector(new VectorIndex<>(
                                VectorIndex.IndexingStrategy.hnsw(),
                                Vectorizer.text2vecContextionary()
                        ))
        );
    }
}