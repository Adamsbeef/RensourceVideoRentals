package com.example.rensourcevideorentals.services.impl;

import com.example.rensourcevideorentals.dtos.responseBody.DataResponse;
import com.example.rensourcevideorentals.repositories.RentRequestRepository;
import com.example.rensourcevideorentals.services.RentRequestService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RentRequestServiceImpl implements RentRequestService {

    private final RentRequestRepository repository;

    public RentRequestServiceImpl(RentRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse getRentRequestLogs(Integer from, Integer to) {
        Pageable pages = PageRequest.of(from, to);
        return new DataResponse(true, repository.findAll(pages).toList());
    }
}
