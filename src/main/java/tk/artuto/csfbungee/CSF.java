package tk.artuto.csfbungee;

import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;

public class CSF extends Plugin
{
    private Config config;

    @Override
    public void onEnable()
    {
        getLogger().info("Loading config...");
        try {config = new Config(this);}
        catch(IOException e)
        {
            getLogger().severe("Could not load config file: " + e.getMessage());
            e.printStackTrace();
            onDisable();
            return;
        }

        getLogger().info("Injecting filters...");
        try
        {
            // Inject filter in proxy logger
            LogFilter filter = new LogFilter(this);
            filter.inject();

            // Inject filter on every plugins
            for(Plugin plugin : getProxy().getPluginManager().getPlugins())
                filter.inject(plugin.getLogger());
         }
        catch(Exception e)
        {
            getLogger().severe("Could not inject filters: " + e.getMessage());
            e.printStackTrace();
            onDisable();
            return;
        }

        getLogger().info("Successfully injected filters.");

        getProxy().getPluginManager().registerCommand(this, new ReloadCmd(this));
    }

    Config getConfig()
    {
        return config;
    }

    void reloadConfig() throws IOException
    {
        this.config = new Config(this);
    }
}
