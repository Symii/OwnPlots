package eu.owncraft.plots.gui;

import eu.owncraft.plots.OwnPlots;
import eu.owncraft.plots.challenge.Challenge;
import eu.owncraft.plots.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ChallengeGUI {

    public static Inventory getChallengesInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, ChatUtil.fixColorsWithPrefix("&e&lWyzwania"));
        Challenge challenge = OwnPlots.getInstance().getPlayerDataManager().getChallenge_players().get(player.getName());

        ItemStack black = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackMeta = black.getItemMeta();
        blackMeta.setDisplayName(ChatUtil.fixColors("&cWyzwania"));
        black.setItemMeta(blackMeta);

        for(int i = 0; i <= 53; i++)
        {
            inventory.setItem(i, black);
        }

        int counter = 1;

        for(int i = 10; i<= 70; i += 10)
        {
            int reward = 50 * i;
            ItemStack plot_level = new ItemStack(Material.CHEST_MINECART);
            ItemMeta plot_level_meta = plot_level.getItemMeta();
            plot_level_meta.setDisplayName(ChatUtil.fixColors("&aPoziom " + i));
            plot_level_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7[&f!&7] &7Aby wykonac to zadanie",
                    "     &7musisz miec " + i + " poziom dzialki",
                    "&7[&f!&7] &eNagrody:",
                    "     &7- &a" + reward + " magicznego pylku",
                    "",
                    "&eKliknij, aby odebrac nagrode!"
            )));
            plot_level.setItemMeta(plot_level_meta);

            if(challenge.isChallengeCompleted(i))
            {
                plot_level.setType(Material.HOPPER_MINECART);
            }

            inventory.setItem(counter, plot_level);
            counter++;
        }

        counter = 10;

        for(int i = 80; i<= 140; i += 10)
        {
            int reward = 50 * i;
            ItemStack plot_level = new ItemStack(Material.CHEST_MINECART);
            ItemMeta plot_level_meta = plot_level.getItemMeta();
            plot_level_meta.setDisplayName(ChatUtil.fixColors("&aPoziom " + i));
            plot_level_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7[&f!&7] &7Aby wykonac to zadanie",
                    "     &7musisz miec " + i + " poziom dzialki",
                    "&7[&f!&7] &eNagrody:",
                    "     &7- &a" + reward + " magicznego pylku",
                    "",
                    "&eKliknij, aby odebrac nagrode!"
            )));
            plot_level.setItemMeta(plot_level_meta);

            if(challenge.isChallengeCompleted(i))
            {
                plot_level.setType(Material.HOPPER_MINECART);
            }

            inventory.setItem(counter, plot_level);
            counter++;
        }

        counter = 20;

        for(int i = 150; i<= 190; i += 10)
        {
            int reward = 50 * i;
            ItemStack plot_level = new ItemStack(Material.CHEST_MINECART);
            ItemMeta plot_level_meta = plot_level.getItemMeta();
            plot_level_meta.setDisplayName(ChatUtil.fixColors("&aPoziom " + i));
            plot_level_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7[&f!&7] &7Aby wykonac to zadanie",
                    "     &7musisz miec " + i + " poziom dzialki",
                    "&7[&f!&7] &eNagrody:",
                    "     &7- &a" + reward + " magicznego pylku",
                    "",
                    "&eKliknij, aby odebrac nagrode!"
            )));
            plot_level.setItemMeta(plot_level_meta);

            if(challenge.isChallengeCompleted(i))
            {
                plot_level.setType(Material.HOPPER_MINECART);
            }

            inventory.setItem(counter, plot_level);
            counter++;
        }

        counter = 30;

        for(int i = 200; i<= 220; i += 10)
        {
            int reward = 50 * i;
            ItemStack plot_level = new ItemStack(Material.CHEST_MINECART);
            ItemMeta plot_level_meta = plot_level.getItemMeta();
            plot_level_meta.setDisplayName(ChatUtil.fixColors("&aPoziom " + i));
            plot_level_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7[&f!&7] &7Aby wykonac to zadanie",
                    "     &7musisz miec " + i + " poziom dzialki",
                    "&7[&f!&7] &eNagrody:",
                    "     &7- &a" + reward + " magicznego pylku",
                    "",
                    "&eKliknij, aby odebrac nagrode!"
            )));
            plot_level.setItemMeta(plot_level_meta);

            if(challenge.isChallengeCompleted(i))
            {
                plot_level.setType(Material.HOPPER_MINECART);
            }

            inventory.setItem(counter, plot_level);
            counter++;
        }

        int reward = 50 * 250;
        ItemStack plot_level = new ItemStack(Material.CHEST_MINECART);
        ItemMeta plot_level_meta = plot_level.getItemMeta();
        plot_level_meta.setDisplayName(ChatUtil.fixColors("&aPoziom " + 250));
        plot_level_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7[&f!&7] &7Aby wykonac to zadanie",
                "     &7musisz miec " + 250 + " poziom dzialki",
                "&7[&f!&7] &eNagrody:",
                "     &7- &a" + reward + " magicznego pylku",
                "",
                "&eKliknij, aby odebrac nagrode!"
        )));
        plot_level.setItemMeta(plot_level_meta);

        if(challenge.isChallengeCompleted(250))
        {
            plot_level.setType(Material.HOPPER_MINECART);
        }

        inventory.setItem(40, plot_level);

        return inventory;
    }

}
