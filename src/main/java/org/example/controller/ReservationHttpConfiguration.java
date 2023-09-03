package org.example.controller;

import org.springframework.context.annotation.Configuration;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class ReservationHttpConfiguration {

//    @Bean
//    RouterFunction<ServerResponse> routes(ReservationRepo reservationRepo){
//        return route()
//                .GET("/reservations",serverRequest -> ServerResponse.ok().body(reservationRepo.findAll(), Reservation.class))
//                .build();
//    }
}
