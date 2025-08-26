package com.akira.undeadwave.core.weapon.melee.blood;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.MeleeWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;

public class BloodyBlade extends MeleeWeapon {
    public BloodyBlade(UndeadWave plugin) {
        super(plugin, WeaponType.BLOODY_BLADE, WeaponAttackType.MELEE,
                Material.STONE_SWORD,
                "鲜血之刃",
                new String[]{"刀刃沾着鲜红的血液，", "似乎对鲜血有强烈的反应。"},
                7, 60, 30, false, false, 1.2F, 0, 30, 15);
    }
}
