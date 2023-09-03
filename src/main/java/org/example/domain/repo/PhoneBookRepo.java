package org.example.domain.repo;

import org.example.domain.PhoneBook;
import org.springframework.data.repository.CrudRepository;

public interface PhoneBookRepo extends CrudRepository<PhoneBook, Integer> {

}
