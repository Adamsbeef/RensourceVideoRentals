package com.example.rensourcevideorentals.services;

import com.example.rensourcevideorentals.dtos.VideoDto;
import com.example.rensourcevideorentals.dtos.responseBody.DataResponse;

public interface VideosService {

    DataResponse saveVideo(VideoDto videoDto);

    DataResponse getVideoById(Long id);

    DataResponse listVideos(Integer from, Integer to);

    DataResponse rentVideo(String userName, Integer rentDuration, Long videoId);
}
