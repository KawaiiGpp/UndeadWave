package com.akira.undeadwave.core.item.consumable.armor;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import org.bukkit.Material;

public class GoldArmor extends ArmorItem{
    public GoldArmor(UndeadWave plugin) {
        super(plugin, ConsumableItemType.GOLD_ARMOR, "金");
    }

    protected Material getHelmet() {
        return Material.GOLDEN_HELMET;
    }

    protected Material getChestplate() {
        return Material.GOLDEN_CHESTPLATE;
    }

    protected Material getLeggings() {
        return Material.GOLDEN_LEGGINGS;
    }

    protected Material getBoots() {
        return Material.GOLDEN_BOOTS;
    }
}
