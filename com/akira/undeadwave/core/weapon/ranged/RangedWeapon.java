package com.akira.undeadwave.core.weapon.ranged;

import com.akira.core.api.item.ItemTagEditor;
import com.akira.core.api.util.*;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.Game;
import com.akira.undeadwave.core.weapon.Weapon;
import com.akira.undeadwave.core.weapon.WeaponAttackType;
import com.akira.undeadwave.core.weapon.WeaponType;
import com.akira.undeadwave.core.weapon.tool.ParticleSpawner;
import org.apache.commons.lang3.Validate;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class RangedWeapon extends Weapon {
    protected final long cooldownTicks;
    protected final ParticleSpawner particleSpawner;
    protected final int maxDistance;
    protected final int maxTargetAmount;
    protected final double stepLength;
    protected final int maxDurability;
    protected final double hitBoxMultiplier;
    protected final boolean piercing;
    protected final boolean repeatHit;

    protected boolean inCooldown;
    protected BukkitTask cooldownResetTask;

    public RangedWeapon(UndeadWave plugin, Game game,
                        WeaponType weaponType, WeaponAttackType attackType,
                        Material material, String displayName, String[] description,
                        double damage, int critDamage, int critChance,
                        long cooldownTicks, ParticleSpawner particleSpawner,
                        int maxDistance, int maxTargetAmount, double stepLength,
                        int maxDurability, double hitBoxMultiplier,
                        boolean piercing, boolean repeatHit) {
        super(plugin, game,
                weaponType, attackType,
                material, displayName, description,
                damage, critDamage, critChance);
        NumberUtils.ensurePositive(cooldownTicks);
        Validate.notNull(particleSpawner);
        NumberUtils.ensurePositive(maxDistance);
        NumberUtils.ensurePositive(maxTargetAmount);
        NumberUtils.ensurePositive(stepLength);
        NumberUtils.ensurePositive(hitBoxMultiplier);
        NumberUtils.ensurePositive(maxDurability);

        this.cooldownTicks = cooldownTicks;
        this.particleSpawner = particleSpawner;
        this.maxDistance = maxDistance;
        this.maxTargetAmount = maxTargetAmount;
        this.stepLength = stepLength;
        this.maxDurability = maxDurability;
        this.hitBoxMultiplier = hitBoxMultiplier;
        this.piercing = piercing;
        this.repeatHit = repeatHit;
    }

    public RangedWeapon(UndeadWave plugin, Game game,
                        WeaponType weaponType, WeaponAttackType attackType,
                        Material material, String displayName, String[] description,
                        double damage, int critDamage, int critChance,
                        long cooldownTicks, Particle particle,
                        int maxDistance, int maxTargetAmount, double stepLength,
                        int maxDurability, double hitBoxMultiplier,
                        boolean piercing, boolean repeatHit) {
        this(plugin, game,
                weaponType, attackType,
                material, displayName, description,
                damage, critDamage, critChance,
                cooldownTicks, (w, l) -> w.spawnParticle(CommonUtils.requireNonNull(particle), l, 1),
                maxDistance, maxTargetAmount, stepLength,
                maxDurability, hitBoxMultiplier,
                piercing, repeatHit);
    }

    public final void onShoot(Player player, ItemStack item, EquipmentSlot slot) {
        Validate.notNull(player);
        Validate.notNull(item);
        Validate.notNull(slot);

        if (this.isInCooldown()) return;
        this.handleShooting(player);

        if (!handleDurabilityChange(item))
            this.startCooldown();
        else handleItemDestruction(player, slot);
    }

    public final void startCooldown() {
        String weaponName = weaponType.name();

        Validate.isTrue(!inCooldown, "Weapon " + weaponName + " is already in cooldown.");
        Validate.isTrue(cooldownResetTask == null, "Cooldown resetting task for " + weaponName + " is already existing.");

        BukkitScheduler scheduler = Bukkit.getScheduler();
        this.cooldownResetTask = scheduler.runTaskLater(plugin, this::endCooldown, cooldownTicks);
        this.inCooldown = true;
    }

    public final boolean isInCooldown() {
        return inCooldown;
    }

    public final void resetCooldown() {
        if (this.isInCooldown())
            cancelCooldownResetTask();
        this.inCooldown = false;
        this.cooldownResetTask = null;
    }

    public final long getCooldownTicks() {
        return cooldownTicks;
    }

    public final ParticleSpawner getParticleSpawner() {
        return particleSpawner;
    }

    public final int getMaxDistance() {
        return maxDistance;
    }

    public final int getMaxTargetAmount() {
        return maxTargetAmount;
    }

    public final double getStepLength() {
        return stepLength;
    }

    public final int getMaxDurability() {
        return maxDurability;
    }

    public final double getHitBoxMultiplier() {
        return hitBoxMultiplier;
    }

    public final boolean isPiercingAllowed() {
        return piercing;
    }

    public final boolean isRepeatHitAllowed() {
        return repeatHit;
    }

    protected List<String> onItemStatsAppend() {
        List<String> list = new ArrayList<>();

        list.add("§f射击距离：§a" + maxDistance + "m");
        list.add("§f冷却：§e" + NumberUtils.format(cooldownTicks / 20F) + "s");
        list.add("§f总耐久：§b" + maxDurability);

        return list;
    }

    protected List<String> onItemDescriptionAppend() {
        List<String> list = new ArrayList<>();

        if (piercing && repeatHit)
            list.add("§4⚔ §c造成增强穿透伤害 §4⚔");
        else if (piercing)
            list.add("§6❁ §e造成穿透伤害 §6❁");

        return list;
    }

    protected void onItemTagAppend(ItemTagEditor editor) {
        Validate.notNull(editor);

        editor.set("ingame.weapon.max_durability", PersistentDataType.INTEGER, maxDurability);
        editor.set("ingame.weapon.durability", PersistentDataType.INTEGER, maxDurability);
    }

    private void endCooldown() {
        String weaponName = weaponType.name();

        Validate.isTrue(inCooldown, "Cooldown for " + weaponName + " was not started.");
        Validate.isTrue(cooldownResetTask != null, "Cooldown resetting task for " + weaponName + " is currently null.");

        inCooldown = false;
        cooldownResetTask = null;
    }

    private void cancelCooldownResetTask() {
        Validate.notNull(cooldownResetTask, "Task for " + weaponType.name() + " is not set.");
        this.cooldownResetTask.cancel();
    }

    private void handleShooting(Player player) {
        Validate.notNull(player);

        if (piercing) PlayerUtils.playSound(player, Sound.ITEM_FLINTANDSTEEL_USE, 1.0F, 0.5F);
        else PlayerUtils.playSound(player, Sound.ENTITY_IRON_GOLEM_HURT, 0.5F, 2.0F);

        Location playerLocation = player.getEyeLocation();
        Vector baseVector = playerLocation.getDirection();

        World world = playerLocation.getWorld();
        Validate.notNull(world);

        List<LivingEntity> markedEntities = new ArrayList<>();

        for (double m = stepLength; m <= maxDistance; m += stepLength) {
            Vector clonedVector = baseVector.clone().multiply(m);
            Location location = playerLocation.clone().add(clonedVector);

            if (this.isBlocked(location)) break;
            if (m >= 2) particleSpawner.run(location);

            List<LivingEntity> validEntities = this.getValidEntities(location, world);
            validEntities.removeAll(markedEntities);
            if (validEntities.isEmpty()) continue;

            handleAttacking(player, validEntities);
            if (!repeatHit) markedEntities.addAll(validEntities);
            if (!piercing) break;
        }
    }

    private boolean handleDurabilityChange(ItemStack item) {
        Validate.notNull(item);

        String keyName = "ingame.weapon.durability";
        ItemTagEditor editor = ItemTagEditor.forItemMeta(plugin, item);
        Validate.isTrue(editor.hasKey(keyName, PersistentDataType.INTEGER),
                "Cannot find the item tag for durability: " + weaponType.name());
        int durability = editor.get(keyName, PersistentDataType.INTEGER) - 1;
        Validate.isTrue(durability >= 0, "Durability for " + weaponType.name() + " is negative: " + durability);

        editor.set(keyName, PersistentDataType.INTEGER, durability);
        editor.apply(item);

        if (durability > 0) {
            ItemMeta meta = item.getItemMeta();
            if (!(meta instanceof Damageable damageable)) return false;

            int vanillaMax = item.getType().getMaxDurability();
            float ratio = durability / (float) maxDurability;
            int itemDamage = vanillaMax - Math.max(Math.round(ratio * vanillaMax), 1);

            damageable.setDamage(itemDamage);
            item.setItemMeta(meta);

            return false;
        } else return true;
    }

    private void handleAttacking(Player player, List<LivingEntity> targets) {
        Validate.notNull(player);
        Validate.noNullElements(targets);

        boolean crit = this.rollCritChance();
        double damageDealt = crit ? calculateCritDamage() : damage;
        float soundPitch = crit ? 1.0F : 2.0F;

        targets.forEach(entity -> handleEntityDamage(player, entity, damageDealt, crit));
        PlayerUtils.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, soundPitch);
    }

    private void handleEntityDamage(Player attacker, LivingEntity entity, double damage, boolean crit) {
        Validate.notNull(attacker);
        Validate.notNull(entity);
        NumberUtils.ensurePositive(damage);

        entity.damage(damage, attacker);
        entity.setVelocity(entity.getVelocity().clone().setY(0));

        if (crit) WorldUtils.playParticle(entity.getEyeLocation(), Particle.FLAME, 10, 0.5);
    }

    private void handleItemDestruction(Player player, EquipmentSlot slot) {
        Validate.notNull(player);
        Validate.notNull(slot);

        PlayerUtils.playSound(player, Sound.BLOCK_GLASS_BREAK, 0.5F);

        player.getInventory().setItem(slot, null);
        player.sendMessage(this.getItemDestroyedMessage());
    }

    private List<LivingEntity> getValidEntities(Location location, World world) {
        Validate.notNull(location);
        Validate.notNull(world);

        double hitboxSize = (stepLength / 2) * hitBoxMultiplier;
        BoundingBox box = BoundingBox.of(location, hitboxSize, hitboxSize, hitboxSize);

        Collection<Entity> entities = world.getNearbyEntities(box);
        List<LivingEntity> filtered = entities.stream()
                .filter(entity -> !(entity instanceof Player))
                .filter(entity -> !entity.isDead())
                .filter(entity -> entity instanceof LivingEntity)
                .map(entity -> (LivingEntity) entity)
                .toList();

        return CommonUtils.getRandomElement(filtered, maxTargetAmount);
    }

    private boolean isBlocked(Location location) {
        Validate.notNull(location);

        Collection<BoundingBox> list = location.getBlock().getCollisionShape().getBoundingBoxes();
        if (list.isEmpty()) return false;
        return list.stream().allMatch(box -> box.getWidthX() >= 1 && box.getWidthZ() >= 1 && box.getHeight() >= 1);
    }
}
