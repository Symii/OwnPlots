package eu.owncraft.plots;

import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import eu.owncraft.plots.commands.ChallengeCommand;
import eu.owncraft.plots.commands.PlotCommand;
import eu.owncraft.plots.commands.RankingCommand;
import eu.owncraft.plots.config.ConfigManager;
import eu.owncraft.plots.config.FileManager;
import eu.owncraft.plots.config.LanguageManager;
import eu.owncraft.plots.database.IPlotDatabase;
import eu.owncraft.plots.database.MySQL;
import eu.owncraft.plots.database.PlotManager;
import eu.owncraft.plots.database.SQLite;
import eu.owncraft.plots.listeners.*;
import eu.owncraft.plots.placeholders.OwnPlaceholder;
import eu.owncraft.plots.playerdata.PlayerDataManager;
import eu.owncraft.plots.tasks.PlotSaveTask;
import eu.owncraft.plots.utils.Metrics;
import eu.owncraft.plots.utils.UpdateChecker;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class OwnPlots extends JavaPlugin {

    private static OwnPlots INSTANCE;
    private static Economy eco = null;

    private ConfigManager configManager;
    private FileManager fileManager;
    private LanguageManager languageManager;
    private PlayerDataManager playerDataManager;
    private PlotManager plotManager;
    private WorldBorderApi worldBorderApi;
    private IPlotDatabase database;

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        RegisteredServiceProvider<WorldBorderApi> worldBorderApiRegisteredServiceProvider = getServer().getServicesManager().getRegistration(WorldBorderApi.class);

        if (worldBorderApiRegisteredServiceProvider == null) {
            getLogger().severe(String.format("[%s] - Disabled due to no WorldBorderAPI dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        worldBorderApi = worldBorderApiRegisteredServiceProvider.getProvider();

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        configManager = new ConfigManager(this);
        if(getConfig().getBoolean("mysql.enabled")) {
            database = new MySQL(this);
        } else {
            database = new SQLite(this);
        }

        fileManager = new FileManager(this);
        languageManager = new LanguageManager(this);
        languageManager.loadMessages();

        playerDataManager = new PlayerDataManager();
        plotManager = new PlotManager(this);

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
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

        new UpdateChecker(this, 100074).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("[OwnPlots] - There is not a new update available.");
            } else {
                getLogger().info("[OwnPlots] - There is a new update available. Download update here https://www.spigotmc.org/resources/ownplots-1-18-plugin-na-dzia%C5%82ki-survival.100074/");
            }
        });
    }

    @Override
    public void onDisable() {
        if(playerDataManager != null)
            playerDataManager.onDisable();
    }

    public IPlotDatabase getDatabase() {
        return database;
    }

    public ConfigManager getConfig_manager() {
        return configManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public static OwnPlots getInstance() {
        return INSTANCE;
    }

    public PlotManager getPlotManager() {
        return plotManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public WorldBorderApi getWorldBorderApi() {
        return worldBorderApi;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        eco = rsp.getProvider();
        return eco != null;
    }

    public Long getPlayerMoney(Player player) {
        return (long) eco.getBalance(player);
    }

    public void setPlayerMoney(Player player, int value) {
        eco.withdrawPlayer(player, eco.getBalance(player));
        eco.depositPlayer(player, value);
    }

    public void takePlayerMoney(Player player, int value) {
        eco.withdrawPlayer(player, value);
    }

    public void addPlayerMoney(Player player, int value) {
        eco.depositPlayer(player, value);
    }
}
