package eu.owncraft.plots.commands;

import eu.owncraft.plots.OwnPlots;
import eu.owncraft.plots.challenge.Challenge;
import eu.owncraft.plots.config.LanguageManager;
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
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        final LanguageManager languageManager = OwnPlots.getInstance().getLanguageManager();
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
                if(!player.getWorld().getName().equalsIgnoreCase(OwnPlots.getInstance().getConfig_manager().getAllowed_world()))
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-command-blocked-in-this-world")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                Plot plot = plugin.getPlotManager().getPlotAt(player.getLocation());
                if(plot != null)
                {
                    player.sendMessage(ChatUtil.fixColors("&8&m----------&8[ &f&lOwn&9&lPlot &8]&8&m----------"));
                    player.sendMessage(ChatUtil.fixColors(languageManager.getMessage("plot-info-name") + plot.getPlot_name()));
                    player.sendMessage(ChatUtil.fixColors(languageManager.getMessage("plot-info-size") + plot.getSize() + "x" + plot.getSize()));
                    player.sendMessage(ChatUtil.fixColors(languageManager.getMessage("plot-info-closed") + plot.isClosed()));
                    player.sendMessage(ChatUtil.fixColors(languageManager.getMessage("plot-info-bedrock") + LocationUtil.convertToString(plot.getLocation())));
                    player.sendMessage(ChatUtil.fixColors(languageManager.getMessage("plot-info-owner") + plot.getOwner()));
                    player.sendMessage(ChatUtil.fixColors(languageManager.getMessage("plot-info-level") + plot.getLevel()));
                    player.sendMessage(ChatUtil.fixColors(languageManager.getMessage("plot-info-pkt") + plot.getPoints()));

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

                    player.sendMessage(ChatUtil.fixColors(languageManager.getMessage("plot-info-banned") + banned_players));
                    player.sendMessage(ChatUtil.fixColors(languageManager.getMessage("plot-info-members") + members));
                    player.sendMessage(ChatUtil.fixColors(languageManager.getMessage("plot-info-member-count") + plot.getMembers().size() + "/" + plot.getMaxMembersCount()));
                    player.sendMessage(ChatUtil.fixColors(languageManager.getMessage("plot-info-bans-count") + plot.getBanned_players().size() + "/30"));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_LAUNCH, 1.0f, 1.0f);
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("no-plot-detected-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
            }
            else if(args[0].equalsIgnoreCase("stworz") || args[0].equalsIgnoreCase("create"))
            {
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&e/ownplots create [name]"));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            }
            else if(args[0].equalsIgnoreCase("bany") || args[0].equalsIgnoreCase("bans"))
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
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("no-plot-error")));
                }
            }
            else if(args[0].equalsIgnoreCase("opusc") || args[0].equalsIgnoreCase("leave"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                if(plot != null)
                {
                    if(plot.getOwner().equalsIgnoreCase(player.getName())) {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-leave-error1")));
                        return true;
                    }

                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-leave").replace("%plot_name%", plot.getPlot_name()).replace("%plot_owner%", plot.getOwner())));

                    PlotManager plotManager = plugin.getPlotManager();
                    plotManager.kickMember(plugin.getPlayerDataManager().getPlots().get(plot.getPlot_name()), player.getName());


                    if(plugin.getPlayerDataManager().getBorder_players().contains(player))
                    {
                        plugin.getPlayerDataManager().getBorder_players().remove(player);
                    }

                    plugin.getPlayerDataManager().getPlotMember(player.getName()).setOwn_plot(null);
                    plugin.getPlayerDataManager().getChallenge_players().remove(player.getName());
                }
                else
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("no-plot-error")));
                }
            }
            else if(args[0].equalsIgnoreCase("zamknij") || args[0].equalsIgnoreCase("close"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                if(plot != null)
                {
                    if(plot.isClosed())
                    {
                        player.playSound(player.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1.0f, 1.0f);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-has-been-opened")));
                        plot.setClosed(false);
                    }
                    else
                    {
                        player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_DOOR_OPEN, 1.0f, 1.0f);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-has-been-closed")));
                        plot.setClosed(true);
                    }
                }
                else
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("no-plot-error")));
                }
            }
            else if(args[0].equalsIgnoreCase("ban"))
            {
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&e/ownplots ban [nick]"));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            }
            else if(args[0].equalsIgnoreCase("unban"))
            {
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&e/ownplots unban [nick]"));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            }
            else if(args[0].equalsIgnoreCase("pomoc") || args[0].equalsIgnoreCase("help"))
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
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("no-plot-error")));
                }
            }
            else if(args[0].equalsIgnoreCase("czlonkowie") || args[0].equalsIgnoreCase("members"))
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
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("no-plot-error")));
                }
            }
            else if(args[0].equalsIgnoreCase("akceptujzaproszenie"))
            {
                if(OwnPlots.getInstance().getPlotManager().hasPlot(player)) {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-player-has-already-plot-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(invite_players.containsKey(player_name))
                {
                    Plot plot = invite_players.get(player_name);
                    invite_players.remove(player_name);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-target-join-plot").replace("%plot_owner%", plot.getOwner())));

                    for(String member : plot.getMembers())
                    {
                        Player plot_player = Bukkit.getPlayer(member);
                        if(plot_player != null && plot_player.isOnline())
                        {
                            plot_player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-player-join-to-your-plot").replace("%player_name%", player_name)));
                        }
                    }

                    PlotManager plotManager = plugin.getPlotManager();
                    plotManager.addMember(plugin.getPlayerDataManager().getPlots().get(plot.getPlot_name()), player_name);

                    Bukkit.getScheduler().scheduleSyncDelayedTask(OwnPlots.getInstance(), () -> {
                        PlotMember plotMember = plotManager.getPlotMember(player);
                        plugin.getPlayerDataManager().addPlayerMember(player.getName(), plotMember);
                        plugin.getPlayerDataManager().getChallenge_players().put(player.getName(), new Challenge(player));
                    }, 10L);
                }
                else
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("there-is-no-plot-invite")));
                }
            }
            else if(args[0].equalsIgnoreCase("anulujzaproszenie"))
            {
                if(invite_players.containsKey(player_name))
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-invite-cancellation")));
                    invite_players.remove(player_name);
                }
                else
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("there-is-no-plot-invite")));
                }
            }
            else if(args[0].equalsIgnoreCase("zapros") || args[0].equalsIgnoreCase("invite"))
            {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&e/ownplots invite [nick]"));
            }
            else if(args[0].equalsIgnoreCase("wyrzuc") || args[0].equalsIgnoreCase("kick"))
            {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&e/ownplots kick [nick]"));
            }
            else if(args[0].equalsIgnoreCase("usunanuluj"))
            {
                if(delete_confirm_players.contains(player_name))
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-delete-confirm-cancell")));
                    delete_confirm_players.remove(player_name);
                }
                else
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-delete-error3")));
                }
            }
            else if(args[0].equalsIgnoreCase("usunpotwierdz"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                if(plot == null)
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-delete-error-errno")));
                    return true;
                }

                if(delete_confirm_players.contains(player_name))
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.2f, 1.4f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-delete-success")));
                    delete_confirm_players.remove(player_name);
                    PlotManager plotManager = plugin.getPlotManager();
                    plotManager.deletePlot(plot);
                }
                else
                {
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-delete-error3")));
                }
            }
            else if(args[0].equalsIgnoreCase("usun") || args[0].equalsIgnoreCase("delete"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                if(plot != null)
                {
                    if(player.getName().equalsIgnoreCase(plot.getOwner()) == false) {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-delete-error1")));
                        return true;
                    }

                    if(delete_confirm_players.contains(player_name))
                    {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-delete-error2")));
                    }
                    else
                    {
                        delete_confirm_players.add(player_name);
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 1.0f, 1.0f);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-delete-confirm-message")));

                        TextComponent confirm_message = new TextComponent("§a§l" + languageManager.getMessage("confirm-text"));
                        confirm_message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Kliknij, aby usunac dzialke!").create()));
                        confirm_message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dzialka usunpotwierdz"));

                        TextComponent cancel_message = new TextComponent("§4§l" + languageManager.getMessage("cancel-text"));
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
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("no-plot-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
            }
            else if(args[0].equalsIgnoreCase("border"))
            {
                Plot plot = plugin.getPlotManager().getPlotAt(player.getLocation());
                if(plugin.getPlayerDataManager().getBorder_players().contains(player))
                {
                    plugin.getPlayerDataManager().getBorder_players().remove(player);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-border-disable")));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    PlotBorder plotBorder = new PlotBorder();
                    plotBorder.hideBorder(player);
                    return true;
                }
                if(plot == null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("no-plot-detected-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
                else
                {
                    plot.displayPlotBorder(player);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-border-toggle")));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    plugin.getPlayerDataManager().getBorder_players().add(player);
                }
            }
            else if(args[0].equalsIgnoreCase("ulepszenia") || args[0].equalsIgnoreCase("upgrades"))
            {
                Plot plot = plugin.getPlotManager().getPlotAt(player.getLocation());
                if(plot == null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("no-plot-detected-error")));
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
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-you-are-not-the-owner-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.closeInventory();
                }
            }
            else if(args[0].equalsIgnoreCase("ustawienia") || args[0].equalsIgnoreCase("settings"))
            {
                Plot plot = plugin.getPlotManager().getPlotAt(player.getLocation());
                if(plot == null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("no-plot-detected-error")));
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
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-you-are-not-the-owner-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    player.closeInventory();
                }

            }
            else if(args[0].equalsIgnoreCase("dom") || args[0].equalsIgnoreCase("home"))
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
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("no-plot-error")));
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-home-help-message-tip")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
            }
            else
            {
                sendHelpMessages(player);
            }
        }
        else if(args.length == 2)
        {
            if(args[0].equalsIgnoreCase("usun") || args[0].equalsIgnoreCase("delete"))
            {
                if(!player.hasPermission("ownplots.admin"))
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-command-no-permission")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                try
                {
                    Plot plot = PlotManager.getPlotByOwner(args[1]);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-admin-delete-plot").replace("%player%", args[1])));
                    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
                    PlotManager plotManager = plugin.getPlotManager();
                    plotManager.deletePlot(plot);
                }
                catch(NullPointerException exception)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-no-plot-error-target")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
                
            }
            else if(args[0].equalsIgnoreCase("dom") || args[0].equalsIgnoreCase("home"))
            {
                Plot plot = PlotManager.getPlotByOfflineOwner(args[1]);
                if(plot != null)
                {
                    if(plot.isBanned(player_name) || (plot.isClosed() && !plot.isMember(player)))
                    {
                        if(player.hasPermission("ownplots.admin"))
                        {
                            player.teleport(plot.getLocation());
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                            player.sendMessage(ChatUtil.fixColorsWithPrefix("&eTeleportuje do dzialki gracza &c" + args[1] + "&e!"));
                            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () ->
                            {
                                player.chat("/dzialka border");
                            }, 10L);
                            return true;
                        }
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-closed-error")));
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
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-no-plot-error-target")));
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
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-ban-error3").replace("%ban_name%", ban_name)));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                    else
                    {
                        if(plot.getBanned_players().size() >= 30)
                        {
                            player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-ban-error1")));
                            player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-ban-error2")));
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                        }
                        else
                        {
                            plot.banPlayer(ban_name);
                            player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-ban").replace("%ban_name%", ban_name)));
                            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                        }
                    }
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("no-plot-error")));
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
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-unban").replace("%ban_name%", ban_name)));
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                    }
                    else
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-unban-error1").replace("%ban_name%", ban_name)));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("no-plot-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
            }
            else if(args[0].equalsIgnoreCase("wyrzuc") || args[0].equalsIgnoreCase("kick"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                if(plot == null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("no-plot-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);

                if(player.getName().equalsIgnoreCase(plot.getOwner()) == false) {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-kick-error4")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(target.getName().equalsIgnoreCase(player.getName())) {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-kick-error3")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(plot.getOwner().equalsIgnoreCase(args[1]))
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-kick-error2")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(target != null && target.isOnline())
                {
                    if(plot.isMember(target))
                    {
                        final String target_name = target.getName();
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-kick-player").replace("%target_name%", target_name)));
                        player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.0f, 1.0f);

                        target.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-kick-target").replace("%plot_name%", plot.getPlot_name().replace("%player_name%", player_name))));
                        target.playSound(target.getLocation(), Sound.ENTITY_IRON_GOLEM_ATTACK, 1.0f, 1.0f);

                        PlotManager plotManager = plugin.getPlotManager();
                        plotManager.kickMember(plugin.getPlayerDataManager().getPlots().get(plot.getPlot_name()), target.getName());


                        if(plugin.getPlayerDataManager().getBorder_players().contains(target))
                        {
                            plugin.getPlayerDataManager().getBorder_players().remove(target);
                        }

                        plugin.getPlayerDataManager().getPlotMember(target.getName()).setOwn_plot(null);
                        plugin.getPlayerDataManager().getChallenge_players().remove(target.getName());
                    }
                    else
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-kick-error1")));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                }
                else
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-player-offline-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }
            }
            else if(args[0].equalsIgnoreCase("zapros") || args[0].equalsIgnoreCase("invite"))
            {
                Plot plot = PlotManager.getPlotByOwner(player_name);
                int max_members_count = plot.getMaxMembersCount();
                if(plot == null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-invite-error5")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(plot.getMembers().size() >= max_members_count)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-invite-error4").replace("%max%", "" + max_members_count)));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if(target != null && target.isOnline())
                {
                    if(OwnPlots.getInstance().getPlotManager().hasPlot(target)) {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-invite-error3")));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                        return true;
                    }

                    final String target_name = target.getName();
                    if(plot.isMember(target))
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-invite-error2")));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                        return true;
                    }
                    else
                    {
                        if(invite_players.containsKey(target.getName()))
                        {
                            player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-invite-error1")));
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                            return true;
                        }
                        else
                        {
                            player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-invite-player").replace("%target_name%", target_name)));
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

                            target.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-invite-receive").replace("%player_name%", player.getName()).replace("%plot_name%", plot.getPlot_name())));
                            target.playSound(target.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 1.0f, 1.0f);
                            invite_players.put(target.getName(), plot);

                            TextComponent confirm_message = new TextComponent("§a§l" + languageManager.getMessage("cancel-text"));
                            confirm_message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Kliknij, aby akceptowac zaproszenie!").create()));
                            confirm_message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dzialka akceptujzaproszenie"));

                            TextComponent cancel_message = new TextComponent("§4§l" + languageManager.getMessage("confirm-text"));
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
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-player-offline-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }
            }
            else if(args[0].equalsIgnoreCase("stworz") || args[0].equalsIgnoreCase("create"))
            {
                PlotMember member = plugin.getPlayerDataManager().getPlotMember(player.getName());
                if(member.getOwn_plot() != null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-creation-already-plot-created-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                String plot_name = args[1];
                if(plot_name.length() <= 3 || plot_name.length() >= 11)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-creation-name-length-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(!Utils.isAlphaNumeric(plot_name))
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-creation-name-special-chars-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(!player.getWorld().getName().equalsIgnoreCase(OwnPlots.getInstance().getConfig_manager().getAllowed_world()))
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-creation-enabled-world-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(plugin.getPlayerDataManager().getPlots().get(plot_name) != null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-creation-name-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                for(String plot : plugin.getPlayerDataManager().getPlots().keySet())
                {
                    if(plot.equalsIgnoreCase(plot_name))
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-creation-name-error")));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                        return true;
                    }
                }

                int cordX = player.getLocation().getBlockX(), cordZ = player.getLocation().getBlockZ();
                if (Math.abs(cordX) < 400 && Math.abs(cordZ) < 400)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-creation-spawn-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(player.getLocation().getBlockY() < 40)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-creation-height-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                if(plugin.getPlotManager().getPlotAt(player.getLocation(), 200) != null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-creation-nearby-plot-error")));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                long player_money = OwnPlots.getInstance().getPlayerMoney(player);
                int plot_create_cost = plugin.getConfig_manager().getPlot_create_cost();

                if(player_money < plot_create_cost)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-creation-no-money").replace("%required_money%", "" + plot_create_cost).replace("%money%", "" + player_money)));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return true;
                }

                String owner = player.getName();
                String location = LocationUtil.convertToString(player.getLocation());

                OwnPlots.getInstance().takePlayerMoney(player, plot_create_cost);

                PlotManager plotManager = OwnPlots.getInstance().getPlotManager();
                plotManager.createPlot(player, plot_name, location);

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                player.sendMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-creation")));
                player.sendTitle(ChatUtil.fixColors("&fOwn&9Craft.EU"), languageManager.getMessage("plot-creation"), 10, 60, 10);
                Bukkit.broadcastMessage(ChatUtil.fixColorsWithPrefix(languageManager.getMessage("plot-creation-broadcast").replace("%player%", owner).replace("%plot_name%", plot_name)));

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if(player != null && player.isOnline()) {
                        player.chat("/dzialka border");
                    }
                }, 15L);

            }
        }

        return true;
    }


    private void sendHelpMessages(Player player)
    {
        LanguageManager languageManager = OwnPlots.getInstance().getLanguageManager();

        player.sendMessage(ChatUtil.fixColors("&8&m-&3|&8&m---------&3[ &f&lOwn&9&lPlots &3]&8&m---------&3|&8&m-"));
        player.sendMessage(languageManager.getMessage("help-create-plot") + plugin.getConfig_manager().getPlot_create_cost());
        player.sendMessage(languageManager.getMessage("help-plot-home"));
        player.sendMessage(languageManager.getMessage("help-plot-kick"));
        player.sendMessage(languageManager.getMessage("help-plot-level"));
        player.sendMessage(languageManager.getMessage("help-plot-ban"));
        player.sendMessage(languageManager.getMessage("help-plot-unban"));
        player.sendMessage(languageManager.getMessage("help-plot-close"));
        player.sendMessage(languageManager.getMessage("help-plot-members"));
        player.sendMessage(languageManager.getMessage("help-plot-invite"));
        player.sendMessage(languageManager.getMessage("help-plot-delete"));
        player.sendMessage(languageManager.getMessage("help-plot-leave"));
        player.sendMessage(languageManager.getMessage("help-plot-settings"));
        player.sendMessage(languageManager.getMessage("help-plot-upgrades"));
        player.sendMessage(languageManager.getMessage("help-plot-border"));
        player.sendMessage(languageManager.getMessage("help-plot-info"));
        player.sendMessage(languageManager.getMessage("help-plot-help"));

        if(player.hasPermission("ownplots.admin")) {
            player.sendMessage(languageManager.getMessage("help-plot-admin-text"));
            player.sendMessage(languageManager.getMessage("help-plot-admin-delete"));
        }
    }

}
