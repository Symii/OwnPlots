package eu.owncraft.plots.plot;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_16_R3.WorldBorder;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;

public class PlotBorder {

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
    }

}
