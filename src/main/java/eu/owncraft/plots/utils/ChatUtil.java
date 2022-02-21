package eu.owncraft.plots.utils;

import eu.owncraft.plots.OwnPlots;
import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {

    private static final Pattern hexPattern = Pattern.compile("<#([A-Fa-f0-9]){6}>");

    public static String applyColor(String message) {
        Matcher matcher = hexPattern.matcher(message);
        while (matcher.find()) {
            final ChatColor hexColor = ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
            final String before = message.substring(0, matcher.start());
            final String after = message.substring(matcher.end());
            message = before + hexColor + after;
            matcher = hexPattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String fixColors(final String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String fixColorsWithPrefix(final String text)
    {
        return ChatColor.translateAlternateColorCodes('&',
                OwnPlots.getInstance().getLanguageManager().getPlugin_prefix() + " " + text);
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
