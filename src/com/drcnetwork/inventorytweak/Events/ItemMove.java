package com.drcnetwork.inventorytweak.Events;

import com.drcnetwork.inventorytweak.InventoryTweak;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;


/**
 * Created by %User
 */
public class ItemMove implements Listener {

    private static boolean moveCancelled;
    InventoryTweak plugin;

    public ItemMove(InventoryTweak plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onItemMove(InventoryMoveItemEvent event) {
        if (!event.getDestination().getTitle().equals(this.plugin.getTitle())) {
            moveCancelled = true;
            event.setCancelled(true);

        }

    }

    public static boolean isMoveCancelled() {
        return moveCancelled;
    }

}
