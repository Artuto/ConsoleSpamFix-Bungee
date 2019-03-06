package tk.artuto.csfbungee;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.LogRecord;
import java.util.logging.Filter;
import java.util.logging.Logger;

public class LogFilter implements CustomFilter
{
    private final Logger log;
    private Set<String> ignored;

    LogFilter(CSF plugin)
    {
        this.log = plugin.getProxy().getLogger();
        this.ignored = new HashSet<>(plugin.getConfig().getIgnoredLines());
    }

    @Override
    public boolean isLoggable(LogRecord record)
    {
        String[] message = record.getMessage().split("\\s+");
        boolean found = false;

        for(int i = 0; i < message.length; i++)
        {
            if(found)
                break;

            found = ignored.contains(message[i]);
        }

        return !(found);
    }

    @Override
    public Filter inject()
    {
        log.setFilter(this);
        return log.getFilter();
    }

    void updateIgnoredLines(List<String> newList)
    {
        ignored.clear();
        ignored.addAll(newList);
    }
}
