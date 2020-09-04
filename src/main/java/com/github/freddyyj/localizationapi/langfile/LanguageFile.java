package com.github.freddyyj.localizationapi.langfile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.bukkit.Bukkit;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class LanguageFile {
    private JsonObject stringList;
    private String languageCode;
    public LanguageFile(String path,String languageCode) throws FileNotFoundException {
        JsonReader reader=new JsonReader(new FileReader(path+"/"+languageCode+".json"));
        stringList= (JsonObject) JsonParser.parseReader(reader);
        this.languageCode=languageCode;
    }
    public String getString(String key){
        return stringList.get(key).getAsString();
    }
    public String getLanguageCode(){
        return languageCode;
    }
}
