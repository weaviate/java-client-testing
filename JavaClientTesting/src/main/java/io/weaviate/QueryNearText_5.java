package io.weaviate;

import io.weaviate.client6.WeaviateClient;
import io.weaviate.client6.v1.collections.query.QueryResult;
import java.util.Map;

public class QueryNearText_5 {
    public static void run(WeaviateClient client) {
        // Perform the following nearText query on the "Product" collection: {query: "phone"}
        //
        // See Weaviate docs: 
        //      Vector search: https://java-client-v6--weaviate-docs.netlify.app/docs/weaviate/search/similarity#search-with-text

        

        var collection = client.collections.use("Product");
        var result = collection.query.nearText(
            "shirt",
            opt -> opt.returnProperties("description", "price")
                .limit(1)
        );

        System.err.println("––––––––––––––––––––––––––––––––––––––––––––––––");
        System.err.println(result);
        System.err.println(result.objects.size());
        System.err.println(result.objects.get(0).properties);
    }
}