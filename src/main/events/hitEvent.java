package main.events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import main.whistle.*;
import org.bukkit.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;

public class hitEvent implements Listener
{
    @EventHandler
    public void playerAttackWolf(final EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        if (!(e.getEntity() instanceof Wolf)) {
            return;
        }
        final Player player = (Player)e.getDamager();
        final Wolf wolf = (Wolf)e.getEntity();
        if (!wolf.isTamed()) {
            return;
        }
        if (player.isSneaking()) {
            return;
        }
        e.setDamage(0.0);
        e.setCancelled(true);
    }

    @EventHandler
    public void playerWhistleWolf(final EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        if (!(e.getEntity() instanceof Wolf)) {
            return;
        }
        final Player player = (Player)e.getDamager();
        final Wolf wolf = (Wolf)e.getEntity();
        if (!wolf.isTamed()) {
            return;
        }
        if (!Whistle.isWhistle(player.getInventory().getItemInMainHand())) {
            return;
        }
        final int health = (int)wolf.getHealth();
        final String owner = wolf.getOwner().getName();
        final String message = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Dog health: " + ChatColor.GREEN + "" + ChatColor.ITALIC + health + " " + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Dog Owner: " + ChatColor.GREEN + "" + ChatColor.ITALIC + owner;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        e.setDamage(0.0);
        e.setCancelled(true);
    }

    @EventHandler
    public void wolfAttackPlayer(final EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Wolf)) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        final Player player = (Player)e.getEntity();
        final Wolf wolf = (Wolf)e.getDamager();
        if (player == wolf.getOwner()) {
            e.setDamage(0.0);
            e.setCancelled(true);
        }
    }
}
