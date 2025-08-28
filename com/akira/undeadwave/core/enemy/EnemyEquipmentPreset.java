package com.akira.undeadwave.core.enemy;

import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EnemyEquipmentPreset {
    private final Material weapon;
    private final Material[] armorSet;

    public EnemyEquipmentPreset(Material weapon, Material[] armorSet) {
        if (armorSet != null)
            Arrays.stream(armorSet).forEach(this::validate);
        validate(weapon);

        this.weapon = weapon;
        this.armorSet = armorSet == null ? null : armorSet.clone();
    }

    public ItemStack getWeapon() {
        return toItem(weapon);
    }

    public ItemStack[] getArmorSet() {
        return armorSet == null ?
                new ItemStack[0] :
                Arrays.stream(armorSet).map(this::toItem).toArray(ItemStack[]::new);
    }

    private ItemStack toItem(Material material) {
        return material == null ? null : new ItemStack(material);
    }

    private void validate(Material material) {
        Validate.isTrue(material == null || material.isItem());
    }
}
