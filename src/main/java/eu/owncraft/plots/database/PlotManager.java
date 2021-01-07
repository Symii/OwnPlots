package eu.owncraft.plots.database;

import eu.owncraft.plots.OwnPlots;
import eu.owncraft.plots.playerdata.PlayerDataManager;
import eu.owncraft.plots.plot.Plot;
import eu.owncraft.plots.plot.PlotMember;
import eu.owncraft.plots.plot.PlotSettings;
import eu.owncraft.plots.plot.VisitorsSettings;
import eu.owncraft.plots.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlotManager {

    private final OwnPlots plugin;
    private final String plots_table, table_plots_settings;

    public PlotManager(OwnPlots plugin)
    {
        this.plugin = plugin;
        this.plots_table = plugin.getDatabase().plots_table;
        this.table_plots_settings = plugin.getDatabase().table_plots_settings;
    }

    public void savePlotAsync(final Plot plot)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String level_data = plot.getIron_blocks() + ";" + plot.getGold_blocks() + ";" +
                            plot.getDiamond_blocks() + ";" + plot.getEmerald_blocks() + ";" +
                            plot.getNetherite_blocks() + ";" + plot.getBeacons() + ";";

                    String upgrade_data = plot.isSpeed_upgrade() + ";" + plot.isJump_upgrade() + ";" +
                            plot.isMob_drop_upgrade() + ";" + plot.isMob_exp_upgrade() + ";" +
                            plot.getSize() + ";";

                    String banned_players = "";
                    for(String banned : plot.getBanned_players())
                    {
                        banned_players = banned_players + banned + ";";
                    }



                    PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement(
                            "UPDATE `" + plots_table + "` SET `closed`=?, `banned-players`=?, `level-data`=?, `upgrade-data`=? WHERE `plot-name`=?");
                    statement.setBoolean(1, plot.isClosed());
                    statement.setString(2, banned_players);
                    statement.setString(3, level_data);
                    statement.setString(4, upgrade_data);
                    statement.setString(5, plot.getPlot_name());
                    statement.executeUpdate();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void savePlotSync(final Plot plot)
    {
        try
        {
            String level_data = plot.getIron_blocks() + ";" + plot.getGold_blocks() + ";" +
                    plot.getDiamond_blocks() + ";" + plot.getEmerald_blocks() + ";" +
                    plot.getNetherite_blocks() + ";" + plot.getBeacons() + ";";

            String upgrade_data = plot.isSpeed_upgrade() + ";" + plot.isJump_upgrade() + ";" +
                    plot.isMob_drop_upgrade() + ";" + plot.isMob_exp_upgrade() + ";" +
                    plot.getSize() + ";";

            String banned_players = "";
            for(String banned : plot.getBanned_players())
            {
                banned_players = banned_players + banned + ";";
            }

            PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement(
                    "UPDATE `" + plots_table + "` SET `closed`=?, `banned-players`=?, `level-data`=?, `upgrade-data`=? WHERE `plot-name`=?");
            statement.setBoolean(1, plot.isClosed());
            statement.setString(2, banned_players);
            statement.setString(3, level_data);
            statement.setString(4, upgrade_data);
            statement.setString(5, plot.getPlot_name());
            statement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }


    public void loadPlots()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    plugin.getPlayerDataManager().getPlots().clear();
                    PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement("SELECT * FROM `" + plots_table + "`");
                    ResultSet row = statement.executeQuery();
                    while(row.next())
                    {
                        String plot_name = row.getString("plot-name");
                        String owner = row.getString("owner");
                        Location location = LocationUtil.getLocationFromString(row.getString("location"));
                        boolean closed = row.getBoolean("closed");
                        String level_data = row.getString("level-data");
                        String upgrade_data = row.getString("upgrade-data");

                        String[] members_array = row.getString("members").split(";");
                        ArrayList<String> members = new ArrayList<>();
                        for(String s : members_array)
                        {
                            members.add(s);
                        }

                        String[] banned_array = row.getString("banned-players").split(";");
                        ArrayList<String> banned_players = new ArrayList<>();
                        for(String s : banned_array)
                        {
                            banned_players.add(s);
                        }

                        Plot plot = new Plot(owner, location, members, plot_name, closed, banned_players, level_data, upgrade_data);
                        plugin.getPlayerDataManager().getPlots().put(plot_name, plot);
                    }
                    plugin.getLogger().info("Plots loaded successfully!");
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void createPlot(final String owner, final String plot_name, final String location)
    {
        Location loc = LocationUtil.getLocationFromString(location);
        loc.add(0,-1,0).getBlock().setType(Material.BEDROCK);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String upgrade_data = "false;false;false;false;16;"; // speed ; jump ; mob-drop ; mob-exp ; size ;
                    String level_data = "0;0;0;0;0;0;";

                    String query = "INSERT INTO `" + table_plots_settings + "` (`id`,`plot-name`,`plot-settings`,`visitors-settings`) " +
                            "VALUES (NULL,?,?,?);";
                    PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement(query);
                    statement.setString(1, plot_name);
                    statement.setString(2, PlotSettings.getDefaultString());
                    statement.setString(3, VisitorsSettings.getDefaultString());
                    statement.executeUpdate();

                    query = "INSERT INTO `" + plots_table + "` (id, owner, `plot-name`, location, members, `closed`, `banned-players`, `level-data`, `upgrade-data`) " +
                            "VALUES (NULL,?,?,?,?,?,?,?,?);";
                    statement = plugin.getDatabase().getConnection().prepareStatement(query);
                    statement.setString(1, owner);
                    statement.setString(2, plot_name);
                    statement.setString(3, location);
                    statement.setString(4, owner + ";");
                    statement.setBoolean(5, false);
                    statement.setString(6, "");
                    statement.setString(7, level_data);
                    statement.setString(8, upgrade_data);
                    statement.executeUpdate();

                    ArrayList<String> members = new ArrayList<>();
                    members.add(owner);
                    Plot plot = new Plot(owner, LocationUtil.getLocationFromString(location), members, plot_name, false, new ArrayList<String>(), level_data, upgrade_data);
                    plugin.getPlayerDataManager().getPlots().put(plot_name, plot);

                    PlotMember member = plugin.getPlayerDataManager().getPlotMember(owner);
                    member.setOwn_plot(plot_name);
                    plugin.getPlayerDataManager().addPlayerMember(owner, member);
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public PlotMember getPlotMember(Player player)
    {
        try
        {
            PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement("SELECT * FROM `" + plots_table + "` WHERE `owner`=?");
            statement.setString(1, player.getName());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                String own_plot = resultSet.getString("plot-name");
                return new PlotMember(player, own_plot);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return new PlotMember(player, null);
    }

    public void deletePlot(final Plot plot)
    {
        Location loc = plot.getLocation();
        loc.add(0,-1,0).getBlock().setType(Material.AIR);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement("DELETE FROM `" + plots_table + "` WHERE `plot-name`=?");
                    statement.setString(1, plot.getPlot_name());
                    statement.executeUpdate();

                    statement = plugin.getDatabase().getConnection().prepareStatement("DELETE FROM `" + table_plots_settings + "` WHERE `plot-name`=?");
                    statement.setString(1, plot.getPlot_name());
                    statement.executeUpdate();

                    if(plugin.getPlayerDataManager().getPlotMember(plot.getOwner()) != null)
                    {
                        plugin.getPlayerDataManager().getPlotMember(plot.getOwner()).setOwn_plot(null);
                    }
                    plugin.getPlayerDataManager().getPlots().remove(plot.getPlot_name());

                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void addMember(final Plot plot, final String new_member)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String members = "";
                    for(String s : plot.getMembers())
                    {
                        members = members + s + ";";
                    }

                    members = members + new_member + ";";

                    PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement("UPDATE `" + plots_table + "` SET `members`=? WHERE `plot-name`=?");
                    statement.setString(1, members);
                    statement.setString(2, plot.getPlot_name());
                    statement.executeUpdate();
                    plot.addMember(new_member);
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public void kickMember(final Plot plot, final String kicked_member)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String members = "";
                    for(String s : plot.getMembers())
                    {
                        if(!s.equalsIgnoreCase(kicked_member))
                        {
                            members = members + s + ";";
                        }
                    }

                    PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement("UPDATE `" + plots_table + "` SET `members`=? WHERE `plot-name`=?");
                    statement.setString(1, members);
                    statement.setString(2, plot.getPlot_name());
                    statement.executeUpdate();
                    plot.removeMember(kicked_member);
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public boolean hasPlot(Player player)
    {
        if(plugin.getPlayerDataManager().getPlotMember(player.getName()).getOwn_plot() != null)
        {
            return true;
        }
        return false;
    }

    @Nullable
    public Plot getPlotAt(Location loc)
    {
        for(Plot plots : OwnPlots.getInstance().getPlayerDataManager().getPlots().values())
        {
            if(plots.getPlotAt(loc) != null)
            {
                return plots;
            }
        }


        return null;
    }

    @Nullable
    public Plot getPlotAt(Location loc, int custom_size)
    {
        for(Plot plots : OwnPlots.getInstance().getPlayerDataManager().getPlots().values())
        {
            if(plots.getPlotAt(loc, custom_size) != null)
            {
                return plots;
            }
        }


        return null;
    }

    public static Plot getPlotByOwner(String owner)
    {
        PlayerDataManager playerDataManager = OwnPlots.getInstance().getPlayerDataManager();
        return playerDataManager.getPlots().get(playerDataManager.getPlotMember(owner).getOwn_plot());
    }

    @Nullable
    public static Plot getPlotByOfflineOwner(String owner)
    {
        PlayerDataManager playerDataManager = OwnPlots.getInstance().getPlayerDataManager();
        for(Plot plot : playerDataManager.getPlots().values())
        {
            if(plot.getOwner().equalsIgnoreCase(owner))
            {
                return plot;
            }
        }

        return null;
    }

    public Plot getPlotByName(String name)
    {
        return plugin.getPlayerDataManager().getPlots().get(name);
    }

}
