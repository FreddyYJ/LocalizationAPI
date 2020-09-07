package com.github.freddyyj.localizationapi.langfile;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.bukkit.Bukkit;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LanguageFile {
    private HashMap<String,String> stringList;
    private String languageCode;
    public LanguageFile(String path,String languageCode) throws IOException {
        stringList=new HashMap<>();

        JsonReader reader=new JsonReader(new FileReader(path+"/"+languageCode+".json"));
        reader.beginObject();
        while(reader.hasNext()){
            stringList.put(reader.nextName(),reader.nextString());
        }
        reader.endObject();

        this.languageCode=languageCode;
    }
    public String getString(String key){
        if (key==null) return null;
        else return stringList.get(key);
    }
    public String getLanguageCode(){
        return languageCode;
    }
}
