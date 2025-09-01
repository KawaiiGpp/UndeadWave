package com.akira.undeadwave.core.shop.item;

import com.akira.core.api.gui.GuiItem;
import com.akira.core.api.util.CommonUtils;
import com.akira.core.api.util.PlayerUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.GameSession;
import org.apache.commons.lang3.Validate;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class ShopItem extends GuiItem {
    protected final UndeadWave plugin;

    public ShopItem(UndeadWave plugin, ItemStack item) {
        super(item);

        Validate.notNull(plugin);
        this.plugin = plugin;
    }

    public final ItemStack createItemFor(Player player) {
        ItemStack item = this.copyItem();

        ItemMeta meta = item.getItemMeta();
        Validate.notNull(meta);

        List<String> lore = meta.getLore() == null ?
                new ArrayList<>() : meta.getLore();

        lore.add("");
        lore.add("§f价格：§6" + this.getCost());

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public final void onClicked(Player player) {
        PlayerInventory inventory = player.getInventory();
        GameSession session = plugin.getGame().getSession();

        if (inventory.firstEmpty() == -1) {
            PlayerUtils.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F);
            player.sendMessage("§c你的背包已满，无法购买。");
            return;
        }

        double heldCoins = session.getCoins();
        double cost = this.getCost();

        if (heldCoins < this.getCost()) {
            PlayerUtils.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F);
            player.sendMessage("§c硬币不足以购买，还需 " + (cost - heldCoins) + " 枚。");
            return;
        }

        String name = CommonUtils.requireNonNull(this.getDisplayName());
        inventory.addItem(CommonUtils.requireNonNull(this.getProduct()));

        PlayerUtils.playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 2.0F);
        player.sendMessage("§a你成功购买了 §e" + name + "§a。");
    }

    protected abstract ItemStack getProduct();

    protected abstract String getDisplayName();

    protected abstract double getCost();
}
