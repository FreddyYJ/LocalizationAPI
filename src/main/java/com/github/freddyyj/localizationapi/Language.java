package com.github.freddyyj.localizationapi;

import com.github.freddyyj.localizationapi.langfile.LanguageFile;
import org.bukkit.Bukkit;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class Language {
    private static HashMap<LanguageCode,Language> languageList=new HashMap<>();
    private LanguageFile file;
    private LanguageCode code;
    protected Language(String languageCode) throws FileNotFoundException {
        file=new LanguageFile(Core.dataFolder.getPath(),languageCode);
        code=LanguageCode.valueOf(languageCode);
    }
    public static Language getLanguage(LanguageCode code) throws FileNotFoundException {
        if (languageList.containsKey(code)) return languageList.get(code);
        else{
            Language language=new Language(code.toString());
            languageList.put(code,language);
            return language;
        }
    }
    public String getText(String key){
        return file.getString(key);
    }
    public LanguageCode getLanguageCode(){
        return code;
    }
}
