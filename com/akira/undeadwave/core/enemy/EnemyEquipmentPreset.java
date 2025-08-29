package com.akira.undeadwave.core.enemy;

import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EnemyEquipmentPreset {
    private final ItemStack weapon;
    private final ItemStack[] armorSet;

    public EnemyEquipmentPreset(Material weapon, Material[] armorSet) {
        Validate.notNull(armorSet);
        Arrays.stream(armorSet).forEach(this::validate);
        validate(weapon);

        this.weapon = this.toItem(weapon);
        this.armorSet = Arrays.stream(armorSet).map(this::toItem).toArray(ItemStack[]::new);
    }

    public EnemyEquipmentPreset(ItemStack weapon, ItemStack[] armorSet) {
        Validate.notNull(armorSet);
        Arrays.stream(armorSet).map(i -> i == null ? null : i.getType()).forEach(this::validate);
        if (weapon != null) validate(weapon.getType());

        this.weapon = cloneItem(weapon);
        this.armorSet = cloneItems(armorSet);
    }

    public ItemStack getWeapon() {
        return cloneItem(weapon);
    }

    public ItemStack[] getArmorSet() {
        return cloneItems(armorSet);
    }

    private ItemStack toItem(Material material) {
        return material == null ? null : new ItemStack(material);
    }

    private ItemStack cloneItem(ItemStack item) {
        return item == null ? null : item.clone();
    }

    private ItemStack[] cloneItems(ItemStack[] items) {
        Validate.notNull(items);
        return Arrays.stream(items).map(this::cloneItem).toArray(ItemStack[]::new);
    }

    private void validate(Material material) {
        Validate.isTrue(material == null || material.isItem());
    }
}
