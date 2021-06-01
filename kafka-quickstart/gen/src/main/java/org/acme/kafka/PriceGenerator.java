package org.acme.kafka;

import java.net.URI;
import java.time.Duration;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.ce.OutgoingCloudEventMetadata;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

/**
 * A bean producing random prices every 5 seconds.
 * The prices are written to a Kafka topic (prices). The Kafka configuration is specified in the application configuration.
 */
@ApplicationScoped
public class PriceGenerator {

    private Random random = new Random();

    // @Outgoing("ints")
    @Outgoing("generated-price")
    public Multi<Integer> generate() {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(3))
            .onOverflow().drop()
            .map(tick -> random.nextInt(100));
    }

    // @Incoming("ints")
    // @Outgoing("generated-price")
    // public Message<Integer> toCloudEvents(Message<Integer> price) {
    //     return price.addMetadata(OutgoingCloudEventMetadata.builder()
    //         // .withId("id-" + price.getPayload())
    //         // .withDataContentType("application/cloudevents+json; charset=UTF-8")
    //         .withType("generated")
    //         .withSource(URI.create("http://redhat.com"))
    //         .withSubject("generated-message")
    //         .build());
    // }

}
