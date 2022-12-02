package com.etsuni.soulbind;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SoulboundItemsList {

    private Map<UUID, List<ItemStack>> playersItems = new HashMap<>();
    private static SoulboundItemsList instance = new SoulboundItemsList();

    private SoulboundItemsList() {

    }

    public static SoulboundItemsList getInstance() {
        return instance;
    }

    public Map<UUID, List<ItemStack>> getPlayersItems() {
        return playersItems;
    }
}
