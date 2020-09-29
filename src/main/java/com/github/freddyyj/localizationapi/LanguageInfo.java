package com.github.freddyyj.localizationapi;

import com.github.freddyyj.localizationapi.langfile.Language;
import com.github.freddyyj.localizationapi.langfile.LanguageFile;

/**
 * Information of languages.
 * <p>
 *     This class is for language information.
 *     Includes local name, english name, region, and code.
 *
 *     Do not create this object manually.
 *     Use {@link Language} getter methods or {@link Core#getAvailableLanguageList()} instead.
 * </p>
 * @author FreddyYJ_
 */
class LanguageInfo {
    private String localName;
    private String englishName;
    private String region;
    private String code;

    /**
     * Get name for local.
     * <p>
     *     For example, en_us return "English (US)"
     * </p>
     * @return local name
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * Get name for english.
     * <p>
     *     For example, en_us return "American English"
     * </p>
     * @return english name
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * Get region.
     * <p>
     *     For example, en_us return "USA"
     * </p>
     * @return region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Get language code.
     * @return language code
     */
    public String getCode() {
        return code;
    }

    /**
     * Constructor with all information.
     * <p>
     *     Don't create this object manually.
     *     Use {@link Language} getter methods to get information of exist language.
     *     Or, Use {@link Core#getAvailableLanguageList()} to get all available language.
     * </p>
     */
    public LanguageInfo(String localName, String englishName, String region, String code){
        this.localName=localName;
        this.englishName=englishName;
        this.region=region;
        this.code=code;
    }
}
