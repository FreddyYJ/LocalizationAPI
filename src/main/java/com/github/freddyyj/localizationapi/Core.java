package com.github.freddyyj.localizationapi;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Core {
    private HashMap<String,Language> languageList;
    public Core(){
        languageList=new HashMap<>();
        File file=new File("./lang");
        for (int i=0;i<file.listFiles().length;i++){
            if (file.listFiles()[i].getName().endsWith(".json")){
                try {
                    languageList.put(file.listFiles()[i].getName().split(".json")[0],new Language(file.getPath(),file.listFiles()[i].getName().split(".json")[0]));
                } catch (FileNotFoundException e) {
                    System.out.println("No language found!");
                }
            }
        }
    }
    public Language getLanguage(String languageCode){
        if (languageList.get(languageCode)!=null) return languageList.get(languageCode);
        else return languageList.get("en-us");
    }
}
