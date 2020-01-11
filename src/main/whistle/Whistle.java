package main.whistle;

import main.commands.WhistleCreator;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class Whistle {
    public final static String WHISTLE_DISPLAY_NAME = ChatColor.DARK_GREEN + "" +ChatColor.BOLD+ "Dog Whistle";
    public final static String WHISTLE_LORE = ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "Calls dogs";
    public final static String WHISTLE_MODE = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD +
            "Mode: " +ChatColor.DARK_GREEN + "" + ChatColor.ITALIC;

    public static void changeMode(ItemStack itemStack){
        if(!(isWhistle(itemStack))) return;

        Mode mode = getMode(itemStack);
        Mode newMode = Mode.UNKNOWN;
        if(mode == Mode.SIT) newMode = Mode.STAND;
        else if(mode == Mode.STAND) newMode = Mode.TOGGLE;
        else if(mode == Mode.TOGGLE) newMode = Mode.COME;
        else if(mode == Mode.COME) newMode = Mode.SIT;
        else if(mode == Mode.UNKNOWN) newMode = Mode.SIT;
       // System.out.println("Newmode is: " + newMode.toString());
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> itemLore = itemMeta.getLore();

        String newLore = " t";
        for (String lore: itemLore) {
            //.println("Lore line: " + lore);

            if(lore.contains(WHISTLE_MODE)){
              //  System.out.println("At lore line: ");
                newLore = WHISTLE_MODE + newMode.toString();



            }
        }
        //System.out.println("Setting lore to: " + itemLore);
        itemLore.remove(itemLore.size()-1);
        itemLore.add(newLore);

        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
    }

    public static Mode getMode(ItemStack itemStack){
        if(!isWhistle(itemStack)) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> itemLore = itemMeta.getLore();

        for (String lore: itemLore) {
            if(lore.contains(WHISTLE_MODE)){
                Mode[] possibleValues = Mode.UNKNOWN.getDeclaringClass().getEnumConstants();
                for (Mode mode: possibleValues) {
                    if(lore.toLowerCase().contains(mode.toString().toLowerCase())) {
                        return mode;
                    }
                }
            }
        }
        return Mode.UNKNOWN;
    }
    public static boolean isWhistle(ItemStack itemStack){
        if (itemStack.getType().equals(Material.IRON_INGOT)) {
            if(itemStack.hasItemMeta() && Objects.requireNonNull(itemStack.getItemMeta()).getLore() != null){
                ItemMeta itemMeta = itemStack.getItemMeta();
                List<String> itemLore = itemMeta.getLore();
                if(itemMeta.getDisplayName().equals(Whistle.WHISTLE_DISPLAY_NAME)
                        && itemLore.contains(Whistle.WHISTLE_LORE)){
                    // System.out.println("Is a whistle");
                    return true;
                }

            }
        }
        return false;
    }
    public enum Mode{
        SIT,
        STAND,
        TOGGLE,
        COME,
        UNKNOWN
    }
}
