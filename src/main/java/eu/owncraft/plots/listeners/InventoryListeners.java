package eu.owncraft.plots.listeners;

import eu.owncraft.plots.OwnPlots;
import eu.owncraft.plots.challenge.Challenge;
import eu.owncraft.plots.database.PlotManager;
import eu.owncraft.plots.gui.PlotGUI;
import eu.owncraft.plots.plot.Plot;
import eu.owncraft.plots.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListeners implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClick(InventoryClickEvent event)
    {
        if(event.getClickedInventory() == null)
        {
            return;
        }

        if(event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColorsWithPrefix("&e&lStworz dzialke")))
        {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if(item == null || !item.hasItemMeta())
            {
                return;
            }

            if(!(event.getWhoClicked() instanceof Player))
            {
                return;
            }

            final Player player = (Player) event.getWhoClicked();

            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&7Stworz &edzialke")))
            {
                player.sendMessage(ChatUtil.fixColorsWithPrefix("&7Poprawne uzycie: &e/dzialka stworz [nazwa]"));
                player.closeInventory();
            }
        }
        else if(event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColorsWithPrefix("&e&lUlepszenia dzialki")))
        {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if(item == null || !item.hasItemMeta())
            {
                return;
            }

            if(!(event.getWhoClicked() instanceof Player))
            {
                return;
            }

            final Player player = (Player) event.getWhoClicked();
            long money = OwnPlots.getInstance().getPlayerMoney(player);

            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&bUlepszenie speeda")))
            {
                int cost = OwnPlots.getInstance().getConfig_manager().getSpeed_upgrade_cost();
                Plot plot = PlotManager.getPlotByOwner(player.getName());
                if(plot.isSpeed_upgrade())
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&emasz juz zakupione to ulepszenie!"));
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                }
                else
                {
                    if(plot.getLevel() < 50)
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eaby zakupic to ulepszenie musisz miec 50 level dzialki!"));
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                        return;
                    }
                    if(money >= cost)
                    {
                        OwnPlots.getInstance().takePlayerMoney(player, cost);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&bzakupiles Ulepszenie speeda II!"));
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                        plot.setSpeed_upgrade(true);
                        player.openInventory(PlotGUI.getPlotUpgradeInventory(player, plot));
                    }
                    else
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie posiadasz &c$" + cost));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                }

            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&bUlepszenie jump boosta")))
            {
                int cost = OwnPlots.getInstance().getConfig_manager().getJump_boost_upgrade_cost();
                Plot plot = PlotManager.getPlotByOwner(player.getName());
                if(plot.isJump_upgrade())
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&emasz juz zakupione to ulepszenie!"));
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                }
                else
                {
                    if(plot.getLevel() < 100)
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eaby zakupic to ulepszenie musisz miec 100 level dzialki!"));
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                        return;
                    }
                    if(money >= cost)
                    {
                        OwnPlots.getInstance().takePlayerMoney(player, cost);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&bzakupiles Ulepszenie jump boost II!"));
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                        plot.setJump_upgrade(true);
                        player.openInventory(PlotGUI.getPlotUpgradeInventory(player, plot));
                    }
                    else
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie posiadasz &c$" + cost));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                }

            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&bDrop z mobow")))
            {
                int cost = OwnPlots.getInstance().getConfig_manager().getMob_drop_upgrade_cost();
                Plot plot = PlotManager.getPlotByOwner(player.getName());
                if(plot.isMob_drop_upgrade())
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&emasz juz zakupione to ulepszenie!"));
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                }
                else
                {
                    if(plot.getLevel() < 80)
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eaby zakupic to ulepszenie musisz miec 80 level dzialki!"));
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                        return;
                    }
                    if(money >= cost)
                    {
                        OwnPlots.getInstance().takePlayerMoney(player, cost);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&bzakupiles Ulepszenie drop z mobow x2!"));
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                        plot.setMob_drop_upgrade(true);
                        player.openInventory(PlotGUI.getPlotUpgradeInventory(player, plot));
                    }
                    else
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie posiadasz &c$" + cost));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                }

            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&bEXP z mobow")))
            {
                int cost = OwnPlots.getInstance().getConfig_manager().getMob_exp_upgrade_cost();
                Plot plot = PlotManager.getPlotByOwner(player.getName());
                if(plot.isMob_exp_upgrade())
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&emasz juz zakupione to ulepszenie!"));
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                }
                else
                {
                    if(plot.getLevel() < 70)
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&eaby zakupic to ulepszenie musisz miec 70 level dzialki!"));
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                        return;
                    }
                    if(money >= cost)
                    {
                        OwnPlots.getInstance().takePlayerMoney(player, cost);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&bzakupiles Ulepszenie exp z mobow x2!"));
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                        plot.setMob_exp_upgrade(true);
                        player.openInventory(PlotGUI.getPlotUpgradeInventory(player, plot));
                    }
                    else
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie posiadasz &c$" + cost));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                }
            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&bPowieksz rozmiar dzialki")))
            {
                int cost = OwnPlots.getInstance().getConfig_manager().getPlot_increase_field_cost();
                Plot plot = PlotManager.getPlotByOwner(player.getName());
                switch(plot.getSize())
                {
                    case 16:
                        break;
                    case 32:
                        cost = cost * 2;
                        break;
                    case 48:
                        cost = cost * 3;
                        break;
                    case 64:
                        cost = cost * 4;
                        break;
                    default:
                        cost = cost * 5;
                        break;
                }


                if(plot.getSize() >= OwnPlots.getInstance().getConfig_manager().getPlot_max_size())
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&etwoja dzialka posiada maksymalny rozmiar!"));
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                }
                else
                {
                    if(money >= cost)
                    {
                        OwnPlots.getInstance().takePlayerMoney(player, cost);
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&bzakupiles Powiekszenie rozmiaru dzialki!"));
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                        plot.setSize(plot.getSize() + 16);
                        player.openInventory(PlotGUI.getPlotUpgradeInventory(player, plot));

                        if(OwnPlots.getInstance().getPlayerDataManager().getBorder_players().contains(player))
                        {
                            plot.displayPlotBorder(player);
                        }
                        else
                        {
                            plot.displayPlotBorder(player);
                            OwnPlots.getInstance().getPlayerDataManager().getBorder_players().add(player);
                        }
                    }
                    else
                    {
                        player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie posiadasz &c$" + cost));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                }

            }
        }
        else if(event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColorsWithPrefix("&e&lUstawienia dzialki")))
        {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if(item == null || !item.hasItemMeta())
            {
                return;
            }

            if(!(event.getWhoClicked() instanceof Player))
            {
                return;
            }

            final Player player = (Player) event.getWhoClicked();

            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&cKliknij, aby wyjsc")))
            {
                player.openInventory(PlotGUI.getPlotSettingsCategoryInventory(player));
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 0.9f);
            }

            switch(event.getSlot())
            {
                case 9:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getPlotSettings().setNatural_spawn(!status);
                    player.openInventory(PlotGUI.getPlotSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
                case 10:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getPlotSettings().setLeaf_decay(!status);
                    player.openInventory(PlotGUI.getPlotSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
                case 11:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getPlotSettings().setFire_spread(!status);
                    player.openInventory(PlotGUI.getPlotSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
                case 12:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getPlotSettings().setExplosions(!status);
                    player.openInventory(PlotGUI.getPlotSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
                case 13:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getPlotSettings().setPvp(!status);
                    player.openInventory(PlotGUI.getPlotSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
                case 14:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getPlotSettings().setMob_damage_players(!status);
                    player.openInventory(PlotGUI.getPlotSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
            }
        }
        else if(event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColorsWithPrefix("&e&lUstawienia odwiedzajacych")))
        {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if(item == null || !item.hasItemMeta())
            {
                return;
            }

            if(!(event.getWhoClicked() instanceof Player))
            {
                return;
            }

            final Player player = (Player) event.getWhoClicked();

            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&cKliknij, aby wyjsc")))
            {
                player.openInventory(PlotGUI.getPlotSettingsCategoryInventory(player));
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 0.9f);
                return;
            }

            switch(event.getSlot())
            {
                case 9:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getVisitorsSettings().setDoor_use(!status);
                    player.openInventory(PlotGUI.getVisitorsSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
                case 10:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getVisitorsSettings().setButton_use(!status);
                    player.openInventory(PlotGUI.getVisitorsSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
                case 11:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getVisitorsSettings().setFurnace_use(!status);
                    player.openInventory(PlotGUI.getVisitorsSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
                case 12:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getVisitorsSettings().setChest_access(!status);
                    player.openInventory(PlotGUI.getVisitorsSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
                case 13:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getVisitorsSettings().setMob_damage(!status);
                    player.openInventory(PlotGUI.getVisitorsSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
                case 14:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getVisitorsSettings().setItem_pickup(!status);
                    player.openInventory(PlotGUI.getVisitorsSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
                case 15:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getVisitorsSettings().setBlock_break(!status);
                    player.openInventory(PlotGUI.getVisitorsSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
                case 16:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getVisitorsSettings().setBlock_place(!status);
                    player.openInventory(PlotGUI.getVisitorsSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
                case 17:
                {
                    boolean status = item.getItemMeta().getLore().get(0).contains("Zezwolone");
                    Plot plot = PlotManager.getPlotByOwner(player.getName());
                    plot.getVisitorsSettings().setTrample(!status);
                    player.openInventory(PlotGUI.getVisitorsSettingsInventory(player, plot));
                    player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                    break;
                }
            }

        }
        else if(event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColorsWithPrefix("&e&lUstawienia - Kategorie")))
        {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if(item == null || !item.hasItemMeta())
            {
                return;
            }

            if(!(event.getWhoClicked() instanceof Player))
            {
                return;
            }

            final Player player = (Player) event.getWhoClicked();

            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&cKliknij, aby wyjsc")))
            {
                player.openInventory(PlotGUI.getMainInventory(player));
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 0.9f);
            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&aUstawienia dzialki")))
            {
                player.openInventory(PlotGUI.getPlotSettingsInventory(player, PlotManager.getPlotByOwner(player.getName())));
                player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&aUstawienia odwiedzajacych")))
            {
                player.openInventory(PlotGUI.getVisitorsSettingsInventory(player, PlotManager.getPlotByOwner(player.getName())));
                player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
            }
        }
        else if(event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColorsWithPrefix("&e&lZbanowani gracze"))) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (!(event.getWhoClicked() instanceof Player)) {
                return;
            }

            final Player player = (Player) event.getWhoClicked();
            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&cKliknij, aby wyjsc")))
            {
                player.openInventory(PlotGUI.getMainInventory(player));
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 0.9f);
            }

            if(item.getType() == Material.PLAYER_HEAD && event.getClick() == ClickType.RIGHT)
            {
                String target_name = item.getItemMeta().getDisplayName().substring(2);
                player.chat("/dzialka unban " + target_name);
                player.openInventory(PlotGUI.getPlotBannedPlayersInventory(player, PlotManager.getPlotByOwner(player.getName())));
            }
        }
        else if(event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColorsWithPrefix("&e&lRanking dzialek")))
        {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if(item == null || !item.hasItemMeta())
            {
                return;
            }

            if(!(event.getWhoClicked() instanceof Player))
            {
                return;
            }

            final Player player = (Player) event.getWhoClicked();

            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&cOdśwież")))
            {
                player.closeInventory();
                player.openInventory(PlotGUI.getRankingInventory(player));
                player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 0.7f, 0.7f);
            }
            else if(item.getItemMeta().getDisplayName().contains("#"))
            {
                player.closeInventory();
                String owner_name = item.getItemMeta().getDisplayName().substring(18);
                player.chat("/d dom " + owner_name);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.7f, 0.7f);
            }

        }
        else if(event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColorsWithPrefix("&e&lMenu dzialki")))
        {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if(item == null || !item.hasItemMeta())
            {
                return;
            }

            if(!(event.getWhoClicked() instanceof Player))
            {
                return;
            }

            final Player player = (Player) event.getWhoClicked();

            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&cUstawienia dzialki")))
            {
                player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&aTeleport na dzialke")))
            {
                player.chat("/dzialka dom");
                player.closeInventory();
            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&aBlokowanie dzialki")))
            {
                player.chat("/dzialka zamknij");
                player.closeInventory();
            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&aWyzwania")))
            {
                player.closeInventory();
                player.chat("/wyzwania");
            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&aRanking")))
            {
                player.openInventory(PlotGUI.getRankingInventory(player));
                player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 0.7f, 0.7f);
            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&aCzlonkowie dzialki")))
            {
                player.chat("/dzialka czlonkowie");
            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&aUlepszenia dzialki")))
            {
                player.chat("/dzialka ulepszenia");
            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&aZbanowani gracze")))
            {
                player.chat("/dzialka bany");
            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&aUstawienia dzialki")))
            {
                player.chat("/dzialka ustawienia");
            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&aBorder dzialki")))
            {
                player.closeInventory();
                player.chat("/dzialka border");
            }
            else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&aPoziom dzialki")))
            {
                player.chat("/dzialka poziom");
            }

        }
        else if(event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColorsWithPrefix("&e&lCzlonkowie dzialki")))
        {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (!(event.getWhoClicked() instanceof Player)) {
                return;
            }

            final Player player = (Player) event.getWhoClicked();
            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&cKliknij, aby wyjsc")))
            {
                player.openInventory(PlotGUI.getMainInventory(player));
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 0.9f);
            }

            if(item.getType() == Material.PLAYER_HEAD && event.getClick() == ClickType.RIGHT)
            {
                String target_name = item.getItemMeta().getDisplayName().substring(2);
                player.chat("/dzialka wyrzuc " + target_name);
                player.openInventory(PlotGUI.getPlotMembersInventory(player, PlotManager.getPlotByOwner(player.getName())));
            }
        }
        else if(event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColorsWithPrefix("&e&lPoziom dzialki")))
        {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (!(event.getWhoClicked() instanceof Player)) {
                return;
            }

            final Player player = (Player) event.getWhoClicked();
            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&cKliknij, aby wyjsc")))
            {
                player.openInventory(PlotGUI.getMainInventory(player));
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 0.9f);
            }
        }
        else if(event.getView().getTitle().equalsIgnoreCase(ChatUtil.fixColorsWithPrefix("&e&lWyzwania")))
        {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (!(event.getWhoClicked() instanceof Player)) {
                return;
            }

            final Player player = (Player) event.getWhoClicked();
            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtil.fixColors("&cWyzwania")))
            {
                player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
            }
            else if(item.getItemMeta().getDisplayName().contains(ChatUtil.fixColors("&aPoziom")))
            {
                int required_level = Integer.parseInt(item.getItemMeta().getDisplayName().substring(9));
                Plot plot = PlotManager.getPlotByOwner(player.getName());
                if(plot == null)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&enie masz swojej dzialki!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return;
                }

                if(plot.getLevel() < required_level)
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&etwoj poziom dzialki &cLvl." + plot.getLevel() + " &ejest zbyt maly! Potrzebujesz &cLvl." + required_level));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return;
                }

                Challenge challenge = OwnPlots.getInstance().getPlayerDataManager().getChallenge_players().get(player.getName());
                if(challenge.isChallengeCompleted(required_level))
                {
                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&emasz juz wykonane to zadanie!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                }
                else
                {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + player.getName()
                            + " " + challenge.getMysteryDust(required_level));
                    challenge.setChallengeComplete(required_level);

                    player.sendMessage(ChatUtil.fixColorsWithPrefix("&ewykonales wyzwanie oraz odebrales nagrode!"));
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                    event.getCurrentItem().setType(Material.HOPPER_MINECART);
                }
            }
        }
    }

}
