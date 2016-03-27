package net.teraoctet.genesys.parcel;

import java.util.Optional;
import static net.teraoctet.genesys.Genesys.parcelManager;
import net.teraoctet.genesys.utils.GData;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.INFO_PARCEL;
import static net.teraoctet.genesys.utils.MessageManager.MISSING_BALANCE;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.PARCEL_SECURE;
import static net.teraoctet.genesys.utils.MessageManager.PROTECT_NO_ENTER;
import static net.teraoctet.genesys.utils.MessageManager.PROTECT_NO_FLY;
import net.teraoctet.genesys.player.GPlayer;
import org.spongepowered.api.block.BlockSnapshot;
import static org.spongepowered.api.block.BlockTypes.AIR;
import static org.spongepowered.api.block.BlockTypes.FIRE;
import static org.spongepowered.api.block.BlockTypes.FLOWING_LAVA;
import static org.spongepowered.api.block.BlockTypes.FLOWING_WATER;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.explosive.Explosive;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.event.world.ExplosionEvent;
import static org.spongepowered.api.item.ItemTypes.WOODEN_SHOVEL;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.explosion.Explosion;

public class ParcelListener {
        
    public ParcelListener() {}

    @Listener
    @SuppressWarnings("null")
    public void onInteractBlock(InteractBlockEvent  event){
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        BlockSnapshot b = event.getTargetBlock();
        Location loc = b.getLocation().get();
        Optional<ItemStack> itemInHand = player.getItemInHand();
        GParcel parcel = parcelManager.getParcel(loc);
        
        // Event click gauche -- saisie angle 1 parcel
        if (event instanceof InteractBlockEvent.Primary){
            if(itemInHand.isPresent()){
                if(itemInHand.get().getItem() == WOODEN_SHOVEL){
                    if (parcel != null) {
                        player.sendMessage(ChatTypes.CHAT,INFO_PARCEL(player,parcel.getNameAllowed(),parcel.getNameOwner(),parcel.getName()));
                        event.setCancelled(true);
                        return;
                    } else {
                        ParcelManager parcelPlayer = ParcelManager.getSett(player);
                        parcelPlayer.setBorder(1, loc);
                        player.sendMessage(ChatTypes.CHAT,Text.of(TextColors.GREEN, "Angle1 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                        event.setCancelled(true);
                        return;
                    }
                } 
            }
        }
        
        // Event click droit -- saisie angle 2 parcel
	if (event instanceof InteractBlockEvent.Secondary){
            if(itemInHand.isPresent()){
                if(itemInHand.get().getItem() == WOODEN_SHOVEL){
                    if (parcel != null) {
                        player.sendMessage(ChatTypes.CHAT,INFO_PARCEL(player,parcel.getNameAllowed(),parcel.getNameOwner(),parcel.getName()));
                        event.setCancelled(true);
                        return;
                    } else {
                        ParcelManager parcelPlayer = ParcelManager.getSett(player);
                        parcelPlayer.setBorder(2, loc);
                        player.sendMessage(ChatTypes.CHAT,Text.of(TextColors.GREEN, "Angle2 : ", TextColors.YELLOW, String.format("%d %d %d", new Object[] { loc.getBlockX(), loc.getBlockY(), loc.getBlockZ() })));
                        event.setCancelled(true);
                    }
                    return;
                } 
            }
        }
        
        // Interact sur sign "Parcelle a vendre"
        Optional<TileEntity> block = loc.getTileEntity();
        if (block.isPresent()) {
            TileEntity tile=block.get();
            if (tile instanceof Sign) {
                if (event instanceof InteractBlockEvent.Secondary){
                    Sign sign=(Sign)tile;
                    Optional<SignData> optional=sign.getOrCreate(SignData.class);
                    if (optional.isPresent()) {
                        SignData offering = optional.get();
                        Text txt1 = offering.lines().get(0);
                        //Text txt2 = offering.lines().get(1);
                        if (txt1.equals(MESSAGE("&1PARCELLE A VENDRE"))){
                            int cout = Integer.valueOf(Text.of(offering.getValue(Keys.SIGN_LINES).get().get(2)).toPlain());
                            if (gplayer.getMoney()< cout){
                                player.sendMessage(ChatTypes.CHAT,MISSING_BALANCE());
                            }
                            parcel = parcelManager.getParcel(Text.of(offering.getValue(Keys.SIGN_LINES).get().get(1)).toPlain());
                            if (!parcel.getUuidOwner().contains("ADMIN")){
                                GPlayer vendeur = getGPlayer(parcel.getUuidOwner());
                                vendeur.creditMoney(cout);
                                vendeur.sendMessage(MESSAGE("&6" + player.getName() + " &7vient d'acheter votre parcelle"));
                                vendeur.sendMessage(MESSAGE("&6" + cout + " emeraudes &7ont ete ajoute a votre compte"));
                                vendeur.sendMessage(MESSAGE("&6/bank &7pour consulter votre compte"));  
                            }
                            player.sendMessage(ChatTypes.CHAT,MESSAGE("&eVous etes maintenant le nouveau proprietaire de cette parcelle"));
                            parcel.delSale();
                            gplayer.debitMoney(cout);
                            parcel.setUuidOwner(gplayer.getUUID());
                            parcel.setUuidAllowed(gplayer.getUUID());
                            parcel.update();
                            GData.commit();
                            return;
                        } 
                    }
                }
            } 
        }
        
        // Interact sur autre block
        if (parcel != null && !parcel.getUuidAllowed().contains(player.getUniqueId().toString()) 
                && parcel.getNoInteract() == 1 && gplayer.getLevel() != 10) {
            player.sendMessage(ChatTypes.CHAT,PARCEL_SECURE());
            event.setCancelled(true);
        }
    }
    
    @Listener
    public void onPlayerMoveParcel(DisplaceEntityEvent.Move event) {
        if (event.getTargetEntity() instanceof Player){
            Player player = (Player) event.getTargetEntity();
            GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
            Location locTo = event.getToTransform().getLocation();
            Location locFrom = event.getFromTransform().getLocation();
            GParcel gparcel = parcelManager.getParcel(locTo);
            GParcel jail = parcelManager.getParcel(locFrom,true);

            if (gparcel != null && !gparcel.getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
                if (player.get(Keys.CAN_FLY).isPresent()) { 
                    if(player.get(Keys.CAN_FLY).get() == true && gparcel.getNoFly() == 1) {
                        player.offer(Keys.IS_FLYING, false); 
                        player.offer(Keys.CAN_FLY, false); 
                        player.sendMessage(ChatTypes.CHAT,PROTECT_NO_FLY());
                        event.setCancelled(true);
                    }

                    if(parcelManager.getParcel(locFrom) == null) {
                        player.sendMessage(ChatTypes.CHAT,MESSAGE(gparcel.getMessage(),player));
                        if(gparcel.getNoEnter() == 1 && !player.hasPermission("genesys.parcel.enter")) {
                            player.setLocation(locFrom);
                            player.sendMessage(ChatTypes.CHAT,PROTECT_NO_ENTER());
                            event.setCancelled(true);
                        }
                    }
                }
            }

            if (jail != null){
                if(parcelManager.getParcel(locTo, true) == null && gplayer.getJail().equalsIgnoreCase(jail.getName())) {
                    player.setLocation(locFrom);
                    player.sendMessage(ChatTypes.CHAT,PROTECT_NO_ENTER());
                }
            }
        }
    }
    
    @Listener
    public void onPlayerSendCommand(SendCommandEvent event) {
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        Location loc = player.getLocation();
        GParcel gparcel = parcelManager.getParcel(loc);
        if (gparcel != null && !gparcel.getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
            player.sendMessage(ChatTypes.CHAT,PARCEL_SECURE());
            event.setCancelled(true);
        }
        
    }
    
    @Listener
    public void onBreakBlock(ChangeBlockEvent.Break event) {
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
    
        GParcel gparcel = parcelManager.getParcel(loc);
        if (gparcel != null && gparcel.getNoBreak() == 1 && !gparcel.getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
            player.sendMessage(ChatTypes.CHAT,PARCEL_SECURE());
            event.setCancelled(true);
        }
    }
    
    @Listener
    public void onPlaceBlock(ChangeBlockEvent.Place event) {
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
        
        GParcel gparcel = parcelManager.getParcel(loc);
        if (gparcel != null && gparcel.getNoBuild() == 1 && !gparcel.getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
            player.sendMessage(ChatTypes.CHAT,PARCEL_SECURE());
            event.setCancelled(true);
        }
    }
    
    @Listener
    public void onFluidBlock(ChangeBlockEvent.Modify event) {
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
        
        GParcel gparcel = parcelManager.getParcel(loc);
        if (gparcel != null && !gparcel.getUuidAllowed().contains(player.getUniqueId().toString()) && gplayer.getLevel() != 10){
            player.sendMessage(ChatTypes.CHAT,PARCEL_SECURE());
            event.setCancelled(true);
        }
    }
    
    @Listener
    public void onBurningBlock(ChangeBlockEvent.Post event) {
        Transaction<BlockSnapshot> block = event.getTransactions().get(0);
        Optional<Location<World>> optLoc = block.getOriginal().getLocation();
        Location loc = optLoc.get();
        GParcel gparcel = parcelManager.getParcel(loc);
        if (gparcel != null){
            if(block.getFinal().getState().getType() == FIRE && gparcel.getNoFire() == 1){                
                event.setCancelled(true);   
                return;
            }
                        
            Optional<Player> optPlayer = event.getCause().first(Player.class);
            Optional<Explosive> optExplosive = event.getCause().first(Explosive.class);
            
            if(block.getFinal().getState().getType() == AIR && block.getOriginal().getState().getType() != FLOWING_LAVA && 
            block.getOriginal().getState().getType() != FLOWING_WATER && !optPlayer.isPresent() && !optExplosive.isPresent() && gparcel.getNoFire() == 1){
                event.setCancelled(true);        
            }
        }
    }
        
    @Listener
    public void onExplosion(ExplosionEvent.Pre event) {
        Explosion explosion = event.getExplosion();
        Location loc = new Location(event.getTargetWorld(),explosion.getOrigin());
        GParcel gparcel = parcelManager.getParcel(loc);
        if (gparcel != null){
            if (gparcel.getNoTNT() == 1){ event.setCancelled(true);;}
        }
    }
}
