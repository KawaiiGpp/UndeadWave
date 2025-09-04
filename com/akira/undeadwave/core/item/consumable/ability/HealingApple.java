package com.akira.undeadwave.core.item.consumable.ability;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItem;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class HealingApple extends ConsumableItem {
    public HealingApple(UndeadWave plugin) {
        super(plugin, ConsumableItemType.HEALING_APPLE);
    }

    protected void onConsume(Player player) {
        addPotionEffect(player, PotionEffectType.REGENERATION, 10, 2);
        healInstantly(player, 3);
        addAbsorption(player, 4);
    }

    protected List<String> getDescription() {
        return List.of("瞬间恢复3♥并获生命恢复，", "持续10秒，等级II级，", "同时可吸收4点伤害。");
    }
}
