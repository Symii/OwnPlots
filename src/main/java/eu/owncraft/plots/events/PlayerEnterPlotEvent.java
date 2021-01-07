package eu.owncraft.plots.events;

import eu.owncraft.plots.plot.Plot;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerEnterPlotEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Player player;
    private Plot plot;

    public PlayerEnterPlotEvent(Player player, Plot plot)
    {
        this.player = player;
        this.plot = plot;
    }

    public Plot getPlot()
    {
        return plot;
    }

    public Player getPlayer()
    {
        return player;
    }

    @Override
    public HandlerList getHandlers()
    {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLERS_LIST;
    }

}
