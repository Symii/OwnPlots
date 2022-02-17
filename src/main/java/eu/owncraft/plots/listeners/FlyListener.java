package eu.owncraft.plots.listeners;

import eu.owncraft.plots.OwnPlots;
import eu.owncraft.plots.events.PlayerExitPlotEvent;
import eu.owncraft.plots.plot.Plot;
import eu.owncraft.plots.utils.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class FlyListener implements Listener {

    @EventHandler
    public void onPlayerFly(PlayerToggleFlightEvent event)
    {
        Player player = event.getPlayer();
        Plot plot = OwnPlots.getInstance().getPlotManager().getPlotAt(player.getLocation());
        if((plot == null || player.getWorld().getName().equalsIgnoreCase(OwnPlots.getInstance().getConfig_manager().getAllowed_world()) == false)
                && player.hasPermission("ownplots.fly.bypass") == false) {
            player.setFlying(false);
            player.setAllowFlight(false);
            player.sendMessage(ChatUtil.fixColors("&f&lO&9&lC &8| &cFLY jest dozwolony tylko na działkach."));
        }
    }

    @EventHandler
    public void onPlayerExitPlot(PlayerExitPlotEvent event)
    {
        Player player = event.getPlayer();
        if(player.hasPermission("ownplots.fly.bypass") == false)
        {
            player.setFlying(false);
            player.setAllowFlight(false);
            player.sendMessage(ChatUtil.fixColors("&f&lO&9&lC &8| &cFLY jest dozwolony tylko na działkach."));
        }
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event)
    {
        Player player = event.getPlayer();
        if(player.isFlying() && player.hasPermission("ownplots.fly.bypass") == false)
        {
            player.setFlying(false);
            player.setAllowFlight(false);
            player.sendMessage(ChatUtil.fixColors("&f&lO&9&lC &8| &cFLY jest dozwolony tylko na działkach."));
        }
    }

}
