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

public class ImpactSkeleton extends DyedSkeletonEnemy {
    public ImpactSkeleton(UndeadWave plugin) {
        super(plugin, EnemyType.IMPACT_SKELETON);
    }

    protected void doEntityPresets(Skeleton entity) {}

    protected Color getHelmetColor() {
        return Color.ORANGE;
    }

    protected Color getChestplateColor() {
        return Color.YELLOW;
    }

    protected Color getLeggingsColor() {
        return Color.YELLOW;
    }

    protected Color getBootsColor() {
        return Color.ORANGE;
    }

    protected ItemStack getWeaponItem() {
        return ItemBuilder.create(Material.BOW)
                .addEnchant(Enchantment.ARROW_KNOCKBACK, 2)
                .getResult();
    }
}
