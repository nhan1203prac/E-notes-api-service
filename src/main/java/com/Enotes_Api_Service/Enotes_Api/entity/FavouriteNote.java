package com.Enotes_Api_Service.Enotes_Api.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class FavouriteNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Notes note;
    private Integer userId;


}
