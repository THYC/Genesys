package net.teraoctet.genesys.commands.economy;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static net.teraoctet.genesys.Genesys.itemShopManager;
import net.teraoctet.genesys.commands.CommandTest;
import net.teraoctet.genesys.economy.ItemShop;
import static net.teraoctet.genesys.utils.MessageManager.ERROR;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.text.BookView;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

public class CommandShopCreate implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.admin.shop")) {
            Player player = (Player) src;
            Optional<String> locationString = ctx.<String> getOne("locationstring");
            Optional<String> transactType = ctx.<String> getOne("transacttype");
            Optional<Double> price = ctx.<Double> getOne("price");
            Optional<Integer> qte = ctx.<Integer> getOne("qte");
                        
            if(player.getItemInHand().isPresent() && locationString.isPresent() && !transactType.isPresent()){
                ItemStack is = player.getItemInHand().get();
                String name = Text.builder(is.getTranslation()).build().toPlain();
                Optional<DisplayNameData> displayData = is.get(DisplayNameData.class);
                if(displayData.isPresent()){
                    name = displayData.get().displayName().get().toPlain();
                }
                BookView.Builder bv = BookView.builder()
                        .author(Text.of(TextColors.GOLD, "genesys"))
                        .title(Text.of(TextColors.GOLD, "ItemShop"));
                Text text = Text.builder()
                        .append(MESSAGE("&l&1   ---- ItemShop ----\n\n"))
                        .append(MESSAGE("&8Nom de l'item : &4" + name + "\n"))
                        .append(MESSAGE("&8Location : &4" + locationString.get() +"\n\n"))
                        .append(MESSAGE("&1Indiquer le type de transaction :\n"))
                        .append(Text.builder().append(MESSAGE("&4- VENTE\n"))
                        .onClick(TextActions.runCommand("/shopcreate " + locationString.get() + " sale"))
                        .onHover(TextActions.showText(MESSAGE("&eClique ici pour un item a la vente"))).build())
                        .append(Text.builder().append(MESSAGE("&4- ACHAT\n"))
                        .onClick(TextActions.runCommand("/shopcreate " + locationString.get() + " buy"))
                        .onHover(TextActions.showText(MESSAGE("&eClique ic pour un rachat d'item"))).build())
                        .build();
                bv.addPage(text);
                player.sendBookView(bv.build());
                return CommandResult.success();
            }else if(player.getItemInHand().isPresent() && locationString.isPresent() && transactType.isPresent() && !price.isPresent()){
                ItemStack is = player.getItemInHand().get();
                String name = Text.builder(is.getTranslation()).build().toPlain();
                Optional<DisplayNameData> displayData = is.get(DisplayNameData.class);
                if(displayData.isPresent()){
                    name = displayData.get().displayName().get().toPlain();
                }
                
                BookView.Builder bv = BookView.builder()
                        .author(Text.of(TextColors.GOLD, "genesys"))
                        .title(Text.of(TextColors.GOLD, "ItemShop"));
                Text text = Text.builder()
                        .append(MESSAGE("&l&1   ---- ItemShop ----\n\n"))
                        .append(MESSAGE("&8Nom de l'item : &l&4" + name +"\n"))
                        .append(MESSAGE("&8Location : &l&4" + locationString.get() +"\n\n"))
                        .append(MESSAGE("&8Transaction : &l&4" + transactType.get() +"\n\n"))
                        .append(MESSAGE("&1Pour finir il te faudra indiquer le prix apres la fermeture du livre\n"))
                        .append(Text.builder().append(MESSAGE("&4Clique ici pour continuer\n"))
                        .onClick(TextActions.runCommand("/shopcreate " + locationString.get() + " " + transactType.get() + "  0"))
                        .onHover(TextActions.showText(MESSAGE("&eClique ici"))).build())
                        .build();
                bv.addPage(text);
                player.sendBookView(bv.build());
                return CommandResult.success();
            }else if(player.getItemInHand().isPresent() && locationString.isPresent() && transactType.isPresent() && price.isPresent()){
                ItemStack is = player.getItemInHand().get(); 
                if(price.get()==0){
                    player.sendMessage(Text.builder()
                            .append(Text.builder().append(MESSAGE("&eA l'affichage de la commande, tu devras changer le zero en fin de ligne qui correspond au tarif par la valeur du tarif que tu souhaites avoir (ex: 2 / 0.002 / 2.50 etc...) "))
                            .onClick(TextActions.suggestCommand("/shopcreate " + locationString.get() + " " + transactType.get() + "  0"))
                            .onHover(TextActions.showText(MESSAGE("&ePour continuer et afficher la commande, clique ici"))).build()).build());
                    return CommandResult.success();
                }
                ItemShop itemShop = new ItemShop(is,transactType.get(),price.get(),-1);
                try {
                    if(itemShopManager.saveShop(locationString.get(), itemShop)){
                        player.sendMessage(MESSAGE("&6ItemShop cr\351\351 avec succès"));
                        return CommandResult.success();   
                    } else {
                        player.sendMessage(ERROR());
                        return CommandResult.empty(); 
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CommandTest.class.getName()).log(Level.SEVERE, null, ex);
                    return CommandResult.empty();  
                }
            }else{
                player.sendMessage(MESSAGE("&6Vous devez avoir un item dans la main"));
                return CommandResult.empty();
            }       
                   
        }else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        }
       
        else {
            src.sendMessage(NO_PERMISSIONS());
        }
                
        return CommandResult.empty();
    }
}
