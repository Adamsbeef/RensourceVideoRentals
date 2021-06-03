package com.example.rensourcevideorentals.dtos;

import com.example.rensourcevideorentals.enums.VideoGenre;
import com.example.rensourcevideorentals.enums.VideoTypeCategory;
import lombok.Data;

@Data
public class VideoListing {

    Long id;

    String title;

    VideoGenre genre;

    String videoType;

    public VideoListing(Long id, String title, VideoGenre genre, VideoTypeCategory category) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.videoType = category.getDescription();
    }
}
