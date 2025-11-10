package com.Enotes_Api_Service.Enotes_Api.service;

import com.Enotes_Api_Service.Enotes_Api.dto.NotesDto;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;

import java.util.List;

public interface NotesService {
    public Boolean createNote(NotesDto note) throws ResourceNotfoundException;
    public List<NotesDto> getAllNotes();
}
