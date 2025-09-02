package com.akira.undeadwave.core.enemy.zombie.speed;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.enemy.EnemyType;
import com.akira.undeadwave.core.enemy.zombie.DyedZombieEnemy;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class LightningZombie extends DyedZombieEnemy {
    private final Color color;

    public LightningZombie(UndeadWave plugin) {
        super(plugin, EnemyType.LIGHTNING_ZOMBIE);

        this.color = Color.WHITE;
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
        return new ItemStack(Material.GOLDEN_SWORD);
    }
}
