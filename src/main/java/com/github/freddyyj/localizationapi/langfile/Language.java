package com.github.freddyyj.localizationapi.langfile;

import com.github.freddyyj.localizationapi.Core;
import com.github.freddyyj.localizationapi.exceptions.LanguageFileNotFoundException;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Language class for multi language.
 * <p>
 *     Add (language code).json at LocalizationAPI/lang.
 *     en_us.json should be required.
 *
 *     This object should be mapped with one language. For example,
 *
 *     [en_us.json, en_uk.json] will create 2 objects of this class.
 * </p>
 * @author FreddyYJ_
 */
public class Language {
    private static HashMap<String, Language> languageList=new HashMap<>();
    private LanguageFile file;
    private String code;
    private String name;
    private String region;

    /**
     * Get name of this language.
     * @return language name
     */
    public String getName() {
        return name;
    }

    /**
     * Get region of this language.
     * @return language region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Constructor with specific language code
     * @param languageCode language code(ex. en_us)
     * @throws IOException Throws when error at reading language file
     */
    protected Language(String languageCode) throws IOException {
        file=new LanguageFile(Core.getDefaultFolder().getPath()+"/lang",languageCode);
        code=languageCode;
        name=file.getString(DefaultKey.NAME.toString());
        if (name==null) throw new NullPointerException("No language name specified! Add language.name in language file.");

        region=file.getString(DefaultKey.REGION.toString());
        if (region==null) throw new NullPointerException("No language region specified! Add language.region in language file.");

        languageList.put(languageCode,this);
    }

    /**
     * Get language with specific language code. 1 language should have only 1 object.
     * @param code language code(ex. en_us)
     * @return {@link Language} object
     * @throws IOException Throws when error at reading language file, if required
     */
    public static Language getLanguage(String code) throws IOException {
        if (languageList.containsKey(code)) return languageList.get(code);
        else{
            Language language=new Language(code);
            languageList.put(code,language);
            return language;
        }
    }

    /**
     * Reload all languages. Required after language file changed.
     * @throws IOException Throws when error at reading language file
     */
    public static void reloadAll() throws IOException {
        languageList.clear();

        File languageFolder=new File(Core.getDefaultFolder().getPath()+"/lang");
        File[] fileList=languageFolder.listFiles();
        if (fileList.length==0){
            throw new LanguageFileNotFoundException("No language file found! Add en_us.json for default.");
        }
        for (int i=0;i<fileList.length;i++){
            String fileName=fileList[i].getName().split(".json")[0];
            LanguageFile file=new LanguageFile(languageFolder.getPath(),fileName);

            languageList.put(fileName,new Language(fileName));
        }
    }

    /**
     * Get value text of given key text.
     * <p>
     *     If value not exist, returns value in en_us.
     *     If value not exist in both this language and en_us, returns key text.
     * </p>
     * @param key key text
     * @return value text
     */
    public String getText(String key){
        String translated=file.getString(key);
        if (translated==null) {
            translated=languageList.get("en_us").getFile().getString(key);
            if (translated==null) translated=key;
        }
        return translated;
    }

    /**
     * Find {@link Language} with their local name.
     * <p>
     *     Exact full name required.
     *     For example, "English (US)" for American English.
     * </p>
     * @param languageName full local name
     * @return {@link Language} object
     */
    @Nullable
    public static Language findByName(String languageName){
        String[] keys=languageList.keySet().toArray(new String[0]);
        for (int i=0;i< languageList.size();i++){
            if (languageList.get(keys[i]).getName().equals(languageName))
                return languageList.get(keys[i]);
        }
        return null;
    }

    /**
     * Get language code
     * @return language code
     */
    public String getLanguageCode(){
        return code;
    }

    /**
     * Get {@link Set} of all language codes
     * @return language code set
     */
    public static Set<String> getLanguageCodes(){
        return languageList.keySet();
    }

    /**
     * Check language object exist
     * @param languageCode language code
     * @return true if exist
     */
    public static boolean hasLanguage(String languageCode){
        return languageList.containsKey(languageCode);
    }

    /**
     * Get language file.
     * @return language file
     */
    protected LanguageFile getFile(){
        return file;
    }

    /**
     * Get full language list that exist.
     * @return {@link Map} of exist language list
     */
    static Map<String, Language> getLanguageList(){
        return languageList;
    }

    /**
     * Reload current language translation.
     */
    public void reload(){
        file.reload();
    }

    /**
     * Add new translation.
     * @param key key string
     * @param value value string
     */
    public void addText(String key,String value){
        file.add(key, value);
    }

    /**
     * Edit translation.
     * @param key key string
     * @param value value string
     */
    public void editText(String key,String value){
        file.edit(key, value);
    }

    /**
     * Remove translation.
     * @param key key string
     */
    public void removeText(String key){
        file.remove(key);
    }

    /**
     * Save current states.
     */
    public void save(){
        file.save();
    }

    /**
     * Create new language.
     * <p>
     *     If already exist, return exist one.
     *
     *     languageCode should be exist in {@link com.github.freddyyj.localizationapi.LanguageList}.
     *     Use {@link com.github.freddyyj.localizationapi.LanguageList#hasLanguageInfo(String)} to check some language code exist.
     *
     *     If language code not available, returns null.
     * </p>
     * @param languageCode language code that available.
     * @return created {@link Language} if not exist and available
     */
    @Nullable
    public static Language createNewLanguage(String languageCode){
        if (!Language.hasLanguage(languageCode) && Core.getAvailableLanguageList().hasLanguageInfo(languageCode)){
            try {
                return new Language(languageCode);
            } catch (IOException e) {
                throw new LanguageFileNotFoundException("Language code not found: "+languageCode);
            }
        }
        else if (Language.hasLanguage(languageCode)){
            try {
                return Language.getLanguage(languageCode);
            } catch (IOException e) {
                throw new LanguageFileNotFoundException("Language code not found: "+languageCode);
            }
        }
        else{
            return null;
        }
    }
}
