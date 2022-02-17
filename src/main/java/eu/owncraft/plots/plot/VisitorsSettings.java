package eu.owncraft.plots.plot;

import eu.owncraft.plots.OwnPlots;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitorsSettings {

    private boolean block_break, button_use, block_place, chest_access, door_use, furnace_use, item_pickup, mob_damage, trample;
    private OwnPlots plugin;
    private String plot_name;

    public VisitorsSettings(String plot_name)
    {
        this.plot_name = plot_name;
        this.plugin = OwnPlots.getInstance();

        try
        {
            PreparedStatement statement = plugin.getDatabase().getConnection().prepareStatement("SELECT * FROM `" + plugin.getDatabase().getTable_plots_settings() + "` WHERE `plot-name`=?");
            statement.setString(1, plot_name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String settings = resultSet.getString("plot-settings");

            this.block_break = getValueFromString(settings, "block_break");
            this.button_use = getValueFromString(settings, "button_use");
            this.block_place = getValueFromString(settings, "block_place");
            this.chest_access = getValueFromString(settings, "chest_access");
            this.door_use = getValueFromString(settings, "door_use");
            this.furnace_use = getValueFromString(settings, "furnace_use");
            this.item_pickup = getValueFromString(settings, "item_pickup");
            this.mob_damage = getValueFromString(settings, "mob_damage");
            this.trample = getValueFromString(settings, "trample");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }


    }


    public void setBlock_break(boolean block_break) {
        this.block_break = block_break;
        save();
    }

    public void setButton_use(boolean button_use) {
        this.button_use = button_use;
        save();
    }

    public void setBlock_place(boolean block_place) {
        this.block_place = block_place;
        save();
    }

    public void setChest_access(boolean chest_access) {
        this.chest_access = chest_access;
        save();
    }

    public void setDoor_use(boolean door_use) {
        this.door_use = door_use;
        save();
    }

    public void setFurnace_use(boolean furnace_use) {
        this.furnace_use = furnace_use;
        save();
    }

    public void setItem_pickup(boolean item_pickup) {
        this.item_pickup = item_pickup;
        save();
    }

    public void setMob_damage(boolean mob_damage) {
        this.mob_damage = mob_damage;
        save();
    }

    public void setTrample(boolean trample) {
        this.trample = trample;
        save();
    }

    public boolean isBlock_break() {
        return block_break;
    }

    public boolean isButton_use() {
        return button_use;
    }

    public boolean isBlock_place() {
        return block_place;
    }

    public boolean isChest_access() {
        return chest_access;
    }

    public boolean isDoor_use() {
        return door_use;
    }

    public boolean isFurnace_use() {
        return furnace_use;
    }

    public boolean isItem_pickup() {
        return item_pickup;
    }

    public boolean isMob_damage() {
        return mob_damage;
    }

    public boolean isTrample() {
        return trample;
    }

    public String convertToString()
    {
        return "block_break:" + block_break + ";button_use:" + button_use + ";block_place:" + block_place
                + ";chest_access:" + chest_access + ";door_use:" + door_use + ";furnace_use:" + furnace_use
                + ";item_pickup:" + item_pickup + ";mob_damage:" + mob_damage + ";trample:" + trample + ";";
    }

    public void save()
    {
        this.plugin.getDatabase().update("UPDATE `" + plugin.getDatabase().getTable_plots_settings() + "` SET `visitors-settings`='" + this.convertToString() + "' WHERE `plot-name`='" + this.plot_name + "';");
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
        return "block_break:" + false + ";button_use:" + false + ";block_place:" + false
                + ";chest_access:" + false + ";door_use:" + true + ";furnace_use:" + false
                + ";item_pickup:" + true + ";mob_damage:" + false + ";trample:" + false + ";";
    }

}
