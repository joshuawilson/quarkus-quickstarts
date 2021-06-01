package org.acme.kafka;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.smallrye.reactive.messaging.ce.OutgoingCloudEventMetadata;
import io.smallrye.reactive.messaging.ce.IncomingCloudEventMetadata;
import io.smallrye.reactive.messaging.annotations.Broadcast;

import org.jboss.logging.Logger;

/**
 * A bean consuming data from the "prices" Kafka topic and applying some conversion.
 * The result is pushed to the "my-data-stream" stream which is an in-memory stream.
 */
@ApplicationScoped
public class PriceConverter {

    // private static final Logger LOGGER = Logger.getLogger("Price-Converter");
    private static final double CONVERSION_RATE = 0.88;

    // Consume from the `prices` channel and produce to the `my-data-stream` channel
    @Incoming("generated-price")
    @Outgoing("my-data-stream")
    // @Outgoing("doubles")
    // Acknowledge the messages before calling this method.
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
    public double process(int priceInUsd) {
        return priceInUsd * CONVERSION_RATE;
    }
    // public Message<Double> process(Message<Integer> priceInUsd) {
        
    //     IncomingCloudEventMetadata<Integer> cloudEventMetadata = priceInUsd.getMetadata(IncomingCloudEventMetadata.class)
    //             .orElseThrow(() -> new IllegalArgumentException("Expected a Cloud Event"));
    
    //     LOGGER.infof("Received Cloud Events (spec-version: %s): source:  '%s', type: '%s', subject: '%s' ",
    //             cloudEventMetadata.getSpecVersion(),
    //             cloudEventMetadata.getSource(),
    //             cloudEventMetadata.getType(),
    //             cloudEventMetadata.getSubject().orElse("no subject"));
    
    //     return priceInUsd.withPayload(Integer.valueOf(priceInUsd.getPayload()) * CONVERSION_RATE);
    // }
    
    // @Incoming("doubles")
    // @Outgoing("my-data-stream")
    // // Send to all subscribers
    // @Broadcast
    // public Message<Double> toCloudEvents(Message<Double> price) {
    //     return price.addMetadata(OutgoingCloudEventMetadata.builder()
    //         // .withId("id-" + price.getPayload())
    //         // .withDataContentType("application/cloudevents+json; charset=UTF-8")
    //         .withType("converted")
    //         .withSource(URI.create("http://redhat.com"))
    //         .withSubject("converted-message")
    //         .build());
    // }

}
