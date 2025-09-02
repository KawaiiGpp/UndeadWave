package com.akira.undeadwave.core.enemy.skeleton.other;

import com.akira.core.api.item.ItemBuilder;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.enemy.EnemyType;
import com.akira.undeadwave.core.enemy.skeleton.DyedSkeletonEnemy;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.ItemStack;

public class SniperSkeleton extends DyedSkeletonEnemy {
    public SniperSkeleton(UndeadWave plugin) {
        super(plugin, EnemyType.SNIPER_SKELETON);
    }

    protected void doEntityPresets(Skeleton entity) {}

    protected Color getHelmetColor() {
        return Color.BLACK;
    }

    protected Color getChestplateColor() {
        return Color.BLACK;
    }

    protected Color getLeggingsColor() {
        return Color.BLACK;
    }

    protected Color getBootsColor() {
        return Color.BLACK;
    }

    protected ItemStack getWeaponItem() {
        return ItemBuilder.create(Material.BOW)
                .addEnchant(Enchantment.DURABILITY, 1)
                .getResult();
    }
}
