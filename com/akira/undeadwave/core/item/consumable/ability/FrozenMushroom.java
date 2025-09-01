package com.akira.undeadwave.core.item.consumable.ability;

import com.akira.core.api.tool.MetadataEditor;
import com.akira.core.api.util.WorldUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItem;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class FrozenMushroom extends ConsumableItem {
    public FrozenMushroom(UndeadWave plugin) {
        super(plugin, ConsumableItemType.FROZEN_MUSHROOM);
    }

    protected void onConsume(Player player) {
        List<Monster> list = player.getNearbyEntities(30, 12, 30)
                .stream()
                .filter(e -> e instanceof Monster)
                .map(e -> (Monster) e)
                .filter(e -> plugin.getGame().getEnemyManager().fromMonster(e) != null)
                .toList();
        list.forEach(e -> applyEffect(player, e));

        player.sendMessage("§f已冻结附近共 §b" + list.size() + " §f名怪物。");
    }

    protected List<String> getDescription() {
        return List.of("在极地险境中生长的强者，", "可冻结30格以内所有怪物，", "并致30%于其生命值的伤害。");
    }

    private void applyEffect(Player player, Monster monster) {
        WorldUtils.playParticle(
                monster.getEyeLocation(),
                Particle.BLOCK_CRACK,
                5, 0.3, 0.3, 0.3, 1,
                Material.BLUE_ICE.createBlockData());
        monster.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, 255, false, true, false));

        double health = monster.getHealth();
        double damageDealt = health - (health * 0.3);
        String keyName = "ingame.enemy.ability_attack";

        MetadataEditor editor = MetadataEditor.create(plugin, monster);
        editor.set(keyName, true);
        monster.damage(damageDealt, player);
        editor.remove(keyName);
        monster.setVelocity(monster.getVelocity().setY(0));
    }
}
