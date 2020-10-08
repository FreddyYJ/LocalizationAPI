package com.github.freddyyj.localizationapi.langfile;

import com.github.freddyyj.localizationapi.Core;
import com.github.freddyyj.localizationapi.exceptions.LanguageFileNotFoundException;
import org.jetbrains.annotations.Nullable;

import javax.json.*;
import java.io.*;
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
 *     This objects will be created at {@link Language}.
 *     Don't create this object manually, access for {@link Language}.
 *
 *     Adding, editing, or removing texts with {@link LanguageFile} is not recommended.
 *     Use {@link Language} methods instead.
 * </p>
 * @author FreddyYJ_
 */
public class LanguageFile {
    private HashMap<String,String> stringList;
    private String path;
    private String languageCode;

    /**
     * Constructor with specific language code.
     * @param path custom path, should be {@link Core#getDefaultFolder()}.
     * @param languageCode specific language code, file should be exist
     * @throws IOException throws if file of language code not exist
     */
    LanguageFile(String path,String languageCode) throws IOException {
        this.path=path;
        stringList=new HashMap<>();

        File file=new File(path + "/" + languageCode + ".json");
        if (!file.exists()) {
            file.createNewFile();
            JsonWriter writer;
            writer=Json.createWriter(new FileWriter(path + "/" + languageCode + ".json"));

            JsonObjectBuilder builder=Json.createObjectBuilder();
            builder.add(DefaultKey.NAME.toString(),Core.getAvailableLanguageList().getLanguageInfo(languageCode).getLocalName());
            builder.add(DefaultKey.REGION.toString(), Core.getAvailableLanguageList().getLanguageInfo(languageCode).getRegion());
            builder.add(DefaultKey.CODE.toString(),Core.getAvailableLanguageList().getLanguageInfo(languageCode).getCode());
            writer.writeObject(builder.build());
            writer.close();
        }

        JsonReader reader= Json.createReader(new FileReader(path + "/" + languageCode + ".json"));
        JsonObject object=reader.readObject();
        String[] keys=object.keySet().toArray(new String[0]);

        for (int i=0;i< keys.length;i++){
            stringList.put(keys[i], object.getString(keys[i]));
        }
        reader.close();

        this.languageCode=languageCode;
    }

    /**
     * Get text of key text.
     * @param key key text
     * @return value text
     */
    @Nullable
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

    /**
     * Add new translation.
     * <p>
     *     Same works with {@link LanguageFile#edit(String, String)}.
     *
     *     {@link Language#addText(String, String)} is more recommended.
     * </p>
     * @param key key string
     * @param value value string
     */
    public void add(String key,String value){
        stringList.put(key, value);
    }

    /**
     * Edit translation.
     * <p>
     *     Same works with {@link LanguageFile#add(String, String)}.
     *
     *     {@link Language#editText(String, String)} is more recommended.
     * </p>
     * @param key key string
     * @param value value string
     */
    public void edit(String key,String value){
        stringList.put(key,value);
    }

    /**
     * Remove translation.
     * <p>
     *     Do nothing if not exist.
     *
     *     {@link Language#removeText(String)} is more recommended.
     * </p>
     * @param key key string
     */
    public void remove(String key){
        stringList.remove(key);
    }

    /**
     * Reload file.
     */
    public void reload(){
        stringList.clear();
        JsonReader reader= null;
        try {
            reader = Json.createReader(new FileReader(path + "/" + languageCode + ".json"));
        } catch (FileNotFoundException e) {
            throw new LanguageFileNotFoundException("No language file detected!",e);
        }
        JsonObject object=reader.readObject();
        String[] keys=object.keySet().toArray(new String[0]);

        for (int i=0;i< keys.length;i++){
            stringList.put(keys[i], object.getString(keys[i]));
        }
        reader.close();

        this.languageCode=stringList.get(DefaultKey.CODE.toString());
    }

    /**
     * Save current states.
     */
    public void save(){
        JsonWriter writer;
        try {
            writer=Json.createWriter(new FileWriter(path + "/" + languageCode + ".json"));
        } catch (IOException e) {
            throw new LanguageFileNotFoundException("No language file detected!",e);
        }

        JsonObjectBuilder builder=Json.createObjectBuilder();
        String[] keys=stringList.keySet().toArray(new String[0]);

        for (int i=0;i< keys.length;i++){
            builder.add(keys[i], stringList.get(keys[i]));
        }

        writer.writeObject(builder.build());
        writer.close();
    }

}
