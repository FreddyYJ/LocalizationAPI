package com.github.freddyyj.localizationapi;

import com.github.freddyyj.localizationapi.langfile.PlayerLanguageData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==0 && sender instanceof Player){
            sender.sendMessage("Current language: "+PlayerLocalization.fromPlayer((Player) sender).getLanguage().getLanguageCode());
            return true;
        }
        else if (args[0].equals("set") && args.length>=2 && sender instanceof Player){ // /lang set <language code> [player]
            if (args.length==2){
                try {
                    PlayerLocalization.fromPlayer((Player) sender).setLanguage(Language.getLanguage(args[1]));
                } catch (IOException e) {
                    sender.sendMessage("No language code found: "+args[1]);
                    return false;
                }
                return true;
            }
        }
        else if (args[0].equals("test") && sender instanceof Player){
            PlayerLocalization.fromPlayer((Player) sender).sendMessage("message.welcome");
            return true;
        }
        else if (args[0].equals("list")){
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
        player.sendMessage("message.welcome");
        e.getPlayer().sendMessage("Current language: "+e.getPlayer().getLocale());
    }
}
