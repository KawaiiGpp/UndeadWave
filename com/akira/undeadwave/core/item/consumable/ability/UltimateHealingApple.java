package com.akira.undeadwave.core.item.consumable.ability;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItem;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class UltimateHealingApple extends ConsumableItem {
    public UltimateHealingApple(UndeadWave plugin) {
        super(plugin, ConsumableItemType.ULTIMATE_HEALING_APPLE);
    }

    protected void onConsume(Player player) {
        addPotionEffect(player, PotionEffectType.REGENERATION, 10, 3);
        healInstantly(player, 6);
        player.setAbsorptionAmount(12);
    }

    protected List<String> getDescription() {
        return List.of("瞬间恢复6♥并获生命恢复，", "持续10秒，等级III级，", "同时可吸收12点伤害。");
    }
}
