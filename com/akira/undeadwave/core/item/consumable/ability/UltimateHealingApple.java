package com.akira.undeadwave.core.item.consumable.ability;

import com.akira.core.api.util.CommonUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItem;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class UltimateHealingApple extends ConsumableItem {
    public UltimateHealingApple(UndeadWave plugin) {
        super(plugin, ConsumableItemType.ULTIMATE_HEALING_APPLE);
    }

    protected void onConsume(Player player) {
        int addition = CommonUtils.getRandom().nextInt(11);
        PotionEffect effect = new PotionEffect(
                PotionEffectType.REGENERATION, (10 + addition) * 20, 1,
                false, false, false);

        player.removePotionEffect(PotionEffectType.REGENERATION);
        player.addPotionEffect(effect);
        player.setAbsorptionAmount(10);
    }

    protected List<String> getDescription() {
        return List.of("加速生命恢复，", "持续10到20秒，", "可抵消10点伤害。");
    }
}
