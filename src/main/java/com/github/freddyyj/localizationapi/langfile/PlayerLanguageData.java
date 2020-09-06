package com.github.freddyyj.localizationapi.langfile;

import com.github.freddyyj.localizationapi.Core;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class PlayerLanguageData{
    private YamlConfiguration config;
    private static PlayerLanguageData load(){
        
    }
    protected PlayerLanguageData(String path){
        config=YamlConfiguration.loadConfiguration(new File(path+"/player.yml"));
    }
}
