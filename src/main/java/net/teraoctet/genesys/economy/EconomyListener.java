package net.teraoctet.genesys.economy;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import static net.teraoctet.genesys.Genesys.inputDouble;
import static net.teraoctet.genesys.Genesys.itemShopManager;
import net.teraoctet.genesys.player.GPlayer;
import net.teraoctet.genesys.utils.DeSerialize;
import static net.teraoctet.genesys.utils.GData.getGPlayer;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.MISSING_BALANCE;
import static net.teraoctet.genesys.utils.MessageManager.WITHDRAW_SUCCESS;
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
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.item.ItemTypes;

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
            
            //on verifie si le joueur clique sur un panneau
            TileEntity tile=block.get();
            if (tile instanceof Sign) {
                if (event instanceof InteractBlockEvent.Secondary){
                    Sign sign=(Sign)tile;
                    Optional<SignData> optional=sign.getOrCreate(SignData.class);
                    if (optional.isPresent()) {
                        SignData offering = optional.get();
                        Text txt1 = offering.lines().get(0);
                        Text txt2 = offering.lines().get(1);
                        
                        GPlayer gplayer = getGPlayer(player.getIdentifier());
                                                
                        //on verifie si le joueur a cliqué sur un panneau BANK
                        if (txt1.equals(MESSAGE("&l&1[BANK]"))){
                            
                            //on verifie si le joueur a cliqué sur un panneau retrait
                            if (txt2.equals(MESSAGE("&o&1Retrait"))){
                                
                                //on verifie si il y a eu une demande de saisie
                                Optional<Double>coin;
                                if(inputDouble.containsKey(player)){
                                    coin = Optional.of(inputDouble.get(player));
                                    if(coin.isPresent()){
                                        
                                        //si une valeur a été saisie précédemment on effectue le versement et on sort
                                        if(coin.get() != 0.0){
                                            int coinInt = coin.get().intValue();
                                            if(gplayer.getMoney()> coinInt){
                                                gplayer.debitMoney(coinInt);
                                                
                                                //on verifie si le joueur a une bourse, si oui on la credite
                                                if(player.getItemInHand().isPresent()){
                                                    if(itemShopManager.hasCoinPurses(player.getItemInHand().get())){
                                                        Optional<ItemStack> coinPurse = itemShopManager.addCoin(coin.get(),player.getItemInHand().get());
                                                        if(coinPurse.isPresent()){
                                                            player.setItemInHand(coinPurse.get());
                                                            return;
                                                        }
                                                    }
                                                }
                                                
                                                //si le joueur n'avait pas de bourse, on lui verse des emeraudes
                                                ItemStack is = ItemStack.of(ItemTypes.EMERALD, coinInt);
                                                player.getInventory().offer(is);
                                                inputDouble.remove(player);
                                                player.sendMessages(WITHDRAW_SUCCESS(String.valueOf(coinInt)));
                                                return;
                                            }else{
                                                player.sendMessage(MISSING_BALANCE());
                                            }
                                        }                                    
                                    }
                                }

                                BookView.Builder bv = BookView.builder()
                                        .author(Text.of(TextColors.GOLD, "genesys"))
                                        .title(Text.of(TextColors.GOLD, "Bank"));
                                Text text = 
                                        Text.builder()  .append(MESSAGE("&l&b   -----------------\n"))
                                                        .append(MESSAGE("&l&b     RETRAIT \n"))
                                                        .append(MESSAGE("&l&b   -----------------\n\n"))
                                                        .append(MESSAGE("&b&oClique sur un des 4 choix\n")).build().concat(
                                        Text.builder()  .append(MESSAGE("&e[1] Retrait de 10 \351meraude(s)\n"))
                                                        .onClick(TextActions.executeCallback(callBank(1,10.0))).build()).concat(
                                        Text.builder()  .append(MESSAGE("&e[2] Retrait de 20 \351meraude(s)\n"))
                                                        .onClick(TextActions.executeCallback(callBank(1,20.0))).build()).concat(
                                        Text.builder()  .append(MESSAGE("&e[3] Retrait de 30 \351meraude(s)\n"))
                                                        .onClick(TextActions.executeCallback(callBank(1,30.0))).build()).concat(
                                        Text.builder()  .append(MESSAGE("&e[4] &bje veux saisir la somme\n\n"))
                                                        .onClick(TextActions.executeCallback(callBank(2,0.0))).build());
                                bv.addPage(text);
                                player.sendBookView(bv.build());
                                player.sendMessage(text);
                            }
                        }
                        
                        //on verifie si le joueur a cliqué sur un panneau depot
                        if (txt2.equals(MESSAGE("&o&1Depot"))){
                                
                            //on verifie si il y a eu une demande de saisie
                            Optional<Double>coin;
                            if(inputDouble.containsKey(player)){
                                coin = Optional.of(inputDouble.get(player));
                                if(coin.isPresent()){

                                    //si une valeur a été saisie précédemment on effectue le versement et on sort
                                    if(coin.get() != 0.0){
                                        int coinInt = coin.get().intValue();
                                        if(gplayer.getMoney()> coinInt){
                                            gplayer.debitMoney(coinInt);
                                            ItemStack is = ItemStack.of(ItemTypes.EMERALD, coinInt);
                                            player.getInventory().offer(is);
                                            inputDouble.remove(player);
                                            player.sendMessages(WITHDRAW_SUCCESS(String.valueOf(coinInt)));
                                            return;
                                        }else{
                                            player.sendMessage(MISSING_BALANCE());
                                        }
                                    }                                    
                                }
                            }

                            BookView.Builder bv = BookView.builder()
                                    .author(Text.of(TextColors.GOLD, "genesys"))
                                    .title(Text.of(TextColors.GOLD, "Bank"));
                            Text text = 
                                    Text.builder()  .append(MESSAGE("&l&b   -----------------\n"))
                                                        .append(MESSAGE("&l&b     DEPOT \n"))
                                                        .append(MESSAGE("&l&b   -----------------\n\n"))
                                                        .append(MESSAGE("&b&oClique sur un des 4 choix\n")).build().concat(
                                        Text.builder()  .append(MESSAGE("&e[1] Depot des \351meraude(s)\n"))
                                                        .onClick(TextActions.executeCallback(callBank(1,10.0))).build()).concat(
                                        Text.builder()  .append(MESSAGE("&e[2] Depot de toutes les \351meraude(s) situés dans mon stuff\n"))
                                                        .onClick(TextActions.executeCallback(callBank(1,20.0))).build()).concat(
                                        Text.builder()  .append(MESSAGE("&e[3] Retrait de 30 \351meraude(s)\n"))
                                                        .onClick(TextActions.executeCallback(callBank(1,30.0))).build()).concat(
                                        Text.builder()  .append(MESSAGE("&e[4] &bje veux saisir la somme\n\n"))
                                                        .onClick(TextActions.executeCallback(callBank(2,0.0))).build());
                            bv.addPage(text);
                            player.sendBookView(bv.build());
                            player.sendMessage(text);
                            
                        }
                    }
                } 
            }
        }
    }
    
    private Consumer<CommandSource> callBank(Integer type, double coin) {
	return (CommandSource src) -> {
            Player player = (Player)src;
            switch (type){
                case 1: //retrait de sommes prédéfini
                    inputDouble.put(player, coin);
                    src.sendMessage(MESSAGE("&eMaintenant cliques de nouveau sur le panneau pour confirmer le retrait de " + coin + " \351meraude(s)"));
                    src.sendMessage(MESSAGE("&esi tu tiens ta bourse dans ta main, la somme sera vers\351 dessus sinon tu aura des \351meraudes"));
                    break;
                case 2: //retrait saisie par le joueur
                    inputDouble.put(player, 0.0);
                    src.sendMessage(MESSAGE("&eTapes la somme voulu :"));
                    break;
            }
        };
    }
}
