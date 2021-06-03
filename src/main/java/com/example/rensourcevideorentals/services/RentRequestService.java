package com.example.rensourcevideorentals.services;

import com.example.rensourcevideorentals.dtos.responseBody.DataResponse;

public interface RentRequestService {

    DataResponse getRentRequestLogs(Integer from, Integer to);
}
