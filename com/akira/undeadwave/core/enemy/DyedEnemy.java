package com.akira.undeadwave.core.enemy;

import com.akira.core.api.util.ItemUtils;
import com.akira.undeadwave.UndeadWave;
import org.apache.commons.lang3.Validate;
import org.bukkit.Color;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public abstract class DyedEnemy<T extends Monster> extends Enemy<T> {
    public DyedEnemy(UndeadWave plugin, EntityType entityType, Class<T> typeClass, EnemyType enemyType) {
        super(plugin, entityType, typeClass, enemyType);
    }

    protected final EnemyEquipmentPreset getEquipmentPreset() {
        return new EnemyEquipmentPreset(
                this.getWeaponItem(),

                new ItemStack[]{
                        createPiece(ItemUtils::createDyedBoots, this.getBootsColor()),
                        createPiece(ItemUtils::createDyedLeggings, this.getLeggingsColor()),
                        createPiece(ItemUtils::createDyedChestplate, this.getChestplateColor()),
                        createPiece(ItemUtils::createDyedHelmet, this.getHelmetColor())
                }
        );
    }

    protected abstract Color getHelmetColor();

    protected abstract Color getChestplateColor();

    protected abstract Color getLeggingsColor();

    protected abstract Color getBootsColor();

    protected abstract ItemStack getWeaponItem();

    private ItemStack createPiece(Function<Color, ItemStack> creator, Color color) {
        Validate.notNull(creator);

        if (color == null) return null;
        else return creator.apply(color);
    }
}
