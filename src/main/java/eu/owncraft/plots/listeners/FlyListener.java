package eu.owncraft.plots.listeners;

import eu.owncraft.plots.OwnPlots;
import eu.owncraft.plots.config.LanguageManager;
import eu.owncraft.plots.events.PlayerExitPlotEvent;
import eu.owncraft.plots.plot.Plot;
import eu.owncraft.plots.utils.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class FlyListener implements Listener {

    private boolean isFlyEnabledOnlyOnPlots = false;
    private LanguageManager languageManager;

    public FlyListener() {
        this.languageManager = OwnPlots.getInstance().getLanguageManager();
        isFlyEnabledOnlyOnPlots = OwnPlots.getInstance().getConfig_manager().isFly_enabled_only_on_plots();
    }

    @EventHandler
    public void onPlayerFly(PlayerToggleFlightEvent event)
    {
        if(!isFlyEnabledOnlyOnPlots) { return; }

        Player player = event.getPlayer();
        Plot plot = OwnPlots.getInstance().getPlotManager().getPlotAt(player.getLocation());
        if((plot == null || player.getWorld().getName().equalsIgnoreCase(OwnPlots.getInstance().getConfig_manager().getAllowed_world()) == false)
                && player.hasPermission("ownplots.fly.bypass") == false) {
            player.setFlying(false);
            player.setAllowFlight(false);
            player.sendMessage(ChatUtil.fixColors(languageManager.getMessage("plot-protection-fly")));
        }
    }

    @EventHandler
    public void onPlayerExitPlot(PlayerExitPlotEvent event)
    {
        if(!isFlyEnabledOnlyOnPlots) { return; }

        Player player = event.getPlayer();
        if(player.hasPermission("ownplots.fly.bypass") == false)
        {
            player.setFlying(false);
            player.setAllowFlight(false);
            player.sendMessage(ChatUtil.fixColors(languageManager.getMessage("plot-protection-fly")));
        }
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event)
    {
        if(!isFlyEnabledOnlyOnPlots) { return; }

        Player player = event.getPlayer();
        if(player.isFlying() && player.hasPermission("ownplots.fly.bypass") == false)
        {
            player.setFlying(false);
            player.setAllowFlight(false);
            player.sendMessage(ChatUtil.fixColors(languageManager.getMessage("plot-protection-fly")));
        }
    }

}
