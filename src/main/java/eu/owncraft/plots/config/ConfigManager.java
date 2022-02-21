package eu.owncraft.plots.config;

import eu.owncraft.plots.OwnPlots;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final OwnPlots plugin;
    private int plot_create_cost, plot_max_size, plot_increase_field_cost, jump_boost_upgrade_cost,
        speed_upgrade_cost, mob_drop_upgrade_cost, mob_exp_upgrade_cost;
    private boolean fly_enabled_only_on_plots, update_check;
    private String allowed_world, language;

    public ConfigManager(OwnPlots plugin)
    {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        reloadConfig();
    }

    public void reloadConfig()
    {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        this.plot_create_cost = config.getInt("plot-create-cost");
        this.plot_max_size = config.getInt("plot-max-size");
        this.plot_increase_field_cost = config.getInt("plot-increase-field-cost");
        this.jump_boost_upgrade_cost = config.getInt("jump-boost-upgrade-cost");
        this.speed_upgrade_cost = config.getInt("speed-upgrade-cost");
        this.mob_drop_upgrade_cost = config.getInt("mob-drop-upgrade-cost");
        this.mob_exp_upgrade_cost = config.getInt("mob-exp-upgrade-cost");
        this.fly_enabled_only_on_plots = config.getBoolean("fly-enabled-only-on-plots");
        this.allowed_world = config.getString("allowed-world");
        this.language = config.getString("language");
        this.update_check = config.getBoolean("update-checker");
    }

    public String getLanguage() {
        return language;
    }

    public boolean isUpdate_check() {
        return update_check;
    }

    public int getPlot_create_cost()
    {
        return plot_create_cost;
    }

    public int getPlot_max_size()
    {
        return plot_max_size;
    }

    public int getPlot_increase_field_cost()
    {
        return plot_increase_field_cost;
    }

    public int getJump_boost_upgrade_cost()
    {
        return jump_boost_upgrade_cost;
    }

    public int getSpeed_upgrade_cost()
    {
        return speed_upgrade_cost;
    }

    public int getMob_drop_upgrade_cost()
    {
        return mob_drop_upgrade_cost;
    }

    public int getMob_exp_upgrade_cost()
    {
        return  mob_exp_upgrade_cost;
    }

    public boolean isFly_enabled_only_on_plots() {
        return fly_enabled_only_on_plots;
    }

    public String getAllowed_world() {
        return allowed_world;
    }
}
