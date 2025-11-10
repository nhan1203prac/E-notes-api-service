package com.Enotes_Api_Service.Enotes_Api.service;

import com.Enotes_Api_Service.Enotes_Api.dto.NotesDto;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NotesService {
    public Boolean createNote(String note, MultipartFile file) throws ResourceNotfoundException, IOException;
    public List<NotesDto> getAllNotes();
}
