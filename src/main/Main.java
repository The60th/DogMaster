package main;

import main.tests.events.DogSpawn;
import main.tests.events.DogTame;
import org.bukkit.plugin.java.*;
import java.util.logging.*;
import main.DogManager.*;
import main.commands.*;
import org.bukkit.command.*;
import main.tests.commands.*;
import org.bukkit.event.*;
import main.events.*;
import org.bukkit.plugin.*;
import java.sql.*;

public class Main extends JavaPlugin
{
    public static Plugin plugin;
    public static JavaPlugin javaPlugin;
    public static Logger logger;
    public static DogManager DOG_MANAGER;
    private final String user = "the60th";
    private final String pass = "DogManager123";
    private final String database = "dogmangager";
    private final String url = "jdbc:mysql://localhost:3306/dogmangager?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private static Statement statement;
    private static Connection connection;
    boolean databaseConnected;

    public Main() {
        this.databaseConnected = false;
    }

    public void onEnable() {
        Main.logger = Logger.getLogger("Minecraft");
        Main.plugin = (Plugin)this;
        Main.javaPlugin = this;
        Main.DOG_MANAGER = new DogManager();
        Main.plugin.saveDefaultConfig();
        this.registerEvents();
        this.registerCommands();
        this.loadDogs();
        this.sqlConnect();
        //this.demoQuery();
    }

    public void onDisable() {
        final PluginDescriptionFile pdfFile = this.getDescription();
        final Logger logger = Logger.getLogger("Minecraft");
        logger.info(pdfFile.getName() + "has successfully disabled.");
        this.saveDogs();
    }

    public void registerCommands() {
        this.getCommand("whistle").setExecutor(new WhistleCreator());
        this.getCommand("dog").setExecutor(new dogSpawn());
    }

    public void registerEvents() {
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new BellEvent(), this);
        pm.registerEvents(new WhistleEvent(), this);
        pm.registerEvents(new hitEvent(), this);
        pm.registerEvents(new loginEvents(), this);
        pm.registerEvents(new DogSpawn(), this);
        pm.registerEvents(new DogTame(), this);
    }

    public static Plugin getPlugin() {
        return Main.plugin;
    }

    public void loadDogs() {
    }

    public void saveDogs() {
    }

    public void sqlConnect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dogmangager?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", "the60th", "DogManager123");
            Main.statement = connection.createStatement();
            this.databaseConnected = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sqlDisconnect() {
        this.databaseConnected = false;
    }

    public void demoQuery() {
        try {
            final ResultSet resultSet = getStatement().executeQuery("select * from dogs;");
            while (resultSet.next()) {
                final String owner = resultSet.getString("owner");
              //  System.out.println(owner);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Statement getStatement() {
        return Main.statement;
    }
    public static Connection getConnection(){return Main.connection;}
}
