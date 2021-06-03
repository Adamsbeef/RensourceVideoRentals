package com.example.rensourcevideorentals.controllers;

import com.example.rensourcevideorentals.dtos.responseBody.DataResponse;
import com.example.rensourcevideorentals.services.RentRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.rensourcevideorentals.setup.Constants.*;

@RequestMapping(path = API)

@Api(tags = {"Rent Logs Controller"})
@SwaggerDefinition(tags = {
        @Tag(name = "Videos", description = "Handles everything from creating video to the calculating cost of rent")
})
public class RentLogsController {

    @Autowired
    private RentRequestService rentRequestService;

    @PostMapping(VERSION + "view-rent-request")
    public DataResponse viewRentRequests(@RequestParam Integer start, Integer to) {
        return rentRequestService.getRentRequestLogs(start, to);
    }

}
