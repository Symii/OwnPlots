package eu.owncraft.plots.plot;

import eu.owncraft.plots.OwnPlots;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class PlotBorder {

    /*
    public void displayBorder(final Player player, final Location location, final int size)
    {
        WorldBorder worldBorder = new WorldBorder();
        worldBorder.world = ((CraftWorld) location.getWorld()).getHandle();
        worldBorder.setCenter(location.getBlockX(), location.getBlockZ());
        worldBorder.setSize(size * 2);
        worldBorder.setDamageAmount(0);
        worldBorder.setWarningDistance(0);
        PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
    }

    public void hideBorder(Player player, World world)
    {
        WorldBorder worldBorder = new WorldBorder();
        worldBorder.world = ((CraftWorld) world).getHandle();

        PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.LERP_SIZE);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
    }*/


    public void displayBorder(Player player, Location location, int size)
    {
        OwnPlots.getInstance().getWorldBorderApi().setBorder(player, size * 2, location);
    }

    public void hideBorder(Player player)
    {
        OwnPlots.getInstance().getWorldBorderApi().resetWorldBorderToGlobal(player);
    }

}
