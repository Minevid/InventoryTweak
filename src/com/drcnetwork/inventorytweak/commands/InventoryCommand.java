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

/**
 * Created by Reynout on 4/05/2017.
 */
public class InventoryCommand implements CommandExecutor {

    InventoryTweak plugin;

    public InventoryCommand(InventoryTweak plugin)
    {
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        this.plugin.init();
        if(!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "You have to be a player to use this command");
            return true;
        }
        Player player = (Player) sender;

        if(!player.isOp() || !player.hasPermission(this.plugin.getPermission()))
        {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that");
            return true;
        }

        Inventory inventory = Bukkit.createInventory(null, this.plugin.getHeight() * this.plugin.getWidth(), this.plugin.getTitle());
        for(String itemName: this.plugin.getWhitelistItems())
        {
            if(!this.plugin.getBlackListItems().contains(itemName))
                inventory.addItem(new ItemStack(Material.getMaterial(itemName), 1));

        }
        player.openInventory(inventory);
        return true;
    }
}
