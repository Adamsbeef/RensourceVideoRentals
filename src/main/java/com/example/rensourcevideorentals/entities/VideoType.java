package com.example.rensourcevideorentals.entities;

import com.example.rensourcevideorentals.enums.VideoTypeCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class VideoType extends BaseEntity {

    @Enumerated
    private VideoTypeCategory category;

    private Date yearReleased;

    private Integer suitableAge;

    public VideoType(VideoTypeCategory videoTypeCategory, Date yearReleased) {
        this.category = videoTypeCategory;
        this.yearReleased = yearReleased;
    }

    public VideoType(VideoTypeCategory videoTypeCategory) {
        this.category = videoTypeCategory;
    }

    public VideoType(VideoTypeCategory videoTypeCategory, Integer suitableAge) {
        this.category = videoTypeCategory;
        this.suitableAge = suitableAge;
    }

}
