package com.akira.undeadwave.core.enemy.zombie.damage;

import com.akira.core.api.item.ItemBuilder;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.enemy.EnemyType;
import com.akira.undeadwave.core.enemy.zombie.DyedZombieEnemy;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class ShadowAssassinZombie extends DyedZombieEnemy {
    private final Color color;

    public ShadowAssassinZombie(UndeadWave plugin) {
        super(plugin, EnemyType.SHADOW_ASSASSIN_ZOMBIE);

        this.color = Color.BLACK;
    }

    protected void doEntityPresets(Zombie entity) {}

    protected Color getHelmetColor() {
        return color;
    }

    protected Color getChestplateColor() {
        return color;
    }

    protected Color getLeggingsColor() {
        return color;
    }

    protected Color getBootsColor() {
        return color;
    }

    protected ItemStack getWeaponItem() {
        return ItemBuilder.create(Material.DIAMOND_AXE)
                .addEnchant(Enchantment.DURABILITY, 1)
                .getResult();
    }
}
