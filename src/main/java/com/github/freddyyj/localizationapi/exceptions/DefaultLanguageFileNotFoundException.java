package com.github.freddyyj.localizationapi.exceptions;

public class DefaultLanguageFileNotFoundException extends RuntimeException{
    private String message;
    private Throwable cause;
    public DefaultLanguageFileNotFoundException(String message,Throwable cause){
        this.message=message;
        this.cause=cause;
    }
    public DefaultLanguageFileNotFoundException(String message){
        this(message,null);
    }
    public DefaultLanguageFileNotFoundException(Throwable cause){
        this("No default language file found!",cause);
    }
    public DefaultLanguageFileNotFoundException(){
        this("No default language file found!");
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
