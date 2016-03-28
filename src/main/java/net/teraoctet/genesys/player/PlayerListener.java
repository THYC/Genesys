package net.teraoctet.genesys.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.addGPlayer;
import static net.teraoctet.genesys.utils.GData.commit;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.GData.getUUID;
import static net.teraoctet.genesys.utils.GData.getWorld;
import static net.teraoctet.genesys.utils.GData.removeGPlayer;
import static net.teraoctet.genesys.utils.GData.removeUUID;
import static net.teraoctet.genesys.utils.MessageManager.FIRSTJOIN_MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.JOIN_MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.NAME_CHANGE;
import net.teraoctet.genesys.utils.GServer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import net.teraoctet.genesys.utils.Permissions;
import net.teraoctet.genesys.utils.DeSerialize;

import org.spongepowered.api.block.BlockTypes;
import static org.spongepowered.api.block.BlockTypes.AIR;
import static org.spongepowered.api.block.BlockTypes.STANDING_SIGN;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Last;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.entity.HumanInventory;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.type.GridInventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

public class PlayerListener {
    
    public static ArrayList<Inventory> inventorys = new ArrayList<>();
    //public PlayerListener(Genesys genesys) { this.genesys = genesys;}
    public PlayerListener() {}
    
    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
    	
    	Player player = event.getTargetEntity();
        String uuid = player.getUniqueId().toString();
        String name = player.getName().toLowerCase();
    	event.setMessage(JOIN_MESSAGE(player));
    	
    	GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        if(gplayer != null) {
            addGPlayer(gplayer.getUUID(), gplayer);
        } else {
            gplayer = new GPlayer(uuid, 0, name, "", 0, "", 20, "", "", System.currentTimeMillis(), System.currentTimeMillis());
            gplayer.insert();
            commit();
            event.setMessage(FIRSTJOIN_MESSAGE(player));         		
        } 
	
        GPlayer player_uuid = getGPlayer(uuid);
        GPlayer player_name = getGPlayer(getUUID(name));
        
        if(player_uuid != null && player_name == null) {
            removeGPlayer(player_uuid.getUUID());
            removeUUID(player_uuid.getName());
            player_uuid.setName(name);
            player_uuid.update();
            GServer.broadcast(NAME_CHANGE(player));
        }
    }
    
    @Listener
    public void onMessage(MessageChannelEvent.Chat event, @First Player player) {
        String smessage = event.getMessage().toPlain();
        smessage = smessage.replaceAll("<" + player.getName() + "> ", "");
        Text message = MESSAGE(Permissions.getPrefix(player) + "&a[" + player.getName() + "] &7" + smessage);
        Text prefixWorld = MESSAGE(getWorld(player.getWorld().getName()).getPrefix()) ;
        Text newMessage = prefixWorld.builder().append(message).build();
        event.setMessage(newMessage);
    }
    
    @Listener
    public void onEntityDeath(DestructEntityEvent.Death event) {
        //try{
            //GServer.broadcast(event.getMessage().get());
        //} catch(Exception e){}
        
        /*Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (optPlayer.isPresent()) {
            Player player = optPlayer.get();
            // The player caused the entity to die
        } else {
            // The entity died from a non-player cause
        }*/
    }
    
    /*@Listener
    public void onDropItem(DropItemEvent.Destruct event) {
        boolean playerCausePresent = event.getCause().first(Player.class).isPresent();
        boolean damageCausePresent = event.getCause().first(DamageSource.class).isPresent();
        
        if (playerCausePresent && damageCausePresent){
            getGame().getServer().getBroadcastChannel().send(MESSAGE("Condition true"));
            List<EntitySnapshot> es = event.getEntitySnapshots();
            Player player = event.getCause().first(Player.class).get();
            
            Location location = player.getLocation().add(0, -2, 0);
            location.setBlockType(BlockTypes.CHEST);
            Optional<TileEntity> chestBlock = location.getTileEntity();
            
            //TileEntity tileChest = chestBlock.get();

            //Chest chest =(Chest)tileChest;
            //DataContainer dc = es.
            //chest.getInventory().getCarrier().get().setRawData(null);
        }
    }*/
        
    @Listener
    public void onPlayerDead(DestructEntityEvent.Death event, @Last Player player) {
        if(event.getTargetEntity() instanceof Player == false) return;
    	
    	String lastdead = DeSerialize.location(player.getLocation());
    	GPlayer gplayer = GData.getGPlayer(player.getUniqueId().toString());
    	gplayer.setLastdeath(lastdead);
        gplayer.setLastposition(lastdead);
    	gplayer.update();
             
        if (player.hasPermission("genesys.grave")) {
            
            Location location = player.getLocation().add(0, -2, 0);
            location.setBlockType(BlockTypes.CHEST);
            Optional<TileEntity> chestBlock = location.getTileEntity();
            
            TileEntity tileChest = chestBlock.get();
            Chest chest =(Chest)tileChest;
            
            
            HumanInventory inv = (HumanInventory)player.getInventory();
            Hotbar bar = inv.getHotbar();
            //bar.set(new SlotIndex(8), itemstack);
            
          

            //Inventory inventory = player.getInventory();
            //ItemStack stack = ...;
            //int index = 0;
            //for(Inventory slot : inventory.slots()) {
                //chest.getInventory().offer(slot.peek().get());
            //}
                
            
            for (int i = 0, len = bar.size(); i < len; i++) {
                Optional<ItemStack> optStack = bar.peek(i);
                if (optStack.isPresent()) {
                    chest.getInventory().query(GridInventory.class).offer(optStack.get());
                    //chest.getInventory().set(optStack.get());
                }
            }
            
            location = controlBlock(location);
            location.setBlockType(STANDING_SIGN);  
            Optional<TileEntity> signBlock = location.getTileEntity();
            TileEntity tileSign = signBlock.get();
            Sign sign=(Sign)tileSign;
            Optional<SignData> opSign = sign.getOrCreate(SignData.class);
                
            SignData signData = opSign.get();
            List<Text> rip = new ArrayList<>();
            rip.add(MESSAGE("&5+++++++++++++"));
            rip.add(MESSAGE("&o&5 Repose En Paix"));
            rip.add(MESSAGE("&5&l" + player.getName()));
            rip.add(MESSAGE("&5+++++++++++++"));
            signData.set(Keys.SIGN_LINES,rip );
            sign.offer(signData);
               
            /*Genesys.game.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
            {
                @Override
                public void run()
                {
                    nb.getLocation().getBlock().setType(Material.AIR);
                    player.sendMessage(formatMsg.format(conf.getStringYAML("messages.yml", "graveDespawn"),player));
                    if (GraveListener.inventorys.size() > 0)
                    {
                        inventorys.remove(0);
                    }
                }
            }*/
        
        }
    }
    
    public Location controlBlock(Location location){                
        if (location.getBlockType() != AIR){ location = location.add(0,+1,0);
            if (location.getBlockType() == AIR){ return location;}
            else { location = location.add(+1,-1,0);
                if (location.getBlockType() == AIR) { return location;}
                else { location = location.add(-1,0,+1);
                    if (location.getBlockType() == AIR) { return location;}
                    else { location = location.add(-1,0,-1);
                        if (location.getBlockType() == AIR) { return location;}
                        else { location = location.add(+1,0,-1);
                            if (location.getBlockType() == AIR) { return location;}
                            else { location = location.add(0,+1,+1);
                                location = controlBlock(location);
                                return location;
                            }
                        }
                    }
                }
            }
        }
        return location;
    }
}