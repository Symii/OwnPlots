package eu.owncraft.plots.tasks;

import eu.owncraft.plots.OwnPlots;
import eu.owncraft.plots.plot.Plot;
import org.bukkit.scheduler.BukkitRunnable;

public class PlotSaveTask extends BukkitRunnable {

    private OwnPlots plugin;

    public PlotSaveTask(OwnPlots plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        plugin.getLogger().info("Saving plots into MySQL database...");
        for(Plot plot : plugin.getPlayerDataManager().getPlots().values())
        {
            plugin.getPlotManager().savePlotAsync(plot);
        }
        plugin.getLogger().info("Plots successfully saved!");
    }
}
