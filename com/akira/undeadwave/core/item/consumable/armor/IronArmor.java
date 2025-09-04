package com.akira.undeadwave.core.item.consumable.armor;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import org.bukkit.Material;

public class IronArmor extends ArmorItem {
    public IronArmor(UndeadWave plugin) {
        super(plugin, ConsumableItemType.IRON_ARMOR, "铁");
    }

    protected Material getHelmet() {
        return Material.IRON_HELMET;
    }

    protected Material getChestplate() {
        return Material.IRON_CHESTPLATE;
    }

    protected Material getLeggings() {
        return Material.IRON_LEGGINGS;
    }

    protected Material getBoots() {
        return Material.IRON_BOOTS;
    }
}
