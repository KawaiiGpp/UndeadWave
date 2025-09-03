package com.akira.undeadwave.core.item.weapon.melee.sweep;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.MeleeWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;

public class WindSword extends MeleeWeapon {
    public WindSword(UndeadWave plugin) {
        super(plugin, WeaponType.WIND_SWORD, WeaponAttackType.MELEE,
                Material.IRON_SWORD,
                "风之剑",
                new String[]{"一把风神眷顾的剑，", "横扫战力十分可观。"},
                17, 110, 40, false, true, 1.45F, 105, 0, 0);
    }
}
