package eu.owncraft.plots;

import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import eu.owncraft.plots.commands.ChallengeCommand;
import eu.owncraft.plots.commands.PlotCommand;
import eu.owncraft.plots.commands.RankingCommand;
import eu.owncraft.plots.config.ConfigManager;
import eu.owncraft.plots.database.IPlotDatabase;
import eu.owncraft.plots.database.MySQL;
import eu.owncraft.plots.database.PlotManager;
import eu.owncraft.plots.database.SQLite;
import eu.owncraft.plots.listeners.*;
import eu.owncraft.plots.placeholders.OwnPlaceholder;
import eu.owncraft.plots.playerdata.PlayerDataManager;
import eu.owncraft.plots.tasks.PlotSaveTask;
import eu.owncraft.plots.utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class OwnPlots extends JavaPlugin {

    private static OwnPlots INSTANCE;
    private ConfigManager config_manager;
    private PlayerDataManager playerDataManager;
    private PlotManager plotManager;

    private WorldBorderApi worldBorderApi;
    private IPlotDatabase database;

    @Override
    public void onLoad()
    {
        INSTANCE = this;
    }

    @Override
    public void onEnable()
    {
        RegisteredServiceProvider<WorldBorderApi> worldBorderApiRegisteredServiceProvider = getServer().getServicesManager().getRegistration(WorldBorderApi.class);

        if (worldBorderApiRegisteredServiceProvider == null) {
            getLogger().info("API not found");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        

        worldBorderApi = worldBorderApiRegisteredServiceProvider.getProvider();

        if(!getServer().getPluginManager().getPlugin("Essentials").isEnabled())
        {
            getLogger().severe("Could not find EssentialsX plugin!");
            getLogger().severe("To run this plugin you must install it!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        config_manager = new ConfigManager(this);
        if(getConfig().getBoolean("mysql.enabled"))
        {
            database = new MySQL(this);
        }
        else
        {
            database = new SQLite(this);
        }
        playerDataManager = new PlayerDataManager();
        plotManager = new PlotManager(this);

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () ->
        {
            getLogger().info("Loading plots...");
            plotManager.loadPlots();
        }, 5L);


        getCommand("dzialka").setExecutor(new PlotCommand(this));
        getCommand("challenge").setExecutor(new ChallengeCommand(this));
        getCommand("ranking").setExecutor(new RankingCommand());
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new BlockListeners(this), this);
        manager.registerEvents(new JoinListeners(this), this);
        manager.registerEvents(new PlayerListeners(this), this);
        manager.registerEvents(new InventoryListeners(), this);
        manager.registerEvents(new PlotListeners(), this);
        manager.registerEvents(new EntityListeners(this), this);
        manager.registerEvents(new ChatListeners(), this);
        manager.registerEvents(new FlyListener(), this);

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new PlotSaveTask(this), 18000L, 18000L);
        new OwnPlaceholder().register();
        new Metrics(this, 14347);
    }

    @Override
    public void onDisable()
    {
        if(playerDataManager != null)
            playerDataManager.onDisable();
    }

    public IPlotDatabase getDatabase()
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

    public WorldBorderApi getWorldBorderApi() {
        return worldBorderApi;
    }

}
