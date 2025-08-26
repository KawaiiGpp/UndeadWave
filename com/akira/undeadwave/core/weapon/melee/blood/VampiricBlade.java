package com.akira.undeadwave.core.weapon.melee.blood;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.MeleeWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;

public class VampiricBlade extends MeleeWeapon {
    public VampiricBlade(UndeadWave plugin) {
        super(plugin, WeaponType.VAMPIRIC_BLADE, WeaponAttackType.MELEE,
                Material.IRON_SWORD,
                "吸血鬼之刃",
                new String[]{"传说中吸血鬼的武器，", "可将鲜血化作无尽的力量。"},
                9, 90, 50, false, false, 1.4F, 0, 40, 30);
    }
}
