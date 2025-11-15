package com.Enotes_Api_Service.Enotes_Api.schedule;

import com.Enotes_Api_Service.Enotes_Api.entity.Notes;
import com.Enotes_Api_Service.Enotes_Api.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class NotesSchedule {
    @Autowired
    private NotesRepository notesRepository;

    public NotesSchedule(NotesRepository notesRepository) {
    }

//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "* * * ? * *")
    public void deleteNotesSchedule() {
        Instant cutOffDate = Instant.now().minus(7, ChronoUnit.DAYS);
        List<Notes> deletedNotes = notesRepository.findAllByIsDeletedAndDeletedOnBefore(true, cutOffDate);
        notesRepository.deleteAll(deletedNotes);
    }
}
