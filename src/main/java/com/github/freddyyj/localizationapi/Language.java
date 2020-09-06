package com.github.freddyyj.localizationapi;

import com.github.freddyyj.localizationapi.langfile.LanguageFile;
import org.bukkit.Bukkit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class Language {
    private static HashMap<String,Language> languageList=new HashMap<>();
    private LanguageFile file;
    private String code;
    protected Language(String languageCode) throws IOException {
        file=new LanguageFile(Core.dataFolder.getPath(),languageCode);
        code=languageCode;
    }
    public static Language getLanguage(String code) throws IOException {
        if (languageList.containsKey(code)) return languageList.get(code);
        else{
            Language language=new Language(code);
            languageList.put(code,language);
            return language;
        }
    }
    public String getText(String key){
        return file.getString(key);
    }
    public String getLanguageCode(){
        return code;
    }
}
