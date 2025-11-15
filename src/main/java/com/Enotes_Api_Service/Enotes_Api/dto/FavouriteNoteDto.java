package com.Enotes_Api_Service.Enotes_Api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavouriteNoteDto {
    private Integer id;
    private NotesDto note;
    private Integer userId;
}
