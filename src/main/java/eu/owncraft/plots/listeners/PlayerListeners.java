package eu.owncraft.plots.listeners;

import eu.owncraft.plots.OwnPlots;
import eu.owncraft.plots.events.PlayerEnterPlotEvent;
import eu.owncraft.plots.events.PlayerExitPlotEvent;
import eu.owncraft.plots.plot.Plot;
import eu.owncraft.plots.plot.PlotBorder;
import eu.owncraft.plots.plot.VisitorsSettings;
import eu.owncraft.plots.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class PlayerListeners implements Listener {

    private final OwnPlots plugin;
    private ArrayList<String> inside_plot_players = new ArrayList<>();

    public PlayerListeners(OwnPlots plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if(!player.getWorld().getName().equalsIgnoreCase("world")) {
            return;
        }

        if(event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.FARMLAND)
        {
            Plot plot = plugin.getPlotManager().getPlotAt(event.getClickedBlock().getLocation());
            if(plot != null && !plot.isMember(player) && !plot.getVisitorsSettings().isTrample())
            {
                event.setCancelled(true);
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz na to pozwolenia!"));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            }
        }

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if(event.getClickedBlock().getType() == Material.BEDROCK) {
            Plot plot = plugin.getPlotManager().getPlotAt(event.getClickedBlock().getLocation());
            if(plot != null && plot.getOwner().equalsIgnoreCase(player.getName()) && plot.getLocation().getBlockY() - 1 == event.getClickedBlock().getLocation().getBlockY())
            {
                ItemStack item = player.getInventory().getItemInMainHand();
                if(item != null && (item.getType() == Material.IRON_BLOCK || item.getType() == Material.GOLD_BLOCK
                    || item.getType() == Material.DIAMOND_BLOCK || item.getType() == Material.EMERALD_BLOCK
                    || item.getType() == Material.NETHERITE_BLOCK || item.getType() == Material.BEACON))
                {
                    event.setCancelled(true);
                    if(player.isSneaking())
                    {
                        Material material = item.getType();
                        switch(material)
                        {
                            case IRON_BLOCK:
                                plot.setIron_blocks(plot.getIron_blocks() + item.getAmount());
                                break;
                            case GOLD_BLOCK:
                                plot.setGold_blocks(plot.getGold_blocks() + item.getAmount());
                                break;
                            case DIAMOND_BLOCK:
                                plot.setDiamond_blocks(plot.getDiamond_blocks() + item.getAmount());
                                break;
                            case EMERALD_BLOCK:
                                plot.setEmerald_blocks(plot.getEmerald_blocks() + item.getAmount());
                                break;
                            case NETHERITE_BLOCK:
                                plot.setNetherite_blocks(plot.getNetherite_blocks() + item.getAmount());
                                break;
                            case BEACON:
                                plot.setBeacons(plot.getBeacons() + item.getAmount());
                                break;
                        }

                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eulepszyles swoja dzialke o x" + item.getAmount() + " blokow " + item.getType()));
                        player.getInventory().getItemInMainHand().setAmount(0);
                    }
                    else
                    {
                        Material material = item.getType();
                        switch(material)
                        {
                            case IRON_BLOCK:
                                plot.setIron_blocks(plot.getIron_blocks() + 1);
                                break;
                            case GOLD_BLOCK:
                                plot.setGold_blocks(plot.getGold_blocks() + 1);
                                break;
                            case DIAMOND_BLOCK:
                                plot.setDiamond_blocks(plot.getDiamond_blocks() + 1);
                                break;
                            case EMERALD_BLOCK:
                                plot.setEmerald_blocks(plot.getEmerald_blocks() + 1);
                                break;
                            case NETHERITE_BLOCK:
                                plot.setNetherite_blocks(plot.getNetherite_blocks() + 1);
                                break;
                            case BEACON:
                                plot.setBeacons(plot.getBeacons() + 1);
                                break;
                        }

                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eulepszyles swoja dzialke o x1 blokow " + item.getType()));
                        player.getInventory().getItemInMainHand().setAmount(item.getAmount() - 1);
                    }
                    plot.calculatePoints();
                    plot.calculateLevel();
                    return;
                }

            }
        }

        if(player.hasPermission("ownplots.bypass")) {
            return;
        }

        Plot plot = plugin.getPlotManager().getPlotAt(event.getClickedBlock().getLocation());
        if(plot != null)
        {
            if(!plot.getMembers().contains(player.getName()))
            {
                Block block = event.getClickedBlock();
                Material type = block.getType();
                VisitorsSettings visitorsSettings = plot.getVisitorsSettings();
                if((type == Material.CHEST || type == Material.ENDER_CHEST
                    || type == Material.CHEST_MINECART || type.toString().contains("SHULKER_BOX")) && !visitorsSettings.isChest_access())
                {
                    event.setCancelled(true);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz na to pozwolenia!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
                else if(type.toString().contains("FURNACE") && !visitorsSettings.isFurnace_use())
                {
                    event.setCancelled(true);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz na to pozwolenia!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
                else if(type.toString().contains("DOOR") && !visitorsSettings.isDoor_use())
                {
                    event.setCancelled(true);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz na to pozwolenia!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
                else if((type.toString().contains("BUTTON") || type == Material.LEVER)
                    && !visitorsSettings.isButton_use())
                {
                    event.setCancelled(true);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz na to pozwolenia!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
                else
                {
                    event.setCancelled(true);
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz na to pozwolenia!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
            }
        }


    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event)
    {
        Player player = event.getPlayer();
        if(!player.getWorld().getName().equalsIgnoreCase("world"))
        {
            return;
        }

        if(player.hasPermission("ownplots.admin"))
        {
            return;
        }

        if(event.getRightClicked() != null && (event.getRightClicked().getType() == EntityType.ITEM_FRAME
                || event.getRightClicked().getType() == EntityType.ARMOR_STAND))
        {
            Plot plot = plugin.getPlotManager().getPlotAt(event.getRightClicked().getLocation());
            if(plot != null && !plot.getMembers().contains(player.getName()))
            {
                event.setCancelled(true);
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie mozesz tego zrobic na tej dzialce!"));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerMove(PlayerMoveEvent event)
    {
        if (event.getFrom().getBlockX() == event.getTo().getBlockX()
                && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) {
            return;
        }
        final Player player = event.getPlayer();
        if(!player.getWorld().getName().equalsIgnoreCase("world"))
        {
            return;
        }

        Plot plot = plugin.getPlotManager().getPlotAt(player.getLocation());
        if(plot != null)
        {
            if(plot.isSpeed_upgrade() && !player.hasPotionEffect(PotionEffectType.SPEED))
            {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
            }

            if(plot.isJump_upgrade() && !player.hasPotionEffect(PotionEffectType.JUMP))
            {
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 1));
            }
        }

        if(inside_plot_players.contains(player.getName()))
        {
            if(plot == null)
            {
                inside_plot_players.remove(player.getName());
                PlayerExitPlotEvent playerExitPlotEvent = new PlayerExitPlotEvent(player);
                plugin.getServer().getPluginManager().callEvent(playerExitPlotEvent);
            }
        }
        else
        {
            if(plot != null)
            {
                inside_plot_players.add(player.getName());
                PlayerEnterPlotEvent playerEnterPlotEvent = new PlayerEnterPlotEvent(player, plot);
                plugin.getServer().getPluginManager().callEvent(playerEnterPlotEvent);
            }
        }

    }
    
    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent event)
    {
        Player player = event.getPlayer();
        if(!player.getWorld().getName().equalsIgnoreCase("world"))
        {
            return;
        }
        if(player.hasPermission("ownplots.admin"))
        {
            return;
        }
        Plot plot = plugin.getPlotManager().getPlotAt(player.getLocation());
        if(plot != null && !plot.getMembers().contains(player.getName()))
        {
            event.setCancelled(true);
            player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie mozesz tego zrobic na tej dzialce!"));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        }
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event)
    {
        Player player = event.getPlayer();
        if(!player.getWorld().getName().equalsIgnoreCase("world"))
        {
            return;
        }
        if(player.hasPermission("ownplots.admin"))
        {
            return;
        }
        Plot plot = plugin.getPlotManager().getPlotAt(player.getLocation());
        if(plot != null && !plot.getMembers().contains(player.getName()))
        {
            event.setCancelled(true);
            player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie mozesz tego zrobic na tej dzialce!"));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
        }
    }

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event)
    {
        if(!(event.getEntity() instanceof Player))
        {
            return;
        }
        Player player = (Player) event.getEntity();
        if(!player.getWorld().getName().equalsIgnoreCase("world"))
        {
            return;
        }
        if(player.hasPermission("ownplots.admin"))
        {
            return;
        }
        Plot plot = plugin.getPlotManager().getPlotAt(event.getItem().getLocation());
        if(plot != null && !plot.isMember(player) && !plot.getVisitorsSettings().isItem_pickup())
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event)
    {
        Player player = event.getPlayer();
        Location to = event.getTo();
        Location from = event.getFrom();
        if((to.getBlockX() != from.getBlockX() || to.getBlockZ() != from.getBlockZ()) &&
                plugin.getPlayerDataManager().getBorder_players().contains(player))
        {
            PlotBorder plotBorder = new PlotBorder();
            plotBorder.hideBorder(player, Bukkit.getWorld("world"));
            plugin.getPlayerDataManager().getBorder_players().remove(player);
        }
    }

}
