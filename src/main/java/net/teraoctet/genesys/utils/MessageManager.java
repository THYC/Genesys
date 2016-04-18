package net.teraoctet.genesys.utils;

import com.google.common.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

public class MessageManager {
    private static Text NO_PERMISSIONS;
    private static Text NO_CONSOLE;
    private static Text JOIN_MESSAGE;
    private static Text EVENT_LOGIN_MESSAGE;
    private static Text FIRSTJOIN_MESSAGE;
    private static Text FIRSTJOIN_BROADCAST_MESSAGE;
    private static Text EVENT_DISCONNECT_MESSAGE;
    private static Text NAME_CHANGE;
    private static Text WRONG_NAME;
    private static Text SUN_MESSAGE;
    private static Text RAIN_MESSAGE;
    private static Text DAY_MESSAGE;
    private static Text NIGHT_MESSAGE;
    private static Text STORM_MESSAGE;
    private static Text GUIDE_FACTION;
    private static Text NO_FACTION;
    private static Text NOT_IN_SAME_FACTION;
    private static Text WRONG_RANK;
    private static Text OWNER_CANNOT_LEAVE;
    private static Text ALREADY_FACTION_MEMBER;
    private static Text FACTION_CREATED_SUCCESS;
    private static Text FACTION_RENAMED_SUCCESS;
    private static Text FACTION_DELETED_SUCCESS;
    private static Text LEAVING_FACTION_SUCCESS;
    private static Text FACTION_MEMBER_REMOVED_SUCCESS;
    private static Text FACTION_RETURNED_BY;
    private static Text FACTION_DELETED_NOTIFICATION;
    private static Text FACTION_NEW_CHEF;
    private static Text FACTION_CHEF_GRADE_GIVEN;
    private static Text FACTION_YOU_ARE_NEW_CHEF;
    private static Text BUYING_COST_PLOT;
    private static Text PROTECT_PLOT_SUCCESS;
    private static Text BEDROCK2SKY_PROTECT_PLOT_SUCCESS;
    private static Text PROTECT_LOADED_PLOT;
    private static Text UNDEFINED_PLOT_ANGLES;
    private static Text ALREADY_OWNED_PLOT;
    private static Text NAME_ALREADY_USED;
    private static Text NO_PLOT;
    private static Text PLOT_INFO;
    private static Text TARGET_PLOT_LIST;
    private static Text PLOT_LIST;
    private static Text PLOT_PROTECTED;
    private static Text PLOT_NO_FLY;
    private static Text PLOT_NO_ENTER;
    private static Text PLOT_NO_BREAK;
    private static Text PLOT_NO_BUILD;
    private static Text PLOT_NO_FIRE;
    private static Text PLOT_NO_EXIT;
    private static Text MISSING_BALANCE;
    private static Text DEPOSIT_SUCCESS;
    private static Text HOME_ALREADY_EXIST;
    private static Text HOME_SET_SUCCESS;
    private static Text HOME_DEL_SUCCESS;
    private static Text NB_HOME;
    private static Text NB_ALLOWED_HOME;
    private static Text HOME_NOT_FOUND;
    private static Text ERROR;
    private static Text HOME_TP_SUCCESS;
    private static Text NOT_FOUND;
    private static Text NOT_CONNECTED;
    private static Text DATA_NOT_FOUND;
    private static Text CANNOT_EJECT_OWNER;
    private static Text WORLD_ALREADY_EXIST;
    private static Text WORLD_CREATED;
    private static Text WORLD_CREATION_ERROR;
    private static Text WORLD_PROPERTIES_ERROR;
    private static Text TELEPORTED_TO_WORLD;
    private static Text OTHER_TELEPORTED_TO_WORLD;
    private static Text PROTECT_PORTAL;
    private static Text TP_BACK;
    private static Text INVENTORY_CLEARED;
    private static Text CLEARINVENTORY_SUCCESS;
    private static Text FLY_ENABLED;
    private static Text FLY_DISABLED;
    private static Text FLY_GIVEN;
    private static Text FLY_RETIRED;
    private static Text TP_AT_COORDS;
    private static Text WRONG_CHARACTERS_NUMBER;
    private static Text KILLED_BY;
    private static Text SUICIDE;
    private static Text ONHOVER_FACTION_MOREACTIONS;
    private static Text ONHOVER_FACTION_SETGRADE;
    private static Text ONHOVER_FACTION_RENAME;
    private static Text ONHOVER_FACTION_INVIT;
    private static Text ONHOVER_FACTION_DELETE;
    private static Text ONHOVER_FACTION_REMOVEMEMBER;
    private static Text ONHOVER_FACTION_WITHDRAWAL;
    private static Text ONHOVER_FACTION_DEPOSIT;
    private static Text ONHOVER_FACTION_LEAVE;
    private static Text ONHOVER_FACTION_LIST_LVL10;
    private static Text ONHOVER_PI_NAME;
    private static Text SHOP_TRANSACT_FORMAT;
    private static Text SHOP_SALE;
    private static Text SHOP_BUY;
    private static Text WITHDRAW_SUCCESS;
    private static Text FACTION_MISSING_BALANCE;
       
    public static File file = new File("config/genesys/message.conf");
    public static final ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
    public static ConfigurationNode message = manager.createEmptyNode(ConfigurationOptions.defaults());
        
    public static void init() throws ObjectMappingException {
        try {
            if (!file.exists()) {
                file.createNewFile();
             }    
                List<String> msg = new ArrayList<>();
           
                //-------------------------
                // Message général serveur
                //-------------------------
                
                if(message.getNode("SERVER","JOIN_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&6Bienvenue, &e%name%!");
                    msg.add("&6Tu es sur la map &e%world%!\n");
                    message.getNode("SERVER","JOIN_MESSAGE").setValue(msg);
                }
                if(message.getNode("SERVER","EVENT_LOGIN_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7%name% a rejoint le serveur");
                    message.getNode("SERVER","EVENT_LOGIN_MESSAGE").setValue(msg);
                }
                
                if(message.getNode("SERVER","FIRSTJOIN_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eSalut &6%name%&e, c'est visiblement la premi\350re fois que tu viens !");
                    msg.add("&7Assure-toi d'avoir bien lu le r\350glement en tapant &e/rules");
                    msg.add("&7Si tu veux participer \340 la vie du serveur ou te tenir inform\351");
                    msg.add("&7inscris-toi sur notre forum &bhttp://craft.teraoctet.net\n");
                    message.getNode("SERVER","FIRSTJOIN_MESSAGE").setValue(msg);
                }
                
                if(message.getNode("SERVER","EVENT_DISCONNECT_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&7%name% s'est d\351connect\351");
                    message.getNode("SERVER","EVENT_DISCONNECT_MESSAGE").setValue(msg);
                }
                
                if(message.getNode("SERVER","FIRSTJOIN_BROADCAST_MESSAGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&9%name% est nouveau sur le serveur !");
                    message.getNode("SERVER","FIRSTJOIN_BROADCAST_MESSAGE").setValue(msg);
                }
                
                if(message.getNode("SERVER","NAME_CHANGE").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&8%var1% &7a chang\351 son nom en &8%var2%");
                    message.getNode("SERVER","NAME_CHANGE").setValue(msg);
                }
                
                if(message.getNode("SERVER","INVENTORY_CLEARED").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&cVotre inventaire a \351t\351 \351ffac\351");
                    message.getNode("SERVER","INVENTORY_CLEARED").setValue(msg);
                }
                
                if(message.getNode("SERVER","CLEARINVENTORY_SUCCESS").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eL'inventaire de &6%var1% a \351t\351 supprim\351");
                    message.getNode("SERVER","CLEARINVENTORY_SUCCESS").setValue(msg);
                }
                
                if(message.getNode("SERVER","FLY_ENABLED").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eFly activ\351");
                    message.getNode("SERVER","FLY_ENABLED").setValue(msg);
                }
                
                if(message.getNode("SERVER","FLY_DISABLED").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eFly d\351sactiv\351");
                    message.getNode("SERVER","FLY_DISABLED").setValue(msg);
                }
                
                if(message.getNode("SERVER","FLY_GIVEN").isVirtual()){
                    msg = new ArrayList<>();
                    msg.add("&eVous avez activ\351 le fly de &6%var1%");
                    message.getNode("SERVER","FLY_GIVEN").setValue(msg);
                }
                
                msg = new ArrayList<>();
                msg.add("&eVous avez d\351sactiv\351 le fly de &6%var1%");
                message.getNode("SERVER","FLY_RETIRED").setValue(msg);
                manager.save(message);
                
                //-------------------------
                // Message Exception/Error
                //-------------------------
                
                msg = new ArrayList<>();
                msg.add("&cUne erreur est survenue !");
                message.getNode("EXCEPTION","ERROR").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cCe nom est incorrect !");
                message.getNode("EXCEPTION","WRONG_NAME").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cCe nom est d\351j\340 utilis\351 !");       
                message.getNode("EXCEPTION","NAME_ALREADY_USED").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cVous ne pouvez pas renvoyer le propri\351taire !");
                message.getNode("EXCEPTION","CANNOT_EJECT_OWNER").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&4%var1% &cest introuvable");
                message.getNode("EXCEPTION","NOT_FOUND").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&4%var1% &cn'est pas connect\351 !");
                message.getNode("EXCEPTION","NOT_CONNECTED").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cVous n'avez pas la permission pour utiliser cette commande !");
                message.getNode("EXCEPTION","NO_PERMISSIONS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cCette commande ne peut pas s'ex\351cuter sur la console");
                message.getNode("EXCEPTION","NO_CONSOLE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&4%var1% &cn'est pas enregistr\351 dans la base de donn\351e");
                message.getNode("EXCEPTION","DATA_NOT_FOUND").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cLe nombre de caract\350res doit \352tre entre %var1% et %var2%");
                message.getNode("EXCEPTION","WRONG_CHARACTERS_NUMBER").setValue(msg);
                manager.save(message);    
                
                //-------------------------
                // Message DeadMsg
                //-------------------------
                
                msg = new ArrayList<>();
                msg.add("&7%var1% a \351t\351 tu\351 par %var2%");
                message.getNode("DEAD_MSG","KILLED_BY").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&7%var1% s'est suicid\351");
                message.getNode("DEAD_MSG","SUICIDE").setValue(msg);
                manager.save(message);
                
                //-------------------------
                // Message weather / time
                //-------------------------
                
                msg = new ArrayList<>();
                msg.add("&e%name% &6a programm\351 le beau temps sur &e%world%");
                message.getNode("WEATHER-TIME","SUN_MESSAGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&e%name% &6a programm\351 la pluie sur &e%world%");
                message.getNode("WEATHER-TIME","RAIN_MESSAGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&e%name% &6a programm\351 l'orage sur &e%world%");
                message.getNode("WEATHER-TIME","STORM_MESSAGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&e%name% &6a mis le jour sur &e%world%");
                message.getNode("WEATHER-TIME","DAY_MESSAGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&e%name% &6a mis la nuit sur &e%world%");
                message.getNode("WEATHER-TIME","NIGHT_MESSAGE").setValue(msg);
                manager.save(message);
                
                //-------------------------
                // Message Faction
                //-------------------------
                
                msg = new ArrayList<>();
                msg.add("&cVous n'\352tes dans aucune faction !");
                message.getNode("FACTION","NO_FACTION").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cVous \352tes d\351j\340 dans une faction !");
                message.getNode("FACTION","ALREADY_FACTION_MEMBER").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cVotre rang dans la faction ne vous permet pas d'utiliser \347a !");
                message.getNode("FACTION","WRONG_RANK").setValue(msg);
                manager.save(message);
                        
                msg = new ArrayList<>();
                msg.add("&c%var1% ne fait pas parti de votre faction !");
                message.getNode("FACTION","NOT_IN_SAME_FACTION").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cVous \352tes chef de votre faction, vous ne pouvez pas la quitter !");
                msg.add("&cVeuillez c\351der le grade à un autre membre avec : /faction setgrade 1 <player>");
                message.getNode("FACTION","OWNER_CANNOT_LEAVE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&9%var1% est le nouveau leader de la faction \"%var2%&9\" !");
                message.getNode("FACTION","FACTION_NEW_CHEF").setValue(msg);
                manager.save(message);
                        
                msg = new ArrayList<>();
                msg.add("&2Vous avez c\351d\351 votre grade de chef \340 %var1% !");
                message.getNode("FACTION","FACTION_CHEF_GRADE_GIVEN").setValue(msg);
                manager.save(message);
                        
                msg = new ArrayList<>();
                msg.add("&2Vous \352tes le nouveau leader de votre faction !");
                message.getNode("FACTION","FACTION_YOU_ARE_NEW_CHEF").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&n&eQu'est-ce que \347a apporte d'\350tre dans une faction ?");
                msg.add("&eComing soon ..!");
                msg.add("&ePlus d'infos sur &bhttp://craft.teraoctet.net\n");
                message.getNode("FACTION","GUIDE_FACTION").setValue(msg);
                
                msg = new ArrayList<>();
                msg.add("&eVous venez de cr\351er la faction \"&r%var1%&e\"");
                message.getNode("FACTION","FACTION_CREATED_SUCCESS").setValue(msg);
                manager.save(message);
                        
                msg = new ArrayList<>();
                msg.add("&eVous venez de supprimer la faction \"&r%var1%&e\"");
                message.getNode("FACTION","FACTION_DELETED_SUCCESS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eLa faction \"&6%var1%&e\" a \351t\351 renomm\351e en \"&6%var2%&e\"");
                message.getNode("FACTION","FACTION_RENAMED_SUCCESS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eVous venez de quitter la faction \"&r%var1%&e\"");
                message.getNode("FACTION","LEAVING_FACTION_SUCCESS").setValue(msg);
                manager.save(message);
                        
                msg = new ArrayList<>();
                msg.add("&eVous avez renvoy\351 &6%var1% &ede votre faction");
                message.getNode("FACTION","FACTION_MEMBER_REMOVED_SUCCESS").setValue(msg);
                manager.save(message);
                     
                msg = new ArrayList<>();
                msg.add("&eVous avez \351t\351 renvoy\351 de votre faction par &6%var1%");
                message.getNode("FACTION","FACTION_RETURNED_BY").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&1La faction \"&r%var1%&1\" a \351t\351 dissoute !");
                message.getNode("FACTION","FACTION_DELETED_NOTIFICATION").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Affiche un menu pour g\351rer la faction");
                msg.add("&n&eAccessible par :&r Chef, Sous-chef, Officier");
                message.getNode("FACTION","ONHOVER_FACTION_MOREACTIONS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Shift+Click pour quitter la faction");
                msg.add("\n&7/faction leave");
                message.getNode("FACTION","ONHOVER_FACTION_LEAVE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Inviter un joueur \340 rejoindre la faction");
                msg.add("&e&nUtilisable par :&r Chef, Sous-chef, Officier");
                msg.add("\n&7/faction addplayer <player>");
                message.getNode("FACTION","ONHOVER_FACTION_INVIT").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Changer le grade d'un membre");
                msg.add("&e&nUtilisable par :&r Chef, Sous-chef, Officier");
                msg.add("\n&7/faction setplayergrade <player> <grade>");
                msg.add("&o&n&7Grade :&r&o&7 2 -> Sous-chef | 3 -> Officer | 4 -> Membre | 5 -> Recrue");
                message.getNode("FACTION","ONHOVER_FACTION_SETGRADE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Renvoyer un joueur de la faction");
                msg.add("&e&nUtilisable par :&r Chef, Sous-chef");
                msg.add("\n&7/faction removeplayer <player>");
                message.getNode("FACTION","ONHOVER_FACTION_REMOVEMEMBER").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Retirer des \351meraudes de la banque de faction");
                msg.add("&e&nUtilisable par :&r Chef, Sous-chef");
                msg.add("\n&7/faction withdraw <montant>");
                message.getNode("FACTION","ONHOVER_FACTION_WITHDRAWAL").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Renommer la faction");
                msg.add("&e&nUtilisable par :&r Chef, Sous-chef");
                msg.add("\n&7/faction rename <nom>");
                message.getNode("FACTION","ONHOVER_FACTION_RENAME").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Supprimer la faction");
                msg.add("&e&nUtilisable par :&r Chef");
                msg.add("\n&7/faction delete <nom>");
                message.getNode("FACTION","ONHOVER_FACTION_DELETE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6D\351poser des \351meraudes dans la banque de la faction");
                msg.add("\n&7/faction depot <montant>");
                message.getNode("FACTION","ONHOVER_FACTION_DEPOSIT").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&l&6Faction : &r%var1%");
                msg.add("&e&nChef : &r%var2%");
                msg.add("\n&7&n&oShift+Click :&r &8&o/faction delete <name>");
                message.getNode("FACTION","ONHOVER_FACTION_LIST_LVL10").setValue(msg);
                manager.save(message);
                
                //-------------------------
                // Message Plot / parcelle
                //-------------------------
                
                msg = new ArrayList<>();
                msg.add("\n&6Le co\373t pour prot\351ger cette parcelle est de &e%var1% \351meraudes");
                msg.add("&6Vous poss\351dez actuellement &e%var2% \351meraude(s) en banque\n");
                message.getNode("PLOT","BUYING_COST_PLOT").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&6La parcelle : &e%var1% &6 est maintenant prot\351g\351e : ");
                msg.add("&6Vous pouvez modifier les param\350tres avec la commande &e/plot flag\n");
                message.getNode("PLOT","PROTECT_PLOT_SUCCESS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&6La parcelle : &e%var1% &6 est maintenant prot\351g\351e de la bedrock jusqu'au ciel");
                msg.add("&6Vous pouvez modifier les param\350tres avec la commande &e/plot flag\n");
                message.getNode("PLOT","BEDROCK2SKY_PROTECT_PLOT_SUCCESS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&6Parcelle &e%var1% &6: protection activ\351e");
                message.getNode("PLOT","PROTECT_LOADED_PLOT").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&cVous n'avez pas d\351fini les angles de votre parcelle");
                msg.add("&cLes angles se d\351finissent en utilisant une pelle en bois :");
                msg.add("&cAngle1 = clic gauche / Angle2 = clic droit\n");
                message.getNode("PLOT","UNDEFINED_PLOT_ANGLES").setValue(msg);
                manager.save(message);
                                
                msg = new ArrayList<>();
                msg.add("&cVous ne pouvez pas cr\351er cette parcelle, d\351j\340 une parcelle prot\351g\351e dans cette s\351lection !");         
                message.getNode("PLOT","ALREADY_OWNED_PLOT").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cAucune parcelle \340 cette position !");
                message.getNode("PLOT","NO_PLOT").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cLe fly est interdit sur cette parcelle");
                message.getNode("PLOT","PLOT_NO_FLY").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&5L'acc\350s \340 cette parcelle est interdit");
                message.getNode("PLOT","PLOT_NO_ENTER").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&6Vous \352tes sur une parcelle proteg\351e : &e%plot%");
                msg.add("&6Propri\351taire : &e%owner%");
                msg.add("&6Habitant(s) : &e%allow%");
                message.getNode("PLOT","PLOT_INFO").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&bListe des parcelles vous appartenant :");
                message.getNode("PLOT","PLOT_LIST").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&bListe des parcelles appartenant \340 &3%var1% :");
                message.getNode("PLOT","TARGET_PLOT_LIST").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&5Cette parcelle est prot\351g\351e par un sort magique !");
                message.getNode("PLOT","PLOT_PROTECTED").setValue(msg);
                manager.save(message);
                
                //-------------------------
                // Message ECONOMY
                //-------------------------
                
                msg = new ArrayList<>();
                msg.add("&eVirement de &6%var1% \351meraudes &eeffectu\351 avec succès !");
                message.getNode("ECONOMY", "DEPOSIT_SUCCESS").setValue(msg);
                
                msg = new ArrayList<>();
                msg.add("&eRerait de &6%var1% \351meraudes &eeffectu\351 avec succès !");
                message.getNode("ECONOMY", "WITHDRAW_SUCCESS").setValue(msg);
                
                msg = new ArrayList<>();
                msg.add("&cVous ne poss\351dez pas assez d'assez d'\351meraudes sur votre compte, tapez /bank pour voir votre solde");
                message.getNode("ECONOMY","MISSING_BALANCE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cVotre faction ne poss\351de pas autant d'\351meraudes dans ses coffres !");
                message.getNode("ECONOMY","FACTION_MISSING_BALANCE").setValue(msg);
                manager.save(message);
                
                //-------------------------
                // Message TELEPORTATION
                //-------------------------
                
                msg = new ArrayList<>();
                msg.add("T\351l\351portation aux coordonn\351es");
                message.getNode("TELEPORTATION","TP_AT_COORDS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&6Woshhhh ..!");
                message.getNode("TELEPORTATION","TP_BACK").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cHome d\351j\340 d\351fini, veuillez le supprimer avant de pouvoir le red\351finir");
                message.getNode("TELEPORTATION","HOME_ALREADY_EXIST").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eLe home &6%var1% &ea \351t\351 cr\351\351 avec succ\350s");
                message.getNode("TELEPORTATION","HOME_SET_SUCCESS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eLe home &6%var1% &ea \351t\351 supprim\351 avec succ\350s");
                message.getNode("TELEPORTATION","HOME_DEL_SUCCESS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eVous poss\351dez actuellement &6%var1% &esur &6%var2% &ehome possible");
                message.getNode("TELEPORTATION","NB_HOME").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eVous \352tes seulement autoris\351 \340 poss\351der %var1% home");
                message.getNode("TELEPORTATION","NB_ALLOWED_HOME").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cHome introuvable !");
                msg.add("&cVeuillez utiliser la commande /sethome pour le d\351finir");
                message.getNode("TELEPORTATION","HOME_NOT_FOUND").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eT\351l\351portation sur votre home : &6%var1%");
                message.getNode("TELEPORTATION","HOME_TP_SUCCESS").setValue(msg);
                manager.save(message);
                
                //-------------------------
                // Message Commande PlayerInfo
                //-------------------------
                
                msg = new ArrayList<>();
                msg.add("&nUUID:&r %var1%\n");
                msg.add("&7&n&oClick :&r &8&o/kick <player> <raison>");
                msg.add("&7&n&oShift+Click :&r &8&o/ban <player> <raison>");
                message.getNode("CMD_PLAYERINFO","ONHOVER_PI_NAME").setValue(msg);
                manager.save(message);
                
                //-------------------------
                // Message WORLD
                //-------------------------
                
                msg = new ArrayList<>();
                msg.add("&cCe monde existe d\351j\340");
                message.getNode("WORLD","WORLD_ALREADY_EXIST").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&c%var1% a \351t\351 cr\351\351 avec succ\350s");
                message.getNode("WORLD","WORLD_CREATED").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&4%ERREUR : &cLe monde n'a pas pu \352tre cr\351\351");
                message.getNode("WORLD","WORLD_CREATION_ERROR").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&4%ERREUR : &cLes propri\351t\351s du monde ne peuvent pas \352tre cr\351\351s");
                message.getNode("WORLD","WORLD_PROPERTIES_ERROR").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eT\351l\351portation sur : &6%world%");
                message.getNode("WORLD","TELEPORTED_TO_WORLD").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&6%name% &ea \351t\351 t\351l\351port\351 sur : &6%world%");
                message.getNode("WORLD","OTHER_TELEPORTED_TO_WORLD").setValue(msg);
                manager.save(message);
                
                //-------------------------
                // Message PORTAL
                //-------------------------
                
                msg = new ArrayList<>();
                msg.add("&cPortail prot\351g\351");
                message.getNode("PORTAL","PROTECT_PORTAL").setValue(msg);
                manager.save(message);        
            //}
            message = manager.load();

        } catch (IOException e) {}
		
    }
    
    public static Text formatText(String string){
        Text text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(string)).toText();
        return text;
    }
    
    private static Text format(Text text, String nodeP, String nodeC){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(nodeP,nodeC).getList(TypeToken.of(String.class));
        } catch (ObjectMappingException ex) { Logger.getLogger(MessageManager.class.getName()).log(Level.SEVERE, null, ex);}
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return text;
    }
    
    private static Text format(Text text, String nodeP, String nodeC, Player player){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(nodeP, nodeC).getList(TypeToken.of(String.class));
        } catch (ObjectMappingException ex) { Logger.getLogger(MessageManager.class.getName()).log(Level.SEVERE, null, ex);}
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        msg = msg.replaceAll("%name%", player.getName());
        msg = msg.replaceAll("%world%", player.getWorld().getName());
        
        text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return text;
    }
    
    private static Text format(Text text, String nodeP, String nodeC, Player player, String var1, String var2){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(nodeP, nodeC).getList(TypeToken.of(String.class));
        } catch (ObjectMappingException ex) { Logger.getLogger(MessageManager.class.getName()).log(Level.SEVERE, null, ex);}
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        msg = msg.replaceAll("%name%", player.getName());
        msg = msg.replaceAll("%world%", player.getWorld().getName());
        msg = msg.replaceAll("%var1%", var1);
        msg = msg.replaceAll("%var2%", var2);
        
        text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return text;
    }
    
    private static Text format(Text text, String nodeP, String nodeC, String var1, String var2){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(nodeP, nodeC).getList(TypeToken.of(String.class));
        } catch (ObjectMappingException ex) { Logger.getLogger(MessageManager.class.getName()).log(Level.SEVERE, null, ex);}
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        msg = msg.replaceAll("%var1%", var1);
        msg = msg.replaceAll("%var2%", var2);
        
        text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return text;
    }
    
    private static Text format(Text text, String nodeP, String nodeC, Player player, String var1, String var2, String owner, String allow, String plot){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(nodeP, nodeC).getList(TypeToken.of(String.class));
        } catch (ObjectMappingException ex) { Logger.getLogger(MessageManager.class.getName()).log(Level.SEVERE, null, ex);}
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        msg = msg.replaceAll("%name%", player.getName());
        msg = msg.replaceAll("%world%", player.getWorld().getName());
        msg = msg.replaceAll("%allow%", allow);
        msg = msg.replaceAll("%owner%", owner);
        msg = msg.replaceAll("%plot%", plot);
        msg = msg.replaceAll("%var1%", var1);
        msg = msg.replaceAll("%var2%", var2);
        
        text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return text;
    }
    
    //-------------------------
    // Message général serveur
    //-------------------------
    
    public static Text JOIN_MESSAGE(Player player){return format(JOIN_MESSAGE, "SERVER", "JOIN_MESSAGE", player);}
    
    public static Text EVENT_LOGIN_MESSAGE(Player player){return format(EVENT_LOGIN_MESSAGE, "SERVER", "EVENT_LOGIN_MESSAGE", player);}
        
    public static Text FIRSTJOIN_MESSAGE(Player player){return format(FIRSTJOIN_MESSAGE, "SERVER", "FIRSTJOIN_MESSAGE", player);}
    
    public static Text FIRSTJOIN_BROADCAST_MESSAGE(Player player){return format(FIRSTJOIN_BROADCAST_MESSAGE, "SERVER", "FIRSTJOIN_BROADCAST_MESSAGE", player);}
    
    public static Text EVENT_DISCONNECT_MESSAGE(Player player){return format(EVENT_DISCONNECT_MESSAGE, "SERVER", "EVENT_DISCONNECT_MESSAGE", player);}
    
    public static Text NAME_CHANGE(String oldName, String newName){return format(NAME_CHANGE, "SERVER", "NAME_CHANGE", oldName, newName);}
    
    public static Text INVENTORY_CLEARED(){return format(INVENTORY_CLEARED, "SERVER", "INVENTORY_CLEARED");}
    
    public static Text CLEARINVENTORY_SUCCESS(String target){return format(CLEARINVENTORY_SUCCESS, "SERVER", "CLEARINVENTORY_SUCCESS",target, "");}
    
    public static Text FLY_ENABLED(){return format(FLY_ENABLED, "SERVER", "FLY_ENABLED");}
    
    public static Text FLY_DISABLED(){return format(FLY_DISABLED, "SERVER", "FLY_DISABLED");}
    
    public static Text FLY_GIVEN(String player){return format(FLY_GIVEN, "SERVER", "FLY_GIVEN",player, "");}
    
    public static Text FLY_RETIRED(String player){return format(FLY_RETIRED, "SERVER", "FLY_RETIRED",player, "");}
    
    //-------------------------
    // Message EXCEPTION / ERROR
    //-------------------------
    
    public static Text ERROR(){return format(ERROR, "EXCEPTION","ERROR");}
    
    public static Text NO_PERMISSIONS(){return format(NO_PERMISSIONS, "EXCEPTION","NO_PERMISSIONS");}
    
    public static Text NO_CONSOLE(){return format(NO_CONSOLE, "EXCEPTION", "NO_CONSOLE");}
    
    public static Text WRONG_CHARACTERS_NUMBER(String minLength, String maxLength){return format(WRONG_CHARACTERS_NUMBER, "EXCEPTION", "WRONG_CHARACTERS_NUMBER", minLength, maxLength);}
    
    public static Text WRONG_NAME(){return format(WRONG_NAME, "EXCEPTION", "WRONG_NAME");}
    
    public static Text NAME_ALREADY_USED(){return format(NAME_ALREADY_USED, "EXCEPTION", "NAME_ALREADY_USED");}
    
    public static Text NOT_FOUND(String name){return format(NOT_FOUND, "EXCEPTION", "NOT_FOUND",name, "");}
    
    public static Text NOT_CONNECTED(String name){return format(NOT_CONNECTED, "EXCEPTION", "NOT_CONNECTED",name, "");}
    
    public static Text DATA_NOT_FOUND(String player){return format(DATA_NOT_FOUND, "EXCEPTION", "DATA_NOT_FOUND",player, "");}
    
    public static Text CANNOT_EJECT_OWNER(){return format(CANNOT_EJECT_OWNER, "EXCEPTION", "CANNOT_EJECT_OWNER");}
    
    //-------------------------
    // Message DEAD_MSG
    //-------------------------
    
    public static Text KILLED_BY(String player, String killer){return format(KILLED_BY, "DEAD_MSG", "KILLED_BY",player,killer);}
    
    public static Text SUICIDE(String player){return format(SUICIDE, "DEAD_MSG", "SUICIDE",player, "");}
    
    //-------------------------
    // Message weather / time
    //-------------------------
    
    public static Text SUN_MESSAGE(Player player){return format(SUN_MESSAGE, "WEATHER-TIME","SUN_MESSAGE", player);}
    
    public static Text RAIN_MESSAGE(Player player){return format(RAIN_MESSAGE, "WEATHER-TIME","RAIN_MESSAGE", player);}
    
    public static Text DAY_MESSAGE(Player player){return format(DAY_MESSAGE, "WEATHER-TIME","DAY_MESSAGE", player);}
    
    public static Text NIGHT_MESSAGE(Player player){return format(NIGHT_MESSAGE, "WEATHER-TIME","NIGHT_MESSAGE", player);}
    
    public static Text STORM_MESSAGE(Player player){return format(STORM_MESSAGE, "WEATHER-TIME","STORM_MESSAGE", player);}
    
    //-------------------------
    // Message FACTION
    //-------------------------
    
    public static Text NO_FACTION(){return format(NO_FACTION, "FACTION", "NO_FACTION");}
    
    public static Text WRONG_RANK(){return format(WRONG_RANK, "FACTION", "WRONG_RANK");}
    
    public static Text NOT_IN_SAME_FACTION(String targetName){return format(NOT_IN_SAME_FACTION, "FACTION", "NOT_IN_SAME_FACTION", targetName, "");}
    
    public static Text ALREADY_FACTION_MEMBER(){return format(ALREADY_FACTION_MEMBER, "FACTION", "ALREADY_FACTION_MEMBER");}
    
    public static Text OWNER_CANNOT_LEAVE(){return format(OWNER_CANNOT_LEAVE, "FACTION", "OWNER_CANNOT_LEAVE");}
    
    public static Text FACTION_NEW_CHEF(String targetName, String factionName){return format(FACTION_NEW_CHEF, "FACTION", "FACTION_NEW_CHEF", targetName, factionName);}
    
    public static Text FACTION_CHEF_GRADE_GIVEN(String targetName){return format(FACTION_CHEF_GRADE_GIVEN, "FACTION", "FACTION_CHEF_GRADE_GIVEN", targetName, "");}
    
    public static Text FACTION_YOU_ARE_NEW_CHEF(){return format(FACTION_YOU_ARE_NEW_CHEF, "FACTION", "FACTION_YOU_ARE_NEW_CHEF");}
    
    public static Text GUIDE_FACTION(){return format(GUIDE_FACTION, "FACTION", "GUIDE_FACTION");}
    
    public static Text FACTION_CREATED_SUCCESS(String factionName){return format(FACTION_CREATED_SUCCESS, "FACTION", "FACTION_CREATED_SUCCESS", factionName, "");}
    
    public static Text FACTION_RENAMED_SUCCESS(String oldName, String newName){return format(FACTION_RENAMED_SUCCESS, "FACTION", "FACTION_RENAMED_SUCCESS", oldName, newName);}
    
    public static Text FACTION_DELETED_SUCCESS(String factionName){return format(FACTION_DELETED_SUCCESS, "FACTION", "FACTION_DELETED_SUCCESS", factionName, "");}
    
    public static Text LEAVING_FACTION_SUCCESS(String factionName){return format(LEAVING_FACTION_SUCCESS, "FACTION", "LEAVING_FACTION_SUCCESS", factionName, "");}
    
    public static Text FACTION_MEMBER_REMOVED_SUCCESS(String targetName){return format(FACTION_MEMBER_REMOVED_SUCCESS, "FACTION", "FACTION_MEMBER_REMOVED_SUCCESS", targetName, "");}
            
    public static Text FACTION_RETURNED_BY(String src){return format(FACTION_RETURNED_BY, "FACTION", "FACTION_RETURNED_BY", src, "");}
    
    public static Text FACTION_DELETED_NOTIFICATION(String factionName){return format(FACTION_DELETED_NOTIFICATION, "FACTION", "FACTION_DELETED_NOTIFICATION", factionName, "");}
    
    public static Text ONHOVER_FACTION_MOREACTIONS(){return format(ONHOVER_FACTION_MOREACTIONS, "FACTION", "ONHOVER_FACTION_MOREACTIONS");}
    
    public static Text ONHOVER_FACTION_LEAVE(){return format(ONHOVER_FACTION_LEAVE, "FACTION", "ONHOVER_FACTION_LEAVE");}
    
    public static Text ONHOVER_FACTION_DEPOSIT(){return format(ONHOVER_FACTION_DEPOSIT, "FACTION", "ONHOVER_FACTION_DEPOSIT");}
    
    public static Text ONHOVER_FACTION_INVIT(){return format(ONHOVER_FACTION_INVIT, "FACTION", "ONHOVER_FACTION_INVIT");}
    
    public static Text ONHOVER_FACTION_SETGRADE(){return format(ONHOVER_FACTION_SETGRADE, "FACTION", "ONHOVER_FACTION_SETGRADE");}
    
    public static Text ONHOVER_FACTION_REMOVEMEMBER(){return format(ONHOVER_FACTION_REMOVEMEMBER, "FACTION", "ONHOVER_FACTION_REMOVEMEMBER");}
    
    public static Text ONHOVER_FACTION_WITHDRAWAL(){return format(ONHOVER_FACTION_WITHDRAWAL, "FACTION", "ONHOVER_FACTION_WITHDRAWAL");}
    
    public static Text ONHOVER_FACTION_RENAME(){return format(ONHOVER_FACTION_RENAME, "FACTION", "ONHOVER_FACTION_RENAME");}
    
    public static Text ONHOVER_FACTION_DELETE(){return format(ONHOVER_FACTION_DELETE, "FACTION", "ONHOVER_FACTION_DELETE");}
    
    public static Text ONHOVER_FACTION_LIST_LVL10(String factionName, String ownerName){return format(ONHOVER_FACTION_LIST_LVL10, "FACTION", "ONHOVER_FACTION_LIST_LVL10", factionName, ownerName);}
    
    //-------------------------
    // Message PLOT / PARCELLE
    //-------------------------
    
    public static Text BUYING_COST_PLOT(Player player, String var1, String var2){return format(BUYING_COST_PLOT, "PLOT", "BUYING_COST_PLOT", player, var1, var2);}
    
    public static Text PROTECT_PLOT_SUCCESS(Player player, String var1){return format(PROTECT_PLOT_SUCCESS, "PLOT", "PROTECT_PLOT_SUCCESS", player, var1, "");}
    
    public static Text BEDROCK2SKY_PROTECT_PLOT_SUCCESS(Player player, String var1){return format(BEDROCK2SKY_PROTECT_PLOT_SUCCESS, "PLOT", "BEDROCK2SKY_PROTECT_PLOT_SUCCESS", player, var1, "");}
    
    public static Text PROTECT_LOADED_PLOT(Player player, String var1){return format(PROTECT_LOADED_PLOT, "PLOT", "PROTECT_LOADED_PLOT", player, var1, "");}
    
    public static Text UNDEFINED_PLOT_ANGLES(){return format(UNDEFINED_PLOT_ANGLES, "PLOT", "UNDEFINED_PLOT_ANGLES");}
        
    public static Text ALREADY_OWNED_PLOT(){return format(ALREADY_OWNED_PLOT, "PLOT", "ALREADY_OWNED_PLOT");}
    
    public static Text NO_PLOT(){return format(NO_PLOT, "PLOT", "NO_PLOT");}
    
    public static Text PLOT_INFO(Player player, String owner, String allow, String plot){return format(PLOT_INFO, "PLOT", "PLOT_INFO", player, "","", owner, allow, plot);}
    
    public static Text PLOT_LIST(){return format(PLOT_LIST, "PLOT", "PLOT_LIST");}
     
    public static Text TARGET_PLOT_LIST(String target){return format(TARGET_PLOT_LIST, "PLOT", "TARGET_PLOT_LIST",target,"");}
    
    public static Text PLOT_PROTECTED(){return format(PLOT_PROTECTED, "PLOT", "PLOT_PROTECTED");}
    
    public static Text PLOT_NO_ENTER(){return format(PLOT_NO_ENTER, "PLOT", "PLOT_NO_ENTER");}
    
    public static Text PLOT_NO_FLY(){return format(PLOT_NO_FLY, "PLOT", "PLOT_NO_FLY");}
    
    public static Text PLOT_NO_BREAK(){return format(PLOT_NO_BREAK, "PLOT", "PLOT_NO_BREAK");}
    
    public static Text PLOT_NO_BUILD(){return format(PLOT_NO_BUILD, "PLOT", "PLOT_NO_BUILD");}
    
    public static Text PLOT_NO_FIRE(){return format(PLOT_NO_FIRE, "PLOT", "PLOT_NO_FIRE");}
    
    public static Text PLOT_NO_EXIT(){return format(PLOT_NO_EXIT, "PLOT", "PLOT_NO_EXIT");}
    
    //-------------------------
    // Message ECONOMY
    //-------------------------
    
    public static Text MISSING_BALANCE(){return format(MISSING_BALANCE, "ECONOMY", "MISSING_BALANCE");}
    
    public static Text FACTION_MISSING_BALANCE(){return format(FACTION_MISSING_BALANCE, "ECONOMY", "FACTION_MISSING_BALANCE");}
    
    public static Text WITHDRAW_SUCCESS(String amount){return format(WITHDRAW_SUCCESS, "ECONOMY", "WITHDRAW_SUCCESS",amount, "");}
    
    public static Text DEPOSIT_SUCCESS(String amount){return format(DEPOSIT_SUCCESS, "ECONOMY", "DEPOSIT_SUCCESS",amount, "");}
    
    //-------------------------
    // Message TELEPORATION
    //-------------------------
    
    public static Text TP_AT_COORDS(){return format(TP_AT_COORDS, "TELEPORTATION", "TP_AT_COORDS");}
    
    public static Text TP_BACK(Player player){return format(TP_BACK, "TELEPORATION", "TP_BACK",player);}
    
    public static Text HOME_ALREADY_EXIST(){return format(HOME_ALREADY_EXIST, "TELEPORTATION","HOME_ALREADY_EXIST");}
    
    public static Text HOME_SET_SUCCESS(Player player, String var1){return format(HOME_SET_SUCCESS, "TELEPORTATION","HOME_SET_SUCCESS",player,var1,"");}
    
    public static Text HOME_DEL_SUCCESS(Player player, String var1){return format(HOME_DEL_SUCCESS, "TELEPORTATION","HOME_DEL_SUCCESS",player,var1,"");}
    
    public static Text NB_HOME(Player player, String var1, String var2){return format(NB_HOME, "TELEPORTATION","NB_HOME",player,var1,var2);}
    
    public static Text NB_ALLOWED_HOME(Player player, String var1){return format(NB_ALLOWED_HOME, "TELEPORTATION","NB_ALLOWED_HOME", player, var1, "");}
    
    public static Text HOME_NOT_FOUND(){return format(HOME_NOT_FOUND, "TELEPORTATION","HOME_NOT_FOUND");}
    
    public static Text HOME_TP_SUCCESS(Player player, String var1){return format(HOME_TP_SUCCESS, "TELEPORTATION","HOME_TP_SUCCESS", player, var1, "");}
    
    //-------------------------
    // Message Commande PlayerInfo
    //-------------------------

    public static Text ONHOVER_PI_NAME(String UUID){return format(ONHOVER_PI_NAME, "CMD_PLAYERINFO","ONHOVER_PI_NAME",UUID, "");}
                
    //-------------------------
    // Message WORLD
    //-------------------------
    
    public static Text WORLD_ALREADY_EXIST(){return format(WORLD_ALREADY_EXIST, "WORLD","WORLD_ALREADY_EXIST");}
    
    public static Text WORLD_CREATED(Player player, String var1){return format(WORLD_CREATED, "WORLD","WORLD_CREATED",player,var1,"");}
    
    public static Text TELEPORTED_TO_WORLD(Player player, String var1){return format(TELEPORTED_TO_WORLD, "WORLD","TELEPORTED_TO_WORLD",player,var1,"");}
    
    public static Text OTHER_TELEPORTED_TO_WORLD(Player player, String var1){return format(OTHER_TELEPORTED_TO_WORLD, "WORLD","OTHER_TELEPORTED_TO_WORLD",player,var1,"");}

    public static Text WORLD_PROPERTIES_ERROR(){return format(WORLD_PROPERTIES_ERROR, "WORLD","WORLD_PROPERTIES_ERROR");}
    
    public static Text WORLD_CREATION_ERROR(){return format(WORLD_CREATION_ERROR, "WORLD","WORLD_CREATION_ERROR");}
    
    //-------------------------
    // Message PORTAL
    //-------------------------
    
    public static Text PROTECT_PORTAL(){return format(PROTECT_PORTAL, "PORTAL","PROTECT_PORTAL");}
    
    //-------------------------
    // Message SHOP
    //-------------------------
    
    public static Text SHOP_SALE(){return format(SHOP_SALE, "SHOP","SHOP_SALE");}
    
    public static Text SHOP_BUY(){return format(SHOP_BUY, "SHOP","SHOP_BUY");}
              
    
    public static Text USAGE(String usage){
        Text USAGE = (Text.of(TextColors.DARK_RED, "Usage: ", TextColors.RED, usage)); 
        return USAGE;
    }
    
    public static Text MESSAGE(List<String> list, Player player, String var){
        Text MessageText = Text.of();
        String msg = "";
        for(String s : list) {
            if(s.equals(list.get(list.size()-1))){
                msg = msg + s; 
            } else {
                msg = msg + s + "\n"; 
            }       
        }
        msg = msg.replaceAll("%name%", player.getName());
        msg = msg.replaceAll("%world%", player.getWorld().getName());
        msg = msg.replaceAll("%var1%", var);
        
        MessageText = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return MessageText;
    }
    
    public static Text MESSAGE(String msg, Player player, String var){
        Text MessageText = Text.of();
        msg = msg.replaceAll("%name%", player.getName());
        msg = msg.replaceAll("%world%", player.getWorld().getName());
        msg = msg.replaceAll("%var1%", var);
        
        MessageText = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return MessageText;
    }
    
    public static Text MESSAGE(String msg, Player player){
        Text MessageText = Text.of();
        msg = msg.replaceAll("%name%", player.getName());
        msg = msg.replaceAll("%world%", player.getWorld().getName());
        
        MessageText = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return MessageText;
    }
    
    public static Text MESSAGE(String msg){
        Text MessageText = Text.of();       
        MessageText = Text.builder().append(TextSerializers.formattingCode('&').deserialize(msg)).toText();
        return MessageText;
    }
}
