package eu.owncraft.plots.utils;

import eu.owncraft.plots.OwnPlots;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Random;

public class RandomTP {

    public static void randomTeleport(final Player player, int counter)
    {
        final World world = player.getWorld();
        Location current_loc = player.getLocation();
        double x = (new Random().nextInt( 32) - 16) + current_loc.getBlockX();
        double z = (new Random().nextInt(32) - 16) + current_loc.getBlockZ();
        int y = 40;
        final Location temp = new Location(world, x, y, z);
        final Location temp2 = new Location(world, x, y + 1, z);
        boolean canTeleport = false;
        while(!canTeleport)
        {
            if (temp.getBlock().getType() == Material.AIR && temp2.getBlock().getType() == Material.AIR)
            {
                canTeleport = true;
            }
            else if(temp.getBlock().getType().toString().contains("LAVA") || temp.getBlock().getType().toString().contains("WATER") || y >= 100)
            {
                x = x + (new Random().nextInt(32) - 16);
                z = z + (new Random().nextInt(32) - 16);
                y = 40;
                temp.setX(x);
                temp.setY(y);
                temp.setZ(z);
                temp2.setX(x);
                temp2.setY(y + 1);
                temp2.setZ(z);
            }
            else {
                ++y;
                temp.setY(y);
                temp2.setY(y + 1);
            }
        }

        if (x >= 0.0) {
            x += 0.5;
        }
        else {
            x -= 0.5;
        }
        if (z >= 0.0) {
            z += 0.5;
        }
        else {
            z -= 0.5;
        }

        Location new_location = new Location(world, x, y, z);
        if(OwnPlots.getInstance().getPlotManager().getPlotAt(new_location) != null)
        {
            if(counter >= 7)
            {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + player.getName());
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&edzialka na ktora probowales wejsc jest zamknieta lub jestes na niej &czbanowany&e!"));
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                return;
            }
            randomTeleport(player, counter + 1);
            return;
        }

        player.teleport(new_location, PlayerTeleportEvent.TeleportCause.UNKNOWN);
        player.sendMessage(ChatUtil.fixColorsWithPrefix("&edzialka na ktora probowales wejsc jest zamknieta lub jestes na niej &czbanowany&e!"));
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
    }

}
