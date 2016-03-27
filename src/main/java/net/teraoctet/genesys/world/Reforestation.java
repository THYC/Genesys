package net.teraoctet.genesys.world;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import net.teraoctet.genesys.Genesys;
//import static net.teraoctet.genesys.Genesys.getPlugin;
import org.spongepowered.api.Sponge;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.trait.BlockTrait;
import org.spongepowered.api.data.key.Keys;
import static org.spongepowered.api.data.manipulator.catalog.CatalogBlockData.TREE_DATA;
import org.spongepowered.api.data.manipulator.mutable.RepresentedItemData;
import org.spongepowered.api.data.type.PlantTypes;
import org.spongepowered.api.data.type.TreeTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Item;
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
                    Object trait = blockType.getTraits().toArray()[1];
                    
                    //Optionnal<TREE_DATA> treeData = item.get().getManipulators();
                    /*if (trait.toString().equalsIgnoreCase("oak")) {
                        BlockState blockState = BlockTypes.SAPLING.getDefaultState().with(Keys.TREE_TYPE, TreeTypes.OAK);    
                    } else if (trait.toString().equalsIgnoreCase("spruce")) {
		damage = "1";
		itemStack.offer(Keys.TREE_TYPE, TreeTypes.SPRUCE);*/
                
                    //BlockType blockType = item.get().getType().getBlock().get(); 
                    //item.get().getType().getBlock().get().
                    //BlockState blockState = BlockTypes.SAPLING.getDefaultState().with(Keys.TREE_TYPE, blockType.getId());                           
                    BlockState blockState = blockType.getDefaultState();
                                       
                    //BlockState state = blockSnapshotTransaction.getOriginal().getState();
                            //ItemStack.Builder builder = getGame().getRegistry().createBuilder(ItemStack.Builder.class);
                            //ItemStack itemStack = builder.itemType(blockState.getType().getDefaultState().getType().getItem().get()).build();
                            //itemStack.offer(Keys.TREE_TYPE, blockState.get(Keys.TREE_TYPE).get());
                            //Entity entity = cause.getWorld().createEntity(EntityTypes.ITEM, blockSnapshotTransaction.getOriginal().getPosition()).get(); // 'cause' is the player
                            //entity.offer(Keys.REPRESENTED_ITEM, itemStack.createSnapshot());
                    Logger.getLogger("INFO").info(drop.getKeys().toString());
                    Logger.getLogger("INFO").info(drop.getValue(Keys.ITEM_BLOCKSTATE).get().toString());
                    Logger.getLogger("INFO").info(drop.getValue(Keys.ITEM_BLOCKSTATE).toString());
                    
                    Location loc = getLocSapling(drop.getLocation());
                    if (loc != null){
                        loc.add(0,1,0).setBlock(blockState);
                        //loc.add(0,1,0).setBlockType(bs);
                        //loc.add(0,1,0).setBlock(itemStack.);
                        drop.remove();
                    }
                    
                }
                
                /*BlockState state = blockSnapshotTransaction.getOriginal().getState();
Object trait = state.getTraitValues().toArray()[1];
Location loc = blockSnapshotTransaction.getOriginal().getLocation().get();
final ItemStackBuilder builder = getGame().getRegistry().createItemBuilder();
ItemStack itemStack = null;
if (state.getType() == BlockTypes.LOG) {
	itemStack = builder.itemType(ItemTypes.LOG).build();
	id = "minecraft:log";
	if (trait.toString().equalsIgnoreCase("oak")) {
		damage = "0";
		itemStack.offer(Keys.TREE_TYPE, TreeTypes.OAK);
	} else if (trait.toString().equalsIgnoreCase("spruce")) {
		damage = "1";
		itemStack.offer(Keys.TREE_TYPE, TreeTypes.SPRUCE);
	} else if (trait.toString().equalsIgnoreCase("birch")) {
		damage = "2";
		itemStack.offer(Keys.TREE_TYPE, TreeTypes.BIRCH);
	} else if (trait.toString().equalsIgnoreCase("jungle")) {
		damage = "3";
		itemStack.offer(Keys.TREE_TYPE, TreeTypes.JUNGLE);
	}
} else if (state.getType() == BlockTypes.LOG2) {
	id = "minecraft:log2";
	itemStack = builder.itemType(ItemTypes.LOG2).build();
	if (trait.toString().equalsIgnoreCase("acacia")) {
		damage = "0";
		itemStack.offer(Keys.TREE_TYPE, TreeTypes.ACACIA);
	} else if (trait.toString().equalsIgnoreCase("dark_oak")) {
		damage = "1";
		itemStack.offer(Keys.TREE_TYPE, TreeTypes.DARK_OAK);
	}*//*
}

Entity entity = cause.getWorld().createEntity(EntityTypes.ITEM, blockSnapshotTransaction.getOriginal().getPosition()).get(); // 'cause' is the player
entity.offer(Keys.REPRESENTED_ITEM, itemStack.createSnapshot());
cause.getWorld().spawnEntity(entity, Cause.empty());*/

            })
                .async().delay(10, TimeUnit.SECONDS)
                .name("Reforestation")
                .submit(getGame());
/*BlockState bs = blockSnapshotTransaction.getOriginal().getState();
                            Entity entity = cause.getWorld().createEntity(EntityTypes.ITEM, blockSnapshotTransaction.getOriginal().getPosition()).get();
                            entity.offer(Keys.REPRESENTED_ITEM, bs.getType().getItem().get().getTemplate().copy());
                            cause.getWorld().spawnEntity(entity, Cause.of(getPlugin()));*/
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
