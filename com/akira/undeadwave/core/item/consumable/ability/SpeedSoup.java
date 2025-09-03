package com.akira.undeadwave.core.item.consumable.ability;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItem;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class SpeedSoup extends ConsumableItem {
    public SpeedSoup(UndeadWave plugin) {
        super(plugin, ConsumableItemType.SPEED_SOUP);
    }

    protected void onConsume(Player player) {
        addPotionEffect(player, PotionEffectType.SPEED, 40, 2);
    }

    protected List<String> getDescription() {
        return List.of("蕴藏着兔子的能量，", "可持续提升移动速度。");
    }
}
