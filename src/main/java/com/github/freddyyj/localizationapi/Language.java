package com.github.freddyyj.localizationapi;

import com.github.freddyyj.localizationapi.langfile.LanguageFile;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Language {
    private static HashMap<String,Language> languageList=new HashMap<>();
    private LanguageFile file;
    private String code;
    protected Language(String languageCode) throws IOException {
        file=new LanguageFile(Core.dataFolder.getPath()+"/lang",languageCode);
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
    public static void reload() throws IOException {
        languageList.clear();

        File languageFolder=new File(Core.dataFolder.getPath()+"/lang");
        File[] fileList=languageFolder.listFiles();
        for (int i=0;i<languageFolder.listFiles().length;i++){
            String fileName=fileList[i].getName().split(".json")[0];
            LanguageFile file=new LanguageFile(languageFolder.getPath(),fileName);

            languageList.put(fileName,new Language(fileName));
        }
    }
    public String getText(String key){
        String translated=file.getString(key);
        if (translated==null) {
            translated=languageList.get("en_us").getFile().getString(key);
            if (translated==null) translated=key;
        }
        return translated;
    }
    public String getLanguageCode(){
        return code;
    }
    public static Set<String> getLanguageCodes(){
        return languageList.keySet();
    }
    protected LanguageFile getFile(){
        return file;
    }
}
