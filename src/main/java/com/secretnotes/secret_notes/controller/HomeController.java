/*Obsługuje logikę strony głównej: wyświetlanie, dodawanie, edycję i usuwanie notatek
 użytkownika oraz przekierowania. */

package com.secretnotes.secret_notes.controller;
import com.secretnotes.secret_notes.model.Note;
import com.secretnotes.secret_notes.model.User;
import com.secretnotes.secret_notes.service.NoteService;
import com.secretnotes.secret_notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;

    @Autowired
    public HomeController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        Optional<User> currentUserOptional = userService.findByUsername(username);
        if (currentUserOptional.isPresent()) {
            User currentUser = currentUserOptional.get();
            List<Note> notes = noteService.findNotesForUser(currentUser.getId());
            model.addAttribute("notes", notes);
            model.addAttribute("newNote", new Note());
        } else {
            return "redirect:/login";
        }
        return "home";
    }

    @PostMapping("/home/add")
    public String addNote(@ModelAttribute("newNote") Note newNote) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> currentUserOptional = userService.findByUsername(username);
        if (currentUserOptional.isPresent()) {
            User currentUser = currentUserOptional.get();
            newNote.setUser(currentUser);
            newNote.setCreatedAt(LocalDateTime.now());
            noteService.saveNote(newNote);
        }
        return "redirect:/home";
    }

    @PostMapping("/home/delete")
    public String deleteNote(@ModelAttribute("id") Long noteId) {
        noteService.deleteNote(noteId);
        return "redirect:/home";
    }

    @GetMapping("/note/edit/{id}")
    public String showEditNoteForm(@PathVariable Long id, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUserOptional = userService.findByUsername(username);

        if (currentUserOptional.isPresent()) {
            User currentUser = currentUserOptional.get();
            Optional<Note> noteOptional = noteService.findNoteById(id);

            if (noteOptional.isPresent() && noteOptional.get().getUser().getId().equals(currentUser.getId())) {
                model.addAttribute("note", noteOptional.get());
                return "edit_note";
            }
        }

        return "redirect:/home";
    }


    @PostMapping("/note/edit")
    public String processEditNote(@ModelAttribute("note") Note noteToUpdate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> currentUserOptional = userService.findByUsername(username);

        if (currentUserOptional.isPresent()) {
            User currentUser = currentUserOptional.get();
            Optional<Note> existingNoteOptional = noteService.findNoteById(noteToUpdate.getId());

            if (existingNoteOptional.isPresent() && existingNoteOptional.get().getUser().getId().equals(currentUser.getId())) {
                Note existingNote = existingNoteOptional.get();
                existingNote.setTitle(noteToUpdate.getTitle());
                existingNote.setContent(noteToUpdate.getContent());
                noteService.saveNote(existingNote);
            }
        }
        return "redirect:/home";
    }

}
