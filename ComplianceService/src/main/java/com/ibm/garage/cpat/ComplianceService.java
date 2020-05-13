package com.ibm.garage.cpat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;

import io.vertx.core.json.JsonObject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A bean producing random temperature data every second.
 * The values are written to a Kafka topic (temperature-values).
 * Another topic contains the name of weather stations (weather-stations).
 * The Kafka configuration is specified in the application configuration.
 */
@ApplicationScoped
public class ComplianceService {
    private final static Logger LOGGER = LoggerFactory.getLogger("ComplianceService");

    private ExecutorService queue = Executors.newSingleThreadExecutor();
    @Channel("stock-data-outgoing") Emitter<String> stockEmitter;

    @Incoming("stock-data-incoming")
    public void process(String stockData) {
        CompletableFuture.supplyAsync(() -> {
            
            LOGGER.info("Received message {} in kafkaTopic", stockData);
            
            JsonObject jsonObject = new JsonObject(stockData);
            
            Map<String, Object> data = jsonObject.getMap();
            // Set<String> keys = data.keySet();

            // for(String key: keys){
            //     System.out.print(key );
            //     System.out.print(" ---> ");
            //     System.out.print(data.get(key));
            //     System.out.println(data.get(key).getClass());                
            // }            

            if (data.containsKey("compliance_services")) {
                LOGGER.info("compliance_services exists");
                if (data.get("compliance_services").equals("True")){
                    LOGGER.info("compliance_services is `True`");
                
                    String compliance = (String) data.get("compliance_services");
                    if (compliance.equals("True")){
                        validateCompliance(data);
                    }
                }else{
                    LOGGER.info("compliance_services is not `True`. skipping");
                }
            }
            return "";
        }, queue);
    }
    
    private void validateCompliance(Map<String, Object> mapdata){
        if (mapdata.containsKey("exchange_id")){
            String exchangeData = (String) mapdata.get("exchange_id");
        }
        if (mapdata.containsKey("trade_type")){
            String tradeType = (String) mapdata.get("trade_type");
        }
        if (mapdata.containsKey("institution_id")){
            String institutionId = (String) mapdata.get("institution_id");
        }
        mapdata.put("compliance_services", "False");
        JsonObject json = new JsonObject(mapdata);
        LOGGER.info(json.toString());
        
        stockEmitter.send(json.toString());
    } 
}