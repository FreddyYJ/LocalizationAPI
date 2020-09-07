package com.github.freddyyj.localizationapi.langfile;

import com.github.freddyyj.localizationapi.Core;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class PlayerLanguageData{
    private YamlConfiguration config;
    private static PlayerLanguageData instance=null;
    private HashMap<UUID,String> playerLanguageList;
    public static PlayerLanguageData getInstance(){
        if (instance==null) instance=new PlayerLanguageData();
        return instance;
    }
    protected PlayerLanguageData(){
        playerLanguageList=new HashMap<>();
        load();
    }
    public void load(){
        config=YamlConfiguration.loadConfiguration(new File(Core.dataFolder.getPath()+"/player.yml"));
        playerLanguageList.clear();

        Set<String> players=config.getKeys(false);
        players.forEach(key->{
            playerLanguageList.put(UUID.fromString(key),config.getString(key));
        });
    }
    public String getLanguageCode(UUID playerUUID){
        return playerLanguageList.get(playerUUID);
    }
    public void addPlayerLanguageCode(UUID player,String languageCode){
        playerLanguageList.put(player,languageCode);
    }

}
