package com.github.freddyyj.localizationapi;

import com.github.freddyyj.localizationapi.langfile.Language;
import com.github.freddyyj.localizationapi.player.Player;
import com.github.freddyyj.localizationapi.player.PlayerLanguageData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Main class for LocalizationAPI. Extends {@link JavaPlugin}. Don't create this object manually.
 * <p>
 *     Implements {@link Listener} to listen player join and quit event.
 *
 *     Don't create this class manually. Use {@link org.bukkit.plugin.PluginManager#getPlugin(String)} to get this object.
 * </p>
 * @author FreddyYJ_
 */
public class Core extends JavaPlugin implements Listener {
    /**
     * Default prefix for LocalizationAPI.
     */
    public static final String PREFIX= ChatColor.AQUA+"["+ChatColor.GREEN+"LocalizationAPI"+ChatColor.AQUA+"] "+ChatColor.WHITE;
    private static File dataFolder;
    private String[] languageCodeList;
    private static PlayerLanguageData languageSavefile;

    /**
     * Get language list that available.
     * @return available language list
     */
    public static LanguageList getAvailableLanguageList() {
        return availableLanguageList;
    }

    private static LanguageList availableLanguageList;

    /**
     * Override {@link JavaPlugin#onEnable()}. Don't call this method.
     */
    @Override
    public void onEnable() {
        getLogger().info("LocalizationAPI v0.2.0 loading...");

        dataFolder=this.getDataFolder();
        Bukkit.getPluginManager().registerEvents(this,this);

        try {
            Language.reloadAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        languageCodeList= Language.getLanguageCodes().toArray(new String[0]);
        languageSavefile=PlayerLanguageData.getInstance();
        try {
            availableLanguageList=LanguageList.getInstance(this);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (!Language.hasLanguage("en_us")){
            getLogger().info("No default language file(en_us.json) detected. Creating...");
            Language.createNewLanguage("en_us");
            languageCodeList= Language.getLanguageCodes().toArray(new String[0]);
        }

        getLogger().info("LocalizationAPI v0.2.0 loaded!");
        getLogger().info("Loaded languages: "+ Arrays.toString(languageCodeList));
    }

    /**
     * Override {@link JavaPlugin#onDisable()}. Don't call this method.
     */
    @Override
    public void onDisable() {
        languageSavefile.save();

        getLogger().info("LocalizationAPI v0.2.0 disabled.");
    }

    /**
     * Override {@link JavaPlugin#onCommand(CommandSender, Command, String, String[])}. Don't call this method.
     * @param sender command sender
     * @param command command class
     * @param label string of command arguments
     * @param args string array of command arguments
     * @return always be true
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==0){
            printCommandList(sender);
            return true;
        }
        else if (args[0].equals("get") && sender instanceof org.bukkit.entity.Player && sender.hasPermission("localization.get")){ // /lang get [player]
            if (sender.hasPermission("localization.get.other") && args.length==2){
                org.bukkit.entity.Player targetPlayer=Bukkit.getPlayer(args[1]);
                if (targetPlayer==null) throw new NullPointerException("Not exist player: "+args[1]);
                else sender.sendMessage(PREFIX+"Current language of "+targetPlayer.getName()+": "+ Player.fromPlayer(targetPlayer).getLanguage().getName());
            }
            else if (args.length==1) sender.sendMessage(PREFIX+"Current language: "+ Player.fromPlayer((org.bukkit.entity.Player) sender).getLanguage().getName());
            else printCommandError(sender);
            return true;
        }
        else if (args[0].equals("set") && args.length>=2 && sender instanceof org.bukkit.entity.Player && sender.hasPermission("localization.set")){ // /lang set <language code> [player]
            if (args.length==3 && sender.hasPermission("localization.set.other")){
                org.bukkit.entity.Player targetPlayer=Bukkit.getPlayer(args[2]);
                if (targetPlayer==null) throw new NullPointerException("Not exist player: "+args[2]);
                else{
                    try {
                        Player.fromPlayer(targetPlayer).setLanguage(Language.getLanguage(args[1]));
                    } catch (IOException e) {
                        sender.sendMessage(PREFIX+"Language not found: "+args[1]);
                        return true;
                    }

                    sender.sendMessage(PREFIX+"Language of "+targetPlayer.getName()+" set to "+args[1]);
                    return true;
                }
            }
            else if (args.length==2){
                try {
                    Player.fromPlayer((org.bukkit.entity.Player) sender).setLanguage(Language.getLanguage(args[1]));
                } catch (IOException e) {
                    sender.sendMessage(PREFIX+"Language not found: "+args[1]);
                    return true;
                }

                sender.sendMessage(PREFIX+"Language set to "+args[1]);
                return true;
            }
            else printCommandError(sender);
            return true;
        }
        else if (args.length>=2 && args[0].equals("create") && sender.hasPermission("localization.create")){ // /lang create <code>
            if (Language.createNewLanguage(args[1])==null){
                sender.sendMessage(PREFIX+"No language code found! Use /lang available to check all available languages.");
                return true;
            }
            languageCodeList= Language.getLanguageCodes().toArray(new String[0]);
            sender.sendMessage(PREFIX+"New language created: "+args[1]);
            return true;
        }
        else if (args[0].equals("test") && sender instanceof org.bukkit.entity.Player && sender.hasPermission("localization.test")){ // /lang test
            sender.sendMessage(PREFIX+ Player.fromPlayer((org.bukkit.entity.Player) sender).getMessage("message.welcome"));
            return true;
        }
        else if (args[0].equals("list") && sender.hasPermission("localization.list")){ // /lang list
            sender.sendMessage(PREFIX+"Current language list:");
            for (int i=0;i<languageCodeList.length;i++){
                try {
                    sender.sendMessage(PREFIX+ Language.getLanguage(languageCodeList[i]).getName()+": "+languageCodeList[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                    return true;
                }
            }
            return true;
        }
        else if (args[0].equals("available") && sender.hasPermission("localization.available")){ // /lang available
            HashMap<String,LanguageInfo> info=availableLanguageList.getLanguageInfos();
            ArrayList<String> codes=new ArrayList<>();
            for (int i=0;i<info.keySet().size();i++){
                codes.add(info.keySet().toArray(new String[0])[i]);
            }
            sender.sendMessage(PREFIX+"Available language list for create: "+codes.toString());
            return true;
        }
        else {
            printCommandError(sender);
            return true;
        }
    }

    /**
     * Get {@link PlayerLanguageData} for save player data.
     * <p>
     *     {@link PlayerLanguageData#getInstance()} works same.
     * </p>
     * @return player language data
     */
    public static PlayerLanguageData getLanguageData(){
        return languageSavefile;
    }

    /**
     * Get data folder of this plugin.
     * @return data folder
     */
    public static File getDefaultFolder(){
        return dataFolder;
    }
    private void printCommandError(CommandSender sender){
        sender.sendMessage(PREFIX + "Unknown Command! Command list:");
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
        if (sender.hasPermission("localization.available")) sender.sendMessage(PREFIX+ChatColor.YELLOW+"/lang available"+ChatColor.WHITE+": Show available list that can created new.");
        if (sender.hasPermission("localization.create")) sender.sendMessage(PREFIX+ChatColor.YELLOW+"/lang create <language code>"+ChatColor.WHITE+": Create new language file with default values.");
    }
    private void printCommandList(CommandSender sender){
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
        if (sender.hasPermission("localization.available")) sender.sendMessage(PREFIX+ChatColor.YELLOW+"/lang available"+ChatColor.WHITE+": Show available list that can created new.");
        if (sender.hasPermission("localization.create")) sender.sendMessage(PREFIX+ChatColor.YELLOW+"/lang create <language code>"+ChatColor.WHITE+": Create new language file with default values.");
    }

    /**
     * player join event listener
     * <p>
     *     Don't call this method manually.
     * </p>
     * @param e {@link PlayerJoinEvent} object
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player= Player.fromPlayer(e.getPlayer());
        
        getLogger().info(player.toPlayer().getName()+" entered!");
    }

    /**
     * player quit event listener
     * <p>
     *     Don't call this method manually.
     * </p>
     * @param e {@link PlayerQuitEvent} object
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent e){
        Player.fromPlayer(e.getPlayer()).remove();

        getLogger().info(e.getPlayer().getName()+" leave server. Localization data removed.");
    }
}
