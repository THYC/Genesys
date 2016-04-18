package net.teraoctet.genesys.commands;

import java.util.Optional;
import static net.teraoctet.genesys.utils.MessageManager.MESSAGE;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import static net.teraoctet.genesys.utils.MessageManager.NO_CONSOLE;
import static net.teraoctet.genesys.utils.MessageManager.NO_PERMISSIONS;
import static net.teraoctet.genesys.utils.MessageManager.USAGE;
import net.teraoctet.genesys.utils.SettingCompass;
import org.spongepowered.api.item.inventory.ItemStack;

public class CommandMagicCompass implements CommandExecutor {
        
    @Override
    public CommandResult execute(CommandSource src, CommandContext ctx) {

        if(src instanceof Player && src.hasPermission("genesys.magiccompass")) {
            Player player = (Player)src;
            
            Optional<String> direction = ctx.<String> getOne("direction");
            if(direction.isPresent()){
                Optional<String> nameOpt = ctx.<String> getOne("name");
                SettingCompass sc = new SettingCompass();
                String name = "";
                Optional<ItemStack> is = Optional.empty();
                
                switch(direction.get().toLowerCase()){
                    case "home":
                        if(nameOpt.isPresent()){
                            name = nameOpt.get();
                        }else{
                            name = "default";
                        }
                        is = sc.MagicCompass(player,"HOME:" + name);
                        break;
                    case "plot":
                        if(nameOpt.isPresent()){
                            name = nameOpt.get();
                            is = sc.MagicCompass(player,"PLOT:" + name);
                        }else{
                            player.sendMessage(MESSAGE("vous devez indiquer le nom de votre parcelle"));
                            player.sendMessage(USAGE("/mc plot NomDeLaParcelle"));
                            return CommandResult.empty();
                        }
                        break;
                    case "faction":
                        player.sendMessage(MESSAGE("Cette commande ne fonctionne pas encore sur faction"));
                        break;
                    case "xyz":
                        if(nameOpt.isPresent()){
                            name = nameOpt.get();
                            Optional<Integer> x = ctx.<Integer> getOne("x");
                            Optional<Integer> y = ctx.<Integer> getOne("y");
                            Optional<Integer> z = ctx.<Integer> getOne("z");
                            if(x.isPresent() && y.isPresent() && z.isPresent()){
                                String loc = x.get().toString() + ":" + y.get().toString() + ":" + z.get().toString();
                                is = sc.MagicCompass(player,name,loc);
                            }
                            
                        }else{
                            player.sendMessage(MESSAGE("&evous devez indiquer un nom pour ce point"));
                            player.sendMessage(USAGE("/mc xyz UnNomDeVotreChoix <X> <Y< <Z>"));
                            return CommandResult.empty();
                        }
                        break;
                    default:
                        player.sendMessage(USAGE("/mc [Direction] [name] [X] [Y] [Z]"));
                        player.sendMessage(MESSAGE("&8Direction : HOME/PLOT/FACTION/XYZ"));
                        player.sendMessage(MESSAGE("&8Name : le nom de votre home, de votre parcelle ..."));
                        player.sendMessage(MESSAGE("&8X Y Z : uniquement si Direction = XYZ"));
                        player.sendMessage(MESSAGE("&8ex : /xyz maMine 100 30 -150"));
                        return CommandResult.empty();
                } 
                if(is.isPresent()){ player.setItemInHand(is.get()); }
                return CommandResult.success();
            }
        } else if (src instanceof ConsoleSource) {
            src.sendMessage(NO_CONSOLE());
        } else {
            src.sendMessage(NO_PERMISSIONS());
        }       
        return CommandResult.empty();
    }
}
