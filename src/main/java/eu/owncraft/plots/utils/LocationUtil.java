package eu.owncraft.plots.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {

    public static String convertToString(Location location)
    {
        String loc = location.getBlockX() + ";" + location.getBlockY() + ";"
                + location.getBlockZ() + ";" + location.getWorld().getName() + ";";

        return loc;
    }

    public static Location getLocationFromString(String loc)
    {
        String[] array = loc.split(";");
        int blockX = Integer.parseInt(array[0]); int blockY = Integer.parseInt(array[1]); int blockZ = Integer.parseInt(array[2]);
        if (blockX >= 0.0) {
            blockX += 0.5;
        }
        else {
            blockX -= 0.5;
        }
        if (blockZ >= 0.0) {
            blockZ += 0.5;
        }
        else {
            blockZ -= 0.5;
        }
        return new Location(Bukkit.getWorld(array[3]), blockX, blockY, blockZ);
    }

}
