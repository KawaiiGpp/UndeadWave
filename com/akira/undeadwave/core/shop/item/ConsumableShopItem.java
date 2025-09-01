package com.akira.undeadwave.core.shop.item;

import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItem;
import org.apache.commons.lang3.Validate;
import org.bukkit.inventory.ItemStack;

public class ConsumableShopItem extends ShopItem {
    private final ConsumableItem consumableItem;

    public ConsumableShopItem(UndeadWave plugin, ItemStack item, ConsumableItem consumableItem) {
        super(plugin, item);

        Validate.notNull(consumableItem);
        this.consumableItem = consumableItem;
    }

    protected ItemStack getProduct() {
        return consumableItem.buildItem();
    }

    protected String getDisplayName() {
        return consumableItem.getType().getDisplayName();
    }

    protected double getCost() {
        return consumableItem.getType().getCost();
    }
}
