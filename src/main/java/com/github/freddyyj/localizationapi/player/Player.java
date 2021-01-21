package com.github.freddyyj.localizationapi.player;

import com.github.freddyyj.localizationapi.Core;
import com.github.freddyyj.localizationapi.exceptions.LanguageFileNotFoundException;
import com.github.freddyyj.localizationapi.langfile.Language;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Player object that has one {@link org.bukkit.entity.Player}.
 * <p>
 *     To get Player Localization, use {@link Player#fromPlayer(org.bukkit.entity.Player)}.
 *
 *     To send or get translated text, use {@link Player#sendMessage(String)} or {@link Player#getMessage(String)}.
 * </p>
 * @author FreddyYJ_
 */
public class Player {
    private org.bukkit.entity.Player player;
    private static ArrayList<Player> playerList=new ArrayList<>();
    private Language language;

    /**
     * Constructor with {@link org.bukkit.entity.Player} and default language code(en_us)
     * @param player player
     */
    protected Player(org.bukkit.entity.Player player){
        this(player,"en_us");
    }

    /**
     * Constructor with {@link org.bukkit.entity.Player} and specific language code
     * @param player player
     * @param languageCode language code
     */
    protected Player(org.bukkit.entity.Player player, String languageCode){
        this.player=player;

        try {
            language= Language.getLanguage(languageCode);
        } catch (IOException e) {
            throw new LanguageFileNotFoundException("Language file ("+languageCode+".json) not found! ",e);
        }
    }

    /**
     * Get PlayerLocalization with specific {@link org.bukkit.entity.Player}.
     * <p>
     *     If object not exist, will create new object.
     * </p>
     * @param player player
     * @return new or existed object
     */
    public static Player fromPlayer(org.bukkit.entity.Player player){
        for (int i=0;i<playerList.size();i++){
            if (playerList.get(i).player.getName().equals(player.getName())) {
                return playerList.get(i);
            }
        }

        Player playerLocalization;
        if (Core.getLanguageData().hasPlayer(player.getUniqueId())){
            playerLocalization = new Player(player,Core.getLanguageData().getLanguageCode(player.getUniqueId()));
        }
        else{
            playerLocalization = new Player(player);
        }
        playerList.add(playerLocalization);
        return playerLocalization;
    }

    /**
     * Send translated message to this player.
     * @param message key text to translate and send
     */
    public void sendMessage(String message){
        String translated=language.getText(message);
        player.sendMessage(translated);
    }

    /**
     * Get translated message of this player.
     * @param message key text to translate
     * @return translated text
     */
    public String getMessage(String message){
        String translated=language.getText(message);
        return translated;
    }

    /**
     * Remove this player.
     */
    public void remove(){
        playerList.remove(this);
    }

    /**
     * Get {@link org.bukkit.entity.Player} if this PlayerLocalization.
     * @return player object
     */
    public org.bukkit.entity.Player toPlayer(){
        return player;
    }

    /**
     * Get language code of this player.
     * @return language code
     */
    public Language getLanguage(){
        return language;
    }

    /**
     * Change language of this player.
     * @param language language code
     */
    public void setLanguage(Language language){
        this.language=language;
        Core.getLanguageData().setPlayerLanguageCode(player.getUniqueId(),language.getLanguageCode());
    }

}
