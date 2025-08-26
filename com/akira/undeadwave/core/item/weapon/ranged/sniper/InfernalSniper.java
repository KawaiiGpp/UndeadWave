package com.akira.undeadwave.core.item.weapon.ranged.sniper;

import com.akira.core.api.tool.Tuple;
import com.akira.core.api.util.WorldUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.RangedWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class InfernalSniper extends RangedWeapon {
    public InfernalSniper(UndeadWave plugin) {
        super(plugin, WeaponType.INFERNAL_SNIPER, WeaponAttackType.RANGED,
                Material.NETHERITE_SHOVEL,
                "炼狱狙击枪",
                new String[]{"藏于暗处打击敌人，", "接受来自地狱的力量。"},
                22, 90, 60, 30,
                (w, l) -> WorldUtils.playParticle(l, Particle.SOUL_FIRE_FLAME),
                new Tuple<>(Sound.BLOCK_GRAVEL_STEP, 2.0F),
                80, 2, 1, 140, 1,
                true, false);
    }
}
