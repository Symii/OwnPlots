package eu.owncraft.plots.listeners;

import eu.owncraft.plots.events.PlayerEnterPlotEvent;
import eu.owncraft.plots.events.PlayerExitPlotEvent;
import eu.owncraft.plots.plot.Plot;
import eu.owncraft.plots.utils.ChatUtil;
import eu.owncraft.plots.utils.RandomTP;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlotListeners implements Listener {

    @EventHandler
    public void onPlayerEnterPlot(PlayerEnterPlotEvent event)
    {
        final Player player = event.getPlayer();
        final Plot plot = event.getPlot();

        if(!plot.isMember(player) && (plot.isClosed() || plot.isBanned(player.getName())))
        {
            if(player.hasPermission("ownplots.admin"))
            {
                return;
            }
            RandomTP.randomTeleport(player, 1);
            return;
        }

        player.sendTitle(ChatColor.YELLOW + "Teren dzialki", ChatColor.GRAY + plot.getOwner(), 10, 40, 10);
        player.sendMessage(ChatUtil.fixColorsWithPrefix("&ewchodzisz na teren dzialki gracza &c" + plot.getOwner()));
    }

    @EventHandler
    public void onPlayerExitPlot(PlayerExitPlotEvent event)
    {
        final Player player = event.getPlayer();
        player.sendTitle(ChatColor.RED + "Opuszczasz", ChatColor.RED + "dzialke", 10, 40, 10);
        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eopuszczasz teren dzialki"));
    }

}
