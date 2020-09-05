package com.github.freddyyj.localizationapi;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Core extends JavaPlugin implements Listener {
    static File dataFolder;
    @Override
    public void onEnable() {
        getLogger().info("LocalizationAPI v0.0.2 loading...");

        dataFolder=this.getDataFolder();
        Bukkit.getPluginManager().registerEvents(this,this);

        getLogger().info("LocalizationAPI v0.0.2 loaded!");
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        PlayerLocalization player=PlayerLocalization.fromPlayer(e.getPlayer());
        player.sendMessage("message.welcome");
        e.getPlayer().sendMessage("Current language: "+e.getPlayer().getLocale());
    }
}
