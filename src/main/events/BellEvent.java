package main.events;

import main.Main;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class BellEvent implements Listener {

    @EventHandler
    public void onBell(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(Objects.requireNonNull(e.getClickedBlock()).getType() == Material.BELL){
            Location loc = e.getClickedBlock().getLocation();
            World world = loc.getWorld();
            DyeColor targetColor = null;
            boolean colorMode = false;
            Block locBlock = loc.getBlock();
            Block topBlock = world.getBlockAt(new Location(world,locBlock.getX(),locBlock.getY()+1,locBlock.getZ()));
            Block bottomBlock = world.getBlockAt(new Location(world,locBlock.getX(),locBlock.getY()-1,locBlock.getZ()));

            if(checkDyeColor(topBlock) != null){
                targetColor = checkDyeColor(topBlock);
                colorMode = true;
            }else if(checkDyeColor(bottomBlock) !=null){
                targetColor = checkDyeColor(bottomBlock);
                colorMode = true;
            }



            System.out.println("Bell clicked");
            Location airLoc = new Location(loc.getWorld(),loc.getX(), loc.getY()+1.0,loc.getZ());
            Villager villager = (Villager) airLoc.getWorld().spawnEntity(airLoc, EntityType.VILLAGER);
            villager.setAI(false);
            villager.setGravity(false);
            villager.setSilent(true);
            villager.setInvulnerable(true);
            villager.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,20000,1));

            final Villager finalVil = villager;

            //This runnable will handle the killing of the villager.
            //And the targeting of the wolves.
            new BukkitRunnable(){
                @Override
                public void run() {
                    finalVil.setInvulnerable(false);
                  //  System.out.println("Killing zombie.");
                    new BukkitRunnable(){
                        @Override
                        public void run(){
                            for (Entity entity : Objects.requireNonNull(loc.getWorld()).getNearbyEntities(loc, 20, 20, 20)){
                                if(entity instanceof Wolf){
                                    Wolf wolf = (Wolf) entity;
                                    if(!wolf.isTamed()) return;

                                    if(wolf.getTarget() != finalVil) return;
                                    wolf.setTarget(null);
                                    //System.out.println("Setting dog target.");
                                }
                            }
                        }
                    }.runTask(Main.getPlugin());
                    finalVil.teleport(new Location(loc.getWorld(),loc.getX(),-100,loc.getZ()));
                    new BukkitRunnable(){
                        @Override
                        public void run(){
                            finalVil.setHealth(0);
                        }
                    }.runTask(Main.getPlugin());
                }
            }.runTaskLaterAsynchronously(Main.getPlugin(),200);

            for (Entity entity : Objects.requireNonNull(loc.getWorld()).getNearbyEntities(loc, 35, 35, 35)){
                if(entity instanceof Wolf){
                    Wolf wolf = (Wolf) entity;
                    if(!wolf.isTamed()) return;
                    //Setting the wolf target. We will check if colormode is enabled.
                    if(colorMode){
                        if(wolf.getCollarColor() == targetColor){
                            if(wolf.isSitting()) wolf.setSitting(false);
                            wolf.setTarget(villager);
                            System.out.println("Setting target color.");
                        }
                    }else {
                        if(wolf.isSitting())wolf.setSitting(false);
                        if(wolf.getOwner() != player) return;
                        wolf.setTarget(villager);
                    }
                    //System.out.println("Setting dog target.");
                }
            }
        }

        if(player.getName().equalsIgnoreCase("the60th")) {
          // System.out.println(e.getClickedBlock());
        }

    }


    public DyeColor checkDyeColor(Block block){
        Material mat = block.getType();
        DyeColor[] possibleValues = DyeColor.WHITE.getDeclaringClass().getEnumConstants();
        for (DyeColor color: possibleValues) {
            if(mat.toString().toLowerCase().contains(color.toString().toLowerCase())) {
                System.out.println(mat.toString());
                System.out.println(color.toString());
                return color;
            }
        }

        return null;
    }
}
