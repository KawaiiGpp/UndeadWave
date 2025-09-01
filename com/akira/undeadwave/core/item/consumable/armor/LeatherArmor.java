package com.akira.undeadwave.core.item.consumable.armor;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import org.apache.commons.lang3.Validate;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class LeatherArmor extends ArmorItem{
    public LeatherArmor(UndeadWave plugin) {
        super(plugin, ConsumableItemType.LEATHER_ARMOR, "皮革");
    }

    protected Material getHelmet() {
        return Material.LEATHER_HELMET;
    }

    protected Material getChestplate() {
        return Material.LEATHER_CHESTPLATE;
    }

    protected Material getLeggings() {
        return Material.LEATHER_LEGGINGS;
    }

    protected Material getBoots() {
        return Material.LEATHER_BOOTS;
    }

    protected void onItemArmorAppend(ItemStack armorItem) {
        ItemMeta itemMeta = armorItem.getItemMeta();
        Validate.isTrue(itemMeta instanceof LeatherArmorMeta);

        LeatherArmorMeta meta = (LeatherArmorMeta) itemMeta;
        meta.setColor(Color.WHITE);
        armorItem.setItemMeta(meta);
    }
}
