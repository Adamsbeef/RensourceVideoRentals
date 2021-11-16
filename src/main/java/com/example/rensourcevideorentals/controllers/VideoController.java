package com.example.rensourcevideorentals.controllers;

import com.example.rensourcevideorentals.dtos.VideoDto;
import com.example.rensourcevideorentals.dtos.responseBody.DataResponse;
import com.example.rensourcevideorentals.services.VideosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

import static com.example.rensourcevideorentals.setup.Constants.API;
import static com.example.rensourcevideorentals.setup.Constants.VERSION;

@AllArgsConstructor
@RestController
@RequestMapping(path = API)

@Api(tags = {"Videos Controller"})
@SwaggerDefinition(tags = {
        @Tag(name = "Videos", description = "Handles everything from creating video to the calculating cost of rent")
})
public class VideoController {

    private final VideosService videosService;

    @PostMapping(VERSION + "create-video")
    public DataResponse createVideo(@RequestBody VideoDto request) {
        return videosService.saveVideo(request);
    }

    @GetMapping(VERSION + "list-videos")
    public DataResponse listVideos(@RequestParam Integer start, Integer to) {
        return videosService.listVideos(start, to);
    }

    @GetMapping(VERSION + "get-video/{id}")
    public DataResponse getAVideo(@PathVariable Long id) {
        return videosService.getVideoById(id);
    }

    @GetMapping(VERSION + "rent-video")
    public DataResponse rentVideo(@RequestParam String userName, @RequestParam Integer rentDuration, @RequestParam Long videoId) {
        return videosService.rentVideo(userName, rentDuration, videoId);
    }

}
