package com.github.freddyyj.localizationapi;

import com.github.freddyyj.localizationapi.exceptions.DefaultLanguageFileNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class PlayerLocalization {
    private Player player;
    private static ArrayList<PlayerLocalization> playerList=new ArrayList<>();
    private Language language;
    protected PlayerLocalization(Player player){
        this(player,"en_us");
    }
    protected PlayerLocalization(Player player,String languageCode){
        this.player=player;

        try {
            language=Language.getLanguage(languageCode);
        } catch (IOException e) {
            throw new DefaultLanguageFileNotFoundException("Language file ("+languageCode+".json) not found! ",e);
        }
    }
    public static PlayerLocalization fromPlayer(Player player){
        for (int i=0;i<playerList.size();i++){
            if (playerList.get(i).player.getName().equals(player.getName())) {
                return playerList.get(i);
            }
        }

        PlayerLocalization playerLocalization;
        if (Core.getLanguageData().hasPlayer(player.getUniqueId())){
            playerLocalization = new PlayerLocalization(player,Core.getLanguageData().getLanguageCode(player.getUniqueId()));
        }
        else{
            playerLocalization = new PlayerLocalization(player);
        }
        playerList.add(playerLocalization);
        return playerLocalization;
    }

    public void sendMessage(String message){
        String translated=language.getText(message);
        player.sendMessage(translated);
    }
    public String getMessage(String message){
        String translated=language.getText(message);
        return translated;
    }
    public void remove(){
        playerList.remove(this);
    }
    public Player toPlayer(){
        return player;
    }
    public Language getLanguage(){
        return language;
    }
    public void setLanguage(Language language){
        this.language=language;
    }

}
