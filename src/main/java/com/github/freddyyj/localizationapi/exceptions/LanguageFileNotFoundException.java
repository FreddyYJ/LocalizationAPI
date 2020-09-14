package com.github.freddyyj.localizationapi.exceptions;

/**
 * throws when no language file exist
 * <p>
 *     This exception threw when no language file added under LocalizationAPI/lang.
 *     To avoid, add en_us.json.
 * </p>
 * @author FreddyYJ_
 */
public class LanguageFileNotFoundException extends RuntimeException{
    private String message;
    private Throwable cause;
    public LanguageFileNotFoundException(String message,Throwable cause){
        this.message=message;
        this.cause=cause;
    }
    public LanguageFileNotFoundException(String message){
        this(message,null);
    }
    public LanguageFileNotFoundException(Throwable cause){
        this("No default language file found!",cause);
    }
    public LanguageFileNotFoundException(){
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
