package main;

import main.commands.WhistleCreator;
import main.events.BellEvent;
import main.events.WhistleEvent;
import main.events.hitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;


public class Main extends JavaPlugin {

    public static Plugin plugin;
    public static JavaPlugin javaPlugin;
    public static Logger logger;

    @Override
    public void onEnable(){
        logger = Logger.getLogger("Minecraft");
        plugin = this;
        javaPlugin = this;

        //Create config file.
        plugin.saveDefaultConfig();

        registerEvents();
        registerCommands();


    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = Logger.getLogger("Minecraft");
        logger.info(pdfFile.getName() + "has successfully disabled.");
    }



    public void registerCommands(){
        getCommand("whistle").setExecutor(new WhistleCreator());

    }

    public void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BellEvent(), this);
        pm.registerEvents(new WhistleEvent(),this);
        pm.registerEvents(new hitEvent(), this);
    }

    public static Plugin getPlugin() {
        return plugin;
    }

}
