package com.Enotes_Api_Service.Enotes_Api.endpoint;

import com.Enotes_Api_Service.Enotes_Api.dto.NotesDto;
import com.Enotes_Api_Service.Enotes_Api.entity.FileDetails;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.Enotes_Api_Service.Enotes_Api.Utils.Constant.*;
@Tag(name = "Notes ", description = "All the Notes Operation APIs")
@RequestMapping ("/api/v1/notes")
public interface NoteControllerEndpoint {

    @Operation(summary = "Saved Notes EndPoint", tags = {"Notes"})
    @PostMapping(value = "/", consumes = "multipart/form-data")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?> saveNotes(
//            @Parameter(description = "Json String Notes", required = true, schema = @Schema(implementation = NotesDto.class))
            @RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws ResourceNotfoundException, IOException ;

    @Operation(summary = "Dowload File", tags = {"Notes"})
    @GetMapping("/dowload/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?> dowloadFile(@PathVariable Integer id) throws ResourceNotfoundException, IOException;


    @Operation(summary = "Get All Notes", tags = {"Notes"})
    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getAllNotes();


    @Operation(summary = "Get All User's Notes", tags = {"Notes"})
    @GetMapping("/user-notes")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getUserNotes(@RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGENO) Integer pageNo,
                                          @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGESIZE) Integer pageSize);


    @Operation(summary = "Search Notes", tags = {"Notes"})
    @GetMapping("/search")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> searchNotes(
            @RequestParam(name = "key", defaultValue = "") String key,
            @RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGENO) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGESIZE) Integer pageSize);


    @Operation(summary = "Delete Notes By id", tags = {"Notes"})
    @DeleteMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)

    public ResponseEntity<?> deleteNote(@PathVariable Integer id) throws ResourceNotfoundException;

    @Operation(summary = "Restore Note Deleted", tags = {"Notes"})
    @PatchMapping("/restore/{id}")
    @PreAuthorize(ROLE_USER)

    public ResponseEntity<?> restoreNote(@PathVariable Integer id) throws ResourceNotfoundException;

    @Operation(summary = "Get All Notes In Recycle Bin", tags = {"Notes"})
    @GetMapping("/recycle-bin")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getUserRecycleBinNotes();

    @Operation(summary = "Delete Notes Hard", tags = {"Notes"})
    @DeleteMapping("/hard-delete/{id}")
    @PreAuthorize(ROLE_USER)

    public ResponseEntity<?> hardDeleteNote(@PathVariable Integer id) throws ResourceNotfoundException;

    @Operation(summary = "Delete Notes In Recycle Bin", tags = {"Notes"})
    @DeleteMapping("/delete-recycle")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> emptyRecyclebin() throws ResourceNotfoundException;

    @Operation(summary = "Marke favourite Notes", tags = {"Notes"})
    @GetMapping("/fav/{noteId}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> favouriteNode(@PathVariable Integer noteId) throws ResourceNotfoundException;


    @Operation(summary = "UnCheck Favourite Notes", tags = {"Notes"})
    @DeleteMapping("/un-fav/{favNoteId}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> unFavouriteNode(@PathVariable Integer favNoteId) throws ResourceNotfoundException;


    @Operation(summary = "Get All Favourite Notes", tags = {"Notes"})
    @GetMapping("/fav-note")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getUserFavouriteNotes() throws ResourceNotfoundException;

    @Operation(summary = "Copies Notes", tags = {"Notes"})
    @GetMapping("/copy/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> copyNote(@PathVariable Integer id) throws ResourceNotfoundException;
}
