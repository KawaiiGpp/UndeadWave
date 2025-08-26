package com.akira.undeadwave.core.weapon.melee.truth;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.MeleeWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;

public class TrueKnife extends MeleeWeapon {
    public TrueKnife(UndeadWave plugin) {
        super(plugin, WeaponType.TRUE_KNIFE, WeaponAttackType.MELEE,
                Material.STONE_SWORD,
                "真·匕首",
                new String[]{"以未知物质锻造而成，", "轻松击破厚实的盔甲。"},
                7, 60, 30, true, true, 1.2F, 0, 0, 0);
    }
}
