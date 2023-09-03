package org.example;

import org.example.domain.Reservation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReservationEntityTest {
    @Autowired
    private ReactiveMongoTemplate template;

    @Test
    public void persist() throws Exception{
        Reservation rs = new Reservation(1, "Hisham");
        Mono<Reservation> save = this.template.save(rs);
        StepVerifier
                .create(save)
                .expectNextMatches(reservation -> reservation.getName().equalsIgnoreCase("Hisham") && reservation.getId()!=0)
                .verifyComplete();
    }
}
