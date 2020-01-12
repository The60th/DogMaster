package main.tests.events;

import main.DogManager.DogManager;
import main.Main;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

public class DogTame implements Listener {
    @EventHandler
    public void onTameEvent(EntityTameEvent event){
        if(!(event.getEntity() instanceof Wolf)) return;
        if(!(event.getOwner() instanceof  Player)) return;
        Wolf wolf = (Wolf) event.getEntity();
        Player player = (Player) event.getOwner();

        Main.DOG_MANAGER.saveDog(player, wolf);
    }
}
