/*Umożliwia operacje na bazie danych związane z użytkownikami,
 w tym wyszukiwanie użytkownika po nazwie użytkownika. */


package com.secretnotes.secret_notes.repository;

import com.secretnotes.secret_notes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
