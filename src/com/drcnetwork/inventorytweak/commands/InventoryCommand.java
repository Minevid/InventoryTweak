package com.drcnetwork.inventorytweak.commands;

import com.drcnetwork.inventorytweak.InventoryTweak;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Reynout on 4/05/2017.
 */
public class InventoryCommand implements CommandExecutor {

    InventoryTweak plugin;

    public InventoryCommand(InventoryTweak plugin) {
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

        List<String> itemsLeft = this.plugin.getWhitelistItems();
        int counterAmount = 1;

            for(int i = 0; i < 40; i++)
            {
                Material material = Material.getMaterial(this.plugin.getWhitelistItems().get(i));
                ItemStack newItemStack = new ItemStack(material,1, material.getMaxDurability());
                this.plugin.getInventories().get(0).setItem(i, newItemStack);
                counterAmount++;
            }

            for(int i = 0; i < counterAmount-1; i++)
            {
                itemsLeft.remove(this.plugin.getWhitelistItems().get(i));
                System.out.println(i + " " + this.plugin.getWhitelistItems().get(i));
            }

            this.plugin.getInventories().get(0).setItem(53,new ItemStack(Material.BARRIER, 1));
            player.openInventory(this.plugin.getInventories().get(0));
            return true;

    }
}
