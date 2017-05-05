package com.drcnetwork.inventorytweak.Events;

import com.drcnetwork.inventorytweak.ItemsGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


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
            } else {
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("NEXT")) {
                    event.getWhoClicked().openInventory(this.plugin.getInventories().get(Integer.parseInt(event.getClickedInventory().getName().substring(event.getClickedInventory().getTitle().length() - 1))));
                    fillInventory(this.plugin.getInventories().get(Integer.parseInt(event.getClickedInventory().getName().substring(event.getClickedInventory().getTitle().length() - 1))));
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("BACK")) {
                    System.out.println(Integer.parseInt(event.getClickedInventory().getName().substring(event.getClickedInventory().getTitle().length() - 1)));
                    System.out.println(Integer.parseInt(event.getClickedInventory().getName().substring(event.getClickedInventory().getTitle().length() - 1)) - 2);
                    event.getWhoClicked().openInventory(this.plugin.getInventories().get(Integer.parseInt(event.getClickedInventory().getName().substring(event.getClickedInventory().getTitle().length() - 1)) - 2));
                }
            }
        }
    }
    private void fillInventory(Inventory openInventory) {
        System.out.println(String.valueOf(this.plugin.getItemsLeft().size()));
        if (this.plugin.getItemsLeft().isEmpty()) {
            this.plugin.getLogger().warning("We can't fill anymore. We are out of items");
            return;
        }

        System.out.println(this.plugin.getItemsLeft().size() / 40);

        System.out.println("BULLSHIT");

        int invID = Integer.parseInt(openInventory.getTitle().substring(openInventory.getTitle().length() - 1)) - 1;
        System.out.println(this.plugin.getWhitelistItems().size() % 40);

        if (this.plugin.getWhitelistItems().size() / 40 == 0) {
            if (this.plugin.getWhitelistItems().size() % 40 > 40) {
                System.out.println("More then 40 items");
                for (int j = 0; j < 40; j++) {
                    Material material = Material.getMaterial(this.plugin.getWhitelistItems().get(j));
                    ItemStack newItemStack = new ItemStack(material, 1);
                    openInventory.setItem(j, newItemStack);
                    ItemStack nextItem = new ItemStack(Material.getMaterial(this.plugin.getNextButtonItem()));
                    ItemMeta nextItemMeta = nextItem.getItemMeta();
                    nextItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aNEXT"));
                    nextItem.setItemMeta(nextItemMeta);
                    openInventory.setItem(53, nextItem);
                    ItemStack backItem = new ItemStack(Material.getMaterial(this.plugin.getPreviousButtonItem()));
                    ItemMeta backItemMeta = backItem.getItemMeta();
                    backItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cBACK"));
                    backItem.setItemMeta(backItemMeta);
                    openInventory.setItem(45, backItem);
                }
                System.out.println("Clearing itemsLeft list");
                this.plugin.getItemsLeft().clear();
                for (int l = 0; l < this.plugin.getItemsLeft().size(); l++) {
                    this.plugin.getItemsLeft().remove(this.plugin.getWhitelistItems().get(l));
                }
                //for (int k = 0; k < 40; k++) {
                //    this.plugin.removeItems(this.plugin.getWhitelistItems().get(k));
                //}


            } else {
                System.out.println("Less then 40 items");
                System.out.println(this.plugin.getItemsLeft().size());
                System.out.println("BITCHES");
                for (int k = 0; k < this.plugin.getWhitelistItems().size() % 40; k++) {
                    Material material = Material.getMaterial(this.plugin.getItemsLeft().get(k));
                    ItemStack itemStack = new ItemStack(material, 1);
                    openInventory.setItem(k, itemStack);
                }
                ItemStack backItem = new ItemStack(Material.getMaterial(this.plugin.getPreviousButtonItem()));
                ItemMeta backItemMeta = backItem.getItemMeta();
                backItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cBACK"));
                backItem.setItemMeta(backItemMeta);
                openInventory.setItem(45, backItem);

                for (int i = 0; i < this.plugin.getWhitelistItems().size() % 40; i++) {
                    System.out.println(i);
                }

                for (int l = 0; l < this.plugin.getItemsLeft().size(); l++) {
                    this.plugin.getItemsLeft().remove(this.plugin.getWhitelistItems().get(l));
                }

            }
        }else{
            System.out.println("More then 40 items");
            for (int k = 0; k < 40; k++) {
                Material material = Material.getMaterial(this.plugin.getItemsLeft().get(k));
                ItemStack newItemStack = new ItemStack(material, 1);
                openInventory.setItem(k, newItemStack);
            }


            ItemStack nextItem = new ItemStack(Material.getMaterial(this.plugin.getNextButtonItem()));
            ItemMeta nextItemMeta = nextItem.getItemMeta();
            nextItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aNEXT"));
            nextItem.setItemMeta(nextItemMeta);
            openInventory.setItem(53, nextItem);
            ItemStack backItem = new ItemStack(Material.getMaterial(this.plugin.getPreviousButtonItem()));
            ItemMeta backItemMeta = backItem.getItemMeta();
            backItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cBACK"));
            backItem.setItemMeta(backItemMeta);
            openInventory.setItem(45, backItem);

            for (int l = 0; l < this.plugin.getItemsLeft().size(); l++) {
                this.plugin.getItemsLeft().remove(this.plugin.getWhitelistItems().get(l));
            }



        }
    }


}
