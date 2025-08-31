package com.akira.undeadwave.core.item.consumable;

import com.akira.core.api.Manager;
import com.akira.core.api.util.CommonUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.inventory.ItemStack;

public class ConsumableItemManager extends Manager<ConsumableItem> {
    public ConsumableItem fromString(String string) {
        Validate.notNull(string);

        ConsumableItemType type = CommonUtils.getEnumSafely(ConsumableItemType.class, string);
        Validate.notNull(type, "Unknown consumable item type: " + string);

        return this.fromType(type);
    }

    public ConsumableItem fromType(ConsumableItemType type) {
        Validate.notNull(type);

        return CommonUtils.singleMatch(copySet().stream(),
                e -> type == e.getType());
    }

    public ConsumableItem fromItemStack(ItemStack item) {
        Validate.notNull(item);

        return CommonUtils.singleMatch(copySet().stream(),
                e -> e.matchesItem(item), false);
    }

    public void resetCooldown() {
        elements.forEach(ConsumableItem::resetCooldown);
    }
}
