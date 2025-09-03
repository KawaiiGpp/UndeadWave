package com.akira.undeadwave.core.item.weapon.melee.blood;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.MeleeWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;

public class BloodyBlade extends MeleeWeapon {
    public BloodyBlade(UndeadWave plugin) {
        super(plugin, WeaponType.BLOODY_BLADE, WeaponAttackType.MELEE,
                Material.STONE_SWORD,
                "鲜血之刃",
                new String[]{"刀刃沾着鲜红的血液，", "似乎对鲜血有强烈的反应。"},
                10, 60, 40, false, false, 1.2F, 0, 20, 20);
    }
}
