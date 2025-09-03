package com.akira.undeadwave.core.item.weapon.melee.truth;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.MeleeWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;

public class TruthSword extends MeleeWeapon {
    public TruthSword(UndeadWave plugin) {
        super(plugin, WeaponType.TRUTH_SWORD, WeaponAttackType.MELEE,
                Material.IRON_SWORD,
                "真理之剑",
                new String[]{"以真理的力量，", "击碎遇到的一切障碍。"},
                15, 80, 50, true, true, 1.5F, 100, 10, 8);
    }
}
