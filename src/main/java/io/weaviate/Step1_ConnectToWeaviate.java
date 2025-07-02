package io.weaviate;

import io.weaviate.client6.Config;
import io.weaviate.client6.WeaviateClient;

public class Step1_ConnectToWeaviate {
    public static WeaviateClient run() {
        String scheme = "http";
        String httpHost = "localhost:8080";
        String grpcHost = "localhost:50051";
        Config config = new Config(scheme, httpHost, grpcHost);
        return new WeaviateClient(config);
    }
}