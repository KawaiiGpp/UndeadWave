package com.akira.undeadwave.core.weapon.ranged.launcher.rocket;

import com.akira.core.api.tool.Tuple;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.RangedWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;

public class AncientRocketLauncher extends RangedWeapon {
    public AncientRocketLauncher(UndeadWave plugin) {
        super(plugin, WeaponType.ANCIENT_ROCKET_LAUNCHER, WeaponAttackType.RANGED,
                Material.DIAMOND_SHOVEL,
                "远古火箭筒",
                new String[]{"让人魂飞魄散的强者，", "在古战场上所向披靡。"},
                12, 80, 30, 50,
                Particle.EXPLOSION_HUGE,
                new Tuple<>(Sound.ENTITY_GENERIC_EXPLODE, 1.0F),
                25, 8, 2, 140, 1.0,
                true, false);
    }
}
