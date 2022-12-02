package com.etsuni.soulbind;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.etsuni.soulbind.SoulPowder.soulPowder;
import static com.etsuni.soulbind.Soulbind.plugin;

public class Events implements Listener {

    @EventHandler
    public void onClick(PrepareAnvilEvent event){
        AnvilInventory anvilInventory = event.getInventory();
        Player player = (Player) event.getView().getPlayer();

        if(anvilInventory.getItem(1) != null && anvilInventory.getItem(1).isSimilar(soulPowder)) {
            if(anvilInventory.getItem(0) != null && anvilInventory.getItem(0).getItemMeta().hasLore()
            && anvilInventory.getItem(0).getItemMeta()
                    .getLore().contains("Soulbound to " + player.getName())){
                return;
            }
            ItemStack item = anvilInventory.getItem(0) != null ? anvilInventory.getItem(0).clone() : null;
            if(item == null) {
                return;
            }
            ItemMeta meta = item.getItemMeta();

            List<String> lore = new ArrayList<>();
            if(meta.hasLore()) {
                for(String l : meta.getLore()) {
                    lore.add(l);
                }
            }
            lore.add("Soulbound to " + player.getName());
            meta.setLore(lore);
            item.setItemMeta(meta);
            event.setResult(item);

            int cost = 5;
            plugin.getServer().getScheduler().runTask(plugin, () -> anvilInventory.setRepairCost(cost));
        }

    }

    @EventHandler
    public void onPickup(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItemDrop();
        ItemStack itemStack = item.getItemStack();

        if(itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
            if(getOwner(itemStack.getItemMeta().getLore()).equals(player)){
                item.setOwner(player.getUniqueId());
            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory() != null ? event.getClickedInventory() : null;
        if (inv == null) {
            return;
        }
        InventoryType invType = inv.getType();
        if(invType.equals(InventoryType.PLAYER)) {
            return;
        }
        int slot = event.getRawSlot();
        ItemStack item = slot > -1 ? event.getInventory().getItem(slot) : null;
        if(item == null) {
            return;
        }

        if(item.hasItemMeta() && item.getItemMeta().hasLore()) {
            if(!getOwner(item.getItemMeta().getLore()).equals(player)) {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onDispense(BlockDispenseEvent event){
        ItemStack item = event.getItem();

        if(item.hasItemMeta() && item.getItemMeta().hasLore()) {
            if(hasOwner(item.getItemMeta().getLore())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        List<ItemStack> soulItems = new ArrayList<>();

        for (ItemStack item : event.getDrops()) {
            if (item == null) {
                return;
            }
            if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
                if (hasOwner(item.getItemMeta().getLore()) && getOwner(item.getItemMeta().getLore()).equals(player)) {
                    soulItems.add(item);
                }
            }
        }
        event.getDrops().removeAll(soulItems);

        SoulboundItemsList.getInstance().getPlayersItems().put(player.getUniqueId(), soulItems);

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if(SoulboundItemsList.getInstance().getPlayersItems().containsKey(playerUUID)) {
            List<ItemStack> items = SoulboundItemsList.getInstance().getPlayersItems().get(playerUUID);

            for(ItemStack item : items) {
                player.getInventory().addItem(item);
            }
        }
    }

    public boolean hasOwner(List<String> lore){
        for(String str : lore) {
            if(str.contains("Soulbound")) {
                return true;
            }
        }
        return false;
    }

    public Player getOwner(List<String> lore) {
        Player player = null;

        for(String str : lore) {
            if(str.contains("Soulbound")) {
                //Substring to cut off "Soulbound to " and get the players name
                player = Bukkit.getPlayer(str.substring(13, str.length() - 1));
            }
        }

        return player;
    }



}
