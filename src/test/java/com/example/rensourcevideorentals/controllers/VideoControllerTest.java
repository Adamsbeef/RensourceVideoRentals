package com.example.rensourcevideorentals.controllers;

import com.example.rensourcevideorentals.dtos.VideoDto;
import com.example.rensourcevideorentals.dtos.responseBody.DataResponse;
import com.example.rensourcevideorentals.entities.Video;
import com.example.rensourcevideorentals.entities.VideoType;
import com.example.rensourcevideorentals.enums.VideoGenre;
import com.example.rensourcevideorentals.enums.VideoTypeCategory;
import com.example.rensourcevideorentals.services.VideosService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideosService videosService;

    @Test
    @DisplayName("GET /get-video/found")
    void testGetVideoByIdFound() throws Exception {

        //Setup mock service
        Video mockVideo = new Video(1L, "How to train a dragon", new VideoType(VideoTypeCategory.Childrens_Movie, 7), VideoGenre.Drama);
        doReturn(new DataResponse<>(true, mockVideo)).when(videosService).getVideoById(1L);

        mockMvc.perform(get("/api/v1/get-video/{id}", 1))
//                check response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                //check response body
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.genre", Matchers.equalTo(VideoGenre.Drama.toString())))
                .andExpect(jsonPath("$.data.videoType.category", Matchers.equalTo(VideoTypeCategory.Childrens_Movie.toString())))
                .andExpect(jsonPath("$.data.videoType.suitableAge", is(7)));
    }

    @Test
    @DisplayName("GET /get-video/ not found")
    void testGetVideoByIdNotFound() throws Exception {
        doReturn(new DataResponse<>(true, new ArrayList())).when(videosService).getVideoById(1L);
        mockMvc.perform(get("/api/vi/get-video/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /create-video Good Data")
    void testCreateVideo() throws Exception {
        VideoDto postVideo = new VideoDto("How to train a dragon", new VideoDto.VideoTypeDto(VideoTypeCategory.Childrens_Movie, 7, null), VideoGenre.Drama);

        Video mockVideo = new Video(1L, "How to train a dragon", new VideoType(VideoTypeCategory.Childrens_Movie, 7), VideoGenre.Drama);
        doReturn(new DataResponse<>(true, mockVideo)).when(videosService).saveVideo(postVideo);

        mockMvc.perform(post("/api/v1/create-video").contentType(MediaType.APPLICATION_JSON).content(convertObjectToString(postVideo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.title", is("How to train a dragon")))
                .andExpect(jsonPath("$.data.genre", is("Drama")))
                .andExpect(jsonPath("$.data.videoType.category", is(VideoTypeCategory.Childrens_Movie.toString())))
                .andExpect(jsonPath("$.data.videoType.suitableAge", is(7)));
    }

    @Test
    @DisplayName("GET /list-videos")
    void testListVideos() throws Exception {
        Video mockVideo = new Video(1L, "How to train a dragon", new VideoType(VideoTypeCategory.Childrens_Movie, 7), VideoGenre.Drama);
        List<Video> videoList = new ArrayList<>();
        videoList.add(mockVideo);

        doReturn(new DataResponse<>(true, videoList)).when(videosService).listVideos(0, 5);
        mockMvc.perform(get("/api/v1/list-videos").contentType(MediaType.APPLICATION_JSON).param("start", "0").param("to", "5"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.data", Matchers.not(emptyArray())))
                .andExpect(jsonPath("$.data", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.data[0].title", is("How to train a dragon")));
    }


    @Test
    @DisplayName("GET /rent-video correct details")
    void testRentVideo() throws Exception {

        doReturn(new DataResponse<>(true,5,"Renting this video will cost you 5 Birrs" )).when(videosService).rentVideo(any(), any(), any());
        mockMvc.perform(get("/api/v1/rent-video").contentType(MediaType.APPLICATION_JSON).param("userName", "Mike").param("rentDuration", "5").param("videoId","1"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.data", Matchers.is(5)))
                .andExpect(jsonPath("$.data", Matchers.notNullValue()));

    }

    @Test
    @DisplayName("GET /rent-video bad request")
    void testRentVideoBadRequest() throws Exception {

        doReturn(new DataResponse<>(true, "")).when(videosService).rentVideo(any(), any(), any());
        mockMvc.perform(get("/api/v1/rent-video").contentType(MediaType.APPLICATION_JSON).param("userName", "").param("rentDuration", "").param("videoId",""))
                .andExpect(status().isBadRequest());
    }


    static String convertObjectToString(Object object) throws IOException {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

