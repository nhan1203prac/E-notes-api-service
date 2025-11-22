package com.Enotes_Api_Service.Enotes_Api.controller;

import com.Enotes_Api_Service.Enotes_Api.dto.FavouriteNoteDto;
import com.Enotes_Api_Service.Enotes_Api.dto.NotesDto;
import com.Enotes_Api_Service.Enotes_Api.endpoint.NoteControllerEndpoint;
import com.Enotes_Api_Service.Enotes_Api.entity.FileDetails;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.handler.CommonUtil;
import com.Enotes_Api_Service.Enotes_Api.response.NotesResponse;
import com.Enotes_Api_Service.Enotes_Api.service.NotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class NotesController implements NoteControllerEndpoint {
    @Autowired
    private NotesService notesService;


    @Override
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws ResourceNotfoundException, IOException {
        Boolean saveNotes = notesService.createNote(notes,file);
        if (saveNotes) {
            return CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override

    public ResponseEntity<?> dowloadFile(@PathVariable Integer id) throws ResourceNotfoundException, IOException {
        FileDetails fileDetails = notesService.getFileDetails(id);
        byte[] dowloadFile = notesService.dowloadFile(fileDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(CommonUtil.getContentType(fileDetails.getOriginalFileName()));
        headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
        return ResponseEntity.ok().headers(headers).body(dowloadFile);
    }

    @Override

    public ResponseEntity<?> getAllNotes() {
        List<NotesDto>  notes = notesService.getAllNotes();
        if (CollectionUtils.isEmpty(notes)) {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }


    @Override

    public ResponseEntity<?> getUserNotes(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Integer userId = CommonUtil.getLoggedUser().getId();
        NotesResponse notesResponse = notesService.getAllNotesByUser(userId, pageNo, pageSize);
//        if(CollectionUtils.isEmpty(notesResponse.getNotes())) {
//            return ResponseEntity.noContent().build();
//        }
//        else{
//
//        }
        return CommonUtil.createBuildResponse(notesResponse, HttpStatus.OK);
    }


    @Override

    public ResponseEntity<?> searchNotes(
            @RequestParam(name = "key", defaultValue = "") String key,
            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Integer userId = CommonUtil.getLoggedUser().getId();
        NotesResponse notesResponse = notesService.getNotesByUserSearch(pageNo, pageSize, key);
//        if(CollectionUtils.isEmpty(notesResponse.getNotes())) {
//            return ResponseEntity.noContent().build();
//        }
//        else{
//
//        }
        return CommonUtil.createBuildResponse(notesResponse, HttpStatus.OK);
    }



    @Override

    public ResponseEntity<?> deleteNote(@PathVariable Integer id) throws ResourceNotfoundException {
        notesService.softDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Note deleted successfully", HttpStatus.OK);
    }

    @Override

    public ResponseEntity<?> restoreNote(@PathVariable Integer id) throws ResourceNotfoundException {
        notesService.restoreNotes(id);
        return CommonUtil.createBuildResponseMessage("Restore successfully", HttpStatus.OK);
    }


    @Override

    public ResponseEntity<?> getUserRecycleBinNotes(){
        Integer userId = CommonUtil.getLoggedUser().getId();
        List<NotesDto> notes = notesService.getUserRecycleBinNotes(userId);
        if(CollectionUtils.isEmpty(notes)) {
            return CommonUtil.createBuildResponse("Notes not available in recycle bin", HttpStatus.OK);
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }


    @Override

    public ResponseEntity<?> hardDeleteNote(@PathVariable Integer id) throws ResourceNotfoundException {
        notesService.hardDeleteNotes(id);
        return CommonUtil.createBuildResponseMessage("Note deleted successfully", HttpStatus.OK);
    }


    @Override

    public ResponseEntity<?> emptyRecyclebin() throws ResourceNotfoundException {
        Integer userId = CommonUtil.getLoggedUser().getId();
        notesService.emptyRecycleBin(userId);
        return CommonUtil.createBuildResponseMessage("Note deleted successfully", HttpStatus.OK);
    }

    @Override

    public ResponseEntity<?> favouriteNode(@PathVariable Integer noteId) throws ResourceNotfoundException {
        notesService.favouriteNote(noteId);
        return CommonUtil.createBuildResponseMessage("Notes added Favourite", HttpStatus.CREATED);
    }


    @Override

    public ResponseEntity<?> unFavouriteNode(@PathVariable Integer favNoteId) throws ResourceNotfoundException {
        notesService.unFavouriteNote(favNoteId);
        return CommonUtil.createBuildResponseMessage("Remove success", HttpStatus.OK);
    }


    @Override

    public ResponseEntity<?> getUserFavouriteNotes() throws ResourceNotfoundException {
        List<FavouriteNoteDto> userFavouriteNotes = notesService.getUserFavouriteNotes();
        if(CollectionUtils.isEmpty(userFavouriteNotes)) {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(userFavouriteNotes,HttpStatus.OK);
    }


    @Override

    public ResponseEntity<?> copyNote(@PathVariable Integer id) throws ResourceNotfoundException {
        Boolean  copyNotes = notesService.copyNotes(id);
        if(copyNotes){
            return CommonUtil.createBuildResponseMessage("Copied success", HttpStatus.CREATED);

        }
        return CommonUtil.createErrorResponseMessage("Copied fail", HttpStatus.INTERNAL_SERVER_ERROR);

    }

}

