package eu.owncraft.plots;

import eu.owncraft.plots.commands.ChallengeCommand;
import eu.owncraft.plots.commands.PlotCommand;
import eu.owncraft.plots.config.ConfigManager;
import eu.owncraft.plots.database.MySQL;
import eu.owncraft.plots.database.PlotManager;
import eu.owncraft.plots.listeners.*;
import eu.owncraft.plots.playerdata.PlayerDataManager;
import eu.owncraft.plots.tasks.PlotSaveTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class OwnPlots extends JavaPlugin {

    private static OwnPlots INSTANCE;
    private ConfigManager config_manager;
    private MySQL database;
    private PlayerDataManager playerDataManager;
    private PlotManager plotManager;

    @Override
    public void onLoad()
    {
        INSTANCE = this;
    }

    @Override
    public void onEnable()
    {
        if(!getServer().getPluginManager().getPlugin("Essentials").isEnabled())
        {
            getLogger().severe("Could not find EssentialsX plugin!");
            getLogger().severe("To run this plugin you must install it!");
            setEnabled(false);
            return;
        }

        config_manager = new ConfigManager(this);
        database = new MySQL(this);
        playerDataManager = new PlayerDataManager();
        plotManager = new PlotManager(this);

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () ->
        {
            getLogger().info("Loading plots...");
            plotManager.loadPlots();
        }, 5L);


        getCommand("dzialka").setExecutor(new PlotCommand(this));
        getCommand("challenge").setExecutor(new ChallengeCommand(this));
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new BlockListeners(this), this);
        manager.registerEvents(new JoinListeners(this), this);
        manager.registerEvents(new PlayerListeners(this), this);
        manager.registerEvents(new InventoryListeners(), this);
        manager.registerEvents(new PlotListeners(), this);
        manager.registerEvents(new EntityListeners(this), this);
        manager.registerEvents(new ChatListeners(), this);

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new PlotSaveTask(this), 18000L, 18000L);
    }

    @Override
    public void onDisable()
    {
        if(playerDataManager != null)
            playerDataManager.onDisable();
    }

    public MySQL getDatabase()
    {
        return database;
    }

    public ConfigManager getConfig_manager()
    {
        return config_manager;
    }

    public static OwnPlots getInstance()
    {
        return INSTANCE;
    }

    public PlotManager getPlotManager()
    {
        return plotManager;
    }

    public PlayerDataManager getPlayerDataManager()
    {
        return playerDataManager;
    }

}
