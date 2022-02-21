package eu.owncraft.plots.config;

import eu.owncraft.plots.OwnPlots;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private final OwnPlots plugin;
    private FileConfiguration english;
    private FileConfiguration polish;
    private File english_file;
    private File polish_file;

    public FileManager(OwnPlots plugin)
    {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        createConfigs();
    }

    public FileConfiguration getEnglish() {
        return english;
    }

    public FileConfiguration getPolish() {
        return polish;
    }

    private void createConfigs()
    {
        polish_file = new File(plugin.getDataFolder() + File.separator + "language", "polish.yml");
        if(!polish_file.exists())
        {
            polish_file.getParentFile().mkdir();
            plugin.saveResource("polish.yml", false);
            File createdFile = new File(plugin.getDataFolder(), "polish.yml");
            createdFile.renameTo(new File(plugin.getDataFolder() + File.separator + "language" + File.separator + "polish.yml"));
        }

        english_file = new File(plugin.getDataFolder() + File.separator + "language", "english.yml");
        if(!english_file.exists())
        {
            english_file.getParentFile().mkdir();
            plugin.saveResource("english.yml", false);
            File createdFile = new File(plugin.getDataFolder(), "english.yml");
            createdFile.renameTo(new File(plugin.getDataFolder() + File.separator + "language" + File.separator + "english.yml"));
        }

        polish = new YamlConfiguration();
        english = new YamlConfiguration();

        try
        {
            polish.load(polish_file);
            english.load(english_file);
        }
        catch(IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }

    }

    public File getFile(String language)
    {
        switch(language)
        {
            case "polish":
                return polish_file;
            default:
                return english_file;
        }
    }

}