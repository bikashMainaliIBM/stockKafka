package com.ibm.garage.cpat;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class StockDeserializer extends JsonbDeserializer<Stock> {
    public StockDeserializer(){
        // pass the class to the parent.
        super(Stock.class);
    }
}