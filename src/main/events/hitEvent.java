package main.events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class hitEvent implements Listener {
    @EventHandler
    public void playerAttackWolf(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player)) return;
        if(!(e.getEntity() instanceof Wolf)) return;

        Player player = (Player) e.getDamager();
        Wolf wolf = (Wolf) e.getEntity();

        if(!wolf.isTamed()) return;

        if(wolf.getOwner() != player) return;

        if(player.isSneaking()) return;

        e.setDamage(0);
        e.setCancelled(true);
    }

    @EventHandler
    public void wolfAttackPlayer(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof  Wolf));
        if((e.getEntity() instanceof Player));

        Player player = (Player) e.getEntity();
        Wolf wolf = (Wolf) e.getDamager();

        if(player == wolf.getOwner()){
            e.setDamage(0);
            e.setCancelled(true);
        }
    }
}
