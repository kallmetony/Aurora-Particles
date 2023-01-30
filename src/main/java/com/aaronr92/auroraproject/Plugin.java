package com.aaronr92.auroraproject;

import com.aaronr92.auroraproject.handler.ParticleLoader;
import com.aaronr92.auroraproject.listener.ParticleCommand;
import com.aaronr92.auroraproject.listener.PluginListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

public class Plugin extends JavaPlugin {
    private static Plugin instance;
    private final Logger log = this.getSLF4JLogger();

    @Override
    public void onEnable() {
        instance = this;

        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new PluginListener(), this);

        FileConfiguration config = this.getConfig();
        this.saveDefaultConfig();
        config.options().copyDefaults();

        ParticleLoader.getInstance().init();

        this.getCommand("particle").setExecutor(new ParticleCommand());

        log.info("Aurora-Particles enabled!");
    }

    @Override
    public void onDisable() {
        log.info("Shutting down...");
    }

    public static Plugin getPlugin() {
        return instance;
    }
}