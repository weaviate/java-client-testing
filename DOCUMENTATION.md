# Weaviate Java Client - Operations Guide

This document provides examples and explanations for common operations using the Weaviate Java client v6.

Table of contents:
* [Connect to Weaviate](#connect-to-weaviate)
* **Collections**
    * [Create a collection](#create-a-collection)
    * [Use a collection](#use-a-collection)
    * [Delete a collection](#delete-a-collection)
* **Objects**
    * [Insert an object](#insert-an-object)
    * [Fetching an object](#fetch-an-object)
    * [Delete an object](#delete-an-object)
* **Querying**
    * [Performing a NearText vector search](#neartext-vector-search)
    * [Performing an aggregate query](#aggregate-queries)
---

## Connect to Weaviate

Establish a connection to a Weaviate instance.

```java
import io.weaviate.client6.Config;
import io.weaviate.client6.WeaviateClient;

String scheme = "http";
String httpHost = "localhost:8080";
String grpcHost = "localhost:50051";

Config config = new Config(scheme, httpHost, grpcHost);
WeaviateClient client = new WeaviateClient(config);
```

### Details

* The `Config` object holds the connection scheme (`http` or `https`), the Weaviate HTTP endpoint (e.g., `localhost:8080`), and the gRPC endpoint (e.g., `localhost:50051`).

---

## Collections

### Create a collection

Define and create collections with specified properties, references, and vector indexing configurations.

```java
import io.weaviate.client6.v1.collections.Property;
import io.weaviate.client6.v1.collections.Vectorizer;
import io.weaviate.client6.v1.collections.VectorIndex;

client.collections.create(COLLECTION_NAME, collectionConfig -> collectionConfig
    .properties(
        Property.text("propertyName1"),       // Example text property
        Property.integer("integerPropertyName") // Example integer property
    )
    .references( // Optional: define a reference to another collection
        Property.reference("referencePropertyName", TARGET_COLLECTION_NAME)
    )
    .vector( // Define vector index configuration
        new VectorIndex<>(
            VectorIndex.IndexingStrategy.hnsw(),
            Vectorizer.text2vecContextionary() // Or your chosen vectorizer
        )
    )
);
```

#### Details

| Method                                 | Description                                                                                                |
| :------------------------------------- | :--------------------------------------------------------------------------------------------------------- |
| `client.collections.create(NAME, FN)`  | Creates a new collection with the given `NAME` and configuration provided by the lambda function `FN`.       |
| `.properties(Property...)`             | Defines data properties (e.g., `Property.text()`, `Property.integer()`).                                   |
| `.references(Property.reference(...))` | Defines a cross-reference property to another collection.                                                  |
| `.vector(VectorIndex)`                 | Configures the vector index type (e.g., `hnsw`) and the vectorizer (e.g., `text2vecContextionary`).         |

---

### Use a collection

Retrieve a collection object to manage its data.

```java
var collection = client.collections.use(COLLECTION_NAME);
```

#### Details

| Method                                  | Description                                                                                                |
| :-------------------------------------- | :--------------------------------------------------------------------------------------------------------- |
| `client.collections.use(COLLECTION_NAME)` | Returns a collection object which can be used to manipulate the data within the collection. |

---

### Delete a collection

Remove entire collections from the Weaviate instance. This action is irreversible.

```java
client.collections.delete(COLLECTION_NAME); 
```

#### Details

| Method                                  | Description                                                                                                |
| :-------------------------------------- | :--------------------------------------------------------------------------------------------------------- |
| `client.collections.delete(COLLECTION_NAME)` | Deletes the entire collection specified by `COLLECTION_NAME`, including its schema and all associated data. |

---

## Objects

### Insert an object

Add data objects to your collections, optionally linking them via references.

```java
import io.weaviate.client6.v1.collections.object.WeaviateObject;
import io.weaviate.client6.v1.collections.Reference;

var collection = client.collections.use(COLLECTION_NAME);

// 1. Insert an object with basic properties
WeaviateObject<Map<String, Object>> objectResult1 = collection.data.insert(
    Map.of("propertyName1", "Some Value")
);
String createdObjectId1 = objectResult1.metadata().id(); // Get ID of the created object

// 2. Insert an object with a reference to another object
WeaviateObject<Map<String, Object>> objectResult2 = collection.data.insert(
    Map.of(
        "propertyName1", "Another Value",
        "integerPropertyName", 100
    ),
    opt -> opt.reference(
        "referencePropertyName", // Name of the reference property in COLLECTION_NAME
        Reference.collection(TARGET_COLLECTION_NAME, createdObjectId1) // Target collection and ID
    )
);
```

#### Details

| Method                                             | Description                                                                                               |
| :------------------------------------------------- | :-------------------------------------------------------------------------------------------------------- |
| `collection.data.insert(PROPERTIES_MAP)`           | Inserts an object with the given properties (a `Map<String, Object>`).                                    |
| `collection.data.insert(PROPERTIES_MAP, OPT_FN)`   | Inserts an object with properties and additional options configured by the lambda `OPT_FN`.                 |
| `opt.reference(REF_PROP_NAME, ReferenceDetails)`   | Within the options lambda, sets a reference. `REF_PROP_NAME` is the linking property in the current object. |
| `Reference.collection(TARGET_COLLECTION, ID)`      | Specifies the target collection and the specific object ID for the reference.                             |
| `objectResult.metadata().id()`                     | Retrieves the Weaviate-generated UUID of the inserted object.                                             |

---

### Fetch an object

Retrieve a single data object from a collection by its unique ID.

```java
import io.weaviate.client6.v1.collections.object.WeaviateObject;

var collection = client.collections.use(COLLECTION_NAME);

Optional<WeaviateObject<Map<String, Object>>> fetchResult = collection.data.get(objectIdToFetch);

if (fetchResult.isPresent()) {
    WeaviateObject<Map<String, Object>> fetchedObject = fetchResult.get();
    System.out.println("Fetched object: " + fetchedObject);
}
```

#### Details

| Method                             | Description                                                                                          |
| :--------------------------------- | :--------------------------------------------------------------------------------------------------- |
| `collection.data.get(OBJECT_ID)`   | Retrieves an object by its `OBJECT_ID`. Returns an `Optional` which is empty if the object isn't found. |

---

### Delete an object

Remove specific data objects from a collection using their IDs.

```java
var collection = client.collections.use(COLLECTION_NAME);
collection.data.delete(objectIdToDelete); // This operation has a void return type
```

#### Details

| Method                             | Description                                       |
| :--------------------------------- | :------------------------------------------------ |
| `collection.data.delete(OBJECT_ID)` | Deletes the object with the specified `OBJECT_ID`. |

---

## Querying

## NearText vector search

Execute a semantic search to find objects based on text similarity.

```java
import io.weaviate.client6.v1.collections.query.QueryResult;

var collection = client.collections.use(COLLECTION_NAME);
String yourQueryText = "your search query"; // The text to search for

QueryResult<Map<String, Object>> queryResult = collection.query.nearText(
    yourQueryText,
    opt -> opt.limit(1) // Example: Limit to 1 result
);

System.out.println("NearText query result: " + queryResult.objects);
```

#### Details

| Method                                          | Description                                                                                  |
| :---------------------------------------------- | :------------------------------------------------------------------------------------------- |
| `collection.query.nearText(QUERY_TEXT, OPT_FN)` | Performs a `nearText` search using `QUERY_TEXT` with options configured by lambda `OPT_FN`.    |
| `opt.limit(N)`                                  | Within the options lambda, limits the number of results to `N`.                                |

---

### Aggregate queries

Execute aggregation operations on collection data, like counting or calculating metrics, optionally grouped by a property.

```java
import io.weaviate.client6.v1.collections.aggregate.AggregateGroupByRequest.GroupBy;
import io.weaviate.client6.v1.collections.aggregate.AggregateGroupByResponse;
import io.weaviate.client6.v1.collections.aggregate.Metric;

var collection = client.collections.use(COLLECTION_NAME);

AggregateGroupByResponse response = collection.aggregate.overAll(
    new GroupBy("groupByPropertyName"), // Property to group by
    with -> with.metrics(
        Metric.integer("integerPropertyName", calculate -> calculate // Property for metrics
            .min()
            .max()
            .count()
        )
    )
    .includeTotalCount() // Include total count of groups
);

System.out.println("Aggregate query result: " + response);
```

#### Details

| Method                                              | Description                                                                                                                                  |
| :-------------------------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------------- |
| `collection.aggregate.overAll(GROUP_BY, WITH_FN)`   | Initiates an aggregation. `GROUP_BY` specifies the grouping property. `WITH_FN` lambda configures metrics.                                   |
| `new GroupBy("propName")`                           | Defines the property name to group results by.                                                                                               |
| `with.metrics(Metric.<type>("propName", CALC_FN))`  | Defines metrics for a property. `<type>` is e.g., `integer`. `CALC_FN` lambda specifies aggregation functions (e.g., `.min()`, `.max()`, `.count()`). |
| `.includeTotalCount()`                              | Appended to `overAll()`, includes the total number of distinct groups found.                                                                   |
