package eu.owncraft.plots.database;

import eu.owncraft.plots.OwnPlots;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class MySQL {

    private OwnPlots plugin;
    private Connection connection;
    private String host, database, username, password;
    private int port;

    public String plots_table, table_plots_settings, table_plots_challenges;

    public MySQL(OwnPlots plugin)
    {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();
        host = config.getString("mysql.host");
        database = config.getString("mysql.database");
        username = config.getString("mysql.username");
        password = config.getString("mysql.password");
        port = config.getInt("mysql.port");
        plots_table = config.getString("mysql.table-plots");
        table_plots_settings = config.getString("mysql.table-plots-settings");
        table_plots_challenges = config.getString("mysql.table-plots-challenges");
        connect();
    }

    public void connect()
    {
        try
        {
            synchronized(this)
            {
                if(connection != null && !connection.isClosed())
                {
                    return;
                }

                final Properties prop = new Properties();
                prop.setProperty("user", username);
                prop.setProperty("password", password);
                prop.setProperty("autoReconnect", "true");


                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + this.host
                        + ":" + this.port + "/" + this.database, prop);

                createTable();

                plugin.getLogger().info("MySQL database connected successfully");
            }
        }
        catch(ClassNotFoundException | SQLException e)
        {
            plugin.getLogger().severe("Failed to connect into MySQL database !!!!");
            e.printStackTrace();
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    private void createTable()
    {
        try
        {
            String query = "CREATE TABLE IF NOT EXISTS `" + database + "`.`" + plots_table + "` ( `id` INT NOT NULL AUTO_INCREMENT , `owner` TEXT NOT NULL , `plot-name` TEXT NOT NULL , `location` TEXT NOT NULL , `members` TEXT NOT NULL , `closed` BOOLEAN NOT NULL , `banned-players` TEXT NOT NULL , `level-data` TEXT NOT NULL , `upgrade-data` TEXT NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;";
            PreparedStatement create_statement = connection.prepareStatement(query);
            create_statement.executeUpdate();

            query = "CREATE TABLE IF NOT EXISTS `" + database + "`.`" + table_plots_settings + "` ( `id` INT NOT NULL AUTO_INCREMENT , `plot-name` TEXT NOT NULL , `plot-settings` TEXT NOT NULL , `visitors-settings` TEXT NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;";
            create_statement = connection.prepareStatement(query);
            create_statement.executeUpdate();

            query = "CREATE TABLE IF NOT EXISTS `" + database + "`.`" + table_plots_challenges + "` ( `id` INT NOT NULL AUTO_INCREMENT , `playername` TEXT NOT NULL , `data` TEXT NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;";
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

    public Connection getConnection()
    {
        return connection;
    }

}
