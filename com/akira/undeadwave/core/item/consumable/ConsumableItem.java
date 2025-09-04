package com.akira.undeadwave.core.item.consumable;

import com.akira.core.api.item.ItemBuilder;
import com.akira.core.api.item.ItemTagEditor;
import com.akira.core.api.util.CommonUtils;
import com.akira.core.api.util.EntityUtils;
import com.akira.core.api.util.NumberUtils;
import com.akira.core.api.util.PlayerUtils;
import com.akira.undeadwave.UndeadWave;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public abstract class ConsumableItem {
    protected final UndeadWave plugin;
    protected final ConsumableItemType type;

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

        if (inCooldown) {
            player.sendMessage("§c道具冷却中，暂时无法使用。");
            return;
        }

        PlayerUtils.playSound(player, type.getSound(), type.getSoundPitch());
        player.sendMessage("§f你使用了 §d" + type.getDisplayName() + "§f。");
        this.onConsume(player);

        PlayerInventory inventory = player.getInventory();
        int amount = item.getAmount();
        if (amount > 1) item.setAmount(amount - 1);
        else inventory.setItem(slot, null);

        inCooldown = true;
        cooldownTask = Bukkit.getScheduler().runTaskLater(plugin,
                () -> inCooldown = false,
                type.getCooldownTicks());
    }

    public final void resetCooldown() {
        if (cooldownTask != null)
            cooldownTask.cancel();

        inCooldown = false;
        cooldownTask = null;
    }

    public final ItemStack buildItem() {
        ItemBuilder builder = ItemBuilder.create(type.getMaterial())
                .setDisplayName("§d" + type.getDisplayName())
                .addFlags(ItemFlag.values())
                .setLore(this.generateLore());

        if (type.isShiny()) builder.addEnchant(Enchantment.DURABILITY, 1);

        ItemStack item = builder.getResult();
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

    protected final void addPotionEffect(Player player, PotionEffectType type, double duration, int level) {
        Validate.notNull(player);
        Validate.notNull(type);
        NumberUtils.ensurePositive(duration);
        NumberUtils.ensurePositive(level);

        PotionEffect existing = player.getPotionEffect(type);
        if (existing != null) {
            int existingLvl = existing.getAmplifier() + 1;
            if (existingLvl >= level) return;
        }

        int ticks = (int) Math.ceil(duration / 20);
        PotionEffect effect = new PotionEffect(type, ticks, level - 1, false, false, false);

        player.removePotionEffect(type);
        player.addPotionEffect(effect);
    }

    protected final void addPotionEffect(Player player, PotionEffectType type, double duration) {
        this.addPotionEffect(player, type, duration, 1);
    }

    protected final void healInstantly(Player player, double amount) {
        Validate.notNull(player);
        NumberUtils.ensurePositive(amount);

        double newHealth = player.getHealth() + amount;
        double maxHealth = EntityUtils.getMaxHealth(player);

        player.setHealth(Math.min(maxHealth, newHealth));
    }

    protected final void addAbsorption(Player player, double amount) {
        Validate.notNull(player);
        NumberUtils.ensurePositive(amount);

        double existing = player.getAbsorptionAmount();
        player.setAbsorptionAmount(Math.min(20, existing + amount));
    }

    protected abstract void onConsume(Player player);

    protected abstract List<String> getDescription();

    private String[] generateLore() {
        List<String> description = this.getDescription();
        List<String> result = new ArrayList<>();

        long cd = type.getCooldownTicks();
        if (cd > 0) {
            result.add("§f冷却：§e" + NumberUtils.format((cd / 20F)) + "s");
            result.add("");
        }

        if (description != null) {
            description.forEach(line -> result.add("§7" + line));
            result.add("");
        }

        result.add("§d游戏道具 右键使用");
        return result.toArray(String[]::new);
    }
}
