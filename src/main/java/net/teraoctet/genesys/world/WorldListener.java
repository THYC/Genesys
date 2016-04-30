package net.teraoctet.genesys.world;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

//import static net.teraoctet.genesys.Genesys.getPlugin;
import static net.teraoctet.genesys.Genesys.plotManager;
import net.teraoctet.genesys.plot.GPlot;
import net.teraoctet.genesys.utils.DeSerialize;
import net.teraoctet.genesys.utils.GData;
import net.teraoctet.genesys.player.GPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.explosive.PrimedTNT;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.monster.Creeper;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.SpongeEventFactory;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.world.ExplosionEvent;
import static org.spongepowered.api.item.ItemTypes.DIAMOND_AXE;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.explosion.Explosion;

public class WorldListener {
    
    private final List<ChangeBlockEvent.Break> firedEvents = Lists.newArrayList();
    
    @Listener
    public void onEntitySpawn(SpawnEntityEvent event) {
    	
	List<Entity> entities = event.getEntities();
	
        for (Entity entity : entities)
        {
            GWorld world = GData.getWorld(entity.getWorld().getName());
            if(world == null) return;
            if(!world.getAnimal() && entity instanceof Animal || entity.getType().equals(EntityTypes.BAT)) { event.setCancelled(true);return;}
            if(!world.getMonster() && entity instanceof Monster) {event.setCancelled(true);return;}
    	}	
    }  
    
    @Listener 
    public void onTeleportEvent(DisplaceEntityEvent.Teleport event){ 
        
        System.out.println("################### tp ######################");
                
 	Location<World> fromWorld = event.getFromTransform().getLocation();
        Location<World> toWorld = event.getToTransform().getLocation();
        Player player = (Player) event.getTargetEntity();
        
        if(fromWorld.getExtent() != toWorld.getExtent()){
            spawnParticles(fromWorld, 0.5, true); 
            spawnParticles(fromWorld.getRelative(Direction.UP), 0.5, true); 
            spawnParticles(toWorld, 1.0, false); 
            spawnParticles(toWorld.getRelative(Direction.UP), 1.0, false);
            
            String world = fromWorld.getExtent().getName();
            double x = fromWorld.getBlockX();
            double y = fromWorld.getBlockY();
            double z = fromWorld.getBlockZ();
            String lastposition = DeSerialize.location(world, x, y, z);
            GPlayer gplayer = GData.getGPlayer(player.getUniqueId().toString());
            gplayer.setLastposition(lastposition);
            gplayer.update();
        }
       
        /*player.sendTitle(Titles.of(Texts.of(TextColors.DARK_GREEN, toWorld.getExtent().getName()), 
                Texts.of(TextColors.AQUA, "x: ", toWorld.getExtent().getSpawnLocation().getBlockX(), ", y: ", 
                        toWorld.getExtent().getSpawnLocation().getBlockY(),", z: ", toWorld.getExtent().getSpawnLocation().getBlockZ()))); */
    } 
    
    public static void spawnParticles(Location<World> location, double range, boolean sub){
        
        Random random = new Random(); 
 		 
        for(int i = 0; i < 5; i++){ 
            double v1 = 0.0 + (range - 0.0) * random.nextDouble(); 
            double v2 = 0.0 + (range - 0.0) * random.nextDouble(); 
            double v3 = 0.0 + (range - 0.0) * random.nextDouble(); 


            location.getExtent().spawnParticles(getGame().getRegistry().createBuilder(ParticleEffect.Builder.class) 
                            .type(ParticleTypes.EXPLOSION_NORMAL).motion(Vector3d.UP).count(3).build(), location.getPosition().add(v3,v1,v2)); 
            location.getExtent().spawnParticles(getGame().getRegistry().createBuilder(ParticleEffect.Builder.class) 
                            .type(ParticleTypes.EXPLOSION_NORMAL).motion(Vector3d.UP).count(3).build(), location.getPosition().add(0,v1,0)); 
            if(sub){ 
                    location.getExtent().spawnParticles(getGame().getRegistry().createBuilder(ParticleEffect.Builder.class) 
                                    .type(ParticleTypes.EXPLOSION_NORMAL).motion(Vector3d.UP).count(3).build(), location.getPosition().sub(v1,v2,v3)); 
                    location.getExtent().spawnParticles(getGame().getRegistry().createBuilder(ParticleEffect.Builder.class) 
                                    .type(ParticleTypes.EXPLOSION_NORMAL).motion(Vector3d.UP).count(3).build(), location.getPosition().sub(0,v2,0)); 
            }else{ 
                    location.getExtent().spawnParticles(getGame().getRegistry().createBuilder(ParticleEffect.Builder.class) 
                                    .type(ParticleTypes.EXPLOSION_NORMAL).motion(Vector3d.UP).count(3).build(), location.getPosition().add(v3,v1,v1)); 
                    location.getExtent().spawnParticles(getGame().getRegistry().createBuilder(ParticleEffect.Builder.class) 
                                    .type(ParticleTypes.EXPLOSION_NORMAL).motion(Vector3d.UP).count(3).build(), location.getPosition().add(v2,v3,v2)); 
            } 
        } 
        
    }
    
    @Listener
    public void onTNTexplode(final ExplosionEvent.Detonate event){ 
        Explosion explosion = event.getExplosion();
        Location loc = new Location(event.getTargetWorld(),explosion.getOrigin());
        GPlot gplot = plotManager.getPlot(loc);
        if (gplot == null){
            if (event.getCause().first(PrimedTNT.class).isPresent()){
                event.setCancelled(true);
            }
        }
    }
        
    @Listener(order = Order.LAST) 
    public void onExplosion(final ExplosionEvent.Detonate event){ 
        Explosion explosion = event.getExplosion();
        Location loc = new Location(event.getTargetWorld(),explosion.getOrigin());
        GPlot gplot = plotManager.getPlot(loc);
        if (gplot == null){
            if (!event.getCause().first(PrimedTNT.class).isPresent()){
                List<Transaction<BlockSnapshot>> bs = event.getTransactions();
                Restore restore = new Restore(bs);
                restore.run();
            }
        }
    }
        
    @Listener 
    public void itemDrop(DropItemEvent.Destruct event){ 
        if (event.getCause().first(Creeper.class).isPresent()){
            event.setCancelled(true);
        }
    } 
    
    @Listener 
    public void saplingDrop(DropItemEvent.Dispense event){ 
        Entity drop = event.getEntities().get(0);
 
        Optional<ItemStackSnapshot> item = drop.get(Keys.REPRESENTED_ITEM);
        if (item.get().getType().getBlock().isPresent()) {
                       
            if (item.get().getType().getBlock().get().equals(BlockTypes.SAPLING)){
                
                //Logger.getLogger("INFO").info(drop.getKeys().toString());
                //Logger.getLogger("INFO").info(drop.getValue(Keys.TREE_TYPE).get().toString());
                                
                Reforestation reforestation = new Reforestation(drop);
                reforestation.run();
            }    
        }
    }
        
    @Listener
    public void treeBreak(ChangeBlockEvent.Break breakEvent) throws Exception {
        Logger.getLogger("INFO").info(breakEvent.getTransactions().get(0).getFinal().get(Keys.TREE_TYPE).toString());
        if (!firedEvents.contains(breakEvent) && breakEvent.getCause().first(Player.class).isPresent() && 
                !breakEvent.isCancelled() && breakEvent.getTransactions().size() == 1 &&
                TreeDetector.isWood(breakEvent.getTransactions().get(0).getOriginal())) {
            
            Player player = breakEvent.getCause().first(Player.class).get();
            Optional<ItemStack> inHand = player.getItemInHand();
            
            if (inHand.isPresent() && inHand.get().getItem() == DIAMOND_AXE) {
                TreeDetector tree = new TreeDetector(breakEvent.getTransactions().get(0).getOriginal());
                List<Transaction<BlockSnapshot>> transactions = new ArrayList<>(tree.getWoodLocations().size());
                
                tree.getWoodLocations().forEach(blockSnapshot -> {
                    if (!blockSnapshot.equals(breakEvent.getTransactions().get(0).getOriginal())) {
                        BlockState newState = BlockTypes.AIR.getDefaultState();
                        BlockSnapshot newSnapshot = blockSnapshot.withState(newState).withLocation(new Location<>(player.getWorld(), blockSnapshot.getPosition()));
                        Transaction<BlockSnapshot> t = new Transaction<>(blockSnapshot, newSnapshot);
                        transactions.add(t);
                    }
                });
                
                transactions.forEach((Transaction<BlockSnapshot> blockSnapshotTransaction) -> {
                    ChangeBlockEvent.Break event = SpongeEventFactory.createChangeBlockEventBreak(breakEvent.getCause(),
                            player.getWorld(), Lists.newArrayList(blockSnapshotTransaction));
                    firedEvents.add(event);
                    
                    if (!getGame().getEventManager().post(event)) {
                        if (player.getGameModeData().get(Keys.GAME_MODE).get() != GameModes.CREATIVE) {
                            BlockState bs = blockSnapshotTransaction.getOriginal().getState();
                            Entity entity = player.getWorld().createEntity(EntityTypes.ITEM, blockSnapshotTransaction.getOriginal().getPosition()).get();
                            entity.offer(Keys.REPRESENTED_ITEM, bs.getType().getItem().get().getTemplate().copy());
                            player.getWorld().spawnEntity(entity, breakEvent.getCause());
                        }
                        blockSnapshotTransaction.getFinal().restore(true, true);
                    }
                });
                firedEvents.clear();
            }
        }
    }
}
