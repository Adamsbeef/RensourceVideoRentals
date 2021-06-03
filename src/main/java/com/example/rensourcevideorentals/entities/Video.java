package com.example.rensourcevideorentals.entities;

import com.example.rensourcevideorentals.enums.VideoGenre;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Video extends BaseEntity {

    private String title;

    @OneToOne(cascade = CascadeType.PERSIST)
    private VideoType videoType;

    @Enumerated
    private VideoGenre genre;

    public Video(Long id, String title, VideoType videoType, VideoGenre genre) {
        this.id = id;
        this.title = title;
        this.videoType = videoType;
        this.genre = genre;
    }
}
