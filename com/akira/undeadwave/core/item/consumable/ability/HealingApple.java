package com.akira.undeadwave.core.item.consumable.ability;

import com.akira.core.api.util.CommonUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItem;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class HealingApple extends ConsumableItem {
    public HealingApple(UndeadWave plugin) {
        super(plugin, ConsumableItemType.HEALING_APPLE);
    }

    protected void onConsume(Player player) {
        int addition = CommonUtils.getRandom().nextInt(6);
        PotionEffect effect = new PotionEffect(
                PotionEffectType.REGENERATION, (5 + addition) * 20, 1,
                false, false, false);

        player.removePotionEffect(PotionEffectType.REGENERATION);
        player.addPotionEffect(effect);
    }

    protected List<String> getDescription() {
        return List.of("加速生命恢复，", "持续5到10秒。");
    }
}
