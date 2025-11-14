package com.Enotes_Api_Service.Enotes_Api.service.Iml;

import com.Enotes_Api_Service.Enotes_Api.dto.CategoryDto;
import com.Enotes_Api_Service.Enotes_Api.dto.NotesDto;
import com.Enotes_Api_Service.Enotes_Api.entity.FileDetails;
import com.Enotes_Api_Service.Enotes_Api.entity.Notes;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.repository.CategoryRepository;
import com.Enotes_Api_Service.Enotes_Api.repository.FileRepository;
import com.Enotes_Api_Service.Enotes_Api.repository.NotesRepository;
import com.Enotes_Api_Service.Enotes_Api.response.NotesResponse;
import com.Enotes_Api_Service.Enotes_Api.service.NotesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotesServiceIml implements NotesService {
    @Autowired
    private NotesRepository notesRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FileRepository fileRepository;
    @Value("${file.upload.path}")
    private String uploadPath;

    @Override
    public Boolean createNote(String note, MultipartFile file) throws ResourceNotfoundException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        NotesDto notesDto = objectMapper.readValue(note, NotesDto.class);

        if(!ObjectUtils.isEmpty(notesDto.getId())){
            updatesNodes(notesDto,file);
        }
        log.info("Uploading file: {}", notesDto);
        checkCategoryExist(notesDto.getCategory());
        Notes notes = modelMapper.map(notesDto, Notes.class);
        log.info("Uploading file2: {}", notes.getFileDetails().getPath());
        FileDetails fileDetails = saveFileDetails(file);
        if(!ObjectUtils.isEmpty(fileDetails)) {
            notes.setFileDetails(fileDetails);
        }else{
            if (ObjectUtils.isEmpty(notesDto.getId())) {
                notes.setFileDetails(null);
            }
        }
        Notes savedNote = notesRepository.save(notes);
        if(!ObjectUtils.isEmpty(savedNote)){
            return true;
        }
        return false;
    }

    private void updatesNodes(NotesDto notesDto, MultipartFile file) throws ResourceNotfoundException {
        Notes existNotes = notesRepository.findById(notesDto.getId()).orElseThrow(()-> new ResourceNotfoundException("Invalid notes Id"));
        if(ObjectUtils.isEmpty(file)){
            notesDto.setFile(modelMapper.map(existNotes.getFileDetails(), NotesDto.FileDto.class));

        }
    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            String originalFileName = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFileName);
            List<String> extensions = Arrays.asList("pdf","xlsx","jpg","jpeg","png");
            log.info("extensions has: {}", extensions.contains(extension));
            log.info("extension: {}", extension);

            if(!extensions.contains(extension)){
                throw new IllegalArgumentException("Invalid file format! upload only pdf xlsx, jpg,jpeg,png");
            }
            FileDetails fileDetails = new FileDetails();
//            set field originalFileName
//            String originalFileName = file.getOriginalFilename();
            fileDetails.setOriginalFileName(originalFileName);
//            set field displayFileName
            fileDetails.setDisplayFileName(getDisplayName(originalFileName));
//            tạo chỗi UUID ngẫu nhiên
            String randomString = UUID.randomUUID().toString();
//            Lấy đuôi tên file
//            String extension = FilenameUtils.getExtension(originalFileName);
//            ghép chuối UUID với đuôi file
            String uploadFileName = randomString + "."+ extension;
//            set uploadFileName
            fileDetails.setUploadFileName(uploadFileName);
//            set size file
            fileDetails.setFileSize(file.getSize());

            File saveFile = new File(uploadPath);
            if(!saveFile.exists()){
                saveFile.mkdirs();
            }
            String storePath = uploadPath.concat(uploadFileName);
            fileDetails.setPath(storePath);

            long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
            if(upload!=0){
                FileDetails savedFile = fileRepository.save(fileDetails);
                return savedFile;
            }
        }
        return null;
    }

    private String getDisplayName(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        String fileName = FilenameUtils.removeExtension(originalFileName);
        if(fileName.length()>8){
            fileName = fileName.substring(0, 7)+"."+extension;
        }
        return fileName;
    }

    private void checkCategoryExist(NotesDto.CategoryDto category) throws ResourceNotfoundException {
        categoryRepository.findById(category.getId()).orElseThrow(()-> new ResourceNotfoundException("Category invalid"));
    }

    @Override
    public List<NotesDto> getAllNotes() {
        return notesRepository.findAll().stream().map(note->modelMapper.map(note, NotesDto.class)).collect(Collectors.toList());
    }

    @Override
    public FileDetails getFileDetails(Integer id) throws ResourceNotfoundException {
        FileDetails fileDetails = fileRepository.findById(id).orElseThrow(()->new ResourceNotfoundException("File is not available"));

        return fileDetails;
    }

    @Override
    public byte[] dowloadFile(FileDetails fileDetails) throws IOException {
        InputStream io = new FileInputStream(fileDetails.getPath());
        return StreamUtils.copyToByteArray(io);
    }

    @Override
    public NotesResponse getAllNotesByUser(Integer id, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Notes> notes = notesRepository.findByCreatedBy(id, pageable);
        List<NotesDto> notesDtos = notes.getContent()
                .stream()
                .map(note -> modelMapper.map(note, NotesDto.class))
                .collect(Collectors.toList());

        NotesResponse notesResponse = NotesResponse.builder()
                .notes(notesDtos)
                .pageNo(notes.getNumber())
                .pageSize(notes.getSize())
                .totalElements(notes.getTotalElements())
                .totalPages(notes.getTotalPages())
                .isFirst(notes.isFirst())
                .isLast(notes.isLast())
                .build();
        return notesResponse;
    }
}
