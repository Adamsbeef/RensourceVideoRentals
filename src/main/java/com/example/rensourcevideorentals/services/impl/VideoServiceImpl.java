package com.example.rensourcevideorentals.services.impl;

import com.example.rensourcevideorentals.dtos.VideoDto;
import com.example.rensourcevideorentals.dtos.VideoListing;
import com.example.rensourcevideorentals.dtos.responseBody.DataResponse;
import com.example.rensourcevideorentals.entities.RentRequests;
import com.example.rensourcevideorentals.entities.Video;
import com.example.rensourcevideorentals.entities.VideoType;
import com.example.rensourcevideorentals.enums.VideoTypeCategory;
import com.example.rensourcevideorentals.exceptions.NotFoundException;
import com.example.rensourcevideorentals.exceptions.ValidationException;
import com.example.rensourcevideorentals.repositories.RentRequestRepository;
import com.example.rensourcevideorentals.repositories.VideosRepository;
import com.example.rensourcevideorentals.services.VideosService;
import com.example.rensourcevideorentals.setup.MessageHelperService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.util.Calendar.*;

@AllArgsConstructor
@Service
public class VideoServiceImpl implements VideosService {

    private final VideosRepository repository;

    private final RentRequestRepository rentRequestRepository;

    private final MessageHelperService messageHelperService;

    private final ModelMapper mapper;



    @Override
    public DataResponse<Video> saveVideo(VideoDto videoDto) {
        Video video = mapper.map(videoDto, Video.class);
        validate(videoDto);
        VideoType videoType;
        switch (videoDto.getVideoTypeDto().getCategory()) {
            case Childrens_Movie:
                videoType = new VideoType(videoDto.getVideoTypeDto().getCategory(), videoDto.getVideoTypeDto().getMaximumAge());
                break;
            case New_Release:
                videoType = new VideoType(videoDto.getVideoTypeDto().getCategory(), videoDto.getVideoTypeDto().getYearReleased());
                break;
            default:
                videoType = new VideoType(videoDto.getVideoTypeDto().getCategory());
                break;
        }
        video.setVideoType(videoType);
        video.setVideoType(new VideoType(videoDto.getVideoTypeDto().getCategory(), videoDto.getVideoTypeDto().getMaximumAge()));
        return new DataResponse(true, repository.save(video));

    }

    @Override
    public DataResponse getVideoById(Long id) {
        Video video = repository.findById(id).orElseThrow((() -> new NotFoundException(messageHelperService.getMessage("video.not.exist"))));
        return new DataResponse(true, video);
    }

    @Override
    public DataResponse listVideos(Integer start, Integer to) {
        Pageable pages = PageRequest.of(start, to);
        List<VideoListing> value = repository.listVideos(pages);
        return new DataResponse(true, value);
    }


    @Override
    public DataResponse rentVideo(String userName, Integer rentDuration, Long videoId) {
        if (userName == null)
            throw new NotFoundException(messageHelperService.getMessage("invalid.username"));
        if (rentDuration == null)
            throw new ValidationException(messageHelperService.getMessage("invalid.duration"));
        if (videoId == null)
            throw new NotFoundException(messageHelperService.getMessage("invalid.video.selection"));

        Video video = repository.findById(videoId).orElseThrow((() -> new NotFoundException(messageHelperService.getMessage("video.not.exist"))));
        Double rentFee = getRentFee(rentDuration, video);
        logRentRequest(userName, video, rentFee);
        return new DataResponse(true, rentFee, "Renting this video will cost you " + rentFee + " Birrs");
    }

    private void logRentRequest(String userName, Video video, Double rentFee) {
        RentRequests request = new RentRequests(userName, rentFee, video);
        rentRequestRepository.save(request);
    }

    public Double getRentFee(Integer rentDuration, Video video) {
        Double amount = 0d;
        VideoTypeCategory category = video.getVideoType().getCategory();
        switch (category) {
            case Regular:
                amount = category.getRate() * rentDuration;
                break;
            case Childrens_Movie:
                amount = (category.getRate() * rentDuration) / 2;
                break;
            case New_Release:
                amount = (category.getRate() * rentDuration) - getDiffYears(video.getVideoType().getYearReleased());
                break;
            default:
                break;
        }
        return amount;
    }

    private Boolean validate(VideoDto videoDto) {
        if (videoDto == null) {
            throw new ValidationException(messageHelperService.getMessage("invalid.request"));
        }
        if (videoDto.getTitle() == null) {
            throw new ValidationException(messageHelperService.getMessage("invalid.title"));
        }
        if (videoDto.getVideoTypeDto() == null) {
            throw new ValidationException(messageHelperService.getMessage("invalid.video.type"));
        }
        if (videoDto.getVideoTypeDto().getCategory() != null) {
            if (videoDto.getVideoTypeDto().getCategory().equals(VideoTypeCategory.Childrens_Movie)) {

            }
            videoDto.getVideoTypeDto().getCategory().equals(VideoTypeCategory.Childrens_Movie);
        }
        return true;
    }

    public static int getDiffYears(Date date) {
        Calendar a = getCalendar(date);
        Calendar b = getCalendar(new Date());
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTime(date);
        return cal;
    }
}
