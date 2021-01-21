package com.github.freddyyj.localizationapi.player;

import com.github.freddyyj.localizationapi.Core;
import com.github.freddyyj.localizationapi.langfile.Language;
import com.github.freddyyj.localizationapi.langfile.LanguageFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * class for managing player language data file
 * <p>
 *     This class is for managing player language data file, named at 'player.yml'.
 *     This class should be singleton.
 *
 *     This file is YAML file with Maps, player UUID is key, and player language code is value.
 *     This file will created automatically if not exist.
 *
 *     Editing this file manually maybe cause serious errors.
 * </p>
 * @author FreddyYJ_
 */
public class PlayerLanguageData{
    private YamlConfiguration config;
    private File saveFile;
    private static PlayerLanguageData instance=null;
    private HashMap<UUID,String> playerLanguageList;

    /**
     * Get instance of this class.
     * @return object of this class
     */
    public static PlayerLanguageData getInstance(){
        if (instance==null) instance=new PlayerLanguageData();
        return instance;
    }

    /**
     * Default constructor
     */
    protected PlayerLanguageData(){
        playerLanguageList=new HashMap<>();
        load();
    }

    /**
     * Reload player language data.
     * <p>
     *     This method only load from file.
     *     It doesn't reload {@link LanguageFile}, {@link Language}, {@link Player}, or other.
     * </p>
     */
    public void load(){
        saveFile=new File(Core.getDefaultFolder().getPath()+"/player.yml");
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

    /**
     * Get language code with specific player.
     * @param playerUUID {@link UUID} of player
     * @return language code
     */
    public String getLanguageCode(UUID playerUUID){
        return playerLanguageList.get(playerUUID);
    }

    /**
     * Set language code at specific player.
     * @param player {@link UUID} of player
     * @param languageCode language code
     */
    public void setPlayerLanguageCode(UUID player,String languageCode){
        playerLanguageList.put(player,languageCode);
    }

    /**
     * Check if save of specific player exist.
     * @param player {@link UUID} of player
     * @return true if exist, false if not
     */
    public boolean hasPlayer(UUID player){
        return playerLanguageList.containsKey(player);
    }

    /**
     * Remove player from data file.
     * @param player {@link UUID} of player
     */
    public void removePlayer(UUID player){
        playerLanguageList.remove(player);
    }

    /**
     * Save current states.
     */
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
