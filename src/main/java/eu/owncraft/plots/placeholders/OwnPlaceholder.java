package eu.owncraft.plots.placeholders;

import eu.owncraft.plots.database.PlotManager;
import eu.owncraft.plots.plot.Plot;
import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class OwnPlaceholder extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "Symi";
    }

    @Override
    public String getIdentifier() {
        return "ownplots";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        if(player == null) {
            return "";
        }

        Plot plot = PlotManager.getPlotByOwner(player.getName());

        if(plot == null) {
            return "0";
        }

        if(params.equalsIgnoreCase("lvl") || params.equalsIgnoreCase("level")) {

            if(plot != null) {
                return "" + plot.getLevel();
            } else {
                return "0";
            }

        } else if(params.equalsIgnoreCase("size")) {
            if(plot != null) {
                return "" + plot.getSize();
            } else {
                return "0";
            }

        } else if(params.equalsIgnoreCase("member_size")) {
            if(plot != null) {
                return "" + plot.getMembers().size();
            } else {
                return "0";
            }

        } else if(params.equalsIgnoreCase("member_max")) {
            if(plot != null) {
                return "" + plot.getMaxMembersCount();
            } else {
                return "0";
            }

        }

        return null; // Placeholder is unknown by the Expansion
    }
}