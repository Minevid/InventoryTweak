package com.drcnetwork.inventorytweak.Events;

import com.drcnetwork.inventorytweak.ItemsGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;


/**
 * Created by Reynout on 4/05/2017.
 */
public class InventoryClick implements Listener {


    ItemsGUI plugin;


    public InventoryClick(ItemsGUI plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        List<Inventory> inventories = this.plugin.getInventories();
        Inventory clickedInventory = event.getClickedInventory();

        if (event.getClickedInventory() == null) {
            event.setCancelled(false);
            return;
        }

        if (!event.getClickedInventory().getName().contains(this.plugin.getTitle())) {
            event.setCancelled(false);
            return;
        }

        if (event.getClickedInventory().getTitle().contains(this.plugin.getTitle())) {
            int firstEmptySlot;
            firstEmptySlot = event.getWhoClicked().getInventory().firstEmpty();

            if (!event.getCurrentItem().getType().equals(Material.BARRIER)) {
                if (firstEmptySlot >= 0) {
                    event.getWhoClicked().getInventory().setItem(event.getWhoClicked().getInventory().firstEmpty(), new ItemStack(event.getCurrentItem().getType(), 64, event.getCurrentItem().getDurability()));
                    event.setCancelled(true);
                } else {
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().sendMessage(ChatColor.RED + "Your inventory is full. Can't place any more items in it");
                }
            } else {
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("NEXT")) {
                    Inventory nextInventory = inventories.get(Integer.parseInt(clickedInventory.getName().substring(clickedInventory.getTitle().length() - 1)));
                    if (nextInventory.equals(inventories.get(inventories.size() - 1)))
                        setBackButton(nextInventory);
                    else if (nextInventory != inventories.get(inventories.size() - 1)) {
                        setBackButton(nextInventory);
                        setNextButton(nextInventory);
                    } else if (nextInventory == inventories.get(0)) {
                        setBackButton(nextInventory);
                        nextInventory.setItem(53, new ItemStack(Material.AIR));
                    }

                    player.openInventory(nextInventory);
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("BACK")) {
                    Inventory previousInventory = inventories.get(Integer.parseInt(clickedInventory.getName().substring(clickedInventory.getTitle().length() - 1)) - 2);
                    if (previousInventory != (inventories.get(0))) {
                        setBackButton(previousInventory);
                    }
                    player.openInventory(previousInventory);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    public void setBackButton(Inventory inventory) {
        ItemStack backItem = new ItemStack(Material.getMaterial(this.plugin.getPreviousButtonItem()));
        ItemMeta backItemMeta = backItem.getItemMeta();
        backItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cBACK"));
        backItem.setItemMeta(backItemMeta);
        inventory.setItem(45, backItem);
    }

    public void setNextButton(Inventory inventory) {
        ItemStack nextItem = new ItemStack(Material.getMaterial(this.plugin.getNextButtonItem()));
        ItemMeta nextItemMeta = nextItem.getItemMeta();
        nextItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aNEXT"));
        nextItem.setItemMeta(nextItemMeta);
        inventory.setItem(53, nextItem);
    }

}
