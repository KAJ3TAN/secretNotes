/*Zapewnia logikę biznesową związaną z notatkami:
pobieranie, zapisywanie, wyszukiwanie i usuwanie notatek użytkowników.*/

package com.secretnotes.secret_notes.service;

import com.secretnotes.secret_notes.model.Note;
import com.secretnotes.secret_notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> findNotesForUser(Long userId) {
        return noteRepository.findAllByUserId(userId);
    }

    public void saveNote(Note note) {
        noteRepository.save(note);
    }

    public Optional<Note> findNoteById(Long id) {
        return noteRepository.findById(id);
    }

    public void deleteNote(Long noteId) {
        noteRepository.deleteById(noteId);
    }
}
