package com.github.freddyyj.localizationapi;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.*;
import java.util.HashMap;

/**
 * list of all available language.
 * <p>
 *     This class should be singleton, and created already in {@link Core}.
 *     Use {@link Core#getAvailableLanguageList()} instead of creating manually.
 *
 *     This class reads "langlist.json" in jar file.
 * </p>
 * @author FreddyYJ_
 */
public class LanguageList {
    private HashMap<String,LanguageInfo> languageList;
    private static LanguageList instance=null;

    /**
     * Constructor with {@link org.bukkit.plugin.java.JavaPlugin} of this plugin.
     * @param core default {@link Core} object
     */
    protected LanguageList(Core core) {
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

    /**
     * Get instance of this class.
     * @param core default {@link Core} object
     * @return instance of this class
     * @throws IOException throws when problem at reading file, especially file not found.
     */
    public static LanguageList getInstance(Core core) throws IOException {
        if (instance==null) instance=new LanguageList(core);
        return instance;
    }

    /**
     * Reload all available language.
     * @param core default {@link Core} object
     */
    public void reload(Core core) {
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

    /**
     * Check that langauge can be used.
     * @param languageCode language code that want to check
     * @return true if it's available
     */
    public boolean hasLanguageInfo(String languageCode){
        return languageList.containsKey(languageCode);
    }

    /**
     * Get information of language.
     * @param languageCode language code
     * @return information of language
     */
    public LanguageInfo getLanguageInfo(String languageCode){
        return languageList.get(languageCode);
    }
}
