package net.teraoctet.genesys.utils;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.util.Tristate;

public class Permissions {
	
    public static boolean has(Player player, String permission) {
	return player.hasPermission(permission);
    }
    
    public Permissions (Player player){
        player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, "my.perm", Tristate.TRUE);
        
    }	
    
    public static String getPrefix(Player player) {
    Subject subject = player.getContainingCollection().get(player.getUniqueId().toString());
         if (subject instanceof OptionSubject) {
             OptionSubject optionSubject = (OptionSubject) subject;
             return optionSubject.getOption("prefix").orElse("");
         }
         return "";
    }
	
    public static List<String> getGroups(Player player) {

        List<String> groups = new ArrayList<>();
        Subject subject = player.getContainingCollection().get(player.getUniqueId().toString());

        if(subject instanceof OptionSubject) {
            OptionSubject option = (OptionSubject) subject;
            for(Subject group : option.getParents()) {
                if(!groups.contains(group.getContainingCollection().getIdentifier())) groups.add(group.getContainingCollection().getIdentifier());
            }
        }
        return groups;
    }
	       
    public static String getSuffix(Player player) {
        Subject subject = player.getContainingCollection().get(player.getUniqueId().toString());
        if (subject instanceof OptionSubject) {
            OptionSubject optionSubject = (OptionSubject) subject;
            return optionSubject.getOption("suffix").orElse("");
        }
        return "";
    }

}
