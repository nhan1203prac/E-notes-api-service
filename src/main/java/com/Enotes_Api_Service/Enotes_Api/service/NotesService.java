package com.Enotes_Api_Service.Enotes_Api.service;

import com.Enotes_Api_Service.Enotes_Api.dto.NotesDto;
import com.Enotes_Api_Service.Enotes_Api.entity.FileDetails;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.response.NotesResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface NotesService {
    public Boolean createNote(String note, MultipartFile file) throws ResourceNotfoundException, IOException;
    public List<NotesDto> getAllNotes();
    public FileDetails getFileDetails(Integer id) throws ResourceNotfoundException;
    byte[] dowloadFile(FileDetails fileDetails) throws ResourceNotfoundException, IOException;
    public NotesResponse getAllNotesByUser(Integer id, Integer pageNo, Integer pageSize);

    void softDeleteNotes(Integer id) throws ResourceNotfoundException;

    void restoreNotes(Integer id) throws ResourceNotfoundException;

    List<NotesDto> getUserRecycleBinNotes(Integer userId);

    void hardDeleteNotes(Integer id) throws ResourceNotfoundException;

    void emptyRecycleBin(Integer userId);
}
