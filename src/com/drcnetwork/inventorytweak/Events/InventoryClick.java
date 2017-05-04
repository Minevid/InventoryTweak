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


            if (event.getCurrentItem().getType().equals(Material.BARRIER)) {

                event.getWhoClicked().openInventory(this.plugin.getInventories().get(Integer.parseInt(event.getClickedInventory().getName().substring(event.getClickedInventory().getTitle().length() - 1))));
                fillInventory(this.plugin.getInventories().get(Integer.parseInt(event.getClickedInventory().getName().substring(event.getClickedInventory().getTitle().length() - 1))));

            }
        }


    }

    private void fillInventory(Inventory openInventory) {
        List<String> left = this.plugin.getItemsLeft();
        if (left.isEmpty()) {
            this.plugin.getLogger().warning("We can't fill anymore. We are out of items");
            return;
        }


        if ((left.size() / 40) != 0 && left.size() >= 40) {
            int counterTest = 1;
            for (int j = 0; j < 40; j++) {
                System.out.println(counterTest + " Bitch " + (j));
                Material material = Material.getMaterial(left.get(j));
                ItemStack newItemStack = new ItemStack(material, 1);
                openInventory.setItem(j, newItemStack);
                openInventory.setItem(53, new ItemStack(Material.BARRIER, 1));
                counterTest++;
            }

            for (int k = 0; k < 40; k++) {
                Material material = Material.getMaterial(left.get(k));
                ItemStack newItemStack = new ItemStack(material, 1);
                left.remove(newItemStack.getType().toString());
            }

        } else {
            System.out.println("BITCHES");
            int counterTest2 = 1;
            if ((this.plugin.getItemsLeft().size() / 40) == 0) {
                for (int k = 0; k < this.plugin.getItemsLeft().size() % 40; k++) {
                    Material material = Material.getMaterial(left.get(k));
                    ItemStack itemStack = new ItemStack(material, 1);
                    System.out.println(counterTest2 + " Homo " + (k));
                    openInventory.setItem(k, itemStack);

                    counterTest2++;
                }
                for (int l = 0; l < left.size() % 40; l++) {
                    Material material = Material.getMaterial(left.get(l));
                    ItemStack itemStack = new ItemStack(material, 1);
                    left.remove(itemStack.getType().toString());
                }
            }
        }
    }


}
