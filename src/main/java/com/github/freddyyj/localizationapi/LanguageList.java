package com.github.freddyyj.localizationapi;

import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class LanguageList {
    private HashMap<String,LanguageInfo> languageList;
    LanguageList() throws IOException {
        languageList=new HashMap<>();

        JsonReader reader=new JsonReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("langlist.json")));
        reader.beginObject();
        while(reader.hasNext()){
            languageList.put(reader.nextName(),reader.nextString());
        }
        reader.endObject();

    }
}
