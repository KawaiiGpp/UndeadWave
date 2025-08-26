package com.akira.undeadwave.core.weapon.melee.truth;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.base.MeleeWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;

public class PiercingKnife extends MeleeWeapon {
    public PiercingKnife(UndeadWave plugin) {
        super(plugin, WeaponType.PIERCING_KNIFE, WeaponAttackType.MELEE,
                Material.WOODEN_SWORD,
                "穿透之刃",
                new String[]{"以一股神秘的力量，", "击碎盔甲的防御。"},
                4, 50, 20, true, false, 1.0F, 0, 0, 0);
    }
}
