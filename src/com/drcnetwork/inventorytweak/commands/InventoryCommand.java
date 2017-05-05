package com.drcnetwork.inventorytweak.commands;

import com.drcnetwork.inventorytweak.ItemsGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reynout on 4/05/2017.
 */
public class InventoryCommand implements CommandExecutor {

    ItemsGUI plugin;
    private boolean firstTime = true;

    public InventoryCommand(ItemsGUI plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        this.plugin.init();
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You have to be a player to use this command");
            return true;
        }
        Player player = (Player) sender;

        if (!player.isOp() || !player.hasPermission(this.plugin.getPermission())) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that");
            return true;
        }

        if (this.plugin.getWhitelistItems().isEmpty()) {
            this.plugin.getLogger().warning("You can't have an empty list of whitelisted Items");
            return true;
        }

        if (this.plugin.getWhitelistItems().size() > 40) {
            ItemStack nextItem = new ItemStack(Material.getMaterial(this.plugin.getNextButtonItem()));
            ItemMeta nextItemMeta = nextItem.getItemMeta();
            nextItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aNEXT"));
            nextItem.setItemMeta(nextItemMeta);
            this.plugin.getInventories().get(0).setItem(53, nextItem);
        }

        if (firstTime) {
            fillInventory(this.plugin.getInventories());
            firstTime = false;
        }
        player.openInventory(this.plugin.getInventories().get(0));
        return true;
    }

    public void fillInventory(List<Inventory> inventories) {
        List<String> whitelistItems = new ArrayList<>();
        for (String s : this.plugin.getWhitelistItems()) {
            whitelistItems.add(s);
        }

        System.out.println(whitelistItems.size());
        List<String> itemsLeft = this.plugin.getWhitelistItems();

        if (whitelistItems.size() <= 40) {
            for (int i = 0; i < whitelistItems.size(); i++) {
                Material material = Material.getMaterial(whitelistItems.get(i));
                ItemStack item = new ItemStack(material, 1);
                item.setDurability(material.getMaxDurability());
                inventories.get(0).setItem(i, item);
            }
        } else {
            for (int j = 0; j < (inventories.size() - 1); j++) {
                for (int k = 0; k < 40; k++) {
                    Material material = Material.getMaterial(whitelistItems.get(k));
                    ItemStack itemStack = new ItemStack(material, 1);
                    itemStack.setDurability(material.getMaxDurability());
                    inventories.get(j).setItem(k, itemStack);
                    itemsLeft.remove(itemStack.getType().toString());
                }
            }
            if (!itemsLeft.isEmpty()) {
                int numberLeft = whitelistItems.size() % 40;
                System.out.println(numberLeft);
                Inventory latestInventory = inventories.get(inventories.size() - 1);
                System.out.println(itemsLeft.size());
                for (int l = 0; l < whitelistItems.size() % 40; l++) {
                    Material material = Material.getMaterial(whitelistItems.get(l));
                    ItemStack itemStack = new ItemStack(material, 1);
                    itemStack.setDurability(material.getMaxDurability());
                    latestInventory.setItem(l, itemStack);
                }
                itemsLeft.clear();
            }
        }
    }
}
