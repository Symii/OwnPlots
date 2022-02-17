package eu.owncraft.plots.database;

import java.sql.Connection;

public interface IPlotDatabase {

    void onDisable();
    Connection getConnection();
    void disconnect();
    void connect();
    String getPlots_table();
    String getTable_plots_settings();
    String getTable_plots_challenges();
    void update(String update);
}
