package io.github.kitrinaludex.file_uploader.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = FolderNotFoundException.class)
    public @ResponseBody ErrorResponse handleException(FolderNotFoundException ex) {
        return ErrorResponse.builder(ex,HttpStatus.NOT_FOUND,ex.getMessage()).build();
    }

    @ExceptionHandler(value = NoFolderAccessException.class)
    public @ResponseBody ErrorResponse handleException(NoFolderAccessException ex) {
        return ErrorResponse.builder(ex,HttpStatus.NOT_FOUND,ex.getMessage()).build();
    }
}
