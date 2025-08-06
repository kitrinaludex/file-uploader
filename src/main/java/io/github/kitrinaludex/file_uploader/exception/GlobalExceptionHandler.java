package io.github.kitrinaludex.file_uploader.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = FolderNotFoundException.class)
    public @ResponseBody ErrorResponse handleException(FolderNotFoundException ex) {
        return ErrorResponse.builder(ex,HttpStatus.NOT_FOUND,ex.getMessage()).build();
    }

    @ExceptionHandler(value = FolderCreationException.class)
    public @ResponseBody ErrorResponse handleException(FolderCreationException ex) {
        return ErrorResponse.builder(ex,HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage()).build();
    }

    @ExceptionHandler(value = NoFolderAccessException.class)
    public @ResponseBody ErrorResponse handleException(NoFolderAccessException ex) {
        return ErrorResponse.builder(ex,HttpStatus.FORBIDDEN,ex.getMessage()).build();
    }

    @ExceptionHandler(value = MalformedURLException.class)
    public @ResponseBody ErrorResponse handleException(MalformedURLException ex) {
        return ErrorResponse.builder(ex,HttpStatus.BAD_REQUEST,"Malformed url").build();
    }

    @ExceptionHandler(value = FileNotFoundException.class)
    public @ResponseBody ErrorResponse handleException(FileNotFoundException ex) {
        return ErrorResponse.builder(ex,HttpStatus.NOT_FOUND,ex.getMessage()).build();
    }

    @ExceptionHandler(value = UsernameAlreadyExistsException.class)
    public @ResponseBody ErrorResponse handleException(UsernameAlreadyExistsException ex) {
        return ErrorResponse.builder(ex,HttpStatus.CONFLICT,ex.getMessage()).build();
    }

}
