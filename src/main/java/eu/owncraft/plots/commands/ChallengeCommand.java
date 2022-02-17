package eu.owncraft.plots.commands;

import eu.owncraft.plots.OwnPlots;
import eu.owncraft.plots.database.PlotManager;
import eu.owncraft.plots.gui.ChallengeGUI;
import eu.owncraft.plots.utils.ChatUtil;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChallengeCommand implements CommandExecutor {

    private final OwnPlots plugin;

    public ChallengeCommand(OwnPlots plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!(sender instanceof Player))
        {
            sender.sendMessage("Nie mozesz tego uzywac");
            return true;
        }

        final Player player = (Player) sender;

        if(OwnPlots.getInstance().getPlotManager().hasPlot(player) == false) {
            player.sendMessage(ChatUtil.fixColorsWithPrefix("&cnie nalezysz do zadnej dzialki, aby z tego korzystac!"));
            return true;
        }

        player.openInventory(ChallengeGUI.getChallengesInventory(player));
        player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, 1.0f ,1.0f);

        return true;
    }
}
