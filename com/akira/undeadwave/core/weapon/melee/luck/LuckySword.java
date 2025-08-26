package com.akira.undeadwave.core.weapon.melee.luck;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.MeleeWeapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.bukkit.Material;

public class LuckySword extends MeleeWeapon {
    public LuckySword(UndeadWave plugin) {
        super(plugin, WeaponType.LUCKY_SWORD, WeaponAttackType.MELEE,
                Material.STONE_SWORD,
                "幸运之剑",
                new String[]{"每一刀都在赌，", "偶尔能打出惊人的伤害。"},
                5, 250, 20, false, false, 1.3F, 0, 0, 0);
    }
}
