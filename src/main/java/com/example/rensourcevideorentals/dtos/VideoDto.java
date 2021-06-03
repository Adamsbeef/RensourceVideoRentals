package com.example.rensourcevideorentals.dtos;

import com.example.rensourcevideorentals.enums.VideoGenre;
import com.example.rensourcevideorentals.enums.VideoTypeCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VideoDto {

    private String title;

    private VideoTypeDto videoTypeDto;

    private VideoGenre genre;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class VideoTypeDto{

        private VideoTypeCategory category;

        private Integer maximumAge;

        private Date yearReleased;
    }
}
