package com.akira.undeadwave.core;

import com.akira.core.api.util.CommonUtils;
import com.akira.core.api.util.EntityUtils;
import com.akira.core.api.util.NumberUtils;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.config.LocationConfig;
import com.akira.undeadwave.config.SettingsConfig;
import com.akira.undeadwave.core.enemy.EnemyType;
import com.akira.undeadwave.core.item.weapon.WeaponType;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.UUID;

public class Game extends GameBase {
    public Game(UndeadWave plugin) {
        super(plugin);
    }

    public void startGame(Player player) {
        Validate.notNull(player);
        validateState(GameState.WAITING);

        this.session = new GameSession(plugin, player);
        this.state = GameState.STARTED;

        teleport(this.getLocationConfig().getSpawnpoint());
        resetPlayerStat();
        sendStartMessage();

        setupStartingGears();
        startNextRound();
    }

    public void endGame(boolean victory) {
        validateState(GameState.STARTED);

        sendEndMessage(victory);
        resetPlayerStat();
        teleport(this.getLocationConfig().getLobby());

        weaponManager.resetRangedWeaponCooldown();
        consumableItemManager.resetCooldown();
        if (!victory) this.removeExistingEnemies();

        this.session = null;
        this.state = GameState.WAITING;
    }

    public void handleEnemyKilled(UUID uniqueId) {
        Validate.notNull(uniqueId);

        session.removeAliveEnemy(uniqueId);
        if (!session.isRoundCleared()) return;

        if (session.isMaxRoundReached())
            endGame(true);
        else startNextRound();
    }

    private void removeExistingEnemies() {
        for (UUID uuid : session.getAliveEnemies()) {
            Entity entity = Bukkit.getEntity(uuid);

            if (entity != null) {
                if (!entity.isValid()) {
                    String type = entity.getType().name();

                    Location l = entity.getLocation();
                    String x = NumberUtils.format(l.getX());
                    String y = NumberUtils.format(l.getY());
                    String z = NumberUtils.format(l.getZ());

                    plugin.logErr("试图清除实体 " + type + " (" + uuid + ") 时发现其无效。");
                    plugin.logErr("该实体位于：" + l.getWorld() + ":" + x + "," + y + "," + z);
                } else entity.remove();
            } else plugin.logErr("试图清除实体时，无法找到对象：" + uuid);
        }
    }

    private void startNextRound() {
        session.increaseRoundCounter();

        SettingsConfig settings = this.getSettingsConfig();
        LocationConfig locations = this.getLocationConfig();
        int round = session.getCurrentRound();

        send("§f第 §2" + round + " §f回合开始了！");

        int enemyAmount = round * settings.getMonstersPerRound();
        int weightSum = Arrays.stream(EnemyType.values()).mapToInt(EnemyType::getWeight).sum();

        for (int i = 0; i < enemyAmount; i++) {
            int weightPoint = CommonUtils.getRandom().nextInt(weightSum) + 1;
            EnemyType type = this.randomEnemyType(weightPoint);
            Location location = CommonUtils.getRandomElement(locations.getMonsterPoints());

            Monster monster = enemyManager.fromType(type).spawn(location);
            this.session.addAliveEnemy(monster.getUniqueId());
        }
    }

    private EnemyType randomEnemyType(int randomPoint) {
        NumberUtils.ensurePositive(randomPoint);

        int counter = 0;
        for (EnemyType value : EnemyType.values()) {
            int weight = value.getWeight();
            NumberUtils.ensurePositive(weight);

            counter += weight;
            if (randomPoint <= counter) return value;
        }

        throw new UnsupportedOperationException("Unreachable code executed.");
    }

    private void resetPlayerStat() {
        Player player = this.getIngamePlayer();

        player.getActivePotionEffects().stream()
                .map(PotionEffect::getType)
                .forEach(player::removePotionEffect);
        player.getInventory().clear();

        EntityUtils.setMaxHealth(player, 20);
        player.setHealth(EntityUtils.getMaxHealth(player));

        player.setAbsorptionAmount(0);
        player.setArrowsInBody(0);

        player.setExp(0);
        player.setLevel(0);
    }

    private void setupStartingGears() {
        this.giveWeapon(WeaponType.NETHERITE_MINER);
        this.giveWeapon(WeaponType.LAVA_LAUNCHER);
        this.giveWeapon(WeaponType.DIAMOND_SHOTGUN);
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
