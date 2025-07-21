package io.github.kitrinaludex.file_uploader.exception;

public class NoFolderAccessException extends RuntimeException{
    private String message;

    public NoFolderAccessException() {
    }

    public NoFolderAccessException(String msg) {
        super(msg);
        this.message = msg;
    }


}
