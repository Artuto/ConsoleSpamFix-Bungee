package tk.artuto.csfbungee;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.LogRecord;
import java.util.logging.Filter;
import java.util.logging.Logger;

public class LogFilter implements CustomFilter
{
    private Logger log;
    private Set<String> ignored;

    LogFilter(CSF plugin)
    {
        this.log = plugin.getProxy().getLogger();
        this.ignored = new HashSet<>(plugin.getConfig().getIgnoredLines());
    }

    @Override
    public boolean isLoggable(LogRecord record)
    {
        boolean found = false;

        for(String line : ignored)
        {
            if(found)
                break;

            found = record.getMessage().toLowerCase().startsWith(line.toLowerCase());
        }

        return !(found);
    }

    Filter inject(Logger log)
    {
        this.log = log;
        return inject();
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
