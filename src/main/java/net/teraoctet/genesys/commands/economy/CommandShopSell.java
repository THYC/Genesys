package net.teraoctet.genesys.commands.economy;

import static java.lang.Math.round;
import java.util.Optional;
import static net.teraoctet.genesys.Genesys.itemShopManager;
import net.teraoctet.genesys.economy.ItemShop;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.type.GridInventory;

public class CommandShopSell implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.admin.shop")) {
            Player player = (Player) src;
            
            Optional<String> locationString = ctx.<String> getOne("locationstring");
            Optional<ItemShop> itemShop = itemShopManager.getItemShop(locationString.get());
            if(itemShop.isPresent()){
                ItemStack is = itemShop.get().getItemStack();
                double price = itemShop.get().getPrice();
                
                if(player.getItemInHand().isPresent()){
                    if(player.getItemInHand().get().getItem().equals(is.getItem())){
                        int qte = player.getItemInHand().get().getQuantity();
                        double coin = price*qte*100;
                        coin = round(coin);
                        coin = coin/100;
                        
                        // On verifie si le joueur a une bourse dans son inventaire et on lui verse la monaie
                        for(Inventory slotInv : player.getInventory().query(Hotbar.class).slots()){
                            Optional<ItemStack> peek = slotInv.peek();
                            if(peek.isPresent()){
                                if(itemShopManager.hasCoinPurses(peek.get())){
                                    slotInv.clear();
                                    slotInv.offer(itemShopManager.addCoin(coin, peek.get()).get());
                                    player.setItemInHand(null);
                                    player.sendMessages(MESSAGE("&ela somme a ete ajoute a votre bourse :)"));
                                    return CommandResult.success();
                                }
                            }
                        }
                        
                        for(Inventory slotInv : player.getInventory().query(GridInventory.class).slots()){
                            Optional<ItemStack> peek = slotInv.peek();
                            if(peek.isPresent()){
                                if(itemShopManager.hasCoinPurses(peek.get())){
                                    slotInv.clear();
                                    slotInv.offer(itemShopManager.addCoin(coin, peek.get()).get());
                                    player.setItemInHand(null);
                                    player.sendMessages(MESSAGE("&ela somme a ete ajoute a votre bourse :)"));
                                    return CommandResult.success();
                                }
                            }
			}

                        // si le joueur n'a pas de bourse, on lui en donne une
                        player.setItemInHand(itemShopManager.CoinPurses(player, coin).get());
                        return CommandResult.success();
                    } else {
                        player.sendMessage(MESSAGE("&bVotre item ne correspond pas à la demande"));
                        return CommandResult.success();
                    }
                } else {
                    player.sendMessage(MESSAGE("&eAucun item disponible"));  
                }
                return CommandResult.success();
            }
                        
        }    
                   
        else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
        
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
