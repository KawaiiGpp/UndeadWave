package com.akira.undeadwave.core;

import com.akira.core.api.util.EntityUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.weapon.WeaponType;
import org.apache.commons.lang3.Validate;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public class Game extends GameBase {
    public Game(UndeadWave plugin) {
        super(plugin);
    }

    public void startGame(Player player) {
        Validate.notNull(player);
        validateState(GameState.WAITING);

        this.session = new GameSession(player);
        this.state = GameState.STARTED;

        teleport(this.getLocationConfig().getSpawnpoint());
        resetPlayerStat();
        sendStartMessage();
        setupStartingGears();
    }

    public void endGame(boolean victory) {
        validateState(GameState.STARTED);

        sendEndMessage(victory);
        resetPlayerStat();
        teleport(this.getLocationConfig().getLobby());

        this.session = null;
        this.state = GameState.WAITING;
    }

    private void resetPlayerStat() {
        Player player = this.getIngamePlayer();

        player.getActivePotionEffects().stream()
                .map(PotionEffect::getType)
                .forEach(player::removePotionEffect);
        player.getInventory().clear();

        EntityUtils.setMaxHealth(player, 20);
        player.setHealth(EntityUtils.getMaxHealth(player));

        player.setExp(0);
        player.setLevel(0);
    }

    private void setupStartingGears() {
        this.giveWeapon(WeaponType.WOOD_KNIFE);
        this.giveWeapon(WeaponType.PISTOL);
    }

    private void giveWeapon(WeaponType type) {
        PlayerInventory inventory = this.getIngamePlayer().getInventory();
        inventory.addItem(weaponManager.fromWeaponType(type).buildItem());
    }

    private void sendStartMessage() {
        send("§a游戏已经开始！");
        playSound(Sound.ENTITY_PLAYER_LEVELUP);
    }

    private void sendEndMessage(boolean victory) {
        playSound(victory ? Sound.ENTITY_ENDER_DRAGON_GROWL : Sound.ENTITY_WITHER_DEATH);
        send("§a游戏结束，" + (victory ? "恭喜你赢了！" : "很遗憾你输了！"));
    }
}
