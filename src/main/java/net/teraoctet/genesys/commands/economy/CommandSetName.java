package net.teraoctet.genesys.commands.economy;

import java.util.List;
import java.util.Optional;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.text.Text;

public class CommandSetName implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.admin.setname")) {
            Player player = (Player) src;
            Optional<String> name = ctx.<String> getOne("name");
            String item = "";
            
            if(player.getItemInHand().isPresent()){
                
                // on récupère l'objet IteemStack contenu dans la main du joeur
                Optional<ItemStack> isOpt = player.getItemInHand();
                if(isOpt.isPresent()){
                    ItemStack is = isOpt.get();
                    // on vérifie que le paramètre name a été renseigné sinon on prends la valeur itemType de l'objet ItemStack
                    if(name.isPresent()){item = "&4" + name.get();}else{item = "&4" + is.getItem().getName();} 
                    
                    LoreData loreData = is.getOrCreate(LoreData.class).get();
                    is.offer(Keys.DISPLAY_NAME, MESSAGE(item));

                    //List<Text> newLore = loreData.lore().get();
                    //newLore.add(MESSAGE("&8" + shopType));
                    //newLore.add(MESSAGE("&5" + itemType));
                    //newLore.add(MESSAGE("&6PRICE: " + price));

                    //DataTransactionResult dataTransactionResult = is.offer(Keys.ITEM_LORE, newLore);

                    player.setItemInHand(is);
                    player.sendMessage(MESSAGE("&6Le nom de votre item a \351t\351 changé\351 en : " + item));
                    return CommandResult.success();
                    
                }else{
                    player.sendMessage(MESSAGE("&6Vous devez avoir un item dans la main"));
                return CommandResult.empty();
                }
            }else{
                player.sendMessage(USAGE("/setname <name> |&8 vous devez avoir un item dan la main"));
                return CommandResult.empty();
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
