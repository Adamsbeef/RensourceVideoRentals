package com.example.rensourcevideorentals.services.impl;

import com.example.rensourcevideorentals.dtos.responseBody.DataResponse;
import com.example.rensourcevideorentals.entities.Video;
import com.example.rensourcevideorentals.entities.VideoType;
import com.example.rensourcevideorentals.enums.VideoGenre;
import com.example.rensourcevideorentals.enums.VideoTypeCategory;
import com.example.rensourcevideorentals.exceptions.NotFoundException;
import com.example.rensourcevideorentals.repositories.VideosRepository;
import com.example.rensourcevideorentals.services.VideosService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class VideoServiceImplTest {

    @Autowired
    private VideosService videosService;

    @MockBean
    private VideosRepository videosRepository;

    @Test
    @DisplayName("test findById Success")
    void testFindByIdSuccess() {
        Video mockVideo = new Video(1L, "How to train a dragon", new VideoType(VideoTypeCategory.Childrens_Movie, 7), VideoGenre.Drama);
        doReturn(Optional.of(mockVideo)).when(videosRepository).findById(1L);
        DataResponse result = videosService.getVideoById(1L);

        Assertions.assertNotNull(result, "The result should contain a value");
        Assertions.assertSame(result.getData(), mockVideo, "Both should be same");

    }

    @Test
    @DisplayName("test findById failure")
    void testFindByIdFailure() {
        Video mockVideo = new Video(1L, "How to train a dragon", new VideoType(VideoTypeCategory.Childrens_Movie, 7), VideoGenre.Drama);
        doReturn(Optional.of(mockVideo)).when(videosRepository).findById(1L);
        Assertions.assertThrows(NotFoundException.class, () -> videosService.getVideoById(null));
    }

    @Test
    void saveVideo() {

    }

    @Test
    void listVideos() {
    }

    @Test
    void rentVideo() {
    }

    @Test
    void getDiffYears() {
    }

    @Test
    void getCalendar() {
    }
}