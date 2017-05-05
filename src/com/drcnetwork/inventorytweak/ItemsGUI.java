package com.drcnetwork.inventorytweak;

import com.drcnetwork.inventorytweak.Events.InventoryClick;
import com.drcnetwork.inventorytweak.Events.ItemMove;
import com.drcnetwork.inventorytweak.commands.InventoryCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Reynout on 4/05/2017.
 */
public class ItemsGUI extends JavaPlugin {

    private List<String> whitelistItems = new ArrayList<>();
    private List<String> blackListItems = new ArrayList<>();
    private List<String> itemsLeft = new ArrayList<>();

    private String nextButtonItem;
    private String previousButtonItem;
    private String permission;
    private String title;
    private List<Inventory> inventories = new ArrayList<>();


    @Override
    public void onEnable() {

        saveDefaultConfig();
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();
        logger.info("Loading commands...");
        registerCommands();
        logger.info("Commands loaded!");
        logger.info("Loading events...");
        registerEvents();
        logger.info("Events loaded");
        logger.info(pdfFile.getName() + " has been enabled!");


    }

    @Override
    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();
        logger.info("Saving configs...");
        saveConfig();
        logger.info("Configs saved!");
        logger.info(pdfFile.getName() + " has been disabled");
    }


    public void init() {
        this.whitelistItems = getConfig().getStringList("Whitelistitems");
        this.blackListItems = getConfig().getStringList("Blacklistitems");
        this.permission = getConfig().getString("Permission");
        this.title = getConfig().getString("InvTitle");
        this.nextButtonItem = getConfig().getString("nextPageItem");
        this.previousButtonItem = getConfig().getString("previousPageItem");

        createInventories();
    }

    private void registerEvents() {

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new InventoryClick(this), this);
        pm.registerEvents(new ItemMove(this), this);

    }

    private void registerCommands() {

        getCommand("gm1").setExecutor(new InventoryCommand(this));
        getCommand("gmc").setExecutor(new InventoryCommand(this));
    }

    public List<String> getWhitelistItems() {
        return whitelistItems;
    }

    public List<String> getBlackListItems() {
        return blackListItems;
    }

    public String getPermission() {
        return permission;
    }

    public String getTitle() {
        return title;
    }

    public void createInventories() {
        for (int i = 1; i <= (this.whitelistItems.size() / 40) + 1; i++) {
            inventories.add(Bukkit.createInventory(null, 6 * 9, this.getTitle() + " #" + i));
        }
    }

    public List<Inventory> getInventories() {
        return this.inventories;
    }

    /* public void setItemsLeft()
     {
         this.itemsLeft = this.whitelistItems;
     }
 */
    public List<String> getItemsLeft() {
        return this.itemsLeft;
    }

    public void addItem(String item) {
        this.itemsLeft.add(item);

    }


    public void removeItems(String item) {
        this.itemsLeft.add(item);
    }

    public String getNextButtonItem() {
        return this.nextButtonItem;
    }

    public String getPreviousButtonItem() {
        return this.previousButtonItem;
    }

}
