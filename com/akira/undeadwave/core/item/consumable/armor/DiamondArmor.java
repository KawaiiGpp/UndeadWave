package com.akira.undeadwave.core.item.consumable.armor;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DiamondArmor extends ArmorItem {
    public DiamondArmor(UndeadWave plugin) {
        super(plugin, ConsumableItemType.DIAMOND_ARMOR, "钻石");
    }

    protected Material getHelmet() {
        return Material.DIAMOND_HELMET;
    }

    protected Material getChestplate() {
        return Material.DIAMOND_CHESTPLATE;
    }

    protected Material getLeggings() {
        return Material.DIAMOND_LEGGINGS;
    }

    protected Material getBoots() {
        return Material.DIAMOND_BOOTS;
    }

    protected void onItemArmorAppend(ItemStack armorItem) {}
}
