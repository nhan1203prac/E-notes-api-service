package com.Enotes_Api_Service.Enotes_Api.service.Iml;

import com.Enotes_Api_Service.Enotes_Api.dto.CategoryDto;
import com.Enotes_Api_Service.Enotes_Api.dto.NotesDto;
import com.Enotes_Api_Service.Enotes_Api.entity.Notes;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.repository.CategoryRepository;
import com.Enotes_Api_Service.Enotes_Api.repository.NotesRepository;
import com.Enotes_Api_Service.Enotes_Api.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotesServiceIml implements NotesService {
    @Autowired
    private NotesRepository notesRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Boolean createNote(NotesDto note) throws ResourceNotfoundException {
        checkCategoryExist(note.getCategory());
        Notes notes = modelMapper.map(note, Notes.class);
        Notes savedNote = notesRepository.save(notes);
        if(!ObjectUtils.isEmpty(savedNote)){
            return true;
        }
        return false;
    }

    private void checkCategoryExist(NotesDto.CategoryDto category) throws ResourceNotfoundException {
        categoryRepository.findById(category.getId()).orElseThrow(()-> new ResourceNotfoundException("Category invalid"));
    }

    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepository.findAll().stream().map(note->modelMapper.map(note, NotesDto.class)).collect(Collectors.toList());
    }
}
