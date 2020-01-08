package main.commands;

import main.whistle.Whistle;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
public class WhistleCreator implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {

        if(sender instanceof Player && alias.toLowerCase().equals("whistle")){
            Player player = (Player) sender;
            if(args.length == 0){
                player.sendMessage(ChatColor.RED + "" +ChatColor.BOLD + "Whistle coming soon!");
                return true;
            }

            switch (args[0].toLowerCase()){
                case "get":
                    ItemStack whistleItem = new ItemStack(Material.IRON_INGOT);
                    whistleItem.addUnsafeEnchantment(Enchantment.DURABILITY,1);

                    ItemMeta itemMeta = whistleItem.getItemMeta();
                    itemMeta.setDisplayName(Whistle.WHISTLE_DISPLAY_NAME);
                    ArrayList<String> itemLore = new ArrayList<>();
                    itemLore.add(Whistle.WHISTLE_LORE);
                    itemLore.add(Whistle.WHISTLE_MODE + Whistle.Mode.UNKNOWN);
                    itemMeta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES);
                    itemMeta.setLore(itemLore);
                    whistleItem.setItemMeta(itemMeta);
                    player.getInventory().addItem(whistleItem);

                    return true;
                default:
                    return true;
            }
        }
        return false;
    }
}
