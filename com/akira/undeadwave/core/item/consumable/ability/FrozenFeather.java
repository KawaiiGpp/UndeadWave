package com.akira.undeadwave.core.item.consumable.ability;

import com.akira.core.api.tool.MetadataEditor;
import com.akira.core.api.util.WorldUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItem;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class FrozenFeather extends ConsumableItem {
    public FrozenFeather(UndeadWave plugin) {
        super(plugin, ConsumableItemType.FROZEN_FEATHER);
    }

    protected void onConsume(Player player) {
        int duration = 5 * 20;
        String keyName = "ingame.enemy.attack_reduction";

        List<Monster> list = player.getNearbyEntities(15, 8, 15)
                .stream()
                .filter(e -> e instanceof Monster)
                .map(e -> (Monster) e)
                .filter(e -> plugin.getGame().getEnemyManager().fromMonster(e) != null)
                .toList();

        list.forEach(e -> applyEffect(e, duration, keyName));
        startMetadataRemoveTask(list, duration, keyName);

        player.sendMessage("§f附近共 §b" + list.size() + " §f名怪物受到了冲击。");
    }

    protected List<String> getDescription() {
        return List.of("轻抖羽毛，散发出魔法寒气，", "减缓周围15格内怪物的行动，", "并削弱其攻击伤害。");
    }

    private void applyEffect(Monster monster, int duration, String keyName) {
        WorldUtils.playParticle(
                monster.getEyeLocation(),
                Particle.BLOCK_CRACK,
                5, 0.3, 0.3, 0.3, 1,
                Material.WHITE_WOOL.createBlockData());

        monster.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 1, false, true, false));
        MetadataEditor.create(plugin, monster).set(keyName, 0.65);
    }

    private void startMetadataRemoveTask(List<Monster> list, int duration, String keyName) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            list.forEach(e -> {
                if (!e.isValid()) return;

                MetadataEditor.create(plugin, e).remove(keyName);
            });
        }, duration);
    }
}
