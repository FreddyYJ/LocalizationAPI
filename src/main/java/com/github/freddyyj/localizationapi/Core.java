package com.github.freddyyj.localizationapi;

import com.github.freddyyj.localizationapi.langfile.PlayerLanguageData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    public static final String PREFIX= ChatColor.AQUA+"["+ChatColor.GREEN+"LocalizationAPI"+ChatColor.AQUA+"] "+ChatColor.WHITE;
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
        if (args.length==0){
            sender.sendMessage(PREFIX+"Command list:");
            sender.sendMessage(PREFIX+ChatColor.YELLOW+"/lang"+ChatColor.WHITE+": Show command list.");
            if (sender.hasPermission("localization.get.other"))
                sender.sendMessage(PREFIX+ChatColor.YELLOW+"/lang get [player]"+ChatColor.WHITE+": Get [player] or your current language.");
            else if (sender.hasPermission("localization.get"))
                sender.sendMessage(PREFIX+ChatColor.YELLOW+"/lang get"+ChatColor.WHITE+": Get your current language.");
            if (sender.hasPermission("localization.set.other"))
                sender.sendMessage(PREFIX+ChatColor.YELLOW+"/lang set <language code> [player]"+ChatColor.WHITE+": Change [player] or your current language.");
            else if (sender.hasPermission("localization.set"))
                sender.sendMessage(PREFIX+ChatColor.YELLOW+"/lang set <language code>"+ChatColor.WHITE+": Change your current language.");
            if (sender.hasPermission("localization.test")) sender.sendMessage(PREFIX+ChatColor.YELLOW+"/lang test"+ChatColor.WHITE+": Test with default message(message.welcome).");
            if (sender.hasPermission("localization.list")) sender.sendMessage(PREFIX+ChatColor.YELLOW+"/lang list"+ChatColor.WHITE+": Show language list.");
            return true;
        }
        else if (args[0].equals("get") && sender instanceof Player && sender.hasPermission("localization.get")){ // /lang get [player]
            sender.sendMessage(PREFIX+"Current language: "+PlayerLocalization.fromPlayer((Player) sender).getLanguage().getLanguageCode());
            return true;
        }
        else if (args[0].equals("set") && args.length>=2 && sender instanceof Player && sender.hasPermission("localization.set")){ // /lang set <language code> [player]
            if (args.length==2){
                try {
                    PlayerLocalization.fromPlayer((Player) sender).setLanguage(Language.getLanguage(args[1]));
                    languageSavefile.setPlayerLanguageCode(((Player) sender).getUniqueId(),args[1]);

                    sender.sendMessage(PREFIX+"Language set to "+args[1]);
                } catch (IOException e) {
                    sender.sendMessage(PREFIX+"No language code found: "+args[1]);
                    return false;
                }
                return true;
            }
            return true;
        }
        else if (args[0].equals("test") && sender instanceof Player && sender.hasPermission("localization.test")){ // /lang test
            sender.sendMessage(PREFIX+PlayerLocalization.fromPlayer((Player) sender).getMessage("message.welcome"));
            return true;
        }
        else if (args[0].equals("list") && sender.hasPermission("localization.list")){ // /lang list
            sender.sendMessage(PREFIX+"Current language list: "+Arrays.toString(languageCodeList));
            return true;
        }
        else {
            sender.sendMessage(PREFIX + "Unknown Command! Command list:");
            sender.sendMessage(PREFIX + ChatColor.YELLOW + "/lang" + ChatColor.WHITE + ": Show command list.");
            sender.sendMessage(PREFIX + ChatColor.YELLOW + "/lang get [player]" + ChatColor.WHITE + ": Get [player] or your current language.");
            sender.sendMessage(PREFIX + ChatColor.YELLOW + "/lang set <language code> [player]" + ChatColor.WHITE + ": Change [player] or your current language.");
            sender.sendMessage(PREFIX + ChatColor.YELLOW + "/lang test" + ChatColor.WHITE + ": Test with default message(message.welcome).");
            sender.sendMessage(PREFIX + ChatColor.YELLOW + "/lang list" + ChatColor.WHITE + ": Show language list.");
            return true;
        }
    }
    public static PlayerLanguageData getLanguageData(){
        return languageSavefile;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        PlayerLocalization player=PlayerLocalization.fromPlayer(e.getPlayer());
        
        getLogger().info(player.toPlayer().getName()+" entered!");
    }
    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent e){
        PlayerLocalization.fromPlayer(e.getPlayer()).remove();

        getLogger().info(e.getPlayer().getName()+" leave server. Localization data removed.");
    }
}
