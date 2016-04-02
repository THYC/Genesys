package net.teraoctet.genesys.world;

import java.util.List;
import java.util.concurrent.TimeUnit;
import static net.teraoctet.genesys.Genesys.thisGame;
//import static net.teraoctet.genesys.Genesys.getPlugin;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.scheduler.Scheduler;

public class Restore {
    
    private final List<Transaction<BlockSnapshot>> bs;
    private Integer i = 0;
    private Task task = null;
    
    public Restore(final List<Transaction<BlockSnapshot>> bs) { 
        this.bs = bs;
    }
    
    public void run(){
        Scheduler scheduler = getGame().getScheduler();
        Task.Builder taskBuilder = scheduler.createTaskBuilder();
            
        task = taskBuilder
                .execute(() -> {
                    try{ bs.get(i).getOriginal().restore(true, false);} catch(Exception e) {}
                    i++;
                    if(i==bs.size()-1){ task.cancel();}
        })
                /*.async()*/.delay(100, TimeUnit.MILLISECONDS)
                .interval(50, TimeUnit.MILLISECONDS)
                .name("Restore")
                .submit(thisGame());

    }
}
