package com.Enotes_Api_Service.Enotes_Api.controller;

import com.Enotes_Api_Service.Enotes_Api.dto.NotesDto;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping ("/api/v1/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;

    @PostMapping("/")
    public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws ResourceNotfoundException, IOException {
        Boolean saveNotes = notesService.createNote(notes,file);
        if (saveNotes) {
            return CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/dowload/{id}")
    public ResponseEntity<?> dowloadFile(@PathVariable Integer id) throws ResourceNotfoundException, IOException {
        FileDetails fileDetails = notesService.getFileDetails(id);
        byte[] dowloadFile = notesService.dowloadFile(fileDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(CommonUtil.getContentType(fileDetails.getOriginalFileName()));
        headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
        return ResponseEntity.ok().headers(headers).body(dowloadFile);
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllNotes() {
        List<NotesDto>  notes = notesService.getAllNotes();
        if (CollectionUtils.isEmpty(notes)) {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }

    @GetMapping("/user-notes")
    public ResponseEntity<?> getUserNotes(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Integer userId = 1;
        NotesResponse notesResponse = notesService.getAllNotesByUser(userId, pageNo, pageSize);
//        if(CollectionUtils.isEmpty(notesResponse.getNotes())) {
//            return ResponseEntity.noContent().build();
//        }
//        else{
//
//        }
        return CommonUtil.createBuildResponse(notesResponse, HttpStatus.OK);
    }
}
