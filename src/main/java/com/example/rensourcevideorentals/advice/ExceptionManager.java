package com.example.rensourcevideorentals.advice;

import com.example.rensourcevideorentals.dtos.responseBody.DataResponse;
import com.example.rensourcevideorentals.exceptions.NotFoundException;
import com.example.rensourcevideorentals.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionManager extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DataResponse> handleNotFoundException(NotFoundException exception) {
        ResponseEntity<DataResponse> response = new ResponseEntity<>(getDataResponse(exception), HttpStatus.NOT_FOUND);
        return response;
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<DataResponse> handleValidationException(ValidationException exception) {
        ResponseEntity<DataResponse> response = new ResponseEntity<>(getDataResponse(exception), HttpStatus.BAD_REQUEST);
        return response;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<DataResponse> handleUnknownException(RuntimeException exception) {
        exception.printStackTrace();
        ResponseEntity<DataResponse> response = new ResponseEntity<>(new DataResponse(false, "An Error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }

    private DataResponse getDataResponse(Exception exception) {
        DataResponse dataResponse = new DataResponse();
        dataResponse.setValid(false);
        dataResponse.addMessage(exception.getMessage());
        return dataResponse;
    }


}