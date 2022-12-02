package com.etsuni.soulbind;

import org.bukkit.plugin.java.JavaPlugin;


public final class Soulbind extends JavaPlugin {

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
    }

}
