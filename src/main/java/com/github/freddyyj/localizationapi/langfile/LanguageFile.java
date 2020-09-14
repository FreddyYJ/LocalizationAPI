package com.github.freddyyj.localizationapi.langfile;

import com.github.freddyyj.localizationapi.Core;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * language file class
 * <p>
 *     This class is for manage each of language files.
 *     This object should be mapped with one file.
 *
 *     For example,
 *     If file name is en_us.json, language code shoule be en_us.
 *
 *     This objects will be created at {@link com.github.freddyyj.localizationapi.Language}.
 *     Don't create this object manually, access for {@link com.github.freddyyj.localizationapi.Language}.
 * </p>
 * @author FreddyYJ_
 */
public class LanguageFile {
    private HashMap<String,String> stringList;
    private String languageCode;

    /**
     * Constructor with specific language code.
     * @param path custom path, should be {@link Core#getDefaultFolder()}.
     * @param languageCode specific language code, file should be exist
     * @throws IOException throws if file of language code not exist
     */
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

    /**
     * Get text of key text.
     * @param key key text
     * @return value text
     */
    public String getString(String key){
        if (key==null) return null;
        else return stringList.get(key);
    }

    /**
     * Get language code of this file.
     * @return language code
     */
    public String getLanguageCode(){
        return languageCode;
    }
}
