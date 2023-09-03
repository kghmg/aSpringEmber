package org.example;

import org.example.domain.Reservation;
import org.example.domain.repo.ReservationRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReservationRepoTest {
    @Autowired
    private ReservationRepo reservationRepo;

    @Test
    public void query() throws Exception{
        // delete everything
        // write 4 records
        // query by name and assert count
        Flux<Reservation> reservationFlux = this.reservationRepo
                .deleteAll()
                .thenMany(
                    Flux.just("a", "b", "c", "c")
                            .map(name -> new Reservation(name.hashCode()+(int) System.currentTimeMillis(),name))
                            .flatMap(r->this.reservationRepo.save(r)))
                .thenMany(this.reservationRepo.findByName("c"));

        StepVerifier
                .create(reservationFlux)
                .expectNextCount(2)
                .verifyComplete();

    }
}
