package org.acme.kafka;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.smallrye.reactive.messaging.ce.OutgoingCloudEventMetadata;

/**
 * A simple resource retrieving the "in-memory" "my-data-stream" and sending the items to a server sent event.
 */
@Path("/prices")
public class PriceResource {

    @Inject
    @Channel("my-data-stream")
    Publisher<Double> prices;
    
    @GET
    @Path("/stream")
    // @Outgoing("prices-out")
    @Outgoing("prices")
    @Produces(MediaType.SERVER_SENT_EVENTS) // denotes that server side events (SSE) will be produced
    @SseElementType("text/plain") // denotes that the contained data, within this SSE, is just regular text/plain data
    public Publisher<Double> stream() {
        return prices;
    }

    // @Incoming("prices-out")
    // @Outgoing("prices")
    // // Send to all subscribers
    // public Message<Double> toCloudEvents(Message<Double> price) {
    //     return price.addMetadata(OutgoingCloudEventMetadata.builder()
    //         // .withId("id-" + price.getPayload())
    //         // .withDataContentType("application/cloudevents+json; charset=UTF-8")
    //         .withType("price")
    //         .withSource(URI.create("http://redhat.com"))
    //         .withSubject("price-message")
    //         .build());
    // }
}
