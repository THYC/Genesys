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
    private static Text NAME_CHANGE;
    private static Text SUN_MESSAGE;
    private static Text RAIN_MESSAGE;
    private static Text DAY_MESSAGE;
    private static Text NIGHT_MESSAGE;
    private static Text STORM_MESSAGE;
    private static Text AMOUNT_PARCEL;
    private static Text PROTECT_PARCEL;
    private static Text ALL_PROTECT_PARCEL;
    private static Text PROTECT_LOADED_PARCEL;
    private static Text UNDEFINED_PARCEL;
    private static Text RESERVED_PARCEL;
    private static Text PARCEL_NAME_FAILED;
    private static Text NO_PARCEL;
    private static Text PROTECT_NO_FLY;
    private static Text PROTECT_NO_ENTER;
    private static Text PROTECT_NO_BREAK;
    private static Text PROTECT_NO_BUILD;
    private static Text PROTECT_NO_FIRE;
    private static Text PROTECT_NO_LEAVE;
    private static Text INFO_PARCEL;
    private static Text PARCEL_SECURE;
    private static Text MISSING_BALANCE;
    private static Text HOME_ALREADY_EXIST;
    private static Text HOME_SET_SUCCESS;
    private static Text NB_HOME;
    private static Text NB_ALLOWED_HOME;
    private static Text HOME_NOT_FOUND;
    private static Text HOME_ERROR;
    private static Text HOME_TP_SUCCESS;
    private static Text PLAYER_NOT_FOUND;
    private static Text WORLD_ALREADY_EXIST;
    private static Text WORLD_CREATED;
    private static Text WORLD_NOT_FOUND;
    private static Text WORLD_CREATION_ERROR;
    private static Text WORLD_PROPERTIES_ERROR;
    private static Text TELEPORTED_TO_WORLD;
    private static Text PROTECT_PORTAL;
    private static Text TP_BACK;
    private static Text INVENTORY_CLEARED;
    private static Text CLEARINVENTORY_SUCCESS;
    private static Text FLY_ENABLED;
    private static Text FLY_DISABLED;
    private static Text FLY_GIVEN;
    private static Text FLY_RETIRED;
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
                msg.add("&6Tu es sur la map &e%world%!");
                msg.add("&7Bon jeu !\n");
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
                msg.add("&6Le co\373t pour prot\351ger cette parcelle est de &e%var1% \351meraudes");
                msg.add("&6Vous poss\351dez actuellement &e%var2% \351meraude(s) en banque");
                message.getNode("AMOUNT_PARCEL").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&6La parcelle : &e%var1% &6 est maintenant prot\351g\351e : ");
                msg.add("&6Vous pouvez modifier les param\350tres avec la commande &e/parcel flag");
                message.getNode("PROTECT_PARCEL").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&6La parcelle : &e%var1% &6 est maintenant prot\351g\351e de la bedrock jusqu'au ciel");
                msg.add("&6Vous pouvez modifier les param\350tres avec la commande &e/parcel flag");
                message.getNode("ALL_PROTECT_PARCEL").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&6Parcelle &e%var1% &6: protection activ\351e");
                message.getNode("PROTECT_LOADED_PARCEL").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&aVous n'avez pas d\351fini les angles de votre parcelle");
                msg.add("&aLes angles se d\351finissent en utilisant une pelle en bois :");
                msg.add("&aAngle1 = clic gauche / Angle2 = clic droit");
                message.getNode("UNDEFINED_PARCEL").setValue(msg);
                manager.save(message);
                                
                msg = new ArrayList<>();
                msg.add("&cVous ne pouvez pas cr\351er cette parcelle ici");
                msg.add("&cil y a d\351j\340Ã‚Â  une parcelle prot\351g\351e dans cette s\351lection, recommencez");            
                message.getNode("RESERVED_PARCEL").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cCe nom de parcelle est d\351j\340Ã‚Â utilis\351, recommencez");       
                message.getNode("PARCEL_NAME_FAILED").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&6Aucune parcelle a cette position !");
                message.getNode("NO_PARCEL").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cLe fly est interdit sur cette parcelle");
                message.getNode("PROTECT_NO_FLY").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&cL'acc\350s \340 cette parcelle est interdit");
                message.getNode("PROTECT_NO_ENTER").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&6Vous \352tes sur une parcelle proteg\351e : &e%parcel%");
                msg.add("&6Propri\351taire : &e%owner%");
                msg.add("&6Habitant(s) : &e%allow%");
                message.getNode("INFO_PARCEL").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eCette parcelle est prot\351g\351e par un sort magique !");
                message.getNode("PARCEL_SECURE").setValue(msg);
                manager.save(message);
                
                msg = new ArrayList<>();
                msg.add("&eVous ne poss\351dez pas assez d'assez d'\351meraudes sur votre compte, tapez /bank pour voir votre solde");
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
                msg.add("&4%name% &cest introuvable, v\351rifiez l'\351criture");
                message.getNode("PLAYER_NOT_FOUND").setValue(msg);
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
    
    private static Text format(Text text, String node, Player player, String var1, String var2, String owner, String allow, String parcel){
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
        msg = msg.replaceAll("%parcel%", parcel);
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
    
    public static Text NAME_CHANGE(Player player){return format(NAME_CHANGE, "NAME_CHANGE", player);}
    
    public static Text SUN_MESSAGE(Player player){return format(SUN_MESSAGE, "SUN_MESSAGE", player);}
    
    public static Text RAIN_MESSAGE(Player player){return format(RAIN_MESSAGE, "RAIN_MESSAGE", player);}
    
    public static Text DAY_MESSAGE(Player player){return format(DAY_MESSAGE, "DAY_MESSAGE", player);}
    
    public static Text NIGHT_MESSAGE(Player player){return format(NIGHT_MESSAGE, "NIGHT_MESSAGE", player);}
    
    public static Text STORM_MESSAGE(Player player){return format(STORM_MESSAGE, "STORM_MESSAGE", player);}
    
    public static Text AMOUNT_PARCEL(Player player, String var1, String var2){return format(AMOUNT_PARCEL, "AMOUNT_PARCEL", player, var1, var2);}
    
    public static Text PROTECT_PARCEL(Player player, String var1){return format(PROTECT_PARCEL, "PROTECT_PARCEL", player, var1, "");}
    
    public static Text ALL_PROTECT_PARCEL(Player player, String var1){return format(ALL_PROTECT_PARCEL, "ALL_PROTECT_PARCEL", player, var1, "");}
    
    public static Text PROTECT_LOADED_PARCEL(Player player, String var1){return format(PROTECT_LOADED_PARCEL, "PROTECT_LOADED_PARCEL", player, var1, "");}
    
    public static Text UNDEFINED_PARCEL(){return format(UNDEFINED_PARCEL, "UNDEFINED_PARCEL");}
        
    public static Text RESERVED_PARCEL(){return format(RESERVED_PARCEL, "RESERVED_PARCEL");}
    
    public static Text PARCEL_NAME_FAILED(){return format(PARCEL_NAME_FAILED, "PARCEL_NAME_FAILED");}
    
    public static Text NO_PARCEL(){return format(NO_PARCEL, "NO_PARCEL");}
    
    public static Text PROTECT_NO_ENTER(){return format(PROTECT_NO_ENTER, "PROTECT_NO_ENTER");}
    
    public static Text PROTECT_NO_FLY(){return format(PROTECT_NO_FLY, "PROTECT_NO_FLY");}
    
    public static Text PROTECT_NO_BREAK(){return format(PROTECT_NO_BREAK, "PROTECT_NO_BREAK");}
    
    public static Text PROTECT_NO_BUILD(){return format(PROTECT_NO_BUILD, "PROTECT_NO_BUILD");}
    
    public static Text PROTECT_NO_FIRE(){return format(PROTECT_NO_FIRE, "PROTECT_NO_FIRE");}
    
    public static Text PROTECT_NO_LEAVE(){return format(PROTECT_NO_LEAVE, "PROTECT_NO_LEAVE");}
    
    public static Text INFO_PARCEL(Player player, String owner, String allow, String parcel){return format(INFO_PARCEL, "INFO_PARCEL", player, "","", owner, allow, parcel);}
    
    public static Text MISSING_BALANCE(){return format(MISSING_BALANCE, "MISSING_BALANCE");}
    
    public static Text PARCEL_SECURE(){return format(PARCEL_SECURE, "PARCEL_SECURE");}
    
    public static Text HOME_ALREADY_EXIST(){return format(HOME_ALREADY_EXIST, "HOME_ALREADY_EXIST");}
    
    public static Text HOME_SET_SUCCESS(Player player, String var1){return format(HOME_SET_SUCCESS, "HOME_SET_SUCCESS",player,var1,"");}
    
    public static Text NB_HOME(Player player, String var1, String var2){return format(NB_HOME, "NB_HOME",player,var1,var2);}
    
    public static Text NB_ALLOWED_HOME(Player player, String var1){return format(NB_ALLOWED_HOME, "NB_ALLOWED_HOME", player, var1, "");}
    
    public static Text HOME_NOT_FOUND(){return format(HOME_NOT_FOUND, "HOME_NOT_FOUND");}
    
    public static Text HOME_ERROR(){return format(HOME_ERROR, "HOME_ERROR");}
    
    public static Text HOME_TP_SUCCESS(Player player, String var1){return format(HOME_TP_SUCCESS, "HOME_TP_SUCCESS", player, var1, "");}
    
    public static Text PLAYER_NOT_FOUND(Player player){return format(PLAYER_NOT_FOUND, "PLAYER_NOT_FOUND",player);}
    
    public static Text WORLD_ALREADY_EXIST(){return format(WORLD_ALREADY_EXIST, "WORLD_ALREADY_EXIST");}
    
    public static Text WORLD_CREATED(Player player, String var1){return format(WORLD_CREATED, "WORLD_CREATED",player,var1,"");}
    
    public static Text TELEPORTED_TO_WORLD(Player player, String var1){return format(TELEPORTED_TO_WORLD, "TELEPORTED_TO_WORLD",player,var1,"");}
    
    public static Text OTHER_TELEPORTED_TO_WORLD(Player player, String var1){return format(TELEPORTED_TO_WORLD, "TELEPORTED_TO_WORLD",player,var1,"");}
    
    public static Text WORLD_NOT_FOUND(Player player, String var1){return format(WORLD_NOT_FOUND, "WORLD_NOT_FOUND",player,var1,"");}
    
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
