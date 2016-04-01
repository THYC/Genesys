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
    private static Text NO_FACTION;
    private static Text WRONG_RANK;
    private static Text ALREADY_FACTION_MEMBER;
    private static Text FACTION_CREATED_SUCCESS;
    private static Text FACTION_RENAMED_SUCCESS;
    private static Text FACTION_DELETED_SUCCESS;
    private static Text BUYING_COST_PLOT;
    private static Text PROTECT_PLOT_SUCCESS;
    private static Text BEDROCK2SKY_PROTECT_PLOT_SUCCESS;
    private static Text PROTECT_LOADED_PLOT;
    private static Text UNDEFINED_PLOT_ANGLES;
    private static Text ALREADY_OWNED_PLOT;
    private static Text PLOT_NAME_ALREADY_USED;
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
    private static Text TRANSFER_SUCCESS;
    private static Text HOME_ALREADY_EXIST;
    private static Text HOME_SET_SUCCESS;
    private static Text HOME_DEL_SUCCESS;
    private static Text NB_HOME;
    private static Text NB_ALLOWED_HOME;
    private static Text HOME_NOT_FOUND;
    private static Text HOME_ERROR;
    private static Text HOME_TP_SUCCESS;
    private static Text PLAYER_NOT_FOUND;
    private static Text PLAYER_DATA_NOT_FOUND;
    private static Text WORLD_ALREADY_EXIST;
    private static Text WORLD_CREATED;
    private static Text WORLD_NOT_FOUND;
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
    private static Text KILLED_BY;
    private static Text SUICIDE;
       
    public static File file = new File("config/genesys/message.conf");
    public static final ConfigurationLoader<?> manager = HoconConfigurationLoader.builder().setFile(file).build();
    public static ConfigurationNode message = manager.createEmptyNode(ConfigurationOptions.defaults());
        
    public static void init() {
        try {
            if (!file.exists()) {
                file.createNewFile();
                
                List<String> msg = new ArrayList<>();
                msg.add("&cVous n'avez pas la permission pour utiliser cette commande !");
                message.getNode("NO_PERMISSIONS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cCette commande ne peut pas s'ex\351cuter sur la console");
                message.getNode("NO_CONSOLE").setValue(msg);
                manager.save(message);
                                
                msg = new ArrayList<>();
                msg.add("&6Bienvenue, &e%name%!");
                msg.add("&6Tu es sur la map &e%world%!\n");
                message.getNode("JOIN_MESSAGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&7%name% a rejoint le serveur");
                message.getNode("EVENT_LOGIN_MESSAGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eSalut &6%name%&e, c'est visiblement la premi\350re fois que tu viens !");
                msg.add("&7Assure-toi d'avoir bien lu le r\350glement en tapant &e/rules");
                msg.add("&7Si tu veux participer \340 la vie du serveur ou te tenir inform\351");
                msg.add("&7inscris-toi sur notre forum &bhttp://craft.teraoctet.net\n");
                message.getNode("FIRSTJOIN_MESSAGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&9%name% est nouveau sur le serveur !");
                message.getNode("FIRSTJOIN_BROADCAST_MESSAGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&8%var1% &7a chang\351 son nom en &8%var2%");
                message.getNode("NAME_CHANGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&7%name% s'est d\351connect\351");
                message.getNode("EVENT_DISCONNECT_MESSAGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cCe nom est incorrect !");
                message.getNode("WRONG_NAME").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&e%name% &6a programm\351 le beau temps sur &e%world%");
                message.getNode("SUN_MESSAGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&e%name% &6a programm\351 la pluie sur &e%world%");
                message.getNode("RAIN_MESSAGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&e%name% &6a programm\351 l'orage sur &e%world%");
                message.getNode("STORM_MESSAGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&e%name% &6a mis le jour sur &e%world%");
                message.getNode("DAY_MESSAGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&e%name% &6a mis la nuit sur &e%world%");
                message.getNode("NIGHT_MESSAGE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cVous \352tes dans aucune faction !");
                message.getNode("NO_FACTION").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cVous \352tes d\351j\340 dans une faction !");
                message.getNode("ALREADY_FACTION_MEMBER").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cVotre rang dans la faction ne vous permet pas d'utiliser \347a !");
                message.getNode("WRONG_RANK").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eVous venez de cr\351er la faction \"&6%var1%&e\"");
                message.getNode("FACTION_CREATED_SUCCESS").setValue(msg);
                manager.save(message);
                        
                msg = new ArrayList<>();
                msg.add("&eVous de supprimer votre faction \"&6%var1%&e\"");
                message.getNode("FACTION_DELETED_SUCCESS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eLa faction \"&6%var1%&e\" a \351t\351 renomm\351e en \"&6%var2%&e\"");
                message.getNode("FACTION_RENAMED_SUCCESS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&6Le co\373t pour prot\351ger cette parcelle est de &e%var1% \351meraudes");
                msg.add("&6Vous poss\351dez actuellement &e%var2% \351meraude(s) en banque\n");
                message.getNode("BUYING_COST_PLOT").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&6La parcelle : &e%var1% &6 est maintenant prot\351g\351e : ");
                msg.add("&6Vous pouvez modifier les param\350tres avec la commande &e/plot flag\n");
                message.getNode("PROTECT_PLOT_SUCCESS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&6La parcelle : &e%var1% &6 est maintenant prot\351g\351e de la bedrock jusqu'au ciel");
                msg.add("&6Vous pouvez modifier les param\350tres avec la commande &e/plot flag\n");
                message.getNode("BEDROCK2SKY_PROTECT_PLOT_SUCCESS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&6Parcelle &e%var1% &6: protection activ\351e");
                message.getNode("PROTECT_LOADED_PLOT").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&cVous n'avez pas d\351fini les angles de votre parcelle");
                msg.add("&cLes angles se d\351finissent en utilisant une pelle en bois :");
                msg.add("&cAngle1 = clic gauche / Angle2 = clic droit\n");
                message.getNode("UNDEFINED_PLOT_ANGLES").setValue(msg);
                manager.save(message);
                                
                msg = new ArrayList<>();
                msg.add("&cVous ne pouvez pas cr\351er cette parcelle, d\351j\340 une parcelle prot\351g\351e dans cette s\351lection !");         
                message.getNode("ALREADY_OWNED_PLOT").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cCe nom de parcelle est d\351j\340 utilis\351 !");       
                message.getNode("PLOT_NAME_ALREADY_USED").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cAucune parcelle \340 cette position !");
                message.getNode("NO_PLOT").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cLe fly est interdit sur cette parcelle");
                message.getNode("PLOT_NO_FLY").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&5L'acc\350s \340 cette parcelle est interdit");
                message.getNode("PLOT_NO_ENTER").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&6Vous \352tes sur une parcelle proteg\351e : &e%plot%");
                msg.add("&6Propri\351taire : &e%owner%");
                msg.add("&6Habitant(s) : &e%allow%\n");
                message.getNode("PLOT_INFO").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&bListe des parcelles vous appartenant :");
                message.getNode("PLOT_LIST").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("\n&bListe des parcelles appartenant \340 &3%var1% :");
                message.getNode("TARGET_PLOT_LIST").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&5Cette parcelle est prot\351g\351e par un sort magique !");
                message.getNode("PLOT_PROTECTED").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cVous ne poss\351dez pas assez d'assez d'\351meraudes sur votre compte, tapez &4/bank &cpour voir votre solde");
                message.getNode("MISSING_BALANCE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eVirement effectu\351 de &6%var1% \351mraudes &eavec succès !");
                message.getNode("MISSING_BALANCE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cHome d\351j\340 d\351fini, veuillez le supprimer avant de pouvoir le red\351finir");
                message.getNode("HOME_ALREADY_EXIST").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eLe home &6%var1% &ea \351t\351 cr\351\351 avec succ\350s");
                message.getNode("HOME_SET_SUCCESS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eLe home &6%var1% &ea \351t\351 supprim\351 avec succ\350s");
                message.getNode("HOME_DEL_SUCCESS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eVous poss\351dez actuellement &6%var1% &esur &6%var2% &ehome possible");
                message.getNode("NB_HOME").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eVous \352tes seulement autoris\351 \340 poss\351der %var1% home");
                message.getNode("NB_ALLOWED_HOME").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cHome introuvable !");
                msg.add("&cVeuillez utiliser la commande /sethome pour le d\351finir");
                message.getNode("HOME_NOT_FOUND").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cUne erreur est survenue avec votre Home, t\351l\351portation impossible !");
                message.getNode("HOME_ERROR").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eT\351l\351portation sur votre home : &6%var1%");
                message.getNode("HOME_TP_SUCCESS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&4%name% &cest introuvable");
                message.getNode("PLAYER_NOT_FOUND").setValue(msg);
                manager.save(message);
                        
                msg = new ArrayList<>();
                msg.add("&4%var1% &cn'est pas enregistr\351 dans la base de donn\351e");
                message.getNode("PLAYER_DATA_NOT_FOUND").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cCe monde existe d\351j\340");
                message.getNode("WORLD_ALREADY_EXIST").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&c%var1% a \351t\351 cr\351\351 avec succ\350s");
                message.getNode("WORLD_CREATED").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&4%var1% &cest introuvable");
                message.getNode("WORLD_NOT_FOUND").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&4%ERREUR : &cLe monde n'a pas pu \352tre cr\351\351");
                message.getNode("WORLD_CREATION_ERROR").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&4%ERREUR : &cLes propri\351t\351s du monde ne peuvent pas \352tre cr\351\351s");
                message.getNode("WORLD_PROPERTIES_ERROR").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eT\351l\351portation sur : &6%world%");
                message.getNode("TELEPORTED_TO_WORLD").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&6%name% &ea \351t\351 t\351l\351port\351 sur : &6%world%");
                message.getNode("OTHER_TELEPORTED_TO_WORLD").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cPortail prot\351g\351");
                message.getNode("PROTECT_PORTAL").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&6Woshhhh ..!");
                message.getNode("TP_BACK").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cVotre inventaire a \351t\351 \351ffac\351");
                message.getNode("INVENTORY_CLEARED").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eL'inventaire de &6%var1% a \351t\351 supprim\351");
                message.getNode("CLEARINVENTORY_SUCCESS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eFly activ\351");
                message.getNode("FLY_ENABLED").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eFly d\351sactiv\351");
                message.getNode("FLY_DISABLED").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eVous avez activ\351 le fly de &6%var1%");
                message.getNode("FLY_GIVEN").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eVous avez d\351sactiv\351 le fly de &6%var1%");
                message.getNode("FLY_RETIRED").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("T\351l\351portation aux coordonn\351es");
                message.getNode("TP_AT_COORDS").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&7%var1% a \351t\351 tu\351 par %var2%");
                message.getNode("KILLED_BY").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&7%var1% s'est suicid\351");
                message.getNode("SUICIDE").setValue(msg);
                manager.save(message);
                
            }
            message = manager.load();

        } catch (IOException e) {}
		
    }
    
    public static Text formatText(String string){
        Text text = Text.builder().append(TextSerializers.formattingCode('&').deserialize(string)).toText();
        return text;
    }
    
    private static Text format(Text text, String node){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(node).getList(TypeToken.of(String.class));
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
    
    private static Text format(Text text, String node, Player player){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(node).getList(TypeToken.of(String.class));
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
    
    private static Text format(Text text, String node, Player player, String var1, String var2){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(node).getList(TypeToken.of(String.class));
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
    
    private static Text format(Text text, String node, String var1, String var2){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(node).getList(TypeToken.of(String.class));
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
    
    private static Text format(Text text, String node, Player player, String var1, String var2, String owner, String allow, String plot){
        List<String> list = new ArrayList<>();
        try { list = message.getNode(node).getList(TypeToken.of(String.class));
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
    
    public static Text NO_PERMISSIONS(){return format(NO_PERMISSIONS, "NO_PERMISSIONS");}
    
    public static Text NO_CONSOLE(){return format(NO_CONSOLE, "NO_CONSOLE");}
    
    public static Text JOIN_MESSAGE(Player player){return format(JOIN_MESSAGE, "JOIN_MESSAGE", player);}
    
    public static Text EVENT_LOGIN_MESSAGE(Player player){return format(EVENT_LOGIN_MESSAGE, "EVENT_LOGIN_MESSAGE", player);}
        
    public static Text FIRSTJOIN_MESSAGE(Player player){return format(FIRSTJOIN_MESSAGE, "FIRSTJOIN_MESSAGE", player);}
    
    public static Text FIRSTJOIN_BROADCAST_MESSAGE(Player player){return format(FIRSTJOIN_BROADCAST_MESSAGE, "FIRSTJOIN_BROADCAST_MESSAGE", player);}
    
    public static Text EVENT_DISCONNECT_MESSAGE(Player player){return format(EVENT_DISCONNECT_MESSAGE, "EVENT_DISCONNECT_MESSAGE", player);}
    
    public static Text NAME_CHANGE(String oldName, String newName){return format(NAME_CHANGE, "NAME_CHANGE", oldName, newName);}
    
    public static Text WRONG_NAME(){return format(WRONG_NAME, "WRONG_NAME");}
    
    public static Text SUN_MESSAGE(Player player){return format(SUN_MESSAGE, "SUN_MESSAGE", player);}
    
    public static Text RAIN_MESSAGE(Player player){return format(RAIN_MESSAGE, "RAIN_MESSAGE", player);}
    
    public static Text DAY_MESSAGE(Player player){return format(DAY_MESSAGE, "DAY_MESSAGE", player);}
    
    public static Text NIGHT_MESSAGE(Player player){return format(NIGHT_MESSAGE, "NIGHT_MESSAGE", player);}
    
    public static Text STORM_MESSAGE(Player player){return format(STORM_MESSAGE, "STORM_MESSAGE", player);}
    
    public static Text NO_FACTION(){return format(NO_FACTION, "NO_FACTION");}
    
    public static Text WRONG_RANK(){return format(WRONG_RANK, "WRONG_RANK");}
    
    public static Text ALREADY_FACTION_MEMBER(){return format(ALREADY_FACTION_MEMBER, "ALREADY_FACTION_MEMBER");}
    
    public static Text FACTION_CREATED_SUCCESS(String factionName){return format(FACTION_CREATED_SUCCESS, "FACTION_CREATED_SUCCESS", factionName, "");}
    
    public static Text FACTION_RENAMED_SUCCESS(String oldName, String newName){return format(FACTION_RENAMED_SUCCESS, "FACTION_RENAMED_SUCCESS", oldName, newName);}
    
    public static Text FACTION_DELETED_SUCCESS(String factionName){return format(FACTION_DELETED_SUCCESS, "FACTION_DELETED_SUCCESS", factionName, "");}
    
    public static Text BUYING_COST_PLOT(Player player, String var1, String var2){return format(BUYING_COST_PLOT, "BUYING_COST_PLOT", player, var1, var2);}
    
    public static Text PROTECT_PLOT_SUCCESS(Player player, String var1){return format(PROTECT_PLOT_SUCCESS, "PROTECT_PLOT_SUCCESS", player, var1, "");}
    
    public static Text BEDROCK2SKY_PROTECT_PLOT_SUCCESS(Player player, String var1){return format(BEDROCK2SKY_PROTECT_PLOT_SUCCESS, "BEDROCK2SKY_PROTECT_PLOT_SUCCESS", player, var1, "");}
    
    public static Text PROTECT_LOADED_PLOT(Player player, String var1){return format(PROTECT_LOADED_PLOT, "PROTECT_LOADED_PLOT", player, var1, "");}
    
    public static Text UNDEFINED_PLOT_ANGLES(){return format(UNDEFINED_PLOT_ANGLES, "UNDEFINED_PLOT_ANGLES");}
        
    public static Text ALREADY_OWNED_PLOT(){return format(ALREADY_OWNED_PLOT, "ALREADY_OWNED_PLOT");}
    
    public static Text PLOT_NAME_ALREADY_USED(){return format(PLOT_NAME_ALREADY_USED, "PLOT_NAME_ALREADY_USED");}
    
    public static Text NO_PLOT(){return format(NO_PLOT, "NO_PLOT");}
    
    public static Text PLOT_INFO(Player player, String owner, String allow, String plot){return format(PLOT_INFO, "PLOT_INFO", player, "","", owner, allow, plot);}
    
    public static Text PLOT_LIST(){return format(PLOT_LIST, "PLOT_LIST");}
     
    public static Text TARGET_PLOT_LIST(String target){return format(TARGET_PLOT_LIST, "TARGET_PLOT_LIST",target,"");}
    
    public static Text PLOT_PROTECTED(){return format(PLOT_PROTECTED, "PLOT_PROTECTED");}
    
    public static Text PLOT_NO_ENTER(){return format(PLOT_NO_ENTER, "PLOT_NO_ENTER");}
    
    public static Text PLOT_NO_FLY(){return format(PLOT_NO_FLY, "PLOT_NO_FLY");}
    
    public static Text PLOT_NO_BREAK(){return format(PLOT_NO_BREAK, "PLOT_NO_BREAK");}
    
    public static Text PLOT_NO_BUILD(){return format(PLOT_NO_BUILD, "PLOT_NO_BUILD");}
    
    public static Text PLOT_NO_FIRE(){return format(PLOT_NO_FIRE, "PLOT_NO_FIRE");}
    
    public static Text PLOT_NO_EXIT(){return format(PLOT_NO_EXIT, "PLOT_NO_EXIT");}
    
    public static Text MISSING_BALANCE(){return format(MISSING_BALANCE, "MISSING_BALANCE");}
    
    public static Text TRANSFER_SUCCESS(String amount){return format(TRANSFER_SUCCESS, "TRANSFER_SUCCESS",amount, "");}
    
    public static Text HOME_ALREADY_EXIST(){return format(HOME_ALREADY_EXIST, "HOME_ALREADY_EXIST");}
    
    public static Text HOME_SET_SUCCESS(Player player, String var1){return format(HOME_SET_SUCCESS, "HOME_SET_SUCCESS",player,var1,"");}
    
    public static Text HOME_DEL_SUCCESS(Player player, String var1){return format(HOME_DEL_SUCCESS, "HOME_DEL_SUCCESS",player,var1,"");}
    
    public static Text NB_HOME(Player player, String var1, String var2){return format(NB_HOME, "NB_HOME",player,var1,var2);}
    
    public static Text NB_ALLOWED_HOME(Player player, String var1){return format(NB_ALLOWED_HOME, "NB_ALLOWED_HOME", player, var1, "");}
    
    public static Text HOME_NOT_FOUND(){return format(HOME_NOT_FOUND, "HOME_NOT_FOUND");}
    
    public static Text HOME_ERROR(){return format(HOME_ERROR, "HOME_ERROR");}
    
    public static Text HOME_TP_SUCCESS(Player player, String var1){return format(HOME_TP_SUCCESS, "HOME_TP_SUCCESS", player, var1, "");}
    
    public static Text PLAYER_NOT_FOUND(Player player){return format(PLAYER_NOT_FOUND, "PLAYER_NOT_FOUND",player);}
    
    public static Text PLAYER_DATA_NOT_FOUND(String player){return format(PLAYER_DATA_NOT_FOUND, "PLAYER_DATA_NOT_FOUND",player, "");}
    
    public static Text WORLD_ALREADY_EXIST(){return format(WORLD_ALREADY_EXIST, "WORLD_ALREADY_EXIST");}
    
    public static Text WORLD_CREATED(Player player, String var1){return format(WORLD_CREATED, "WORLD_CREATED",player,var1,"");}
    
    public static Text TELEPORTED_TO_WORLD(Player player, String var1){return format(TELEPORTED_TO_WORLD, "TELEPORTED_TO_WORLD",player,var1,"");}
    
    public static Text OTHER_TELEPORTED_TO_WORLD(Player player, String var1){return format(OTHER_TELEPORTED_TO_WORLD, "OTHER_TELEPORTED_TO_WORLD",player,var1,"");}
    
    public static Text WORLD_NOT_FOUND(String worldname){return format(WORLD_NOT_FOUND, "WORLD_NOT_FOUND",worldname,"");}
    
    public static Text WORLD_PROPERTIES_ERROR(){return format(WORLD_PROPERTIES_ERROR, "WORLD_PROPERTIES_ERROR");}
    
    public static Text WORLD_CREATION_ERROR(){return format(WORLD_CREATION_ERROR, "WORLD_CREATION_ERROR");}
    
    public static Text PROTECT_PORTAL(){return format(PROTECT_PORTAL, "PROTECT_PORTAL");}
    
    public static Text TP_BACK(Player player){return format(TP_BACK, "TP_BACK",player);}
    
    public static Text INVENTORY_CLEARED(){return format(INVENTORY_CLEARED, "INVENTORY_CLEARED");}
    
    public static Text CLEARINVENTORY_SUCCESS(String target){return format(CLEARINVENTORY_SUCCESS, "CLEARINVENTORY_SUCCESS",target, "");}
    
    public static Text FLY_ENABLED(){return format(FLY_ENABLED, "FLY_ENABLED");}
    
    public static Text FLY_DISABLED(){return format(FLY_DISABLED, "FLY_DISABLED");}
    
    public static Text FLY_GIVEN(String player){return format(FLY_GIVEN, "FLY_GIVEN",player, "");}
    
    public static Text FLY_RETIRED(String player){return format(FLY_RETIRED, "FLY_RETIRED",player, "");}
   
    public static Text TP_AT_COORDS(){return format(TP_AT_COORDS, "TP_AT_COORDS");}
    
    public static Text KILLED_BY(String player, String killer){return format(KILLED_BY, "KILLED_BY",player,killer);}
    
    public static Text SUICIDE(String player){return format(SUICIDE, "SUICIDE",player, "");}
                
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
