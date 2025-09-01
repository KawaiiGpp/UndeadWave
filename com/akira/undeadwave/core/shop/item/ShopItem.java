package com.akira.undeadwave.core.shop.item;

import com.akira.core.api.gui.GuiItem;
import com.akira.core.api.util.CommonUtils;
import com.akira.core.api.util.PlayerUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public abstract class ShopItem extends GuiItem {
    public ShopItem(ItemStack item) {
        super(item);
    }

    public final ItemStack createItemFor(Player player) {
        return this.copyItem();
    }

    public final void onClicked(Player player) {
        PlayerInventory inventory = player.getInventory();

        if (inventory.firstEmpty() != -1) {
            String name = CommonUtils.requireNonNull(this.getDisplayName());
            inventory.addItem(CommonUtils.requireNonNull(this.getProduct()));

            PlayerUtils.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F);
            player.sendMessage("§a你成功购买了 §e" + name + "§a。");
        } else {
            PlayerUtils.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F);
            player.sendMessage("§c你的背包已满，无法购买。");
        }
    }

    protected abstract ItemStack getProduct();

    protected abstract String getDisplayName();
}
