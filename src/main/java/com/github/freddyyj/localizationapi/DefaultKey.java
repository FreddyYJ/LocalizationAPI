package com.github.freddyyj.localizationapi;

/**
 * Keys that language file should have.
 * <p>
 *     All language file should have this keys.
 *     Values of this elements are JSON key for all language files.
 *
 *     But, editing language file may occur some errors, so don't access at them.
 * </p>
 * @author FreddyYJ_
 */
public enum DefaultKey {
    NAME("language.name"),
    REGION("language.region"),
    CODE("language.code");
    private final String value;
    DefaultKey(String value){
        this.value=value;
    }

    @Override
    public String toString() {
        return value;
    }
}
