package com.Enotes_Api_Service.Enotes_Api.endpoint;

import com.Enotes_Api_Service.Enotes_Api.entity.FileDetails;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.Enotes_Api_Service.Enotes_Api.Utils.Constant.*;

@RequestMapping ("/api/v1/notes")
public interface NoteControllerEndpoint {

    @PostMapping("/")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws ResourceNotfoundException, IOException ;


    @GetMapping("/dowload/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?> dowloadFile(@PathVariable Integer id) throws ResourceNotfoundException, IOException;

    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)

    public ResponseEntity<?> getAllNotes();

    @GetMapping("/user-notes")
    @PreAuthorize(ROLE_USER)

    public ResponseEntity<?> getUserNotes(@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGENO) Integer pageNo,
                                          @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGESIZE) Integer pageSize);

    @GetMapping("/search")
    @PreAuthorize(ROLE_USER)

    public ResponseEntity<?> searchNotes(
            @RequestParam(name = "key", defaultValue = "") String key,
            @RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGENO) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGESIZE) Integer pageSize);

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)

    public ResponseEntity<?> deleteNote(@PathVariable Integer id) throws ResourceNotfoundException;


    @PatchMapping("/restore/{id}")
    @PreAuthorize(ROLE_USER)

    public ResponseEntity<?> restoreNote(@PathVariable Integer id) throws ResourceNotfoundException;

    @GetMapping("/recycle-bin")
    @PreAuthorize(ROLE_USER)

    public ResponseEntity<?> getUserRecycleBinNotes();

    @DeleteMapping("/hard-delete/{id}")
    @PreAuthorize(ROLE_USER)

    public ResponseEntity<?> hardDeleteNote(@PathVariable Integer id) throws ResourceNotfoundException;


    @DeleteMapping("/delete-recycle")
    @PreAuthorize(ROLE_USER)

    public ResponseEntity<?> emptyRecyclebin() throws ResourceNotfoundException;

    @GetMapping("/fav/{noteId}")
    @PreAuthorize(ROLE_USER)

    public ResponseEntity<?> favouriteNode(@PathVariable Integer noteId) throws ResourceNotfoundException;

    @DeleteMapping("/un-fav/{favNoteId}")
    @PreAuthorize(ROLE_USER)

    public ResponseEntity<?> unFavouriteNode(@PathVariable Integer favNoteId) throws ResourceNotfoundException;

    @GetMapping("/fav-note")
    @PreAuthorize(ROLE_USER)

    public ResponseEntity<?> getUserFavouriteNotes() throws ResourceNotfoundException;


    @GetMapping("/copy/{id}")
    @PreAuthorize(ROLE_USER)

    public ResponseEntity<?> copyNote(@PathVariable Integer id) throws ResourceNotfoundException;
}
