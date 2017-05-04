package com.drcnetwork.inventorytweak.Events;

import com.drcnetwork.inventorytweak.InventoryTweak;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Reynout on 4/05/2017.
 */
public class InventoryClick implements Listener {


    InventoryTweak plugin;

    public InventoryClick(InventoryTweak plugin)
    {
        this.plugin = plugin;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if(!event.getClickedInventory().getTitle().equals(this.plugin.getTitle()))
        {
            return;
        }
            event.getWhoClicked().getInventory().addItem(new ItemStack(event.getCurrentItem().getType(),64,event.getCurrentItem().getDurability()));

    }


    @EventHandler
    public void onItemMove(InventoryMoveItemEvent event)
    {
        if(event.getDestination().getTitle().equals(this.plugin.getTitle()))
        {
            event.setCancelled(true);
        }

        if(event.getSource().getTitle().equals(this.plugin.getTitle()))
        {
            event.setCancelled(true);
        }
    }

}
