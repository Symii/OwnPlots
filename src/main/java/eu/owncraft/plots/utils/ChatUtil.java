package eu.owncraft.plots.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class ChatUtil {

    public static String fixColors(final String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String fixColorsWithPrefix(final String text)
    {
        return ChatColor.translateAlternateColorCodes('&', "&f&lO&9&lC &8» " + text);
    }

    public static List<String> fixColors(final List<String> list)
    {
        for(int i = 0; i < list.size(); i++)
        {
            list.set(i, fixColors(list.get(i)));
        }
        return list;
    }

}
