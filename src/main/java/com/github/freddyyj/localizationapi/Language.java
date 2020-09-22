package com.github.freddyyj.localizationapi;

import com.github.freddyyj.localizationapi.exceptions.LanguageFileNotFoundException;
import com.github.freddyyj.localizationapi.langfile.LanguageFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
    private static HashMap<String,Language> languageList=new HashMap<>();
    private LanguageFile file;
    private String code;

    /**
     * Constructor with specific language code
     * @param languageCode language code(ex. en_us)
     * @throws IOException Throws when error at reading language file
     */
    protected Language(String languageCode) throws IOException {
        file=new LanguageFile(Core.getDefaultFolder().getPath()+"/lang",languageCode);
        code=languageCode;
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
    public static void reload() throws IOException {
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
}
