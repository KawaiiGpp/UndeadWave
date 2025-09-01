package com.akira.undeadwave.core.shop.item;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.weapon.base.Weapon;
import org.apache.commons.lang3.Validate;
import org.bukkit.inventory.ItemStack;

public class WeaponShopItem extends ShopItem {
    private final Weapon weapon;

    public WeaponShopItem(UndeadWave plugin, ItemStack item, Weapon weapon) {
        super(plugin, item);

        Validate.notNull(weapon);
        this.weapon = weapon;
    }

    protected ItemStack getProduct() {
        return weapon.buildItem();
    }

    protected String getDisplayName() {
        return weapon.getDisplayName();
    }

    protected int getCost() {
        return weapon.getWeaponType().getCost();
    }
}
