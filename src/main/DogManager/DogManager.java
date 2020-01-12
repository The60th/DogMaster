package main.DogManager;

import com.sun.istack.internal.Nullable;
import main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DogManager {
    //private HashMap<UUID, CustomDog> loadedDogs = new HashMap<UUID, CustomDog>();
    private ArrayList<CustomDog> loadedDogs = new ArrayList<>();
    private ArrayList<Player> loadedPlayers = new ArrayList<>();

    @Nullable
    public CustomDog getDogFromUUID(UUID uuid) {
        //return this.loadedDogs.get(uuid);
        for (CustomDog dog : loadedDogs) {
            if (dog.getUuid() == uuid) return dog;
        }
        return null;
    }


    public void loadDog(UUID playerUUID, CustomDog customDog) {
        this.loadedDogs.add(customDog);
    }

    private void getDogsFromDB(Player player) {
        //Table is: dogs
        //The query should return all the data we need to recreate a dog object
        //rather then the wolf object itself.

        //Start fields will be:
        //owner: player
        //name: string
        //attack: int
        //health: int
        //color: string
        //dogID: uuid

        String dogID = "";
        String test = "'test'";
        String query = "SELECT owner, name, attack, health, color, uuid FROM dogs WHERE owner = '"+player.getUniqueId()+"';";
                //"WHERE owner = '"+player.getUniqueId() + "';";
        String query2 = "SELECT owner, name, attack, health, color, uuid " +
                "                FROM dogs " +
                "                WHERE owner = '353a6216-2437-4b36-93fd-ea31d59b7852';";

        ResultSet resultSet = null;
        try {
            resultSet = Main.getStatement().executeQuery(query);


            //Get each sql result and put them into the hashmap.
            while (resultSet.next()) {
                String owner = resultSet.getString("owner");
                String name = resultSet.getString("name");
                int attack = resultSet.getInt("attack");
                int health = resultSet.getInt("health");
                String color = resultSet.getString("color");
                String uuid = resultSet.getString("uuid");
                CustomDog customDog = new CustomDog(Bukkit.getPlayer(UUID.fromString(owner)),
                        name, attack,
                        health, color,
                        UUID.fromString("353a6216-2437-4b36-93fd-ea31d59b7852"));

                loadDog(UUID.fromString("353a6216-2437-4b36-93fd-ea31d59b7852"), customDog);
            }

            loadedPlayers.add(player);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void despawnDogs(Player player) {
    }

    public  void spawnDogs(Player player) {
        if (!Main.DOG_MANAGER.playerLoaded(player)) {
         //   System.out.println("Loading dogs.");
            Main.DOG_MANAGER.getDogsFromDB(player);
        }
        for (CustomDog dog : Main.DOG_MANAGER.loadedDogs) {
           // System.out.println("Any dogs to spawn?");
            if (dog.getOwner() == player) {
           //     System.out.println("Spawning dog.");
                //Spawn this dog.
                dog.setWolf((Wolf) player.getWorld().spawnEntity(player.getLocation(),
                       EntityType.WOLF),player);
            }
        }
    }
    /**
     *insert into dogs values(
     * 3,
     * "353a6216-2437-4b36-93fd-ea31d59b7852",
     * "test",
     * 2,
     * 2,
     * "pink",
     * "uuid");
     */
    public  void saveDog(Player player, Wolf wolf){
        UUID playerUUID = player.getUniqueId();
        UUID wolfUUID = UUID.randomUUID();
        String name = "NewDog";
        int attack = 2;
        int health = 2;
        String color = "Blue";

        String query = " insert into dogs (owner, name, attack, health,color,uuid)"
                + " values (?, ?, ?, ?, ?,?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = Main.getConnection().prepareStatement(query);

        //preparedStmt.setString (1, "NULL");
        preparedStmt.setString (1, player.getUniqueId().toString());
        preparedStmt.setString   (2, name);
        preparedStmt.setInt(3, 2);
        preparedStmt.setInt    (4, 2);
        preparedStmt.setString(5,color);
        preparedStmt.setString(6,wolfUUID.toString());

        preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*
        String query = "inset into dogs value(NULL," + "'" + playerUUID.toString() +
                "','" + name + "'," + attack +","+health+",'" +
                color +"','"+wolfUUID.toString() + "');";
        try {
            Main.getStatement().executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }*/

    }
    private boolean playerLoaded(Player player) {
        return this.loadedPlayers.contains(player);
    }
}
