package eu.owncraft.plots.gui;

import eu.owncraft.plots.OwnPlots;
import eu.owncraft.plots.plot.Plot;
import eu.owncraft.plots.plot.PlotSettings;
import eu.owncraft.plots.plot.VisitorsSettings;
import eu.owncraft.plots.utils.ChatUtil;
import eu.owncraft.plots.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class PlotGUI {

    public static Inventory getMainInventory(Player player)
    {
        Inventory inventory = Bukkit.createInventory(player, 36, ChatUtil.fixColorsWithPrefix("&e&lMenu dzialki"));
        ItemStack black = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blackMeta = black.getItemMeta();
        blackMeta.setDisplayName(ChatUtil.fixColors("&cUstawienia dzialki"));
        black.setItemMeta(blackMeta);
        ItemStack blue = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta blueMeta = blue.getItemMeta();
        blueMeta.setDisplayName(ChatUtil.fixColors("&cUstawienia dzialki"));
        blue.setItemMeta(blueMeta);

        ItemStack ranking = new ItemStack(Material.NETHERITE_INGOT);
        ItemMeta ranking_meta = ranking.getItemMeta();
        ranking_meta.setDisplayName(ChatUtil.fixColors("&aRanking"));
        ranking_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7[&f!&7] &7Zdobywal level na dzialce",
                "     &7aby byc na samym szczycie rankingu!",
                "",
                "&eKliknij, aby zobaczyć ranking!"
        )));
        ranking.setItemMeta(ranking_meta);

        ItemStack challenges = new ItemStack(Material.CHEST_MINECART);
        ItemMeta challenges_meta = challenges.getItemMeta();
        challenges_meta.setDisplayName(ChatUtil.fixColors("&aWyzwania"));
        challenges_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7[&f!&7] &7Zdobywal level na dzialce",
                "     &7oraz odbieraj nagrody za",
                "     &7wyzwania!",
                "",
                "&eKliknij, aby otworzyc menu wyzwan!"
        )));
        challenges.setItemMeta(challenges_meta);

        ItemStack plot_level = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta plot_level_meta = plot_level.getItemMeta();
        plot_level_meta.setDisplayName(ChatUtil.fixColors("&aPoziom dzialki"));
        plot_level_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7[&f!&7] &7Zdobywal level na dzialce",
                "     &7dzieki jej ulepszaniu",
                "     &7oraz stawianiu rzadkich blokow.",
                "",
                "&eKliknij, aby obliczyc level dzialki!"
        )));
        plot_level.setItemMeta(plot_level_meta);

        ItemStack plot_border = new ItemStack(Material.BARRIER);
        ItemMeta plot_border_meta = plot_border.getItemMeta();
        plot_border_meta.setDisplayName(ChatUtil.fixColors("&aBorder dzialki"));
        plot_border_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7[&f!&7] &7Sprawdz, jak duza jest",
                "     &7twoja dzialka!",
                "",
                "&eKliknij, aby sprawdzic rozmiar dzialki!"
        )));
        plot_border.setItemMeta(plot_border_meta);

        ItemStack plot_settings = new ItemStack(Material.NAME_TAG);
        ItemMeta plot_settings_meta = plot_settings.getItemMeta();
        plot_settings_meta.setDisplayName(ChatUtil.fixColors("&aUstawienia dzialki"));
        plot_settings_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7[&f!&7] &7Zarzadzaj ustawieniami dzialki",
                "     &7i zadecyduj, ktore pozwolenia",
                "     &7sa dla Ciebie najwygodniejsze.",
                "",
                "&eKliknij, aby zobaczyc ustawienia wyspy!"
        )));
        plot_settings.setItemMeta(plot_settings_meta);

        ItemStack banned_players = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta banned_players_meta = banned_players.getItemMeta();
        banned_players_meta.setDisplayName(ChatUtil.fixColors("&aZbanowani gracze"));
        banned_players_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7[&f!&7] &7Zobacz, ktorzy gracze",
                "     &7sa zbanowani i nie moga",
                "     &7dolaczyc do Twojej wyspy.",
                "",
                "&eKliknij, aby zobaczyc liste banow!"
        )));
        banned_players.setItemMeta(banned_players_meta);

        ItemStack plot_upgrades = new ItemStack(Material.ANVIL);
        ItemMeta plot_upgrades_meta = plot_upgrades.getItemMeta();
        plot_upgrades_meta.setDisplayName(ChatUtil.fixColors("&aUlepszenia dzialki"));
        plot_upgrades_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7[&f!&7] &7Ulepszaj swoja dzialke",
                "     &7i coraz bardziej ją rozbuduj!",
                "",
                "&eKliknij, aby zobaczyc ulepszenia działki!"
        )));
        plot_upgrades.setItemMeta(plot_upgrades_meta);

        ItemStack plot_members = new ItemStack(Material.ITEM_FRAME);
        ItemMeta plot_members_meta = plot_members.getItemMeta();
        plot_members_meta.setDisplayName(ChatUtil.fixColors("&aCzlonkowie dzialki"));
        plot_members_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7[&f!&7] &7Wyswietl liste członków na dzialce",
                "     &7oraz zobacz ich statystyki.",
                "",
                "&eKliknij, aby zobaczyc liste czlonkow dzialki!"
        )));
        plot_members.setItemMeta(plot_members_meta);

        ItemStack plot_block = new ItemStack(Material.IRON_DOOR);
        ItemMeta plot_block_meta = plot_block.getItemMeta();
        plot_block_meta.setDisplayName(ChatUtil.fixColors("&aBlokowanie dzialki"));
        plot_block_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7[&f!&7] &7Otworz lub zamknij",
                "     &7swoja wyspe dla odwiedzajacych.",
                "",
                "&eKliknij, aby otworzyc/zamknac działke!"
        )));
        plot_block.setItemMeta(plot_block_meta);

        ItemStack plot_home = new ItemStack(Material.OAK_DOOR);
        ItemMeta plot_home_meta = plot_home.getItemMeta();
        plot_home_meta.setDisplayName(ChatUtil.fixColors("&aTeleport na dzialke"));
        plot_home_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7[&f!&7] &7Teleportuje Cie na",
                "     &7spawn Twojej dzialki.",
                "",
                "&eKliknij, aby teleportować się na działke!"
        )));
        plot_home.setItemMeta(plot_home_meta);



        inventory.setItem(0, black);
        inventory.setItem(9, black);
        inventory.setItem(18, black);
        inventory.setItem(27, black);
        inventory.setItem(28, black);
        inventory.setItem(29, black);

        inventory.setItem(32, black);
        inventory.setItem(33, black);

        inventory.setItem(2, blue);
        inventory.setItem(3, blue);

        inventory.setItem(6, blue);
        inventory.setItem(7, blue);
        inventory.setItem(8, blue);
        inventory.setItem(17, blue);
        inventory.setItem(26, blue);
        inventory.setItem(35, blue);

        inventory.setItem(10, plot_home);
        inventory.setItem(19, plot_block);

        inventory.setItem(24, challenges);
        inventory.setItem(25, plot_upgrades);

        inventory.setItem(15, ranking);
        inventory.setItem(16, plot_members);

        inventory.setItem(22, banned_players);
        inventory.setItem(21, plot_border);

        inventory.setItem(12, plot_level);
        inventory.setItem(13, plot_settings);

        return inventory;
    }

    public static Inventory getPlotUpgradeInventory(Player player, Plot plot)
    {
        Inventory inventory = Bukkit.createInventory(player, 9, ChatUtil.fixColorsWithPrefix("&e&lUlepszenia dzialki"));

        ItemStack mob_exp = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta mob_exp_meta = mob_exp.getItemMeta();
        mob_exp_meta.setDisplayName(ChatUtil.fixColors("&bEXP z mobow"));
        if(plot.isMob_exp_upgrade()) {
            mob_exp_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7Drop EXPa z mobow",
                    "&7na Twojej dzialce",
                    "&7zostanie zwiekszony!",
                    "",
                    "&7Wymagania:",
                    "&aLevel dzialki: 70",
                    "",
                    "&7Drop&8: &f2x",
                    "&7Koszt&8: &f$" + OwnPlots.getInstance().getConfig_manager().getMob_exp_upgrade_cost(),
                    "",
                    "&aMasz juz to ODBLOKOWANE!"
            )));
        }
        else {
            mob_exp_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7Drop EXPa z mobow",
                    "&7na Twojej dzialce",
                    "&7zostanie zwiekszony!",
                    "",
                    "&7Wymagania:",
                    "&aLevel dzialki: 70",
                    "",
                    "&7Drop&8: &f2x",
                    "&7Koszt&8: &f$" + OwnPlots.getInstance().getConfig_manager().getMob_exp_upgrade_cost(),
                    "",
                    "&eKliknij, aby ulepszyc!"
            )));
        }

        mob_exp.setItemMeta(mob_exp_meta);

        ItemStack mob_drop = new ItemStack(Material.SPIDER_EYE);
        ItemMeta mob_drop_meta = mob_drop.getItemMeta();
        mob_drop_meta.setDisplayName(ChatUtil.fixColors("&bDrop z mobow"));
        if(plot.isMob_drop_upgrade()) {
            mob_drop_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7Drop z mobow",
                    "&7na Twojej dzialce",
                    "&7zostanie zwiekszony!",
                    "",
                    "&7Wymagania:",
                    "&aLevel dzialki: 80",
                    "",
                    "&7Drop&8: &f2x",
                    "&7Koszt&8: &f$" + OwnPlots.getInstance().getConfig_manager().getMob_drop_upgrade_cost(),
                    "",
                    "&aMasz juz to ODBLOKOWANE!"
            )));
        }
        else {
            mob_drop_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7Drop z mobow",
                    "&7na Twojej dzialce",
                    "&7zostanie zwiekszony!",
                    "",
                    "&7Wymagania:",
                    "&aLevel dzialki: 80",
                    "",
                    "&7Drop&8: &f2x",
                    "&7Koszt&8: &f$" + OwnPlots.getInstance().getConfig_manager().getMob_drop_upgrade_cost(),
                    "",
                    "&eKliknij, aby ulepszyc!"
            )));
        }

        mob_drop.setItemMeta(mob_drop_meta);

        ItemStack speed = new ItemStack(Material.POTION);
        PotionMeta speed_meta = (PotionMeta) speed.getItemMeta();
        speed_meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 400, 1), true);
        speed_meta.setDisplayName(ChatUtil.fixColors("&bUlepszenie speeda"));
        if(plot.isSpeed_upgrade()) {
            speed_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7Kazdy gracz na dzialce",
                    "&7otrzyma speeda 2!",
                    "",
                    "&7Wymagania:",
                    "&aLevel dzialki: 50",
                    "",
                    "&7Speed&8: &fII",
                    "&7Koszt&8: &f$" + OwnPlots.getInstance().getConfig_manager().getSpeed_upgrade_cost(),
                    "",
                    "&aMasz juz to ODBLOKOWANE!"
            )));
        }
        else{
            speed_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7Kazdy gracz na dzialce",
                    "&7otrzyma speeda 2!",
                    "",
                    "&7Wymagania:",
                    "&aLevel dzialki: 50",
                    "",
                    "&7Speed&8: &fII",
                    "&7Koszt&8: &f$" + OwnPlots.getInstance().getConfig_manager().getSpeed_upgrade_cost(),
                    "",
                    "&eKliknij, aby ulepszyc!"
            )));

        }
        speed.setItemMeta(speed_meta);

        ItemStack jump_boost = new ItemStack(Material.POTION);
        PotionMeta jump_boost_meta = (PotionMeta) jump_boost.getItemMeta();
        jump_boost_meta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 400, 1), true);
        jump_boost_meta.setDisplayName(ChatUtil.fixColors("&bUlepszenie jump boosta"));
        if(plot.isJump_upgrade()) {
            jump_boost_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7Kazdy gracz na dzialce",
                    "&7otrzyma jump boosta 2!",
                    "",
                    "&7Wymagania:",
                    "&aLevel dzialki: 100",
                    "",
                    "&7Jump Boost&8: &fII",
                    "&7Koszt&8: &f$" + OwnPlots.getInstance().getConfig_manager().getJump_boost_upgrade_cost(),
                    "",
                    "&aMasz juz to ODBLOKOWANE!"
            )));
        }
        else {
            jump_boost_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7Kazdy gracz na dzialce",
                    "&7otrzyma jump boosta 2!",
                    "",
                    "&7Wymagania:",
                    "&aLevel dzialki: 100",
                    "",
                    "&7Jump Boost&8: &fII",
                    "&7Koszt&8: &f$" + OwnPlots.getInstance().getConfig_manager().getJump_boost_upgrade_cost(),
                    "",
                    "&eKliknij, aby ulepszyc!"
            )));
        }

        jump_boost.setItemMeta(jump_boost_meta);

        int increase_cost = OwnPlots.getInstance().getConfig_manager().getPlot_increase_field_cost();
        switch(plot.getSize())
        {
            case 16:
                break;
            case 32:
                increase_cost = increase_cost * 2;
                break;
            case 48:
                increase_cost = increase_cost * 3;
                break;
            case 64:
                increase_cost = increase_cost * 4;
                break;
            default:
                increase_cost = increase_cost * 5;
                break;
        }

        ItemStack plot_size = new ItemStack(Material.BEACON);
        ItemMeta plot_size_meta = plot_size.getItemMeta();
        plot_size_meta.setDisplayName(ChatUtil.fixColors("&bPowieksz rozmiar dzialki"));
        if(plot.getSize() >= OwnPlots.getInstance().getConfig_manager().getPlot_max_size())
        {
            plot_size_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7To ulepszenie powiekszy",
                    "&7rozmiar Twojej dzialki!",
                    "",
                    "&7Rozmiar&8: &f" + plot.getSize() + "x" + plot.getSize(),
                    "",
                    "&aTwoja dzialka posiada MAKSYMALNY rozmiar!"
            )));
        }
        else
        {
            plot_size_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                    "&7To ulepszenie powiekszy",
                    "&7rozmiar Twojej dzialki!",
                    "",
                    "&7Rozmiar&8: &f" + plot.getSize() + "x" + plot.getSize(),
                    "&7Koszt&8: &f$" + increase_cost,
                    "",
                    "&eKliknij, aby ulepszyc!"
            )));
        }

        plot_size.setItemMeta(plot_size_meta);

        inventory.setItem(0, speed);
        inventory.setItem(1, jump_boost);

        inventory.setItem(5, mob_drop);
        inventory.setItem(6, mob_exp);
        inventory.setItem(7, plot_size);

        return inventory;
    }

    public static Inventory getPlotsLevelInventory(Player player, Plot plot)
    {
        Inventory inventory = Bukkit.createInventory(player, 54, ChatUtil.fixColorsWithPrefix("&e&lPoziom dzialki"));
        PlotSettings plotSettings = plot.getPlotSettings();

        int members_size = plot.getMembers().size();

        ItemStack stats = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta stats_meta = stats.getItemMeta();
        stats_meta.setDisplayName(ChatUtil.fixColors("&3Statystyki materialow na dzialce"));
        stats_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&bLevel:",
                "&e" + plot.getLevel(),
                "",
                "&bPunkty:",
                "&e" + plot.getPoints()
        )));
        stats.setItemMeta(stats_meta);

        ItemStack blank = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blank_meta = blank.getItemMeta();
        blank_meta.setDisplayName(ChatUtil.fixColors("&cMaterialy na dzialce"));
        blank.setItemMeta(blank_meta);

        ItemStack back = new ItemStack(Material.OAK_FENCE_GATE);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatUtil.fixColors("&cKliknij, aby wyjsc"));
        back.setItemMeta(backMeta);

        inventory.setItem(0, back);
        inventory.setItem(4, stats);
        inventory.setItem(8, back);

        inventory.setItem(9, blank);
        inventory.setItem(10, blank);
        inventory.setItem(11, blank);
        inventory.setItem(12, blank);
        inventory.setItem(13, blank);
        inventory.setItem(14, blank);
        inventory.setItem(15, blank);
        inventory.setItem(16, blank);
        inventory.setItem(17, blank);

        int beacon_pkt = plot.getBeacons() * 250;
        int netherite_pkt = plot.getNetherite_blocks() * 100;
        int emerald_pkt = plot.getEmerald_blocks() * 75;
        int diamond_pkt = plot.getDiamond_blocks() * 50;
        int gold_pkt = plot.getGold_blocks() * 25;
        int iron_pkt = plot.getIron_blocks() * 10;

        ItemStack beacon = new ItemStack(Material.BEACON);
        ItemMeta beacon_meta = beacon.getItemMeta();
        beacon_meta.setDisplayName(ChatUtil.fixColors("&7Blok netherytu &6(&e+" + beacon_pkt + "pkt&6)"));
        beacon_meta.setLore(Arrays.asList(ChatUtil.fixColors("&8(&e" + plot.getBeacons() + " blokow&8)")));
        beacon.setItemMeta(beacon_meta);

        ItemStack netherite_block = new ItemStack(Material.NETHERITE_BLOCK);
        ItemMeta netherite_block_meta = netherite_block.getItemMeta();
        netherite_block_meta.setDisplayName(ChatUtil.fixColors("&7Blok netherytu &6(&e+" + netherite_pkt + "pkt&6)"));
        netherite_block_meta.setLore(Arrays.asList(ChatUtil.fixColors("&6(&e" + plot.getNetherite_blocks() + " blokow&6)")));
        netherite_block.setItemMeta(netherite_block_meta);

        ItemStack emerald_block = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta emerald_block_meta = emerald_block.getItemMeta();
        emerald_block_meta.setDisplayName(ChatUtil.fixColors("&7Blok szmaragdu &6(&e+" + emerald_pkt + "pkt&6)"));
        emerald_block_meta.setLore(Arrays.asList(ChatUtil.fixColors("&6(&e" + plot.getEmerald_blocks() + " blokow&6)")));
        emerald_block.setItemMeta(emerald_block_meta);

        ItemStack diamond_block = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta diamond_block_meta = diamond_block.getItemMeta();
        diamond_block_meta.setDisplayName(ChatUtil.fixColors("&7Blok diamentu &6(&e+" + diamond_pkt + "pkt&6)"));
        diamond_block_meta.setLore(Arrays.asList(ChatUtil.fixColors("&6(&e" + plot.getDiamond_blocks() + " blokow&6)")));
        diamond_block.setItemMeta(diamond_block_meta);

        ItemStack gold_block = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta gold_block_meta = gold_block.getItemMeta();
        gold_block_meta.setDisplayName(ChatUtil.fixColors("&7Blok zlota &6(&e+" + gold_pkt + "pkt&6)"));
        gold_block_meta.setLore(Arrays.asList(ChatUtil.fixColors("&6(&e" + plot.getGold_blocks() + " blokow&6)")));
        gold_block.setItemMeta(gold_block_meta);

        ItemStack iron_block = new ItemStack(Material.IRON_BLOCK);
        ItemMeta iron_block_meta = iron_block.getItemMeta();
        iron_block_meta.setDisplayName(ChatUtil.fixColors("&7Blok zelaza &6(&e+" + iron_pkt + "pkt&6)"));
        iron_block_meta.setLore(Arrays.asList(ChatUtil.fixColors("&6(&e" + plot.getIron_blocks() + " blokow&6)")));
        iron_block.setItemMeta(iron_block_meta);

        inventory.setItem(31, beacon);
        inventory.setItem(38, iron_block);
        inventory.setItem(39, gold_block);
        inventory.setItem(40, diamond_block);
        inventory.setItem(41, emerald_block);
        inventory.setItem(42, netherite_block);

        return inventory;
    }

    public static Inventory getPlotMembersInventory(Player player, Plot plot)
    {
        Inventory inventory = Bukkit.createInventory(player, 54, ChatUtil.fixColorsWithPrefix("&e&lCzlonkowie dzialki"));
        PlotSettings plotSettings = plot.getPlotSettings();

        int members_size = plot.getMembers().size();

        ItemStack stats = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta stats_meta = stats.getItemMeta();
        stats_meta.setDisplayName(ChatUtil.fixColors("&3Statystyki czlonkow"));
        stats_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&bCzlonkowie dzialki " + plot.getPlot_name() + ":",
                "&e" +  + members_size + "/" + plot.getMaxMembersCount(),
                "",
                "&bWlasciciel:",
                "&e" + plot.getOwner()
        )));
        stats.setItemMeta(stats_meta);

        ItemStack blank = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blank_meta = blank.getItemMeta();
        blank_meta.setDisplayName(ChatUtil.fixColors("&cCzlonkowie dzialki"));
        blank.setItemMeta(blank_meta);

        ItemStack back = new ItemStack(Material.OAK_FENCE_GATE);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatUtil.fixColors("&cKliknij, aby wyjsc"));
        back.setItemMeta(backMeta);

        inventory.setItem(0, back);
        inventory.setItem(4, stats);
        inventory.setItem(8, back);

        inventory.setItem(9, blank);
        inventory.setItem(10, blank);
        inventory.setItem(11, blank);
        inventory.setItem(12, blank);
        inventory.setItem(13, blank);
        inventory.setItem(14, blank);
        inventory.setItem(15, blank);
        inventory.setItem(16, blank);
        inventory.setItem(17, blank);


        int counter = 18;

        for(String member : plot.getMembers())
        {
            String last_online = Utils.getStringDate(Bukkit.getOfflinePlayer(member).getLastPlayed());
            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.AQUA + member);
            if(member.equalsIgnoreCase(plot.getOwner()))
            {
                itemMeta.setLore(ChatUtil.fixColors(Arrays.asList(
                        "&7Rola:",
                        "&6Wlasciciel",
                        "",
                        "&7Ostatnio online:",
                        "&6" + last_online,
                        "",
                        "&eKliknij &cPRAWYM&e, aby wyrzucic gracza!"
                )));
            }
            else
            {
                itemMeta.setLore(ChatUtil.fixColors(Arrays.asList(
                        "&7Rola:",
                        "&bCzlonek",
                        "",
                        "&7Ostatnio online:",
                        "&6" + last_online,
                        "",
                        "&eKliknij &cPRAWYM&e, aby wyrzucic gracza!"
                )));
            }

            item.setItemMeta(itemMeta);
            inventory.setItem(counter, item);
            counter++;
        }


        return inventory;
    }

    public static Inventory getPlotBannedPlayersInventory(Player player, Plot plot)
    {
        Inventory inventory = Bukkit.createInventory(player, 54, ChatUtil.fixColorsWithPrefix("&e&lZbanowani gracze"));
        PlotSettings plotSettings = plot.getPlotSettings();

        int bammed_size = plot.getBanned_players().size();

        ItemStack stats = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta stats_meta = stats.getItemMeta();
        stats_meta.setDisplayName(ChatUtil.fixColors("&3Statystyki zbanowanych"));
        stats_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&bZbanowani gracze dzialki " + plot.getPlot_name() + ":",
                "&e" +  + bammed_size + "/30",
                "",
                "&bWlasciciel:",
                "&e" + plot.getOwner()
        )));
        stats.setItemMeta(stats_meta);

        ItemStack blank = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta blank_meta = blank.getItemMeta();
        blank_meta.setDisplayName(ChatUtil.fixColors("&cZbanowani gracze"));
        blank.setItemMeta(blank_meta);

        ItemStack back = new ItemStack(Material.OAK_FENCE_GATE);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatUtil.fixColors("&cKliknij, aby wyjsc"));
        back.setItemMeta(backMeta);

        inventory.setItem(0, back);
        inventory.setItem(4, stats);
        inventory.setItem(8, back);

        inventory.setItem(9, blank);
        inventory.setItem(10, blank);
        inventory.setItem(11, blank);
        inventory.setItem(12, blank);
        inventory.setItem(13, blank);
        inventory.setItem(14, blank);
        inventory.setItem(15, blank);
        inventory.setItem(16, blank);
        inventory.setItem(17, blank);


        int counter = 18;

        if(plot.getBanned_players().size() >= 1)
        {
            for(String banned : plot.getBanned_players())
            {
                if(banned.length() >= 2)
                {
                    ItemStack item = new ItemStack(Material.PLAYER_HEAD);
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.GOLD + banned);
                    itemMeta.setLore(ChatUtil.fixColors(Arrays.asList(
                            "&7Status: &cZBANOWANY",
                            "",
                            "&eKliknij &cPRAWYM&e, aby dac &aODBANOWAC &egracza!"
                    )));

                    item.setItemMeta(itemMeta);
                    inventory.setItem(counter, item);
                    counter++;
                }
            }
        }

        return inventory;
    }

    public static Inventory getPlotSettingsInventory(Player player, Plot plot)
    {
        Inventory inventory = Bukkit.createInventory(player, 54, ChatUtil.fixColorsWithPrefix("&e&lUstawienia dzialki"));
        PlotSettings plotSettings = plot.getPlotSettings();

        ItemStack back = new ItemStack(Material.OAK_FENCE_GATE);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatUtil.fixColors("&cKliknij, aby wyjsc"));
        back.setItemMeta(backMeta);

        ItemStack mob_damage_players = new ItemStack(Material.RED_DYE);
        ItemMeta mob_damage_players_meta = mob_damage_players.getItemMeta();
        mob_damage_players_meta.setDisplayName(ChatUtil.fixColors("&aMoby zadaja obrazenia graczom"));
        mob_damage_players_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (plotSettings.isMob_damage_players() ? "&aZezwolone" : "&cZabronione")
        )));
        mob_damage_players.setItemMeta(mob_damage_players_meta);

        ItemStack pvp = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta pvp_meta = pvp.getItemMeta();
        pvp_meta.setDisplayName(ChatUtil.fixColors("&aPvP"));
        pvp_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (plotSettings.isPvp() ? "&aZezwolone" : "&cZabronione")
        )));
        pvp.setItemMeta(pvp_meta);

        ItemStack explosions = new ItemStack(Material.GUNPOWDER);
        ItemMeta explosions_meta = explosions.getItemMeta();
        explosions_meta.setDisplayName(ChatUtil.fixColors("&aEksplozje"));
        explosions_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (plotSettings.isExplosions() ? "&aZezwolone" : "&cZabronione")
        )));
        explosions.setItemMeta(explosions_meta);

        ItemStack fire_spread = new ItemStack(Material.FLINT_AND_STEEL);
        ItemMeta fire_spread_meta = fire_spread.getItemMeta();
        fire_spread_meta.setDisplayName(ChatUtil.fixColors("&aRozprzestrzenianie sie ognia"));
        fire_spread_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (plotSettings.isFire_spread() ? "&aZezwolone" : "&cZabronione")
        )));
        fire_spread.setItemMeta(fire_spread_meta);

        ItemStack leaf_decay = new ItemStack(Material.OAK_LEAVES);
        ItemMeta leaf_decay_meta = leaf_decay.getItemMeta();
        leaf_decay_meta.setDisplayName(ChatUtil.fixColors("&aRozpadanie sie lisci"));
        leaf_decay_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (plotSettings.isLeaf_decay() ? "&aZezwolone" : "&cZabronione")
        )));
        leaf_decay.setItemMeta(leaf_decay_meta);

        ItemStack natural_spawn = new ItemStack(Material.BEE_SPAWN_EGG);
        ItemMeta natural_spawn_meta = natural_spawn.getItemMeta();
        natural_spawn_meta.setDisplayName(ChatUtil.fixColors("&aNaturalne spawnowanie mobow"));
        natural_spawn_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (plotSettings.isNatural_spawn() ? "&aZezwolone" : "&cZabronione")
        )));
        natural_spawn.setItemMeta(natural_spawn_meta);

        inventory.setItem(0, back);
        inventory.setItem(8, back);

        inventory.setItem(9, natural_spawn);
        inventory.setItem(10, leaf_decay);
        inventory.setItem(11, fire_spread);
        inventory.setItem(12, explosions);
        inventory.setItem(13, pvp);
        inventory.setItem(14, mob_damage_players);

        return inventory;
    }

    public static Inventory getVisitorsSettingsInventory(Player player, Plot plot)
    {
        Inventory inventory = Bukkit.createInventory(player, 54, ChatUtil.fixColorsWithPrefix("&e&lUstawienia odwiedzajacych"));
        VisitorsSettings visitorsSettings = plot.getVisitorsSettings();

        ItemStack back = new ItemStack(Material.OAK_FENCE_GATE);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatUtil.fixColors("&cKliknij, aby wyjsc"));
        back.setItemMeta(backMeta);

        ItemStack trample = new ItemStack(Material.FARMLAND);
        ItemMeta trample_meta = trample.getItemMeta();
        trample_meta.setDisplayName(ChatUtil.fixColors("&aNiszczenie upraw"));
        trample_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (visitorsSettings.isTrample() ? "&aZezwolone" : "&cZabronione")
        )));
        trample.setItemMeta(trample_meta);

        ItemStack block_place = new ItemStack(Material.DIRT);
        ItemMeta block_place_meta = block_place.getItemMeta();
        block_place_meta.setDisplayName(ChatUtil.fixColors("&aStawianie blokow"));
        block_place_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (visitorsSettings.isBlock_place() ? "&aZezwolone" : "&cZabronione")
        )));
        block_place.setItemMeta(block_place_meta);

        ItemStack block_break = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta block_break_meta = block_break.getItemMeta();
        block_break_meta.setDisplayName(ChatUtil.fixColors("&aNiszczenie blokow"));
        block_break_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (visitorsSettings.isBlock_break() ? "&aZezwolone" : "&cZabronione")
        )));
        block_break.setItemMeta(block_break_meta);

        ItemStack item_pickup = new ItemStack(Material.MELON_SEEDS);
        ItemMeta item_pickup_meta = item_pickup.getItemMeta();
        item_pickup_meta.setDisplayName(ChatUtil.fixColors("&aPodnoszenie itemow"));
        item_pickup_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (visitorsSettings.isItem_pickup() ? "&aZezwolone" : "&cZabronione")
        )));
        item_pickup.setItemMeta(item_pickup_meta);

        ItemStack mob_damage = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta mob_damage_meta = mob_damage.getItemMeta();
        mob_damage_meta.setDisplayName(ChatUtil.fixColors("&aZadawanie obrazen mobom"));
        mob_damage_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (visitorsSettings.isMob_damage() ? "&aZezwolone" : "&cZabronione")
        )));
        mob_damage.setItemMeta(mob_damage_meta);

        ItemStack chest_access = new ItemStack(Material.CHEST);
        ItemMeta chest_access_meta = chest_access.getItemMeta();
        chest_access_meta.setDisplayName(ChatUtil.fixColors("&aDostep do skrzynek"));
        chest_access_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (visitorsSettings.isChest_access() ? "&aZezwolone" : "&cZabronione")
        )));
        chest_access.setItemMeta(chest_access_meta);

        ItemStack furnace_use = new ItemStack(Material.FURNACE);
        ItemMeta furnace_use_meta = furnace_use.getItemMeta();
        furnace_use_meta.setDisplayName(ChatUtil.fixColors("&aUzywanie piecykow"));
        furnace_use_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (visitorsSettings.isFurnace_use() ? "&aZezwolone" : "&cZabronione")
        )));
        furnace_use.setItemMeta(furnace_use_meta);

        ItemStack button_use = new ItemStack(Material.LEVER);
        ItemMeta button_use_meta = button_use.getItemMeta();
        button_use_meta.setDisplayName(ChatUtil.fixColors("&aUzywanie przyciskow/dzwigni"));
        button_use_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (visitorsSettings.isButton_use() ? "&aZezwolone" : "&cZabronione")
        )));
        button_use.setItemMeta(button_use_meta);

        ItemStack door_use = new ItemStack(Material.OAK_DOOR);
        ItemMeta door_use_meta = door_use.getItemMeta();
        door_use_meta.setDisplayName(ChatUtil.fixColors("&aUzywanie drzwi"));
        door_use_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Status: " + (visitorsSettings.isDoor_use() ? "&aZezwolone" : "&cZabronione")
        )));
        door_use.setItemMeta(door_use_meta);

        inventory.setItem(0, back);
        inventory.setItem(8, back);

        inventory.setItem(9, door_use);
        inventory.setItem(10, button_use);
        inventory.setItem(11, furnace_use);
        inventory.setItem(12, chest_access);
        inventory.setItem(13, mob_damage);
        inventory.setItem(14, item_pickup);
        inventory.setItem(15, block_break);
        inventory.setItem(16, block_place);
        inventory.setItem(17, trample);

        return inventory;
    }

    public static Inventory getPlotSettingsCategoryInventory(Player player)
    {
        Inventory inventory = Bukkit.createInventory(player, 9, ChatUtil.fixColorsWithPrefix("&e&lUstawienia - Kategorie"));

        ItemStack back = new ItemStack(Material.OAK_FENCE_GATE);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatUtil.fixColors("&cKliknij, aby wyjsc"));
        back.setItemMeta(backMeta);

        ItemStack plot_settings = new ItemStack(Material.OAK_SAPLING);
        ItemMeta plot_settings_meta = plot_settings.getItemMeta();
        plot_settings_meta.setDisplayName(ChatUtil.fixColors("&aUstawienia dzialki"));
        plot_settings_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Zadecyduj jakie pozwolenia moga miec",
                "&7sa dla Ciebie najbardziej odpowiednie.",
                "",
                "&eKliknij, aby edytowac ustawienia!"
        )));
        plot_settings.setItemMeta(plot_settings_meta);

        ItemStack visitors = new ItemStack(Material.OAK_SIGN);
        ItemMeta visitors_meta = visitors.getItemMeta();
        visitors_meta.setDisplayName(ChatUtil.fixColors("&aUstawienia odwiedzajacych"));
        visitors_meta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Zadecyduj jakie pozwolenia moga miec",
                "&7odwiedzajacy w momencie",
                "&7kiedy sa na Twojej dzialce.",
                "",
                "&eKliknij, aby edytowac ustawienia!"
        )));
        visitors.setItemMeta(visitors_meta);

        inventory.setItem(0, back);

        inventory.setItem(2, visitors);
        inventory.setItem(7, plot_settings);

        return inventory;
    }

    public static Inventory getPlotCreateInventory(Player player)
    {
        Inventory inventory = Bukkit.createInventory(player, InventoryType.HOPPER, ChatUtil.fixColorsWithPrefix("&e&lStworz dzialke"));

        ItemStack black = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack blue = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);

        ItemStack item = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatUtil.fixColors("&7Stworz &edzialke"));
        itemMeta.setLore(ChatUtil.fixColors(Arrays.asList(
                "&7Aby stworzyc dzilke musisz znajdowac sie",
                "&7na mapie &csurvival &7oraz przynajmniej w",
                "&7odległosci ponad &c400 kratek &7od spawna.",
                "",
                "&7Cena: &a$" + OwnPlots.getInstance().getConfig_manager().getPlot_create_cost(),
                "",
                "&e&oINFO:",
                "&7&oDzialka zostanie stworzona w",
                "&7&oaktualnym miejscu gdzie się znajdujesz!",
                "",
                "&eKliknij, aby stworzyc dzialke."
        )));
        item.setItemMeta(itemMeta);

        inventory.setItem(0, blue);
        inventory.setItem(2, item);
        inventory.setItem(4, black);

        return inventory;
    }

}
