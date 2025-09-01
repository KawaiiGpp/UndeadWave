package com.akira.undeadwave.core.item.consumable.ability;

import com.akira.core.api.util.EntityUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItem;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class EnergySteak extends ConsumableItem {
    public EnergySteak(UndeadWave plugin) {
        super(plugin, ConsumableItemType.ENERGY_STEAK);
    }

    protected void onConsume(Player player) {
        PotionEffect speed = new PotionEffect(
                PotionEffectType.SPEED, 10 * 20, 1,
                false, false, false);
        PotionEffect healing = new PotionEffect(
                PotionEffectType.REGENERATION, 15 * 20, 0,
                false, false, false);

        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.REGENERATION);

        player.addPotionEffect(speed);
        player.addPotionEffect(healing);

        double newHealth = player.getHealth() + 4;
        double maxHealth = EntityUtils.getMaxHealth(player);
        player.setHealth(Math.min(maxHealth, newHealth));
    }

    protected List<String> getDescription() {
        return List.of("吃下牛排的瞬间，", "你的力量在不断涌现。");
    }
}
