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
        List<Monster> list = player.getNearbyEntities(15, 8, 15)
                .stream()
                .filter(e -> e instanceof Monster)
                .map(e -> (Monster) e)
                .filter(e -> plugin.getGame().getEnemyManager().fromMonster(e) != null)
                .toList();
        list.forEach(this::applyEffect);

        player.sendMessage("§f附近共 §b" + list.size() + " §f名怪物受到了冲击。");
    }

    protected List<String> getDescription() {
        return List.of("轻抖羽毛，散发出魔法寒气，", "减缓周围15格内怪物的行动，", "并削弱其攻击伤害。");
    }

    private void applyEffect(Monster monster) {
        WorldUtils.playParticle(
                monster.getEyeLocation(),
                Particle.BLOCK_CRACK,
                5, 0.3, 0.3, 0.3, 1,
                Material.WHITE_WOOL.createBlockData());
        int duration = 5 * 20;
        monster.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 1, false, true, false));

        MetadataEditor editor = MetadataEditor.create(plugin, monster);
        String keyName = "ingame.enemy.attack_reduction";
        editor.set(keyName, 0.65);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (monster.isValid()) editor.remove(keyName);
        }, duration);
    }
}
