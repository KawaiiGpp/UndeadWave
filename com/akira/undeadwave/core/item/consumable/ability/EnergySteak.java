package com.akira.undeadwave.core.item.consumable.ability;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItem;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class EnergySteak extends ConsumableItem {
    public EnergySteak(UndeadWave plugin) {
        super(plugin, ConsumableItemType.ENERGY_STEAK);
    }

    protected void onConsume(Player player) {
        addPotionEffect(player, PotionEffectType.SPEED, 10, 2);
        addPotionEffect(player, PotionEffectType.REGENERATION, 20, 1);
    }

    protected List<String> getDescription() {
        return List.of("吃下牛排的瞬间，", "你的力量在不断涌现。");
    }
}
