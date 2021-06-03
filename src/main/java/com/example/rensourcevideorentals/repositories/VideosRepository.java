package com.example.rensourcevideorentals.repositories;

import com.example.rensourcevideorentals.dtos.VideoListing;
import com.example.rensourcevideorentals.entities.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideosRepository extends PagingAndSortingRepository<Video, Long> {

    @Query("select new com.example.rensourcevideorentals.dtos.VideoListing(a.id,a.title,a.genre,a.videoType.category) from Video a")
    List<VideoListing> listVideos(Pageable pageable);

}
