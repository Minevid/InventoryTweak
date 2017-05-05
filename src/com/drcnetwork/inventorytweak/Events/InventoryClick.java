package com.drcnetwork.inventorytweak.Events;

import com.drcnetwork.inventorytweak.InventoryTweak;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;


/**
 * Created by Reynout on 4/05/2017.
 */
public class InventoryClick implements Listener {


    InventoryTweak plugin;


    public InventoryClick(InventoryTweak plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {



        /*
        if (!event.getInventory().getTitle().contains(this.plugin.getTitle()) && !this.plugin.getWhitelistItems().contains(event.getCurrentItem().getType().toString()) || this.plugin.getBlackListItems().contains(event.getCurrentItem().getType().toString())) {
            event.setCancelled(true);
            return;
        }
        */

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

            }

            System.out.println(String.valueOf(this.plugin.getItemsLeft().size()));
            if (event.getCurrentItem().getType().equals(Material.BARRIER)) {

                event.getWhoClicked().openInventory(this.plugin.getInventories().get(Integer.parseInt(event.getClickedInventory().getName().substring(event.getClickedInventory().getTitle().length() - 1))));
                fillInventory(this.plugin.getInventories().get(Integer.parseInt(event.getClickedInventory().getName().substring(event.getClickedInventory().getTitle().length() - 1))));

            }
        }


    }

    private void fillInventory(Inventory openInventory) {
        System.out.println(String.valueOf(this.plugin.getItemsLeft().size()));
        if (this.plugin.getItemsLeft().isEmpty()) {
            this.plugin.getLogger().warning("We can't fill anymore. We are out of items");
            return;
        }

        System.out.println(this.plugin.getItemsLeft().size()/40);

        System.out.println("BULLSHIT");

        int invID = Integer.parseInt(openInventory.getTitle().substring(openInventory.getTitle().length() - 1))-1;
        System.out.println(this.plugin.getWhitelistItems().size()%40);


        if (this.plugin.getItemsLeft().size() > 40) {
            System.out.println("More then 40 items");
            for (int j = 0; j < 40; j++) {
                Material material = Material.getMaterial(this.plugin.getWhitelistItems().get(j + 40));
                ItemStack newItemStack = new ItemStack(material, 1);
                openInventory.setItem(j, newItemStack);
                openInventory.setItem(53, new ItemStack(Material.BARRIER, 1));

            }

            for (int k = 0; k < 40; k++) {
                this.plugin.removeItems(this.plugin.getWhitelistItems().get(k));
            }


        } else {
            System.out.println("Less then 40 items");
            System.out.println(this.plugin.getItemsLeft().size());
            System.out.println("BITCHES");
            if ((this.plugin.getItemsLeft().size() / 40) == 0) {
                for (int k = 0; k < this.plugin.getItemsLeft().size(); k++) {
                    Material material = Material.getMaterial(this.plugin.getItemsLeft().get(k));
                    ItemStack itemStack = new ItemStack(material, 1);
                    openInventory.setItem(k, itemStack);
                }

                for (int i = 0; i < this.plugin.getWhitelistItems().size() % 40; i++) {
                    System.out.println(i);
                }

                for (int l = 0; l < this.plugin.getItemsLeft().size(); l++) {
                    this.plugin.removeItems(this.plugin.getItemsLeft().get(l));
                }
            }
        }
    }


}
