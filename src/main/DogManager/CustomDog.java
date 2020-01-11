package main.DogManager;

import main.Main;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public class CustomDog  {
    //owner: player
    //name: string
    //attack: int
    //health: int
    //color: string
    //dogID: uuid

    private Wolf wolf;

    private Player owner;
    private String name;
    private double attack;
    private double health;
    private String color;
    private UUID uuid;

    public CustomDog(Player owner,String name, double attack, double health,
                     String color,UUID uuid){
        this.owner = owner;
        this.name = name;
        this.attack = attack;
        this.health = health;
        this.color = color;
        this.uuid = uuid;
    }

    public DyeColor convertToDye(String string){
        DyeColor[] possibleValues = DyeColor.WHITE.getDeclaringClass().getEnumConstants();
        for (DyeColor color: possibleValues) {
            if(string.toLowerCase().contains(color.toString().toLowerCase())) {
                //System.out.println(string);
                //System.out.println(color.toString());
                return color;
            }
        }

        return null;
    }
    private DyeColor convertToDye(int color){
        return DyeColor.PURPLE;
    }
    public Wolf getWolf() {
        return wolf;
    }

    public void setWolf(Wolf wolf, Player player) {
        wolf.setOwner(player);
        wolf.setHealth(this.health);
        wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2.0);

        wolf.setCustomName(this.name);
        wolf.setCollarColor(convertToDye(this.color));
        wolf.setMetadata("uuid:",
                new FixedMetadataValue(Main.getPlugin(),
                        uuid.toString()));

        this.wolf = wolf;
    }


    public Player getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public UUID getUuid() {
        return uuid;
    }


}
