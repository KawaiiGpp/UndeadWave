package com.akira.undeadwave.core.item.weapon.ranged.launcher.fire;

import com.akira.core.api.tool.Tuple;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.RangedWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class FireLauncher extends RangedWeapon {
    public FireLauncher(UndeadWave plugin) {
        super(plugin, WeaponType.FIRE_LAUNCHER, WeaponAttackType.RANGED,
                Material.STONE_SHOVEL,
                "火焰发射器",
                new String[]{"喷出熊熊火焰，", "制造范围性伤害。"},
                6, 50, 25, 30,
                Particle.LAVA,
                new Tuple<>(Sound.ITEM_FIRECHARGE_USE, 1.0F),
                8, 6, 0.5, 100, 1.0,
                true, false);
    }
}
