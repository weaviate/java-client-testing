package io.weaviate;

import io.weaviate.client6.Config;
import io.weaviate.client6.WeaviateClient;

public class ConnectToWeaviate_1 {
    public static WeaviateClient run() {
        Config config = new Config("http", "localhost:8080", "localhost:50051");
        WeaviateClient client = new WeaviateClient(config);

        return client;
    }
}