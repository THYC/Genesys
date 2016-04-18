package net.teraoctet.genesys.world;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import static net.teraoctet.genesys.Genesys.plugin;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;

public class Reforestation {

    private final Entity drop;
    private Task task = null;
        
    public Reforestation(final Entity drop) { 
        this.drop = drop;
    }
    
    public void run(){
        Scheduler scheduler = getGame().getScheduler();
        Task.Builder taskBuilder = scheduler.createTaskBuilder();
            
        task = taskBuilder
            .execute(() -> {
                Optional<ItemStackSnapshot> item = drop.get(Keys.REPRESENTED_ITEM);

                if (item.isPresent()) {
                    ItemStack itemStack = item.get().createStack();
                    BlockType blockType = item.get().getType().getBlock().get(); 
                    
                    //itemStack.offer(Keys.TREE_TYPE, TreeTypes.SPRUCE);
                    //BlockState blockState = BlockTypes.SAPLING.getDefaultState().with(Keys.TREE_TYPE, blockType.getId());                           
                    BlockState blockState = blockType.getDefaultState();           
                    Location loc = getLocSapling(drop.getLocation());
                    if (loc != null){
                        loc.add(0,1,0).setBlock(blockState);
                        drop.remove();
                    }
                }
            })
                .async().delay(10, TimeUnit.SECONDS)
                .name("Reforestation")
                .submit(plugin);
    }
        
    private Location getLocSapling(Location dropSapling){
        Location locSpawn = dropSapling;
        
        if (locSpawn.getBlock().getType().equals(BlockTypes.AIR) || locSpawn.getBlock().getType().equals(BlockTypes.LEAVES) || locSpawn.getBlock().getType().equals(BlockTypes.LEAVES2)){
            locSpawn = getLocSapling(locSpawn.add(0, -1, 0));
            return locSpawn;
        }  
        if (dropSapling.getBlock().getType().equals(BlockTypes.DIRT) || dropSapling.getBlock().getType().equals(BlockTypes.GRASS)){
            return locSpawn;
        } else {
            locSpawn = null;
            return locSpawn;
        }
    }
    
}
