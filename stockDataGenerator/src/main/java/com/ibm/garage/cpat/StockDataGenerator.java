package com.ibm.garage.cpat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import io.vertx.core.json.JsonObject;

/**
 * A bean producing random temperature data every second.
 * The values are written to a Kafka topic (temperature-values).
 * Another topic contains the name of weather stations (weather-stations).
 * The Kafka configuration is specified in the application configuration.
 */
@ApplicationScoped
public class StockDataGenerator {

    private static final Logger LOG = Logger.getLogger(StockDataGenerator.class);

    private Random random = new Random();

    @Outgoing("stock-data")                             
    public Flowable<KafkaRecord<String, String>> generate() {
        
        return Flowable.interval(10, TimeUnit.SECONDS)
                .onBackpressureDrop()  
                .map(tick -> {
                    int quantity = random.nextInt(100);
                    double price = random.nextInt(1000-1) * random.nextDouble();
                    
                    JsonObject json = new JsonObject();
                    json.put("user_id" , random.nextInt(100));
                    json.put("stock_symbol" , "MET");
                    json.put("exchange_id", "SWISS");
                    json.put("trade_type" , "bonds");
                    json.put("date_created" , "10/20/2019");
                    json.put("date_submitted" , "10/21/2019");
                    json.put("quantity" , quantity);
                    json.put("stock_price" , price);
                    json.put("total_cost" , quantity * price);
                    json.put("institution_id" , "94");
                    json.put("country_id" , "7");
                    json.put("compliance_services" , "True");
                    json.put("technical_validation" , "True");
                    json.put("schema_validation" , "True");
                    json.put("business_validation" , "True");
                    json.put("trade_enrichment" , "True");
                    // Stock stock = new Stock("MET", "SWISS", "bonds", random.nextInt(100000-1) + 1, random.nextInt(1000-1) * random.nextDouble() , 94, 7);
                    return KafkaRecord.of("001", json.toString());

                    // return KafkaRecord.of(stock.getUserId(), stock);
                });
    }
}