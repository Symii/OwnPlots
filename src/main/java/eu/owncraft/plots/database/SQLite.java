package eu.owncraft.plots.database;

import eu.owncraft.plots.OwnPlots;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLite implements IPlotDatabase {

    private final OwnPlots plugin;
    private Connection connection;

    public String plots_table, table_plots_settings, table_plots_challenges;

    public SQLite(OwnPlots plugin)
    {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();
        plots_table = config.getString("mysql.table-plots");
        table_plots_settings = config.getString("mysql.table-plots-settings");
        table_plots_challenges = config.getString("mysql.table-plots-challenges");
        connect();
    }

    public void connect()
    {
        try
        {
            File file = new File(plugin.getDataFolder(), "database.db");
            if(!file.exists())
            {
                new File(plugin.getDataFolder().getPath()).mkdir();
            }
            String URL = "jdbc:sqlite:" + file;

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            plugin.getLogger().info("connected into SQLite database successfully");
            createTable();
        }
        catch(SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getPlots_table() {
        return plots_table;
    }

    @Override
    public String getTable_plots_settings() {
        return table_plots_settings;
    }

    @Override
    public String getTable_plots_challenges() {
        return table_plots_challenges;
    }

    public void disconnect()
    {
        try
        {
            connection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void createTable()
    {
        try
        {
            String query = "CREATE TABLE IF NOT EXISTS `" + plots_table + "` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `owner` TEXT NOT NULL , `plot-name` TEXT NOT NULL , `location` TEXT NOT NULL , `members` TEXT NOT NULL , `closed` BOOLEAN NOT NULL , `banned-players` TEXT NOT NULL , `level-data` TEXT NOT NULL , `upgrade-data` TEXT NOT NULL, `level` INT NOT NULL);";
            PreparedStatement create_statement = connection.prepareStatement(query);
            create_statement.executeUpdate();

            query = "CREATE TABLE IF NOT EXISTS `" + table_plots_settings + "` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `plot-name` TEXT NOT NULL , `plot-settings` TEXT NOT NULL , `visitors-settings` TEXT NOT NULL);";
            create_statement = connection.prepareStatement(query);
            create_statement.executeUpdate();

            query = "CREATE TABLE IF NOT EXISTS `" + table_plots_challenges + "` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `playername` TEXT NOT NULL , `data` TEXT NOT NULL);";
            create_statement = connection.prepareStatement(query);
            create_statement.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void update(final String update)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                try (final PreparedStatement preparedStatement = connection.prepareStatement(update))
                {
                    if (preparedStatement == null)
                        return;

                    preparedStatement.executeUpdate();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);

    }

    public void onDisable()
    {
        disconnect();
    }

    public Connection getConnection()
    {
        return connection;
    }

}
