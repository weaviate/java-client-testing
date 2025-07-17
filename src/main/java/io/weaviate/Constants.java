package io.weaviate;

import java.util.Map;

public class Constants {
    // Collection Name
    public static final String PRODUCT_COLLECTION_NAME = "Product";

    // Property Names
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_DESCRIPTION = "description";
    public static final String PROPERTY_PRICE = "price";

    // Data for insertion
    @SuppressWarnings("unchecked")
    public static final Map<String, Object>[] PRODUCTS_DATA = new Map[] {
            Map.of(PROPERTY_NAME, "Some shirt", PROPERTY_DESCRIPTION, "A very nice shirt...", PROPERTY_PRICE, 1000),
            Map.of(PROPERTY_NAME, "Some phone is coming out", PROPERTY_DESCRIPTION, "New features, new hardware...",
                    PROPERTY_PRICE, 800),
            Map.of(PROPERTY_NAME, "Some watch is coming out", PROPERTY_DESCRIPTION, "Need to know what time it is?...",
                    PROPERTY_PRICE, 600)
    };
}
