package com.akira.undeadwave.core.item.weapon.melee.luck;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.MeleeWeapon;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.bukkit.Material;

public class FateSword extends MeleeWeapon {
    public FateSword(UndeadWave plugin) {
        super(plugin, WeaponType.FATE_SWORD, WeaponAttackType.MELEE,
                Material.IRON_SWORD,
                "命运之剑",
                new String[]{"紧握这把剑，闭上眼睛，", "将一切交给命运来决定。"},
                7, 400, 20, false, true, 1.6F, 0, 0, 0);
    }
}
