package eu.owncraft.plots.commands;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import eu.owncraft.plots.OwnPlots;
import eu.owncraft.plots.database.PlotManager;
import eu.owncraft.plots.gui.PlotGUI;
import eu.owncraft.plots.plot.Plot;
import eu.owncraft.plots.plot.PlotBorder;
import eu.owncraft.plots.plot.PlotMember;
import eu.owncraft.plots.utils.ChatUtil;
import eu.owncraft.plots.utils.LocationUtil;
import eu.owncraft.plots.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class PlotCommand implements CommandExecutor {

    private final OwnPlots plugin;
    private ArrayList<String> delete_confirm_players = new ArrayList<>();
    private HashMap<String, Plot> invite_players = new HashMap<>();

    public PlotCommand(OwnPlots plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(!(sender instanceof Player))
        {
            sender.sendMessage("Tylko gracze moga tego uzywac");
            return true;
        }

        final Player player = (Player) sender;
        final String player_name = player.getName();

        if(args.length == 0)
        {
            PlotManager plotManager = plugin.getPlotManager();
            if(plotManager.hasPlot(player))
            {
                player.openInventory(PlotGUI.getMainInventory(player));
            }
            else
            {
                player.openInventory(PlotGUI.getPlotCreateInventory(player));
            }

            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0f, 1.0f);
        }
        else if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("info"))
            {
                if(!player.getWorld().getName().equalsIgnoreCase("world"))
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&eaby uzyc tej komendy musisz sie znajdowac na mapie survival."));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                Plot plot = plugin.getPlotManager().getPlotAt(player.getLocation());
                if(plot != null)
                {
                    player.sendMessage(ChatUtil.fixColors("&8&m----------&8[ &eDzialka &8]&8&m----------"));
                    player.sendMessage(ChatUtil.fixColors("&eNazwa: &7" + plot.getPlot_name()));
                    player.sendMessage(ChatUtil.fixColors("&eRozmiar: &7" + plot.getSize() + "x" + plot.getSize()));
                    player.sendMessage(ChatUtil.fixColors("&eDzialka jest zamknieta: &7" + plot.isClosed()));
                    player.sendMessage(ChatUtil.fixColors("&ePozycja bedrocka: &7" + LocationUtil.convertToString(plot.getLocation())));
                    player.sendMessage(ChatUtil.fixColors("&eWlasciciel: &7" + plot.getOwner()));
                    player.sendMessage(ChatUtil.fixColors("&ePoziom: &7" + plot.getLevel()));
                    player.sendMessage(ChatUtil.fixColors("&ePunkty: &7" + plot.getPoints()));

                    String banned_players = "";
                    String members = "";
                    for(String string : plot.getBanned_players())
                    {
                        banned_players = banned_players + string + ", ";
                    }

                    for(String string : plot.getMembers())
                    {
                        members = members + string + ", ";
                    }

                    player.sendMessage(ChatUtil.fixColors("&eZbanowani: &7" + banned_players));
                    player.sendMessage(ChatUtil.fixColors("&eCzlonkowie: &7" + members));
                    player.sendMessage(ChatUtil.fixColors("&eLiczba czlonkow: &7" + plot.getMembers().size() + "/" + plot.getMaxMembersCount()));
                    player.sendMessage(ChatUtil.fixColors("&eLiczba zbanowanych: &7" + plot.getBanned_players().size() + "/30"));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 1.0f, 1.0f);
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&eaby uzyc tej komendy musisz sie znajdowac na dzialce."));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
            }
            else if(args[0].equalsIgnoreCase("stworz"))
            {
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&7Poprawne uzycie: &e/dzialka stworz [nazwa]"));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            }
            else if(args[0].equalsIgnoreCase("bany"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                if(plot != null)
                {
                    player.openInventory(PlotGUI.getPlotBannedPlayersInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                }
                else
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz swojej dzialki!"));
                }
            }
            else if(args[0].equalsIgnoreCase("zamknij"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                if(plot != null)
                {
                    if(plot.isClosed())
                    {
                        player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1.0f, 1.0f);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&etwoja dzialka została &aotwarta&e!"));
                        plot.setClosed(false);
                    }
                    else
                    {
                        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_DOOR_OPEN, 1.0f, 1.0f);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&etwoja dzialka została &czamknieta&e!"));
                        plot.setClosed(true);
                    }
                }
                else
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz swojej dzialki!"));
                }
            }
            else if(args[0].equalsIgnoreCase("ban"))
            {
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&7Poprawne uzycie: &e/dzialka ban [nick gracza]"));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            }
            else if(args[0].equalsIgnoreCase("unban"))
            {
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&7Poprawne uzycie: &e/dzialka unban [nick gracza]"));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            }
            else if(args[0].equalsIgnoreCase("pomoc"))
            {
                sendHelpMessages(player);
                player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 1.0f, 1.0f);
            }
            else if(args[0].equalsIgnoreCase("poziom") || args[0].equalsIgnoreCase("level"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                if(plot != null)
                {
                    player.openInventory(PlotGUI.getPlotsLevelInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                }
                else
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz swojej dzialki!"));
                }
            }
            else if(args[0].equalsIgnoreCase("czlonkowie"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                if(plot != null)
                {
                    player.openInventory(PlotGUI.getPlotMembersInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                }
                else
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz swojej dzialki!"));
                }
            }
            else if(args[0].equalsIgnoreCase("akceptujzaproszenie"))
            {
                if(invite_players.containsKey(player_name))
                {
                    Plot plot = invite_players.get(player_name);
                    invite_players.remove(player_name);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&edolaczyles do dzialki gracza &c" + plot.getOwner() + " &eo nazwie &c" + plot.getPlot_name()));

                    for(String member : plot.getMembers())
                    {
                        Player plot_player = Bukkit.getPlayer(member);
                        if(plot_player != null && plot_player.isOnline())
                        {
                            plot_player.sendMessage(ChatUtil.fixColorsWithPrefix("&eGracz &5" + player_name + " &edolaczyl do twojej dzialki!"));
                        }
                    }

                    PlotManager plotManager = plugin.getPlotManager();
                    plotManager.addMember(plugin.getPlayerDataManager().getPlots().get(plot.getPlot_name()), player_name);
                }
                else
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz zadnego oczekujacego zaproszenia do dzialki!"));
                }
            }
            else if(args[0].equalsIgnoreCase("anulujzaproszenie"))
            {
                if(invite_players.containsKey(player_name))
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&eanulowales zaproszenie do dzialki!"));
                    invite_players.remove(player_name);
                }
                else
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz zadnego oczekujacego zaproszenia do dzialki!"));
                }
            }
            else if(args[0].equalsIgnoreCase("zapros"))
            {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&7poprawne uzycie: &e/dzialka zapros [nick gracza]"));
            }
            else if(args[0].equalsIgnoreCase("wyrzuc"))
            {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&7poprawne uzycie: &e/dzialka wyrzuc [nick gracza]"));
            }
            else if(args[0].equalsIgnoreCase("usunanuluj"))
            {
                if(delete_confirm_players.contains(player_name))
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&eanulowales prośbę o usunięcie działki!"));
                    delete_confirm_players.remove(player_name);
                }
                else
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz zadnego oczekujacego zapytania o usuniecie dzialki!"));
                }
            }
            else if(args[0].equalsIgnoreCase("usunpotwierdz"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                if(plot == null)
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&cBłąd..."));
                    return true;
                }

                if(delete_confirm_players.contains(player_name))
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.2f, 1.4f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&6usuneles swoja dzialke!"));
                    delete_confirm_players.remove(player_name);
                    PlotManager plotManager = plugin.getPlotManager();
                    plotManager.deletePlot(plot);
                }
                else
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz zadnego oczekujacego zapytania o usuniecie dzialki!"));
                }
            }
            else if(args[0].equalsIgnoreCase("usun"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                if(plot != null)
                {
                    if(delete_confirm_players.contains(player_name))
                    {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&emasz juz oczekujace zapytanie o usuniecie dzialki!"));
                    }
                    else
                    {
                        delete_confirm_players.add(player_name);
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 1.0f, 1.0f);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eczy aby napewno chcesz &c&lUSUNAC &edzialke?"));

                        TextComponent confirm_message = new TextComponent("§a§lPOTWIERDZ");
                        confirm_message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Kliknij, aby usunac dzialke!").create()));
                        confirm_message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dzialka usunpotwierdz"));

                        TextComponent cancel_message = new TextComponent("§4§lANULUJ");
                        cancel_message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Kliknij, aby anulowac!").create()));
                        cancel_message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dzialka usunanuluj"));

                        player.spigot().sendMessage(confirm_message);
                        player.spigot().sendMessage(cancel_message);

                        new BukkitRunnable()
                        {
                            @Override
                            public void run()
                            {
                                if(delete_confirm_players.contains(player_name))
                                {
                                    delete_confirm_players.remove(player_name);
                                }
                            }
                        }.runTaskLaterAsynchronously(plugin, 300L);
                    }
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie posiadasz swojej dzialki!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
            }
            else if(args[0].equalsIgnoreCase("border"))
            {
                Plot plot = plugin.getPlotManager().getPlotAt(player.getLocation());
                if(plugin.getPlayerDataManager().getBorder_players().contains(player))
                {
                    plugin.getPlayerDataManager().getBorder_players().remove(player);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&6wylaczyles border!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    PlotBorder plotBorder = new PlotBorder();
                    plotBorder.hideBorder(player, player.getWorld());
                    return true;
                }
                if(plot == null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie znajdujesz sie na zadnej dzialce!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
                else
                {
                    plot.displayPlotBorder(player);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&awłączyłeś border!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    plugin.getPlayerDataManager().getBorder_players().add(player);
                }
            }
            else if(args[0].equalsIgnoreCase("ulepszenia"))
            {
                Plot plot = plugin.getPlotManager().getPlotAt(player.getLocation());
                if(plot == null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&eaby uzyc tej komendy musisz sie znajdowac na dzialce!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(plot.getOwner().equalsIgnoreCase(player.getName()))
                {
                    player.openInventory(PlotGUI.getPlotUpgradeInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0f, 1.0f);
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie jestes wlascicielem tej dzialki!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.closeInventory();
                }
            }
            else if(args[0].equalsIgnoreCase("ustawienia"))
            {
                Plot plot = plugin.getPlotManager().getPlotAt(player.getLocation());
                if(plot == null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&eaby uzyc tej komendy musisz sie znajdowac na dzialce!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(plot.getOwner().equalsIgnoreCase(player.getName()))
                {
                    player.openInventory(PlotGUI.getPlotSettingsCategoryInventory(player));
                    player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0f, 1.0f);
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie jestes wlascicielem tej dzialki!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.closeInventory();
                }

            }
            else if(args[0].equalsIgnoreCase("dom"))
            {
                PlotMember plotMember = plugin.getPlayerDataManager().getPlotMember(player.getName());
                if(plotMember.getOwn_plot() != null)
                {
                    Plot plot = plugin.getPlotManager().getPlotByName(plotMember.getOwn_plot());
                    player.teleport(plot.getLocation());
                    player.playSound(player.getLocation(), Sound.ENTITY_FOX_TELEPORT, 1.0f, 1.0f);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                    {
                        player.chat("/dzialka border");
                    }, 10L);
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&cnie posiadasz wlasnej dzialki!"));
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&7Czy chodzilo ci o &e/dzialka dom [nick]"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
            }
        }
        else if(args.length == 2)
        {
            if(args[0].equalsIgnoreCase("usun"))
            {
                if(!player.hasPermission("ownplots.admin"))
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&cnie posiadasz uprawnien!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                try
                {
                    Plot plot = PlotManager.getPlotByOwner(args[1]);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&eusuneles dzialke gracza " + args[1]));
                    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
                    PlotManager plotManager = plugin.getPlotManager();
                    plotManager.deletePlot(plot);
                }
                catch(NullPointerException exception)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&eten gracz nie ma zadnej dzialki!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
                
            }
            else if(args[0].equalsIgnoreCase("dom"))
            {
                Plot plot = PlotManager.getPlotByOfflineOwner(args[1]);
                if(plot != null)
                {
                    if(plot.isBanned(player_name) || plot.isClosed())
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eta dzialka jest &czamknieta&e lub jestes na niej &czbanowany&e!"));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                    else
                    {
                        player.teleport(plot.getLocation());
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eTeleportuje do dzialki gracza &c" + args[1] + "&e!"));
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                        {
                            player.chat("/dzialka border");
                        }, 10L);
                    }
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&eten gracz nie posiada swojej dzialki!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
            }
            else if(args[0].equalsIgnoreCase("ban"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                if(plot != null)
                {
                    String ban_name = args[1].toLowerCase();
                    if(plot.isBanned(ban_name))
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eGracz &c" + ban_name + " &ejest juz zbanowany!"));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                    else
                    {
                        if(plot.getBanned_players().size() >= 30)
                        {
                            player.sendMessage(ChatUtil.fixColorsWithPrefix("&ezbyt duzo graczy jest zbanowanych na twojej dzialce!"));
                            player.sendMessage(ChatUtil.fixColorsWithPrefix("&ezanim kogos znowu zbanujesz, &bodbanuj &ekogos :)"));
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                        }
                        else
                        {
                            plot.banPlayer(ban_name);
                            player.sendMessage(ChatUtil.fixColorsWithPrefix("&eZbanowales gracza &c" + ban_name + "&e!"));
                            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                        }
                    }
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&cnie posiadasz wlasnej dzialki!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
            }
            else if(args[0].equalsIgnoreCase("unban"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                if(plot != null)
                {
                    String ban_name = args[1].toLowerCase();
                    if(plot.isBanned(ban_name))
                    {
                        plot.unbanPlayer(ban_name);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eOdbanowales gracza &c" + ban_name + "&e!"));
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                    }
                    else
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eGracz &c" + ban_name + " &enie jest zbanowany!"));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&cnie posiadasz wlasnej dzialki!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
            }
            else if(args[0].equalsIgnoreCase("wyrzuc"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                if(plot == null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz swojej dzialki!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);

                if(plot.getOwner().equalsIgnoreCase(args[1]))
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie mozesz wyrzucic wlasciciela dzialki!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(target != null && target.isOnline())
                {
                    final String target_name = target.getName();
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&ewyrzuciles gracza &c" + target_name + " &eze swojej dzialki!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.0f, 1.0f);

                    target.sendMessage(ChatUtil.fixColorsWithPrefix("&ezostales wyrzucony z dzialki o nazwie &c" + plot.getPlot_name() + " &cprzez &c" + player_name));
                    target.playSound(target.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.0f, 1.0f);

                    PlotManager plotManager = plugin.getPlotManager();
                    plotManager.kickMember(plugin.getPlayerDataManager().getPlots().get(plot.getPlot_name()), target.getName());
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&eten gracz jest &coffline&e!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }
            }
            else if(args[0].equalsIgnoreCase("zapros"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                int max_members_count = plot.getMaxMembersCount();
                if(plot == null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz swojej dzialki!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(plot.getMembers().size() >= max_members_count)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&emaksymalnie mozesz miec " + max_members_count + " czlonkow na dzialce!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if(target != null && target.isOnline())
                {
                    final String target_name = target.getName();
                    if(plot.isMember(target))
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eten gracz jest juz dodany do dzialki!"));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                        return true;
                    }
                    else
                    {
                        if(invite_players.containsKey(target.getName()))
                        {
                            player.sendMessage(ChatUtil.fixColorsWithPrefix("&eten gracz ma juz oczekujace zaproszenie!"));
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                            return true;
                        }
                        else
                        {
                            player.sendMessage(ChatUtil.fixColorsWithPrefix("&ezaprosiles gracza &c" + target.getName() + "&e do swojej dzialki o nazwie &c" + plot.getPlot_name()));
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

                            target.sendMessage(ChatUtil.fixColorsWithPrefix("&eotrzymales zaproszenie do dzialki od gracza &c" + player_name + "&e do dzialki o nazwie &c" + plot.getPlot_name()));
                            target.playSound(target.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 1.0f, 1.0f);
                            invite_players.put(target.getName(), plot);

                            TextComponent confirm_message = new TextComponent("§a§lPOTWIERDZ");
                            confirm_message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Kliknij, aby akceptowac zaproszenie!").create()));
                            confirm_message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dzialka akceptujzaproszenie"));

                            TextComponent cancel_message = new TextComponent("§4§lANULUJ");
                            cancel_message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Kliknij, aby anulowac zaproszenie!").create()));
                            cancel_message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dzialka anulujzaproszenie"));

                            target.spigot().sendMessage(confirm_message);
                            target.spigot().sendMessage(cancel_message);

                            new BukkitRunnable()
                            {
                                @Override
                                public void run()
                                {
                                    if(invite_players.containsKey(target_name))
                                    {
                                        invite_players.remove(target_name);
                                    }
                                }
                            }.runTaskLaterAsynchronously(plugin, 400L);
                        }

                    }
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&eten gracz jest &coffline&e!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }
            }
            else if(args[0].equalsIgnoreCase("stworz"))
            {
                PlotMember member = plugin.getPlayerDataManager().getPlotMember(player.getName());
                if(member.getOwn_plot() != null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&ejestes juz wlascicielem dzialki"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                String plot_name = args[1];
                if(plot_name.length() <= 3 || plot_name.length() >= 11)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enazwa dzialki musi miec od &c4 do 10 znakow"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(!Utils.isAlphaNumeric(plot_name))
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enazwa dzialki nie moze zawierac znakow specjalnych"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(!player.getWorld().getName().equalsIgnoreCase("world"))
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&edzialka musi byc stworzona na swiecie survival"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(plugin.getPlayerDataManager().getPlots().get(plot_name) != null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&eistnieje juz dzialka o podanej nazwie! Wybierz inna..."));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                for(String plot : plugin.getPlayerDataManager().getPlots().keySet())
                {
                    if(plot.equalsIgnoreCase(plot_name))
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eistnieje juz dzialka o podanej nazwie! Wybierz inna..."));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                        return true;
                    }
                }

                int cordX = player.getLocation().getBlockX(), cordZ = player.getLocation().getBlockZ();
                if (Math.abs(cordX) < 400 && Math.abs(cordZ) < 400)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&eznajdujesz sie zbyt blisko spawnu! Minimalna odleglosc to &6400 kratek od spawnu"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(player.getLocation().getBlockY() < 40)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&eminimalna wysokosc utworzenia dzialki to Y40"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(plugin.getPlotManager().getPlotAt(player.getLocation(), 200) != null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&ew poblizu znajduje sie inna dzialka!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                int player_money = 0;
                int plot_create_cost = plugin.getConfig_manager().getPlot_create_cost();

                try {
                    player_money = Economy.getMoneyExact(player.getUniqueId()).intValue();
                } catch (UserDoesNotExistException e) {
                    e.printStackTrace();
                }

                if(player_money < plot_create_cost)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie posiadasz &c$" + plot_create_cost + "&e, masz tylko &c$" + player_money));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                String owner = player.getName();
                String location = LocationUtil.convertToString(player.getLocation());

                try {
                    Economy.add(player.getName(), -plot_create_cost);
                } catch (NoLoanPermittedException | UserDoesNotExistException e) {
                    e.printStackTrace();
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&ewystapil nieoczekiwany blad..."));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                PlotManager plotManager = OwnPlots.getInstance().getPlotManager();
                plotManager.createPlot(owner, plot_name, location);

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&7Tworzenie dzialki..."));
                player.sendTitle(ChatUtil.fixColors("&fOwn&9Craft.EU"), ChatUtil.fixColors("&7Tworzenie działki..."), 10, 60, 10);
                Bukkit.broadcastMessage(ChatUtil.fixColorsWithPrefix("&7Gracz &e" + owner + " &7stworzył działkę o nazwie &e" + plot_name));
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                {
                    if(player != null && player.isOnline())
                    {
                        player.chat("/dzialka border");
                    }
                }, 15L);

            }
        }

        return true;
    }


    private void sendHelpMessages(Player player)
    {
        player.sendMessage(ChatUtil.fixColors("&8&m-&6|&8&m---------&6[ &e&lDzialki &6]&8&m---------&6|&8&m-"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka stworz [nazwa] &7- tworzy dzialke, cena to &c$" + plugin.getConfig_manager().getPlot_create_cost()));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka dom <nazwa gracza> &7- teleportuje do dzialki gracza lub do swojej dzialki"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka wyrzuc [nick gracza] &7- wyrzuca gracza z dzialki"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka poziom &7- sprawdza poziom dzialki"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka ban [nick gracza] &7- banuje gracza na dzialce"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka unban [nick gracza] &7- odbanowuje gracza na dzialce"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka zamknij &7- zamyka dostep dla odwiedzajacych na dzialce"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka czlonkowie &7- wyswietla liste czlonkow dzialki"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka zapros [nick gracza] &7- zaprasza gracza do dzialki"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka usun &7- usuwa dzialke"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka opusc [nazwa dzialki] &7- opuszcza dzialke jesli jestes czlonkiem"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka ustawienia &7- wyswietla ustawienia dzialki"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka ulepszenia &7- wyswietla ulepszenia dzialki"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka border &7- wyswietla lub wylacza border dzialki na ktorej sie znajdujesz"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka info &7- wyswietla informacje o dzialce na ktorej sie znajdujesz"));
        player.sendMessage(ChatUtil.fixColors("&e/dzialka pomoc &7- wyswietla pomocne komendy"));

        if(player.hasPermission("ownplots.admin"))
        {
            player.sendMessage(ChatUtil.fixColors("&c&lKomendy dla admina:"));
            player.sendMessage(ChatUtil.fixColors("&e/dzialka usun [nazwa gracza] &7- usuwa dzialke gracza"));
        }
    }

}
