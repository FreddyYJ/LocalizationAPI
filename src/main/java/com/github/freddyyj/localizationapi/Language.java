package com.github.freddyyj.localizationapi;

import com.github.freddyyj.localizationapi.langfile.LanguageFile;
import org.bukkit.Bukkit;

import java.io.FileNotFoundException;

public class Language {
    private LanguageFile file;
    public Language(String path,String languageCode) throws FileNotFoundException {
        file=new LanguageFile(path,languageCode);
    }
    public String getText(String key){
        return file.getString(key);
    }
    public String getLanguageCode(){
        return file.getLanguageCode();
    }
}
