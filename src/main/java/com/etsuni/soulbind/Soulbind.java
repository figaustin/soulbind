package com.etsuni.soulbind;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Soulbind extends JavaPlugin {

    private File customConfigFile;
    private FileConfiguration customConfig;

    protected static Soulbind plugin;
    @Override
    public void onEnable() {
        plugin = this;
        this.getServer().getPluginManager().registerEvents(new Events(), this);
        SoulPowder soulPowder = new SoulPowder();
        soulPowder.createSoulPowder();
        soulPowder.createRecipe();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public File getCustomConfigFile() {
        return this.customConfigFile;
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    public void setCustomConfig(FileConfiguration fileConfiguration) {
        this.customConfig = fileConfiguration;
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "config.yml");
        if(!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        customConfig = new YamlConfiguration();

        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }
}
