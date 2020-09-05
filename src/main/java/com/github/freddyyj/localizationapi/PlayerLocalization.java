package com.github.freddyyj.localizationapi;

import com.github.freddyyj.localizationapi.exceptions.DefaultLanguageFileNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

public class PlayerLocalization {
    private Player player;
    private static ArrayList<PlayerLocalization> playerList=new ArrayList<>();
    private Language language;
    protected PlayerLocalization(Player player) throws FileNotFoundException {
        this.player=player;
        try {
            language=Language.getLanguage(LanguageCode.valueOf(player.getLocale()));
        } catch (FileNotFoundException e) {
            language=Language.getLanguage(LanguageCode.en_us);
        }
    }
    public static PlayerLocalization fromPlayer(Player player){
        for (int i=0;i<playerList.size();i++){
            if (playerList.get(i).player.getUniqueId().equals(player.getUniqueId())) {
                try {
                    playerList.get(i).language=Language.getLanguage(LanguageCode.valueOf(player.getLocale()));
                } catch (FileNotFoundException e) {
                    try {
                        playerList.get(i).language=Language.getLanguage(LanguageCode.en_us);
                    } catch (FileNotFoundException fileNotFoundException) {
                        throw new DefaultLanguageFileNotFoundException("Default language file (en_us.json) not found! ",fileNotFoundException);
                    }
                }
            }
        }

        PlayerLocalization playerLocalization= null;
        try {
            playerLocalization = new PlayerLocalization(player);
        } catch (FileNotFoundException e) {
            throw new DefaultLanguageFileNotFoundException("Default language file (en_us.json) not found! ",e);
        }
        playerList.add(playerLocalization);
        return playerLocalization;
    }

    public void sendMessage(String message){
        String translated=language.getText(message);
        player.sendMessage(translated);
    }
    public Player toPlayer(){
        return player;
    }
    public Language getLanguage(){
        return language;
    }

}
