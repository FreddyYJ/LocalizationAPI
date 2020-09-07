package com.github.freddyyj.localizationapi.langfile;

import com.github.freddyyj.localizationapi.Core;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class PlayerLanguageData{
    private YamlConfiguration config;
    private File saveFile;
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
        saveFile=new File(Core.dataFolder.getPath()+"/player.yml");
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config=YamlConfiguration.loadConfiguration(saveFile);
        playerLanguageList.clear();

        Set<String> players=config.getKeys(false);
        players.forEach(key->{
            playerLanguageList.put(UUID.fromString(key),config.getString(key));
        });
    }
    public String getLanguageCode(UUID playerUUID){
        return playerLanguageList.get(playerUUID);
    }
    public void setPlayerLanguageCode(UUID player,String languageCode){
        playerLanguageList.put(player,languageCode);
    }
    public boolean hasPlayer(UUID player){
        return playerLanguageList.containsKey(player);
    }
    public void save() {
        Set<UUID> players=playerLanguageList.keySet();

        players.forEach(key->{
            config.set(key.toString(),playerLanguageList.get(key));
        });

        try {
            config.save(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
