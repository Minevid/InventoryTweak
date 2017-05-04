package com.drcnetwork.inventorytweak;

import com.drcnetwork.inventorytweak.Events.InventoryClick;
import com.drcnetwork.inventorytweak.commands.InventoryCommand;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Reynout on 4/05/2017.
 */
public class InventoryTweak extends JavaPlugin
{

    private List<String> whitelistItems = new ArrayList<>();
    private List<String> blackListItems = new ArrayList<>();
    private String permission;
    private int width;
    private int height;
    private String title;


    @Override
    public void onEnable()
    {

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
    public void onDisable()
    {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();
        logger.info("Saving configs...");
        saveConfig();
        logger.info("Configs saved!");
        logger.info(pdfFile.getName() + " has been disabled");
    }


    public void init()
    {
        this.whitelistItems = getConfig().getStringList("Whitelistitems");
        this.blackListItems = getConfig().getStringList("Blacklistitems");
        this.permission = getConfig().getString("Permission");
        this.width = getConfig().getInt("width");
        this.height = getConfig().getInt("height");
        this.title = getConfig().getString("InvTitle");
    }

    private void registerEvents() {

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new InventoryClick(this), this);

    }

    private void registerCommands() {

        getCommand("gm1").setExecutor(new InventoryCommand(this));
        getCommand("gmc").setExecutor(new InventoryCommand(this));
    }

    public List<String> getWhitelistItems()
    {
        return whitelistItems;
    }

    public List<String> getBlackListItems()
    {
        return blackListItems;
    }

    public String getPermission()
    {
        return permission;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public String getTitle()
    {
        return title;
    }
}
