package org.example.domain.repo;

import org.example.domain.Reservation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ReservationRepo extends ReactiveCrudRepository<Reservation, Integer> {
    Flux<Reservation> findByName(String name);
}
