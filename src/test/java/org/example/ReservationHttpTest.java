package org.example;

import org.example.controller.ReservationHttpConfiguration;
import org.example.domain.Reservation;
import org.example.domain.repo.ReservationRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@WebFluxTest(ReservationHttpConfiguration.class)
public class ReservationHttpTest {

    @MockBean
    private ReservationRepo reservationRepo;
    @Autowired
    private WebTestClient client;

    @Test
    public void getAllReservations() throws Exception{

        Mockito.when(this.reservationRepo.findAll())
                .thenReturn(Flux.just(
                        new Reservation(1,"Iman"),
                        new Reservation(2, "Haroon")
                ));
        this.client
                .get()
                .uri("/reservations")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("@.[0].name").isEqualTo("Iman")
                .jsonPath("@.[0].name").isEqualTo("Haroon");
    }
}
