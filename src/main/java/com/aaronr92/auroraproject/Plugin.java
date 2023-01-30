package com.aaronr92.auroraproject;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {
    private static Plugin instance;

    @Override
    public void onEnable() {
        this.instance = this;

        FileConfiguration config = this.getConfig();
        this.saveDefaultConfig();
        config.options().copyDefaults();
    }

    @Override
    public void onDisable() {
    }

    public static Plugin getPlugin() {
        return instance;
    }
}