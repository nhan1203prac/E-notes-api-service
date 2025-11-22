package com.Enotes_Api_Service.Enotes_Api.endpoint;

import com.Enotes_Api_Service.Enotes_Api.entity.FileDetails;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RequestMapping ("/api/v1/notes")
public interface NoteControllerEndpoint {

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws ResourceNotfoundException, IOException ;


    @GetMapping("/dowload/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> dowloadFile(@PathVariable Integer id) throws ResourceNotfoundException, IOException;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> getAllNotes();

    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<?> getUserNotes(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<?> searchNotes(
            @RequestParam(name = "key", defaultValue = "") String key,
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize);

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<?> deleteNote(@PathVariable Integer id) throws ResourceNotfoundException;


    @PatchMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<?> restoreNote(@PathVariable Integer id) throws ResourceNotfoundException;

    @GetMapping("/recycle-bin")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<?> getUserRecycleBinNotes();

    @DeleteMapping("/hard-delete/{id}")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<?> hardDeleteNote(@PathVariable Integer id) throws ResourceNotfoundException;


    @DeleteMapping("/delete-recycle")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<?> emptyRecyclebin() throws ResourceNotfoundException;

    @GetMapping("/fav/{noteId}")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<?> favouriteNode(@PathVariable Integer noteId) throws ResourceNotfoundException;

    @DeleteMapping("/un-fav/{favNoteId}")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<?> unFavouriteNode(@PathVariable Integer favNoteId) throws ResourceNotfoundException;

    @GetMapping("/fav-note")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<?> getUserFavouriteNotes() throws ResourceNotfoundException;


    @GetMapping("/copy/{id}")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<?> copyNote(@PathVariable Integer id) throws ResourceNotfoundException;
}
