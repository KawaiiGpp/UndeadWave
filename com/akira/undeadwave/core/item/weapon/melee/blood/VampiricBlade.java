package com.akira.undeadwave.core.item.weapon.melee.blood;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.MeleeWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;

public class VampiricBlade extends MeleeWeapon {
    public VampiricBlade(UndeadWave plugin) {
        super(plugin, WeaponType.VAMPIRIC_BLADE, WeaponAttackType.MELEE,
                Material.IRON_SWORD,
                "吸血鬼之刃",
                new String[]{"传说中吸血鬼的武器，", "可将鲜血化作无尽的力量。"},
                16, 80, 70, false, false, 1.4F, 0, 20, 30);
    }
}
