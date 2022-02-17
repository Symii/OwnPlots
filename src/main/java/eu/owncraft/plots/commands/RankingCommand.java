package eu.owncraft.plots.commands;

import eu.owncraft.plots.gui.PlotGUI;
import eu.owncraft.plots.utils.ChatUtil;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("Nie mozesz tego uzywac.");
            return true;
        }

        Player player = (Player) sender;
        player.openInventory(PlotGUI.getRankingInventory(player));
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 0.7f, 0.7f);

        return true;
    }

}
