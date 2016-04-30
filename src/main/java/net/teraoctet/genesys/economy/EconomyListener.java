package net.teraoctet.genesys.economy;

import java.io.IOException;
import java.util.Optional;
import static net.teraoctet.genesys.Genesys.bookManager;
import static net.teraoctet.genesys.Genesys.itemShopManager;
import net.teraoctet.genesys.player.GPlayer;
import net.teraoctet.genesys.utils.DeSerialize;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.event.block.InteractBlockEvent;

public class EconomyListener {
        
    public EconomyListener() {}

    @Listener
    public void onInteractShopRight(InteractEntityEvent.Secondary event, @First Player player){     
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        Entity entity = event.getTargetEntity();
        Location loc = entity.getLocation();
        if(entity.getType().getName().contains("itemframe") || entity.getType().getName().contains("armorstand")){
            if(!itemShopManager.hasShop(loc) && player.hasPermission("genesys.admin.shop") && gplayer.getLevel()==10){ 
                Optional<ItemStack> is = player.getItemInHand();
                if(is.isPresent()){
                    String locationString = DeSerialize.location(loc);
                    player.sendMessage(MESSAGE("\n\n"));
                    player.sendMessage(MESSAGE("&l&e-------------------------"));
                    player.sendMessage(MESSAGE("&7Voulez vous cr\351er un nouveau ItemShop ?"));
                    player.sendMessage(Text.builder("Clique ici pour lancer la cr\351ation d'un ItemShop").onClick(TextActions.runCommand("/shopcreate " + locationString)).color(TextColors.AQUA).build()); 
                    player.sendMessage(MESSAGE("&l&e-------------------------"));
                }
            }else{
                if(itemShopManager.hasShop(loc) && gplayer.getLevel()!=10){
                    Optional<ItemShop> is = itemShopManager.getItemShop(loc);
                    if(is.isPresent()){
                        ItemStack itemStack = is.get().getItemStack();
                        String locationString = DeSerialize.location(loc);
                        String name = "";
                        Optional<DisplayNameData> displayData = itemStack.get(DisplayNameData.class);
                        if(displayData.isPresent()){
                            name = displayData.get().displayName().get().toPlain();
                        }else{                          
                            name = Text.builder(itemStack.getTranslation()).build().toPlain();
                        }
                        EnchantmentData enchantmentData = itemStack.getOrCreate(EnchantmentData.class).get();
                        final ListValue<ItemEnchantment> enchantments = enchantmentData.enchantments();
                        String enchantment = "";
                        for (ItemEnchantment e : enchantments) {
                            enchantment = enchantment + e.getEnchantment().getId() + "\n";
                        }
                        enchantment = enchantment.replace("minecraft:", "");
                        BookView.Builder bv;
                        if(is.get().getTransactType().contains("buy")){
                            bv = BookView.builder()
                                    .author(Text.of(TextColors.GOLD, "genesys"))
                                    .title(Text.of(TextColors.GOLD, "ItemShop"));
                            Text text = Text.builder()
                                    .append(MESSAGE("&l&1   ---- ItemShop ----\n\n"))
                                    .append(MESSAGE("&l&0" + is.get().getTransactType() + " : &l&4" + name +"\n"))
                                    .append(MESSAGE("&o&7(" + itemStack.getItem().getName() + ")\n\n"))
                                    .append(MESSAGE("&l&0Prix : &l&4" + is.get().getPrice() + " Emeraude(s)\n\n"))
                                    .append(MESSAGE("&1Cliquer ici pour confirmer la transaction\n"))
                                    .onClick(TextActions.runCommand("/shopsell " + locationString)).color(TextColors.AQUA)
                                    .build();
                            bv.addPage(text);
                        } else {
                            bv = BookView.builder()
                                    .author(Text.of(TextColors.GOLD, "genesys"))
                                    .title(Text.of(TextColors.GOLD, "ItemShop"));
                            Text text = Text.builder()
                                    .append(MESSAGE("&l&1   ---- ItemShop ----\n\n"))
                                    .append(MESSAGE("&l&0" + is.get().getTransactType() + " : &l&4" + name +"\n"))
                                    .append(MESSAGE("&o&7(" + itemStack.getItem().getName() + ")\n"))
                                    .append(MESSAGE("&l&0Enchantments :\n"))
                                    .append(MESSAGE("&4" + enchantment + "\n"))
                                    .append(MESSAGE("&l&0Prix : &l&4" + is.get().getPrice() + " Emeraude(s)\n\n"))
                                    .append(MESSAGE("&1Cliquer ici pour confirmer la transaction\n"))
                                    .onClick(TextActions.runCommand("/shoppurchase " + locationString)).color(TextColors.AQUA)
                                    .build();
                            bv.addPage(text);
                        }
                        
                        player.sendBookView(bv.build());
                        event.setCancelled(true);
                    } 
                }
            }
        } 
    }
    
    @Listener
    public void onInteractShopLeft(InteractEntityEvent.Primary event, @First Player player) throws IOException{     
        GPlayer gplayer = getGPlayer(player.getUniqueId().toString());
        Entity entity = event.getTargetEntity();
        Location loc = entity.getLocation();
        
        if(entity.getType().getName().contains("itemframe") || entity.getType().getName().contains("armorstand")){
            if(itemShopManager.hasShop(loc) && player.hasPermission("genesys.admin.shop") && gplayer.getLevel()==10){ 
                itemShopManager.delItemShop(loc);
                player.sendMessage(MESSAGE("&e-------------------------"));
                player.sendMessage(MESSAGE("&4ItemShop supprim\351"));
                player.sendMessage(MESSAGE("&e-------------------------"));                
            }else{
                if(itemShopManager.hasShop(loc)){
                    event.setCancelled(true);
                }
            }
        } 
    }
    
    @Listener
    public void onInteractSignBank(InteractBlockEvent event){ 
        Optional<Player> optPlayer = event.getCause().first(Player.class);
        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();

        BlockSnapshot b = event.getTargetBlock();
        if(!b.getLocation().isPresent()){return;}
        Location loc = b.getLocation().get();              
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
                        if (txt1.equals(MESSAGE("&l&1[BANK]"))){
                            BookView.Builder bv = BookView.builder()
                                    .author(Text.of(TextColors.GOLD, "genesys"))
                                    .title(Text.of(TextColors.GOLD, "Bank"));
                            Text text = Text.builder()
                                    .append(MESSAGE("&l&1   ---- Bank ----\n\n"))
                                    .append(MESSAGE("&l&0Quel op\351ration voulez vous faire ?\n"))
                                    .append(MESSAGE("&l&7Faire un retrait d'\351meraude(s)\n"))
                                    .onClick(TextActions.runCommand("/banktr " + "&l&1[BANK]")).color(TextColors.AQUA)
                                    .append(MESSAGE("&l&7D\351poser des \351meraude(s)\n"))
                                    .append(MESSAGE("&l&7Faire un d\351pot de ma bourse\n"))
                                    .append(MESSAGE("&l&7Faire un retrait sur ma bourse\n"))
                                    .build();
                            bv.addPage(text);
                            player.sendBookView(bv.build());
                        }
                    }
                } 
            }
        }
    }
}
