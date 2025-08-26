package com.akira.undeadwave.core;

import com.akira.core.api.util.EntityUtils;
import com.akira.core.api.util.PlayerUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.apache.commons.lang3.Validate;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Game extends GameBase {
    public Game(UndeadWave plugin) {
        super(plugin);
    }

    public void startGame(Player player) {
        Validate.notNull(player);
        validateState(GameState.WAITING);

        this.resetPlayerStat(player);
        player.teleport(this.getLocationConfig().getSpawnpoint());

        sendStartMessage(player);
        setupStartingGears(player);

        this.session = new GameSession(player);
        this.state = GameState.STARTED;
    }

    public void endGame(boolean victory) {
        validateState(GameState.STARTED);
        Player player = this.getSessionSafely().getOwner();

        this.resetPlayerStat(player);
        player.teleport(this.getLocationConfig().getLobby());

        sendEndMessage(player, victory);

        this.session = null;
        this.state = GameState.WAITING;
    }

    private void resetPlayerStat(Player player) {
        Validate.notNull(player);

        player.getActivePotionEffects().stream()
                .map(PotionEffect::getType)
                .forEach(player::removePotionEffect);
        player.getInventory().clear();

        EntityUtils.setMaxHealth(player, 20);
        player.setHealth(EntityUtils.getMaxHealth(player));

        player.setExp(0);
        player.setLevel(0);
    }

    private void setupStartingGears(Player player) {
        Validate.notNull(player);

        for (WeaponType value : WeaponType.values()) {
            giveWeapon(player, value);
        }
    }

    private void giveWeapon(Player player, WeaponType type) {
        player.getInventory().addItem(weaponManager.fromWeaponType(type).buildItem());
    }

    private void sendStartMessage(Player player) {
        Validate.notNull(player);

        player.sendMessage("§a游戏已经开始！");
        PlayerUtils.playSound(player, Sound.ENTITY_PLAYER_LEVELUP);
    }

    private void sendEndMessage(Player player, boolean victory) {
        Validate.notNull(player);

        Sound sound = victory ? Sound.ENTITY_ENDER_DRAGON_GROWL : Sound.ENTITY_WITHER_DEATH;
        PlayerUtils.playSound(player, sound);

        player.sendMessage("§a游戏结束，" + (victory ? "恭喜你赢了！" : "很遗憾你输了！"));
    }
}
