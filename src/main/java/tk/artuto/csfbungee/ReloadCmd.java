package tk.artuto.csfbungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCmd extends Command
{
    private final CSF plugin;

    ReloadCmd(CSF plugin)
    {
        super("consolespamfixbungee", "csf.admin", "csfb");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if(args.length < 1)
        {
            sender.sendMessage(new TextComponent(ChatColor.RED + "You must specify a subcommand!\n" +
                    ChatColor.GREEN + "Valid subcommands are: reload"));
            return;
        }

        if(!(args[0].equals("reload")))
        {
            sender.sendMessage(new TextComponent(ChatColor.RED + "You must specify a valid subcommand!\n" +
                    ChatColor.GREEN + "Valid subcommands are: reload"));
            return;
        }

        try
        {
            plugin.reloadConfig();
            ((LogFilter) plugin.getLogger().getFilter()).updateIgnoredLines(plugin.getConfig().getIgnoredLines());
            sender.sendMessage(new TextComponent(ChatColor.GREEN + "Successfully updated ignored lines"));
        }
        catch(Exception e)
        {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Error whilst updating ignored lines. Check the console" +
                    " for more information."));
            System.err.println("[ConsoleSpamFixBungee] Could not update ignored lines: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
