package com.github.freddyyj.localizationapi;

class LanguageInfo {
    private String localName;
    private String englishName;
    private String region;
    private String code;

    public String getLocalName() {
        return localName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getRegion() {
        return region;
    }

    public String getCode() {
        return code;
    }

    public LanguageInfo(String localName, String englishName, String region, String code){
        this.localName=localName;
        this.englishName=englishName;
        this.region=region;
        this.code=code;
    }
}
