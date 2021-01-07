package eu.owncraft.plots.plot;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class PlotMember {

    private Player player;
    private String own_plot;

    public PlotMember(Player player, String own_plot)
    {
        this.player = player;
        this.own_plot = own_plot;
    }

    public void setOwn_plot(String own_plot)
    {
        this.own_plot = own_plot;
    }

    @Nullable
    public String getOwn_plot()
    {
        return own_plot;
    }

    public Player getPlayer()
    {
        return player;
    }


}
