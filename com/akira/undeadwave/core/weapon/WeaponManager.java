package com.akira.undeadwave.core.weapon;

import com.akira.core.api.Manager;
import com.akira.core.api.util.CommonUtils;
import com.akira.undeadwave.core.weapon.base.Weapon;
import org.apache.commons.lang3.Validate;
import org.bukkit.inventory.ItemStack;

public class WeaponManager extends Manager<Weapon> {
    public Weapon fromString(String name) {
        Validate.notNull(name);

        WeaponType type = CommonUtils.getEnumSafely(WeaponType.class, name);
        Validate.notNull(type, "Unknown weapon type: " + name);

        return fromWeaponType(type);
    }

    public Weapon fromWeaponType(WeaponType weaponType) {
        Validate.notNull(weaponType);
        return CommonUtils.singleMatch(copySet().stream(),
                e -> e.getWeaponType() == weaponType);
    }

    public Weapon fromItemStack(ItemStack item) {
        Validate.notNull(item);
        return CommonUtils.singleMatch(copySet().stream(),
                e -> e.matchesItem(item), false);
    }
}
