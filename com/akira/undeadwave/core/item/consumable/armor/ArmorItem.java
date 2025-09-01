package com.akira.undeadwave.core.item.consumable.armor;

import com.akira.core.api.item.ItemBuilder;
import com.akira.undeadwave.UndeadWave;
import com.akira.undeadwave.core.item.consumable.ConsumableItem;
import com.akira.undeadwave.core.item.consumable.ConsumableItemType;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class ArmorItem extends ConsumableItem {
    protected final String namePrefix;

    public ArmorItem(UndeadWave plugin, ConsumableItemType type, String namePrefix) {
        super(plugin, type);

        Validate.notNull(namePrefix);
        this.namePrefix = namePrefix;
    }

    protected final void onConsume(Player player) {
        player.getInventory().setArmorContents(this.getArmorSet());
    }

    protected final List<String> getDescription() {
        return List.of("手持并右键即可使用，", "可直接覆盖旧盔甲。");
    }

    protected abstract Material getHelmet();

    protected abstract Material getChestplate();

    protected abstract Material getLeggings();

    protected abstract Material getBoots();

    protected abstract void onItemArmorAppend(ItemStack armorItem);

    private ItemStack[] getArmorSet() {
        ItemStack helmet = createItem("头盔", this.getHelmet());
        ItemStack chestplate = createItem("胸甲", this.getChestplate());
        ItemStack leggings = createItem("护腿", this.getLeggings());
        ItemStack boots = createItem("靴子", this.getBoots());

        return new ItemStack[]{boots, leggings, chestplate, helmet};
    }

    private ItemStack createItem(String name, Material material) {
        Validate.notNull(name);

        if (material != null) {
            ItemStack item = ItemBuilder.create(material)
                    .addFlags(ItemFlag.values())
                    .setDisplayName("§d" + namePrefix + name)
                    .setUnbreakable(true)
                    .getResult();

            this.onItemArmorAppend(item);
            return item;
        }
        else return null;
    }
}
