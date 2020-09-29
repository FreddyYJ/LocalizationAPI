package com.github.freddyyj.localizationapi;

import com.github.freddyyj.localizationapi.exceptions.LanguageFileNotFoundException;
import com.github.freddyyj.localizationapi.langfile.Language;
import com.github.freddyyj.localizationapi.langfile.LanguageFile;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Player object that has one {@link Player}.
 * <p>
 *     To get Player Localization, use {@link PlayerLocalization#fromPlayer(Player)}.
 *
 *     To send or get translated text, use {@link PlayerLocalization#sendMessage(String)} or {@link PlayerLocalization#getMessage(String)}.
 * </p>
 * @author FreddyYJ_
 */
public class PlayerLocalization {
    private Player player;
    private static ArrayList<PlayerLocalization> playerList=new ArrayList<>();
    private Language language;

    /**
     * Constructor with {@link Player} and default language code(en_us)
     * @param player player
     */
    protected PlayerLocalization(Player player){
        this(player,"en_us");
    }

    /**
     * Constructor with {@link Player} and specific language code
     * @param player player
     * @param languageCode language code
     */
    protected PlayerLocalization(Player player,String languageCode){
        this.player=player;

        try {
            language= Language.getLanguage(languageCode);
        } catch (IOException e) {
            throw new LanguageFileNotFoundException("Language file ("+languageCode+".json) not found! ",e);
        }
    }

    /**
     * Get PlayerLocalization with specific {@link Player}.
     * <p>
     *     If object not exist, will create new object.
     * </p>
     * @param player player
     * @return new or existed object
     */
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
     * Get {@link Player} if this PlayerLocalization.
     * @return player object
     */
    public Player toPlayer(){
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
