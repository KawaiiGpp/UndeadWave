package com.akira.undeadwave.core.item.consumable;

import com.akira.core.api.item.ItemBuilder;
import com.akira.core.api.item.ItemTagEditor;
import com.akira.core.api.util.CommonUtils;
import com.akira.core.api.util.PlayerUtils;
import com.akira.undeadwave.UndeadWave;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public abstract class ConsumableItem {
    private final UndeadWave plugin;
    private final ConsumableItemType type;

    private BukkitTask cooldownTask;
    private boolean inCooldown;

    public ConsumableItem(UndeadWave plugin, ConsumableItemType type) {
        Validate.notNull(plugin);
        Validate.notNull(type);

        this.plugin = plugin;
        this.type = type;
    }

    public final void consume(Player player, ItemStack item, EquipmentSlot slot) {
        Validate.notNull(player);
        Validate.notNull(item);
        Validate.notNull(slot);

        if (inCooldown) return;
        this.onConsume(player);

        PlayerInventory inventory = player.getInventory();
        int amount = item.getAmount();
        if (amount > 1) item.setAmount(amount - 1);
        else inventory.setItem(slot, null);

        PlayerUtils.playSound(player, type.getSound(), type.getSoundPitch());
        player.sendMessage("§f你使用了 §a" + type.getDisplayName() + "§f。");

        inCooldown = true;
        cooldownTask = Bukkit.getScheduler().runTask(plugin, () -> inCooldown = false);
    }

    public final void resetCooldown() {
        if (cooldownTask != null)
            cooldownTask.cancel();

        inCooldown = false;
        cooldownTask = null;
    }

    public final ItemStack buildItem() {
        ItemStack item = ItemBuilder.create(type.getMaterial())
                .setDisplayName(type.getDisplayName())
                .addFlags(ItemFlag.values())
                .setLore(this.generateLore())
                .getResult();

        ItemTagEditor editor = ItemTagEditor.forItemMeta(plugin, item);
        editor.set("ingame.consumable", PersistentDataType.STRING, type.name());
        editor.apply(item);

        return item;
    }

    public final boolean matchesItem(ItemStack item) {
        Validate.notNull(item);

        String keyName = "ingame.consumable";
        ItemTagEditor editor = ItemTagEditor.forItemMeta(plugin, item);
        if (!editor.hasKey(keyName, PersistentDataType.STRING)) return false;

        String value = editor.get(keyName, PersistentDataType.STRING);
        ConsumableItemType type = CommonUtils.getEnumSafely(ConsumableItemType.class, value);
        Validate.notNull(type, "Unknown consumable type in item tag: " + value);

        return this.type == type;
    }

    public final ConsumableItemType getType() {
        return type;
    }

    protected abstract void onConsume(Player player);

    protected abstract List<String> getDescription();

    private String[] generateLore() {
        List<String> description = this.getDescription();

        List<String> result = new ArrayList<>();
        if (description != null) {
            description.forEach(line -> result.add("§7" + line));
            result.add("");
        }

        result.add("§f冷却：§e" + (type.getCooldownTicks() / 20F) + "s");
        result.add("§d游戏道具 右键使用");

        return result.toArray(String[]::new);
    }
}
