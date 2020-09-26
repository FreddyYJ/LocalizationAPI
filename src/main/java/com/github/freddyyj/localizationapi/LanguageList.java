package com.github.freddyyj.localizationapi;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class LanguageList {
    private HashMap<String,LanguageInfo> languageList;
    private static LanguageList instance=null;
    protected LanguageList(Core core) throws IOException {
        languageList=new HashMap<>();

        JsonReader jsonReader= Json.createReader(core.getResource("langlist.json"));
        JsonObject object=jsonReader.readObject();
        String[] keys=object.keySet().toArray(new String[0]);

        JsonObject language;
        for (int i=0;i< keys.length;i++){
            language=object.getJsonObject(keys[i]);

            LanguageInfo info;
            if (language.get("region").equals(JsonValue.NULL)) info=new LanguageInfo(language.getString("local_name"),language.getString("eng_name"),null,keys[i]);
            else info=new LanguageInfo(language.getString("local_name"),language.getString("eng_name"),language.getString("region"),keys[i]);
            languageList.put(keys[i], info);
        }
        jsonReader.close();
    }
    static LanguageList getInstance(Core core) throws IOException {
        if (instance==null) instance=new LanguageList(core);
        return instance;
    }
    public void reload(Core core) throws IOException {
        languageList.clear();
        JsonReader jsonReader= Json.createReader(core.getResource("langlist.json"));
        JsonObject object=jsonReader.readObject();
        String[] keys=object.keySet().toArray(new String[0]);

        JsonObject language;
        for (int i=0;i< keys.length;i++){
            language=object.getJsonObject(keys[i]);

            LanguageInfo info;
            if (language.get("region").equals(JsonValue.NULL)) info=new LanguageInfo(language.getString("local_name"),language.getString("eng_name"),null,keys[i]);
            else info=new LanguageInfo(language.getString("local_name"),language.getString("eng_name"),language.getString("region"),keys[i]);
            languageList.put(keys[i], info);
        }
        jsonReader.close();
    }
    public boolean hasLanguageInfo(String languageCode){
        return languageList.containsKey(languageCode);
    }
    public LanguageInfo getLanguageInfo(String languageCode){
        return languageList.get(languageCode);
    }
}
