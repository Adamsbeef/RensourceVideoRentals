package com.example.rensourcevideorentals.services.impl;

import com.example.rensourcevideorentals.dtos.VideoDto;
import com.example.rensourcevideorentals.dtos.VideoListing;
import com.example.rensourcevideorentals.dtos.responseBody.DataResponse;
import com.example.rensourcevideorentals.entities.RentRequests;
import com.example.rensourcevideorentals.entities.Video;
import com.example.rensourcevideorentals.entities.VideoType;
import com.example.rensourcevideorentals.enums.VideoGenre;
import com.example.rensourcevideorentals.enums.VideoTypeCategory;
import com.example.rensourcevideorentals.exceptions.NotFoundException;
import com.example.rensourcevideorentals.repositories.RentRequestRepository;
import com.example.rensourcevideorentals.repositories.VideosRepository;
import com.example.rensourcevideorentals.services.VideosService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class VideoServiceImplTest {

    @Autowired
    private VideosService videosService;

    @MockBean
    private VideosRepository videosRepository;

    @MockBean
    private RentRequestRepository rentRequestRepository;

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
    @DisplayName("test findById null failure")
    void testFindByIdFNullValue() {
        Video mockVideo = new Video(1L, "How to train a dragon", new VideoType(VideoTypeCategory.Childrens_Movie, 7), VideoGenre.Drama);
        doReturn(Optional.of(mockVideo)).when(videosRepository).findById(1L);
        Assertions.assertThrows(NotFoundException.class, () -> videosService.getVideoById(null));
    }

    @Test
    void saveVideo() {
        VideoDto postVideo = new VideoDto("How to train a dragon", new VideoDto.VideoTypeDto(VideoTypeCategory.Childrens_Movie, 7, null), VideoGenre.Drama);

        Video mockVideo = new Video(1L, "How to train a dragon", new VideoType(VideoTypeCategory.Childrens_Movie, 7), VideoGenre.Drama);
        doReturn(mockVideo).when(videosRepository).save(any());

        DataResponse result = videosService.saveVideo(postVideo);
        Video video = (Video) result.getData();

        Assertions.assertNotNull(result.getData(), "The saved video should not be null");
        Assertions.assertEquals(1L, video.getId().intValue());
    }

    @Test
    void listVideos() {

        Video mockVideo1 = new Video(1L, "How to train a dragon", new VideoType(VideoTypeCategory.Childrens_Movie, 7), VideoGenre.Drama);
        Video mockVideo2 = new Video(1L, "How to talk to a dragon", new VideoType(VideoTypeCategory.Childrens_Movie, 7), VideoGenre.Action);
        Pageable pages = PageRequest.of(0, 5);

        doReturn((Arrays.asList(mockVideo1, mockVideo2))).when(videosRepository).listVideos(pages);

        DataResponse result = videosService.listVideos(0, 5);

        List<VideoListing> list = (List<VideoListing>) result.getData();
        Assertions.assertEquals(2, list.size(), "Should return 2 values");


    }

    @Test
    @DisplayName("test rent of children movies")
    void rentVideo() {
        Video mockVideo = new Video(1L, "How to train a dragon", new VideoType(VideoTypeCategory.Childrens_Movie, 7), VideoGenre.Drama);
        RentRequests mockRentRequest = new RentRequests("UserName", 5d, mockVideo);
        doReturn(Optional.of(mockVideo)).when(videosRepository).findById(1L);
        doReturn(mockRentRequest).when(rentRequestRepository).save(any());

        DataResponse result = videosService.rentVideo("User123", 3, 1L);
        Assertions.assertEquals(result.getData(), (VideoTypeCategory.Childrens_Movie.getRate() * 3) / 2);

    }

    @Test
    @DisplayName("test rent of Regular movies")
    void rentVideoRegular() {
        Video mockVideo = new Video(1L, "How to train a dragon", new VideoType(VideoTypeCategory.Regular), VideoGenre.Drama);
        RentRequests mockRentRequest = new RentRequests("UserName", 5d, mockVideo);
        doReturn(Optional.of(mockVideo)).when(videosRepository).findById(1L);
        doReturn(mockRentRequest).when(rentRequestRepository).save(any());

        DataResponse result = videosService.rentVideo("User123", 3, 1L);
        Assertions.assertEquals(result.getData(), (VideoTypeCategory.Regular.getRate() * 3));

    }

    @Test
    @DisplayName("test rent fee of Newly Realeased  category with realease date 3 years ago")
    void rentVideoNewRelease() {
        Video mockVideo = new Video(1L, "How to train a dragon", new VideoType(VideoTypeCategory.New_Release, get3yearsAgo()), VideoGenre.Drama);
        RentRequests mockRentRequest = new RentRequests("UserName", 5d, mockVideo);
        doReturn(Optional.of(mockVideo)).when(videosRepository).findById(1L);
        doReturn(mockRentRequest).when(rentRequestRepository).save(any());

        DataResponse result = videosService.rentVideo("User123", 3, 1L);
        Assertions.assertEquals(result.getData(), (VideoTypeCategory.New_Release.getRate() * 3) - 3);

    }

    private Date get3yearsAgo() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -3);
        Date threeYearsAgo = cal.getTime();
        return threeYearsAgo;
    }
}