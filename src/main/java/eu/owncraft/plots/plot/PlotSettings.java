package eu.owncraft.plots.plot;

import eu.owncraft.plots.OwnPlots;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlotSettings {

    private boolean explosions, fire_spread, leaf_decay, natural_spawn, pvp, mob_damage_players;
    private OwnPlots plugin;
    private String plot_name;

    public PlotSettings(String plot_name)
    {
        this.plot_name = plot_name;
        this.plugin = OwnPlots.getInstance();

        try
        {
            PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement("SELECT * FROM `" + plugin.getDatabase().table_plots_settings + "` WHERE `plot-name`=?");
            statement.setString(1, plot_name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String settings = resultSet.getString("plot-settings");

            this.explosions = getValueFromString(settings, "explosions");
            this.fire_spread = getValueFromString(settings, "fire_spread");
            this.leaf_decay = getValueFromString(settings, "leaf_decay");
            this.natural_spawn = getValueFromString(settings, "natural_spawn");
            this.pvp = getValueFromString(settings, "pvp");
            this.mob_damage_players = getValueFromString(settings, "mob_damage_players");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void setExplosions(boolean explosions) {
        this.explosions = explosions;
        save();
    }

    public void setFire_spread(boolean fire_spread) {
        this.fire_spread = fire_spread;
        save();
    }

    public void setLeaf_decay(boolean leaf_decay) {
        this.leaf_decay = leaf_decay;
        save();
    }

    public void setNatural_spawn(boolean natural_spawn) {
        this.natural_spawn = natural_spawn;
        save();
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
        save();
    }

    public void setMob_damage_players(boolean mob_damage_players) {
        this.mob_damage_players = mob_damage_players;
        save();
    }

    public boolean isMob_damage_players() {
        return mob_damage_players;
    }

    public boolean isExplosions() {
        return explosions;
    }

    public boolean isFire_spread() {
        return fire_spread;
    }

    public boolean isLeaf_decay() {
        return leaf_decay;
    }

    public boolean isNatural_spawn() {
        return natural_spawn;
    }

    public boolean isPvp() {
        return pvp;
    }

    public String convertToString()
    {
        return "explosions:" + explosions + ";fire_spread:" + fire_spread + ";leaf_decay:" + leaf_decay
                + ";natural_spawn:" + natural_spawn + ";pvp:" + pvp + ";mob_damage_players:" + mob_damage_players +";";
    }

    public void save()
    {
        this.plugin.getDatabase().update("UPDATE `" + plugin.getDatabase().table_plots_settings + "` SET `plot-settings`='" + this.convertToString() + "' WHERE `plot-name`='" + this.plot_name + "';");
    }

    public static boolean getValueFromString(String string, String flag)
    {
        String[] array = string.split(";");
        for(String text : array)
        {
            String[] text_array = text.split(":");
            if(text_array[0].equals(flag))
            {
                return Boolean.valueOf(text_array[1]);
            }
        }
        return false;
    }

    public static String getDefaultString()
    {
        return "explosions:" + false + ";fire_spread:" + false + ";leaf_decay:" + false
                + ";natural_spawn:" + true + ";pvp:" + false + ";mob_damage_players:" + false + ";" ;
    }

}
