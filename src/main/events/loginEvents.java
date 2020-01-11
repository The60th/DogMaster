package main.events;

import main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class loginEvents implements Listener {

    @EventHandler
    public void playerLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();

        Main.DOG_MANAGER.spawnDogs(player);
    }

    @EventHandler
    public void playerLogOut(PlayerQuitEvent event){
        Player player = event.getPlayer();

        Main.DOG_MANAGER.despawnDogs(player);
    }
}
