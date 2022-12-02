package com.etsuni.soulbind;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import static com.etsuni.soulbind.Soulbind.plugin;

public class SoulPowder {

    public static ItemStack soulPowder;

    public void createSoulPowder() {
        soulPowder = new ItemStack(Material.BLAZE_POWDER);

        ItemMeta meta = soulPowder.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Soul Powder");

        meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        soulPowder.setItemMeta(meta);

    }

    public void createRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "soul_powder");

        ShapedRecipe recipe = new ShapedRecipe(key, soulPowder);

        recipe.shape("SSS", "SBS", "SSS");

        recipe.setIngredient('B', Material.BLAZE_POWDER);
        recipe.setIngredient('S', Material.SOUL_SAND);

        Bukkit.addRecipe(recipe);
    }
}
