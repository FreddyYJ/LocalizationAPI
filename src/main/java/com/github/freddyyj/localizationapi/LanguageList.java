package com.github.freddyyj.localizationapi;

public enum LanguageList {
    af_za("Afrikaans"),
    ar_sa("اللغة العربية"),
    ast_es("Asturianu"),
    az_az("Azərbaycanca"),
    be_by("Беларуская");
    private String value;
    LanguageList(String value){
        this.value=value;
    }

    @Override
    public String toString() {
        return value;
    }
}
