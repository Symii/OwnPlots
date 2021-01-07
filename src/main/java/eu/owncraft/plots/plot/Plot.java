package eu.owncraft.plots.plot;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Plot {

    private String owner;
    private Location location;
    private ArrayList<String> members;
    private String plot_name;
    private boolean closed;

    private PlotSettings plotSettings;
    private VisitorsSettings visitorsSettings;
    private ArrayList<String> banned_players;
    private int points, level;
    private int iron_blocks, gold_blocks, diamond_blocks, emerald_blocks, netherite_blocks, beacons;
    private boolean speed_upgrade, jump_upgrade, mob_drop_upgrade, mob_exp_upgrade;
    private int size;

    public Plot(String owner, Location location, ArrayList<String> members, String plot_name, boolean closed, ArrayList<String> banned_players, String level_data, String upgrade_data)
    {
        this.owner = owner;
        this.location = location;
        this.members = members;
        this.plot_name = plot_name;

        this.plotSettings = new PlotSettings(plot_name);
        this.visitorsSettings = new VisitorsSettings(plot_name);
        this.closed = closed;
        this.banned_players = banned_players;

        String[] array = level_data.split(";");
        this.iron_blocks = Integer.parseInt(array[0]);
        this.gold_blocks = Integer.parseInt(array[1]);
        this.diamond_blocks = Integer.parseInt(array[2]);
        this.emerald_blocks = Integer.parseInt(array[3]);
        this.netherite_blocks = Integer.parseInt(array[4]);
        this.beacons = Integer.parseInt(array[5]);

        String[] upgrade_data_array = upgrade_data.split(";");
        this.speed_upgrade = Boolean.valueOf(upgrade_data_array[0]);
        this.jump_upgrade = Boolean.valueOf(upgrade_data_array[1]);
        this.mob_drop_upgrade = Boolean.valueOf(upgrade_data_array[2]);
        this.mob_exp_upgrade = Boolean.valueOf(upgrade_data_array[3]);
        this.size = Integer.parseInt(upgrade_data_array[4]);
        calculatePoints();
        calculateLevel();
    }

    public int getMaxMembersCount()
    {
        int max = 5;
        if(level >= 10)
        {
            max = max + (int) (level / 10);
        }
        if(max > 30)
        {
            max = 30;
        }
        return max;
    }

    public void calculatePoints()
    {
        int pkt = 0;
        pkt += (iron_blocks * 10);
        pkt += (gold_blocks * 25);
        pkt += (diamond_blocks * 50);
        pkt += (emerald_blocks * 75);
        pkt += (netherite_blocks * 100);
        pkt += (beacons * 250);
        this.setPoints(pkt);
    }

    public void calculateLevel()
    {
        int lvl = (int) (points / 150);
        this.setLevel(lvl);
    }

    public void setSpeed_upgrade(boolean speed_upgrade) {
        this.speed_upgrade = speed_upgrade;
    }

    public void setJump_upgrade(boolean jump_upgrade) {
        this.jump_upgrade = jump_upgrade;
    }

    public void setMob_drop_upgrade(boolean mob_drop_upgrade) {
        this.mob_drop_upgrade = mob_drop_upgrade;
    }

    public void setMob_exp_upgrade(boolean mob_exp_upgrade) {
        this.mob_exp_upgrade = mob_exp_upgrade;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public boolean isSpeed_upgrade() {
        return speed_upgrade;
    }

    public boolean isJump_upgrade() {
        return jump_upgrade;
    }

    public boolean isMob_drop_upgrade() {
        return mob_drop_upgrade;
    }

    public boolean isMob_exp_upgrade() {
        return mob_exp_upgrade;
    }

    public int getPoints() {
        return points;
    }

    public int getLevel() {
        return level;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setIron_blocks(int iron_blocks) {
        this.iron_blocks = iron_blocks;
    }

    public void setGold_blocks(int gold_blocks) {
        this.gold_blocks = gold_blocks;
    }

    public void setDiamond_blocks(int diamond_blocks) {
        this.diamond_blocks = diamond_blocks;
    }

    public void setEmerald_blocks(int emerald_blocks) {
        this.emerald_blocks = emerald_blocks;
    }

    public void setNetherite_blocks(int netherite_blocks) {
        this.netherite_blocks = netherite_blocks;
    }

    public void setBeacons(int beacons) {
        this.beacons = beacons;
    }

    public int getIron_blocks() {
        return iron_blocks;
    }

    public int getGold_blocks() {
        return gold_blocks;
    }

    public int getDiamond_blocks() {
        return diamond_blocks;
    }

    public int getEmerald_blocks() {
        return emerald_blocks;
    }

    public int getNetherite_blocks() {
        return netherite_blocks;
    }

    public int getBeacons() {
        return beacons;
    }

    public List<String> getBanned_players() {
        return banned_players;
    }

    public boolean isBanned(String banned_player_name)
    {
        if(banned_players.contains(banned_player_name.toLowerCase()))
        {
            return true;
        }
        return false;
    }

    public void unbanPlayer(String unbanned)
    {
        banned_players.remove(unbanned);
    }

    public void banPlayer(String banned)
    {
        if(!banned_players.contains(banned))
        {
            banned_players.add(banned);
        }
    }

    public boolean isClosed()
    {
        return closed;
    }

    public void setClosed(boolean closed)
    {
        this.closed = closed;
    }

    public PlotSettings getPlotSettings()
    {
        return plotSettings;
    }

    public VisitorsSettings getVisitorsSettings()
    {
        return visitorsSettings;
    }

    public String getPlot_name()
    {
        return plot_name;
    }

    public List<String> getMembers()
    {
        return members;
    }

    public void addMember(String new_member)
    {
        members.add(new_member);
    }

    public void removeMember(String new_member)
    {
        members.remove(new_member);
    }

    public boolean isMember(Player player)
    {
        for(String member : members)
        {
            if(member.equals(player.getName()))
            {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public Plot getPlotAt(Location loc)
    {
        boolean isPlot = false;
        int x = location.getBlockX(); int z = location.getBlockZ();
        int blockX = loc.getBlockX();
        int blockZ = loc.getBlockZ();

        if((blockX >= x && blockX <= x + size) && (blockZ >= z && blockZ <= z + size)) {
            isPlot = true;
        }
        else if((blockX <= x && blockX >= x - size) && (blockZ <= z && blockZ >= z - size)) {
            isPlot = true;
        }
        else if((blockX >= x && blockX <= x + size) && (blockZ <= z && blockZ >= z - size)) {
            isPlot = true;
        }
        else if((blockX <= x && blockX >= x - size) && (blockZ >= z && blockZ <= z + size)) {
            isPlot = true;
        }

        if(isPlot)
        {
            return this;
        }

        return null;
    }

    @Nullable
    public Plot getPlotAt(Location loc, int custom_size)
    {
        boolean isPlot = false;
        int x = location.getBlockX(); int z = location.getBlockZ();
        int blockX = loc.getBlockX();
        int blockZ = loc.getBlockZ();

        if((blockX >= x && blockX <= x + custom_size) && (blockZ >= z && blockZ <= z + custom_size)) {
            isPlot = true;
        }
        else if((blockX <= x && blockX >= x - custom_size) && (blockZ <= z && blockZ >= z - custom_size)) {
            isPlot = true;
        }
        else if((blockX >= x && blockX <= x + custom_size) && (blockZ <= z && blockZ >= z - custom_size)) {
            isPlot = true;
        }
        else if((blockX <= x && blockX >= x - custom_size) && (blockZ >= z && blockZ <= z + custom_size)) {
            isPlot = true;
        }

        if(isPlot)
        {
            return this;
        }

        return null;
    }

    public void displayPlotBorder(Player player)
    {
        PlotBorder plotBorder = new PlotBorder();
        if(player.getWorld().getName().equalsIgnoreCase(location.getWorld().getName()))
        {
            plotBorder.displayBorder(player, location, size);
        }
    }

    public Location getLocation()
    {
        return location;
    }

    public String getOwner()
    {
        return owner;
    }

}
