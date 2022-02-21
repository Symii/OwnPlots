package eu.owncraft.plots.listeners;

import eu.owncraft.plots.OwnPlots;
import eu.owncraft.plots.config.LanguageManager;
import eu.owncraft.plots.database.PlotManager;
import eu.owncraft.plots.plot.Plot;
import eu.owncraft.plots.utils.ChatUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListeners implements Listener {

    private final OwnPlots plugin;
    PlotManager plotManager;
    LanguageManager languageManager;

    public BlockListeners(OwnPlots plugin)
    {
        this.plugin = plugin;
        this.plotManager = new PlotManager(plugin);
        this.languageManager = OwnPlots.getInstance().getLanguageManager();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event)
    {
        Location location = event.getBlock().getLocation();
        if(!location.getWorld().getName().equalsIgnoreCase(OwnPlots.getInstance().getConfig_manager().getAllowed_world()))
        {
            return;
        }
        Player player = event.getPlayer();
        if(player.hasPermission("ownplots.admin"))
        {
            return;
        }
        Plot plot = plotManager.getPlotAt(location);
        if(plot != null && !plot.isMember(player) && !plot.getVisitorsSettings().isBlock_break())
        {
            event.setCancelled(true);
            player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-protection-break").replace("%plot_owner%", plot.getOwner())));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent event)
    {
        Location location = event.getBlock().getLocation();
        if(!location.getWorld().getName().equalsIgnoreCase(OwnPlots.getInstance().getConfig_manager().getAllowed_world()))
        {
            return;
        }
        Player player = event.getPlayer();
        if(player.hasPermission("ownplots.admin"))
        {
            return;
        }
        Plot plot = plotManager.getPlotAt(location);
        if(plot != null && !plot.isMember(player) && !plot.getVisitorsSettings().isBlock_place())
        {
            event.setCancelled(true);
            player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-protection-build").replace("%plot_owner%", plot.getOwner())));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event)
    {
        Location location = event.getBlock().getLocation();
        if(!location.getWorld().getName().equalsIgnoreCase(OwnPlots.getInstance().getConfig_manager().getAllowed_world()))
        {
            return;
        }

        Plot plot = plugin.getPlotManager().getPlotAt(location);
        if(plot != null && !plot.getPlotSettings().isFire_spread())
        {
            event.setCancelled(true);
        }
    }

}
