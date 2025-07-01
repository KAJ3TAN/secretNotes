/*definiuje pola takie jak nazwa użytkownika i hasło (z walidacją), a także relację
z notatkami. Umożliwia mapowanie danych użytkowników między aplikacją a bazą danych.*/

package com.secretnotes.secret_notes.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Nazwa użytkownika nie może być pusta.")
    @Size(min = 3, max = 20, message = "Nazwa użytkownika musi mieć od 3 do 20 znaków.")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Hasło nie może być puste.")
    @Size(min = 6, message = "Hasło musi mieć co najmniej 6 znaków.")
    @Column(nullable = false)
    private String password;



    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Note> notes = new ArrayList<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}