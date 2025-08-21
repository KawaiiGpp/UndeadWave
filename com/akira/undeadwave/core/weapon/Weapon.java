package com.akira.undeadwave.core.weapon;

import com.akira.core.api.item.ItemBuilder;
import com.akira.core.api.item.ItemTagEditor;
import com.akira.core.api.util.NumberUtils;
import com.akira.undeadwave.UndeadWave;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Weapon {
    protected final WeaponType weaponType;
    protected final Material material;
    protected final String displayName;
    protected final List<String> description;
    protected final double damage;
    protected final long cooldownTicks;
    protected final Particle particle;
    protected final boolean melee;

    public Weapon(WeaponType weaponType, Material material,
                  String displayName, boolean melee,
                  double damage, long cooldownTicks,
                  Particle particle, String... description) {
        Validate.notNull(weaponType);
        Validate.notNull(material);
        Validate.notNull(displayName);
        Validate.noNullElements(description);
        Validate.notNull(particle);
        NumberUtils.ensurePositive(damage);
        NumberUtils.ensurePositive(cooldownTicks);

        this.weaponType = weaponType;
        this.material = material;
        this.displayName = displayName;
        this.description = new ArrayList<>(Arrays.asList(description));
        this.melee = melee;
        this.damage = damage;
        this.cooldownTicks = cooldownTicks;
        this.particle = particle;
    }

    public final WeaponType getWeaponType() {
        return weaponType;
    }

    public final Material getMaterial() {
        return material;
    }

    public final String getDisplayName() {
        return displayName;
    }

    public final List<String> getDescription() {
        return new ArrayList<>(description);
    }

    public final double getDamage() {
        return damage;
    }

    public final long getCooldownTicks() {
        return cooldownTicks;
    }

    public final Particle getParticle() {
        return particle;
    }

    public final boolean isMelee() {
        return melee;
    }

    public final boolean matches(ItemStack item) {
        Validate.notNull(item);

        return ItemTagEditor.forItemMeta(UndeadWave.getInstance(), item)
                .hasKey("game.weapon", PersistentDataType.BOOLEAN);
    }

    private List<String> generateItemLore() {
        List<String> lore = new ArrayList<>();

        lore.add("§f伤害：§c" + NumberUtils.format(damage) + "♥");
        lore.add("§f冷却时间：§e" + NumberUtils.format(cooldownTicks / 2F) + "秒");
        lore.add("");
        lore.addAll(description);

        return lore;
    }

    private ItemStack buildItem() {
        ItemStack result =  ItemBuilder.create(material)
                .addFlags(ItemFlag.values())
                .setLore(generateItemLore().toArray(String[]::new))
                .setDisplayName(displayName)
                .getResult();

        ItemTagEditor editor = ItemTagEditor.forItemMeta(UndeadWave.getInstance(), result);
        editor.set("game.weapon", PersistentDataType.BOOLEAN, true);
        editor.set("game.weapon.name", PersistentDataType.STRING, weaponType.name());
        editor.set("game.weapon.cooldown", PersistentDataType.LONG, cooldownTicks);
        editor.set("game.weapon.particle", PersistentDataType.STRING, particle.name());
        this.onItemTagSet(editor);

        editor.apply(result);
        return result;
    }

    protected abstract void onItemTagSet(ItemTagEditor editor);
}
