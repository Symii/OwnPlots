package eu.owncraft.plots.playerdata;

import eu.owncraft.plots.OwnPlots;
import eu.owncraft.plots.challenge.Challenge;
import eu.owncraft.plots.plot.Plot;
import eu.owncraft.plots.plot.PlotMember;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerDataManager {

    private final Map<String, PlotMember> plot_members = new HashMap<>();
    private final Map<String, Plot> plots = new HashMap<>();
    private final Map<String, Challenge> challenge_players = new HashMap<>();
    private final List<Player> border_players = new ArrayList<>();

    public List<Player> getBorder_players()
    {
        return border_players;
    }

    public Map<String, Plot> getPlots()
    {
        return plots;
    }

    public Map<String, Challenge> getChallenge_players()
    {
        return challenge_players;
    }

    public void addPlayerMember(String player_name, PlotMember member)
    {
        plot_members.put(player_name, member);
    }

    public void removePlayerMember(String player_name)
    {
        if(plot_members.get(player_name) != null)
        {
            plot_members.remove(player_name);
        }
    }

    @Nullable
    public PlotMember getPlotMember(String player_name)
    {
        if(plot_members.containsKey(player_name))
        {
            return plot_members.get(player_name);
        }

        return null;
    }

    public void onDisable()
    {
        for(Plot plot : plots.values())
        {
            OwnPlots.getInstance().getPlotManager().savePlotSync(plot);
        }
        plot_members.clear();
        border_players.clear();
        plots.clear();
    }

}
