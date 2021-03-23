package eu.owncraft.plots.listeners;

import eu.owncraft.plots.OwnPlots;
import eu.owncraft.plots.plot.Plot;
import eu.owncraft.plots.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class EntityListeners implements Listener {

    private final OwnPlots plugin;
    public EntityListeners(OwnPlots plugin)
    {
        this.plugin = plugin;
    }


    @EventHandler
    public void onHangingBreak(HangingBreakByEntityEvent event)
    {
        if(!event.getEntity().getWorld().getName().equalsIgnoreCase("world"))
        {
            return;
        }

        if(event.getRemover() instanceof Arrow)
        {
            Plot plot = plugin.getPlotManager().getPlotAt(event.getEntity().getLocation());
            if(plot != null)
            {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        final String world_name = event.getEntity().getWorld().getName();
        if(!world_name.equalsIgnoreCase("world")) {
            return;
        }

        if(event.getEntity() instanceof ItemFrame || event.getEntity() instanceof ArmorStand)
        {
            if(event.getDamager() instanceof Projectile)
            {
                Plot plot = plugin.getPlotManager().getPlotAt(event.getEntity().getLocation());
                if(plot != null)
                {
                    event.getDamager().remove();
                    event.setCancelled(true);
                }
            }
            else if(event.getDamager() instanceof Player)
            {
                final Player damager = (Player) event.getDamager();
                if(damager.hasPermission("ownplots.admin"))
                {
                    return;
                }
                Plot plot = plugin.getPlotManager().getPlotAt(event.getEntity().getLocation());
                if(plot != null && !plot.isMember(damager))
                {
                    event.setCancelled(true);
                    damager.sendMessage(ChatUtil.fixColorsWithPrefix("&enie mozesz tego zrobic w tym miejscu!"));
                    damager.playSound(damager.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
            }
        }
        else if(event.getEntity() instanceof Player && event.getDamager() instanceof Player)
        {
            final Player damager = (Player) event.getDamager();
            if(damager.hasPermission("ownplots.admin"))
            {
                return;
            }
            final Player victim = (Player) event.getEntity();
            Plot plot = plugin.getPlotManager().getPlotAt(victim.getLocation());
            if(plot != null && !plot.getPlotSettings().isPvp())
            {
                event.setCancelled(true);
                damager.sendMessage(ChatUtil.fixColorsWithPrefix("&ePvP jest &cwylaczone &ena tej dzialce!"));
                damager.playSound(damager.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            }
        }
        else if(event.getEntity() instanceof Player && !(event.getDamager() instanceof Player))
        {
            Player vicitm = (Player) event.getEntity();
            Plot plot = plugin.getPlotManager().getPlotAt(vicitm.getLocation());
            if(plot != null && !plot.getPlotSettings().isMob_damage_players())
            {
                event.setCancelled(true);
            }
        }
        else if(event.getDamager() instanceof Player)
        {
            final Player damager = (Player) event.getDamager();
            if(damager.hasPermission("ownplots.admin"))
            {
                return;
            }
            Plot plot = plugin.getPlotManager().getPlotAt(event.getEntity().getLocation());
            if(plot != null && !plot.getMembers().contains(damager.getName())
                    && !plot.getVisitorsSettings().isMob_damage())
            {
                event.setCancelled(true);
                damager.sendMessage(ChatUtil.fixColorsWithPrefix("&enie mozesz bic mobow na tej dzialce!"));
                damager.playSound(damager.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event)
    {
        if(!event.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            return;
        }

        Plot plot = plugin.getPlotManager().getPlotAt(event.getLocation());
        if(plot != null && !plot.getPlotSettings().isExplosions()) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event)
    {
        if(!event.getEntity().getWorld().getName().equalsIgnoreCase("world")) {
            return;
        }

        if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) {
            return;
        }

        Plot plot = plugin.getPlotManager().getPlotAt(event.getLocation());
        if(plot != null && !plot.getPlotSettings().isNatural_spawn())
        {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onLeavesDecayEvent(LeavesDecayEvent event)
    {
        if(!event.getBlock().getWorld().getName().equalsIgnoreCase("world")) {
            return;
        }

        Plot plot = plugin.getPlotManager().getPlotAt(event.getBlock().getLocation());
        if(plot != null && !plot.getPlotSettings().isLeaf_decay()) {
            event.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDeath(EntityDeathEvent event)
    {
        if(!event.getEntity().getWorld().getName().equalsIgnoreCase("world")
                || event.getEntity() instanceof Player) {
            return;
        }

        if(event.getEntityType() == EntityType.ARMOR_STAND)
        {
            return;
        }

        Plot plot = plugin.getPlotManager().getPlotAt(event.getEntity().getLocation());
        if(plot != null)
        {
            if(plot.isMob_exp_upgrade())
            {
                event.setDroppedExp(event.getDroppedExp() * 2);
            }

            if(plot.isMob_drop_upgrade())
            {
                for(ItemStack item : event.getDrops())
                {
                    int new_amount = item.getAmount() * 2;
                    if(new_amount > 64)
                    {
                        new_amount = 64;
                    }
                    item.setAmount(new_amount);
                }
            }

        }

    }


}
