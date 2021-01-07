package eu.owncraft.plots.challenge;

import eu.owncraft.plots.OwnPlots;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Challenge {

    private Player player;
    private String data;

    public Challenge(Player player)
    {
        this.player = player;
        this.loadData();
    }

    private void loadData()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String table = OwnPlots.getInstance().getDatabase().table_plots_challenges;
                    PreparedStatement statement = OwnPlots.getInstance().getDatabase().getConnection().prepareStatement(
                            "SELECT `data` FROM `" + table + "` WHERE `playername`=?");
                    statement.setString(1, player.getName());
                    ResultSet resultSet = statement.executeQuery();
                    if(resultSet.next())
                    {
                        data = resultSet.getString("data");
                    }
                    else
                    {
                        statement = OwnPlots.getInstance().getDatabase().getConnection().prepareStatement(
                                "INSERT INTO `" + table + "` (`id`,`playername`,`data`) VALUES (NULL,?,?);");
                        statement.setString(1, player.getName());
                        statement.setString(2, ";");
                        statement.executeUpdate();
                        data = ";";
                    }
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(OwnPlots.getInstance());
    }

    private void saveData()
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String table = OwnPlots.getInstance().getDatabase().table_plots_challenges;
                    PreparedStatement statement = OwnPlots.getInstance().getDatabase().getConnection().prepareStatement(
                            "UPDATE `" + table + "` SET `data`=? WHERE `playername`=?");
                    statement.setString(1, data);
                    statement.setString(2, player.getName());
                    statement.executeUpdate();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(OwnPlots.getInstance());
    }

    public void setChallengeComplete(int challengeLevel)
    {
        data = data + challengeLevel + ";";
        saveData();
    }

    public boolean isChallengeCompleted(int challengeLevel)
    {
        return data.contains(";" + challengeLevel + ";");
    }

    public int getMysteryDust(int challengeLevel)
    {
        return 50 * challengeLevel;
    }

}
