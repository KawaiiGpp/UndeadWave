package com.akira.undeadwave.core.shop;

import com.akira.core.api.gui.Gui;
import com.akira.core.api.gui.GuiItem;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.Game;
import com.akira.undeadwave.core.item.consumable.ConsumableItem;
import com.akira.undeadwave.core.item.consumable.ConsumableItemManager;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import com.akira.undeadwave.core.item.consumable.armor.ArmorItem;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import com.akira.undeadwave.core.item.weapon.base.Weapon;
import com.akira.undeadwave.core.shop.item.ConsumableShopItem;
import com.akira.undeadwave.core.shop.item.WeaponShopItem;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;

public class ShopGui extends Gui {
    private final UndeadWave plugin;
    private final Game game;

    public ShopGui(UndeadWave plugin) {
        super("shop", "商店", 6);

        Validate.notNull(plugin);
        Validate.notNull(plugin.getGame());

        this.plugin = plugin;
        this.game = plugin.getGame();

        this.initialize();
    }

    private void initialize() {
        int currentSlot = 0;

        for (WeaponType weaponType : WeaponType.values()) {
            Weapon weapon = game.getWeaponManager().fromWeaponType(weaponType);

            this.setItem(currentSlot, new WeaponShopItem(plugin, weapon.buildItem(), weapon));
            currentSlot++;
        }

        for (ConsumableItemType consumableItemType : ConsumableItemType.values()) {
            ConsumableItem consumableItem = game.getConsumableItemManager().fromType(consumableItemType);
            if (consumableItem instanceof ArmorItem) continue;

            this.setItem(currentSlot, new ConsumableShopItem(plugin, consumableItem.buildItem(), consumableItem));
            currentSlot++;
        }

        for (int i = 0; i < 9; i++) {
            this.setItem(currentSlot, this.createSeparator(Material.BLACK_STAINED_GLASS_PANE));
            currentSlot++;
        }

        while (currentSlot < size) {
            ConsumableItemType type = null;

            if (currentSlot == 47) type = ConsumableItemType.GOLD_ARMOR;
            else if (currentSlot == 49) type = ConsumableItemType.IRON_ARMOR;
            else if (currentSlot == 51) type = ConsumableItemType.DIAMOND_ARMOR;

            if (type != null) setItem(currentSlot, createConsumableShopItem(type));
            else setItem(currentSlot, createSeparator(Material.WHITE_STAINED_GLASS_PANE));

            currentSlot++;
        }
    }

    private GuiItem createSeparator(Material material) {
        Validate.notNull(material);
        return GuiItem.create(material, "§7分割线");
    }

    private ConsumableShopItem createConsumableShopItem(ConsumableItemType type) {
        Validate.notNull(type);

        ConsumableItemManager manager = game.getConsumableItemManager();
        ConsumableItem item = manager.fromType(type);

        return new ConsumableShopItem(plugin, item.buildItem(), item);
    }
}
