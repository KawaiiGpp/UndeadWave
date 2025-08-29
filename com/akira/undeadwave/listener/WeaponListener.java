package com.akira.undeadwave.listener;

import com.akira.core.api.util.EventUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.Game;
import com.akira.undeadwave.core.item.weapon.WeaponAttackType;
import com.akira.undeadwave.core.item.weapon.WeaponManager;
import com.akira.undeadwave.core.item.weapon.base.MeleeWeapon;
import com.akira.undeadwave.core.item.weapon.base.RangedWeapon;
import com.akira.undeadwave.core.item.weapon.base.Weapon;
import com.akira.undeadwave.core.item.weapon.tool.MeleeAttackData;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unchecked")
public class WeaponListener extends ListenerBase {
    public WeaponListener(UndeadWave plugin) {
        super(plugin);
    }

    @EventHandler
    public void onEntityDamaged(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player player)) return;
        if (!this.isIngamePlayer(player)) return;
        if (this.cancelInvalidAttack(e)) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (this.cancelIfNothingInHand(player, item, e)) return;
        LivingEntity victim = (LivingEntity) e.getEntity();

        MeleeWeapon meleeWeapon = parseItem(item, WeaponAttackType.MELEE, MeleeWeapon.class);
        if (meleeWeapon != null) {
            DamageCause cause = e.getCause();
            boolean sweep = cause == DamageCause.ENTITY_SWEEP_ATTACK;
            MeleeAttackData data = new MeleeAttackData(player, victim, sweep);

            meleeWeapon.onAttack(data);
            this.applyMeleeAttackData(e, data);
            return;
        }

        RangedWeapon rangedWeapon = parseItem(item, WeaponAttackType.RANGED, RangedWeapon.class);
        if (rangedWeapon != null) {
            if (victim.hasMetadata("ingame.ranged_weapon.targeted")) return;

            e.setCancelled(true);
            player.sendMessage("§c枪械类武器只能用于远程射击。");
            return;
        }

        e.setCancelled(true);
        player.sendMessage("§c你无法通过此途径攻击实体。");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!this.isIngamePlayer(player)) return;

        e.setCancelled(true);
        ItemStack item = e.getItem();

        RangedWeapon rangedWeapon = parseItem(item, WeaponAttackType.RANGED, RangedWeapon.class);
        if (rangedWeapon == null) return;

        Action action = e.getAction();
        EquipmentSlot hand = e.getHand();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;
        if (hand != EquipmentSlot.HAND) return;

        rangedWeapon.onShoot(player, e.getItem(), e.getHand());
    }

    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent e) {
        Player player = e.getPlayer();
        if (!this.isIngamePlayer(player)) return;

        ItemStack item = e.getBrokenItem();
        Weapon weapon = parseItem(item, WeaponAttackType.MELEE, MeleeWeapon.class);
        if (weapon == null) return;

        player.sendMessage(weapon.getItemDestroyedMessage());
    }

    private void applyMeleeAttackData(EntityDamageByEntityEvent e, MeleeAttackData data) {
        Validate.notNull(e);
        Validate.notNull(data);

        LivingEntity livingEntity = (LivingEntity) e.getEntity();

        if (data.shouldBeCancelled()) {
            e.setCancelled(true);
            return;
        }

        if (!data.isTrueDamage()) {
            e.setDamage(data.getDamage());
            return;
        }

        double damage = data.getDamage();
        double health = livingEntity.getHealth();

        if (damage >= health) {
            e.setDamage(Long.MAX_VALUE);
            return;
        }

        livingEntity.setHealth(health - damage);
        e.setDamage(0);
    }

    private <T extends Weapon> T parseItem(ItemStack item, WeaponAttackType type, Class<T> clz) {
        Validate.notNull(type);
        Validate.notNull(clz);
        if (item == null) return null;

        Game game = plugin.getGame();
        WeaponManager manager = game.getWeaponManager();
        Weapon weapon = manager.fromItemStack(item);

        if (weapon == null) return null;
        if (weapon.getAttackType() != type) return null;
        if (!clz.isInstance(weapon)) return null;

        return (T) weapon;
    }

    private boolean cancelInvalidAttack(EntityDamageByEntityEvent event) {
        Validate.notNull(event);

        Entity victim = event.getEntity();
        DamageCause cause = event.getCause();

        boolean shouldCancel = !(victim instanceof LivingEntity livingEntity)
                || (plugin.getGame().getEnemyManager().fromEntity(livingEntity) == null)
                || (cause != DamageCause.ENTITY_ATTACK && cause != DamageCause.ENTITY_SWEEP_ATTACK);

        return EventUtils.cancelIf(shouldCancel, event);
    }

    private boolean cancelIfNothingInHand(Player player, ItemStack item, Cancellable event) {
        Validate.notNull(player);
        Validate.notNull(item);
        Validate.notNull(event);

        return EventUtils.cancelIf(item.getType().isAir(), event,
                () -> player.sendMessage("§c你必须手持武器才能攻击。"));
    }
}
