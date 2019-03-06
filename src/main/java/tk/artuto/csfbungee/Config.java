package tk.artuto.csfbungee;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

class Config
{
    private final Configuration config;

    Config(CSF plugin) throws IOException
    {
        File dataFolder = plugin.getDataFolder();
        File file = new File(dataFolder, "config.yml");

        if(!(dataFolder.exists()))
            dataFolder.mkdir();
        if(!(file.exists()))
        {
            try(InputStream stream = plugin.getResourceAsStream("config.yml"))
            {Files.copy(stream, file.toPath());}
            catch(IOException e)
            {
                plugin.getLogger().severe("Could not load config file: " + e.getMessage());
                e.printStackTrace();
            }
        }

        this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

    List<String> getIgnoredLines()
    {
        return config.getStringList("ignored-lines");
    }
}
