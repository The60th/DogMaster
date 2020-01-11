package main.tests.commands;

import main.DogManager.DogManager;
import main.whistle.Whistle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class dogSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if(sender instanceof Player && alias.toLowerCase().equals("dog")) {
            Player player = (Player) sender;

            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Whistle coming soon!");
                return true;
            }

            switch (args[0].toLowerCase()){
                case "spawn":
                    Location location = player.getLocation();
                    //System.out.println("test");
                    DogManager.spawnDogs(player);

                    return true;
                default:
                    return true;
            }

        }

        return false;
    }
}
