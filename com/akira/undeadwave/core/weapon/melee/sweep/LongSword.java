package com.akira.undeadwave.core.weapon.melee.sweep;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.base.MeleeWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;

public class LongSword extends MeleeWeapon {
    public LongSword(UndeadWave plugin) {
        super(plugin, WeaponType.LONG_SWORD, WeaponAttackType.MELEE,
                Material.STONE_SWORD,
                "长剑",
                new String[]{"这把剑格外地长，", "也许具备横扫千军潜力。"},
                6, 50, 25, false, true, 1.15F, 30, 0, 0);
    }
}
