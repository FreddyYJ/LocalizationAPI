package com.github.freddyyj.localizationapi;

import com.github.freddyyj.localizationapi.langfile.PlayerLanguageData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Core extends JavaPlugin implements Listener {
    public static File dataFolder;
    private String[] languageCodeList;
    private static PlayerLanguageData languageSavefile;
    @Override
    public void onEnable() {
        getLogger().info("LocalizationAPI v0.0.2 loading...");

        dataFolder=this.getDataFolder();
        Bukkit.getPluginManager().registerEvents(this,this);

        try {
            Language.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
        languageCodeList= Language.getLanguageCodes().toArray(new String[0]);
        languageSavefile=PlayerLanguageData.getInstance();

        getLogger().info("LocalizationAPI v0.0.2 loaded!");
        getLogger().info("Loaded languages: "+ Arrays.toString(languageCodeList));
    }

    @Override
    public void onDisable() {
        languageSavefile.save();

        getLogger().info("LocalizationAPI v0.0.2 disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equals("get") && sender instanceof Player){ // /lang get [player]
            sender.sendMessage("Current language: "+PlayerLocalization.fromPlayer((Player) sender).getLanguage().getLanguageCode());
            return true;
        }
        else if (args[0].equals("set") && args.length>=2 && sender instanceof Player){ // /lang set <language code> [player]
            if (args.length==2){
                try {
                    PlayerLocalization.fromPlayer((Player) sender).setLanguage(Language.getLanguage(args[1]));
                    languageSavefile.setPlayerLanguageCode(((Player) sender).getUniqueId(),args[1]);
                } catch (IOException e) {
                    sender.sendMessage("No language code found: "+args[1]);
                    return false;
                }
                return true;
            }
        }
        else if (args[0].equals("test") && sender instanceof Player){ // /lang test
            PlayerLocalization.fromPlayer((Player) sender).sendMessage("message.welcome");
            return true;
        }
        else if (args[0].equals("list")){ // /lang list
            sender.sendMessage("Current language list: "+Arrays.toString(languageCodeList));
            return true;
        }
        return false;
    }
    public static PlayerLanguageData getLanguageData(){
        return languageSavefile;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        PlayerLocalization player=PlayerLocalization.fromPlayer(e.getPlayer());
        e.getPlayer().sendMessage("message.welcome");
        player.sendMessage("message.welcome");
        player.sendMessage("message.welcome1");

        getLogger().info(player.toPlayer().getName()+" entered!");

    }
    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent e){
        languageSavefile.removePlayer(e.getPlayer().getUniqueId());
        PlayerLocalization.fromPlayer(e.getPlayer()).remove();

        getLogger().info(e.getPlayer().getName()+" leave server. Localization data removed.");
    }
}
