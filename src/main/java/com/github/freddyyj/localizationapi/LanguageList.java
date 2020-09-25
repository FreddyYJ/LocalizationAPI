package com.github.freddyyj.localizationapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.HashMap;

public class LanguageList {
    private HashMap<String,LanguageInfo> languageList;
    LanguageList(Core core) throws IOException {
        languageList=new HashMap<>();

        Reader reader=new InputStreamReader(core.getResource("langlist.json"));
        JsonObject root=new Gson().fromJson(reader,JsonObject.class);
        String[] keys=root.keySet().toArray(new String[0]);

        JsonObject value;
        for (int i=0;i< keys.length;i++){
            value=root.getAsJsonObject(keys[i]);
            LanguageInfo info=new LanguageInfo(value.getAsJsonPrimitive("local_name").getAsString(),value.getAsJsonPrimitive("eng_name").getAsString(),value.getAsJsonPrimitive("region").getAsString(),keys[i]);
            languageList.put(keys[i], info);
        }
        reader.close();
    }
    public void reload(Core core) throws IOException {
        Reader reader=new InputStreamReader(core.getResource("langlist.json"));
        JsonObject root=new Gson().fromJson(reader,JsonObject.class);
        String[] keys=root.keySet().toArray(new String[0]);

        JsonObject value;
        for (int i=0;i< keys.length;i++){
            value=root.getAsJsonObject(keys[i]);
            LanguageInfo info=new LanguageInfo(value.getAsJsonPrimitive("local_name").getAsString(),value.getAsJsonPrimitive("eng_name").getAsString(),value.getAsJsonPrimitive("region").getAsString(),keys[i]);
            languageList.put(keys[i], info);
        }
        reader.close();
    }
    public boolean hasLanguageInfo(String languageCode){
        return languageList.containsKey(languageCode);
    }
    public LanguageInfo getLanguageInfo(String languageCode){
        return languageList.get(languageCode);
    }
}
