package main.events;

import main.Main;
import main.commands.WhistleCreator;
import main.whistle.Whistle;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

public class WhistleEvent implements Listener {
    @EventHandler
    public void onRightInteract(PlayerInteractEvent event) {
        if(event.getHand().equals(EquipmentSlot.OFF_HAND)) return; //Return the right click event for the off hand trigger.

       // System.out.println("Right click call?");
        String message = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD +  "Changing Whistle Mode to: " + ChatColor.GREEN + "" +ChatColor.ITALIC+ "";

        Action action = event.getAction();

        Player player = event.getPlayer();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemStack = player.getItemInHand();
            if(!Whistle.isWhistle(itemStack)) return;
            boolean colorMode = false;
            DyeColor targetColor = null;
            if(dyeInOffHand(player) !=null){
                colorMode = true;
                targetColor = dyeInOffHand(player);
            }
            Location loc = player .getLocation();
            World world = loc.getWorld();
            world.playSound(loc,Sound.BLOCK_BELL_USE,3.0f,2.0f);

            for (Entity entity : Objects.requireNonNull(loc.getWorld()).getNearbyEntities(loc, 10, 10, 10)){
                if(entity instanceof Wolf){
                    Wolf wolf = (Wolf) entity;
                    if(!wolf.isTamed()) return;
                    if(wolf.getOwner() == player){
                        //System.out.println("owners match.");
                        Whistle.Mode mode = Whistle.getMode(itemStack);
                        switch (mode){
                            case SIT:

                                if(colorMode){
                                   // System.out.println("Color mode: target: " + targetColor);
                                    if(wolf.getCollarColor() == targetColor) wolf.setSitting(true);
                                }
                                else {
                                    wolf.setSitting(true);
                                }
                                break;
                            case COME:
                                if(colorMode){
                                    if(wolf.getCollarColor() == targetColor) {
                                        wolfFollow(wolf, player);
                                    }
                                }else{
                                    wolfFollow(wolf,player);
                                }

                                break;
                            case STAND:
                                if(colorMode){
                                    if(wolf.getCollarColor() == targetColor) wolf.setSitting(false);
                                }else {
                                    wolf.setSitting(false);
                                }
                                break;
                            case TOGGLE:
                                if(colorMode){
                                    if(wolf.getCollarColor() == targetColor){
                                        if(wolf.isSitting()){
                                           // System.out.println("Wolf sitting.");
                                            wolf.setSitting(false);
                                        }else{
                                           // System.out.println("Wolf not is sitting");
                                            wolf.setSitting(true);
                                        }
                                    }
                                }else {
                                    if (wolf.isSitting()) {
                                        //System.out.println("Wolf sitting. noncolor");
                                        wolf.setSitting(false);
                                    } else {
                                       //  System.out.println("Wolf not is sitting noncolor");
                                        wolf.setSitting(true);
                                    }
                                }
                                break;
                            case UNKNOWN:
                                player.sendMessage(ChatColor.RED + "Change your whistle mode.");
                        }

                    }
                    //System.out.println("Setting dog target.");
                }
            }
                //THe player has clicked an whistle at this point.


        }
    }

    @EventHandler
    public void onLeftInteract(PlayerInteractEvent event){
        Action action = event.getAction();
        Player player = event.getPlayer();
        //System.out.println("Mode call");
        if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK){
            if(player.isSneaking()) {
                ItemStack itemStack = player.getItemInHand();
                if (!Whistle.isWhistle(itemStack)) return;
             //   System.out.println("Calling change mode.");
                Whistle.changeMode(itemStack);
                String message = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD +  "Changing Whistle Mode to: " + ChatColor.GREEN + "" +ChatColor.ITALIC+ "";
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message+Whistle.getMode(itemStack).toString()));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void leftTarget(PlayerInteractEvent event){
       /* Player player = event.getPlayer();
        int maxDistance = 10;
        for(int i=0; i < maxDistance; i++){
            //Location l = player.getLocation().getDirection().normalize().multiply(i);
            for(Entity entity: l.getWorld().getNearbyEntities(l,1.5,1.5,1.5)){
                if(entity instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.setGlowing(true);
                    break;
                }
            }
        }*/
    }
    public DyeColor dyeInOffHand(Player player){
       // System.out.println(player.getInventory().getItemInOffHand().getType());
        if(player.getInventory().getItemInOffHand().getType() != Material.AIR){
            Material mat = player.getInventory().getItemInOffHand().getType();
            switch (mat){
                case BLACK_DYE:
                    return DyeColor.BLACK;
                case BLUE_DYE:
                    return DyeColor.BLUE;
                case BROWN_DYE:
                    return DyeColor.BROWN;
                case CYAN_DYE:
                    return DyeColor.CYAN;
                case GRAY_DYE:
                    return DyeColor.GRAY;
                case GREEN_DYE:
                    return DyeColor.GREEN;
                case LIGHT_BLUE_DYE:
                    return DyeColor.LIGHT_BLUE;
                case LIGHT_GRAY_DYE:
                    return DyeColor.LIGHT_GRAY;
                case LIME_DYE:
                    return DyeColor.LIME;
                case MAGENTA_DYE:
                    return DyeColor.MAGENTA;
                case ORANGE_DYE:
                    return DyeColor.ORANGE;
                case PINK_DYE:
                    return DyeColor.PINK;
                case PURPLE_DYE:
                    return DyeColor.PURPLE;
                case RED_DYE:
                    return  DyeColor.RED;
                case WHITE_DYE:
                    return DyeColor.WHITE;
                case YELLOW_DYE:
                    return DyeColor.YELLOW;
                default:
                    return null;
            }
        }
        return null;
    }

    private void wolfFollow(Wolf wolf, Player player){
        wolf.setSitting(false);

        wolf.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,15,3));

        wolf.setTarget(player);
        new BukkitRunnable(){
            @Override
            public void run() {
                //System.out.println("Clearing taget.");
                wolf.setTarget(null);
            }
        }.runTaskLaterAsynchronously(Main.getPlugin(),200);
    }

}
