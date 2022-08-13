package edu.school21.chat.exeptions;

public class NotSavedSubEntityException extends RuntimeException {
    public NotSavedSubEntityException(String msg) {
        super(msg);
    }

}
