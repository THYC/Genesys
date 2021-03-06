package net.teraoctet.genesys.commands;

import net.teraoctet.genesys.commands.economy.CommandSignBank;
import net.teraoctet.genesys.commands.portal.CommandPortalList;
import net.teraoctet.genesys.commands.portal.CommandPortalMsg;
import net.teraoctet.genesys.commands.portal.CommandPortalCreateOK;
import net.teraoctet.genesys.commands.portal.CommandPortal;
import net.teraoctet.genesys.commands.portal.CommandPortalTPFrom;
import net.teraoctet.genesys.commands.portal.CommandPortalRemove;
import net.teraoctet.genesys.commands.portal.CommandPortalCreate;
import net.teraoctet.genesys.commands.plot.CommandPlotRemove;
import net.teraoctet.genesys.commands.plot.CommandPlotCreate;
import net.teraoctet.genesys.commands.plot.CommandPlotOwnerset;
import net.teraoctet.genesys.commands.plot.CommandPlot;
import net.teraoctet.genesys.commands.plot.CommandPlotRemoveplayer;
import net.teraoctet.genesys.commands.plot.CommandPlotMsg;
import net.teraoctet.genesys.commands.plot.CommandPlotCreateOK;
import net.teraoctet.genesys.commands.plot.CommandPlotFlag;
import net.teraoctet.genesys.commands.plot.CommandPlotExpand;
import net.teraoctet.genesys.commands.plot.CommandPlotAddplayer;
import net.teraoctet.genesys.commands.plot.CommandPlotList;
import net.teraoctet.genesys.commands.plot.CommandPlotFlaglist;
import net.teraoctet.genesys.commands.plot.CommandPlotSale;
import net.teraoctet.genesys.commands.faction.CommandFaction;
import net.teraoctet.genesys.commands.faction.CommandFactionWithdrawal;
import net.teraoctet.genesys.commands.faction.CommandFactionDelete;
import net.teraoctet.genesys.commands.faction.CommandFactionSetowner;
import net.teraoctet.genesys.commands.faction.CommandFactionDeposit;
import net.teraoctet.genesys.commands.faction.CommandFactionCreate;
import net.teraoctet.genesys.commands.faction.CommandFactionRename;
import net.teraoctet.genesys.commands.faction.CommandFactionInvit;
import net.teraoctet.genesys.commands.faction.CommandFactionRemoveplayer;
import net.teraoctet.genesys.commands.faction.CommandFactionList;
import net.teraoctet.genesys.commands.faction.CommandFactionMemberslist;
import net.teraoctet.genesys.commands.faction.CommandFactionLeave;
import net.teraoctet.genesys.commands.plot.CommandPlotLevel;
import net.teraoctet.genesys.commands.plot.CommandPlotTP;
import net.teraoctet.genesys.commands.economy.CommandSetName;
import net.teraoctet.genesys.commands.economy.CommandShopCreate;
import net.teraoctet.genesys.commands.economy.CommandShopPurchase;
import net.teraoctet.genesys.commands.economy.CommandShopSell;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandManager {
        
        public CommandSpec CommandSetName = CommandSpec.builder()
                .description(Text.of("/setname <name>"))
                .permission("genesys.admin.setname")
                .arguments(GenericArguments.optional(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))))
                .executor(new CommandSetName())
                .build();
    
        public CommandSpec CommandKill = CommandSpec.builder()
                .description(Text.of("/kill"))
                .permission("genesys.kill")
                .arguments(GenericArguments.optional(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new CommandKill())
                .build();
        
        public CommandSpec CommandSun = CommandSpec.builder()
                .description(Text.of("/sun"))
                .permission("genesys.weather.sun")
                .executor(new CommandSun())
                .build();

        public CommandSpec CommandRain = CommandSpec.builder()
                .description(Text.of("Commande rain pluie"))
                .permission("genesys.weather.rain")
                .executor(new CommandRain())
                .build();
        
        public CommandSpec CommandStorm = CommandSpec.builder()
                .description(Text.of("Commande storm orage"))
                .permission("genesys.weather.storm")
                .executor(new CommandStorm())
                .build();
        
        public CommandSpec CommandDay = CommandSpec.builder()
                .description(Text.of("Commande day jour"))
                .permission("genesys.time.day")
                .executor(new CommandDay())
                .build();
        
        public CommandSpec CommandNight = CommandSpec.builder()
                .description(Text.of("Commande night nuit"))
                .permission("genesys.time.nuit")
                .executor(new CommandNight())
                .build();
                
        public CommandSpec CommandPlotCreate = CommandSpec.builder() 
                .description(Text.of("/plot create <name> [strict]")) 
                .permission("genesys.plot.create") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("strict")))))
                .executor(new CommandPlotCreate()) 
                .build(); 
        
        public CommandSpec CommandPlotCreateOK = CommandSpec.builder() 
                .description(Text.of("use /plot create <name> [strict]")) 
                .permission("genesys.plot.create") 
                .arguments(GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
                        GenericArguments.onlyOne(GenericArguments.integer(Text.of("amount"))),
                        GenericArguments.onlyOne(GenericArguments.bool(Text.of("strict")))))
                .executor(new CommandPlotCreateOK()) 
                .build(); 
        
        public CommandSpec CommandPlotFlag = CommandSpec.builder()
                .description(Text.of("/plot flag <flag> <0|1> [name]")) 
                .extendedDescription(Text.of("/plot flag <flag> <0|1> [name]"))
                .permission("genesys.plot.flag") 
                .arguments(GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.string(Text.of("flag"))),
                        GenericArguments.optional(GenericArguments.integer(Text.of("value"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotFlag()) 
                .build(); 
        
        public CommandSpec CommandPlotFlaglist = CommandSpec.builder()
                .description(Text.of("/plot flaglist [name]")) 
                .permission("genesys.plot.flag")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotFlaglist()) 
                .build(); 
        
        public CommandSpec CommandPlotRemove = CommandSpec.builder()
                .description(Text.of("/plot remove [name]")) 
                .permission("genesys.plot.remove")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotRemove()) 
                .build(); 
        
        public CommandSpec CommandPlotSale = CommandSpec.builder()
                .description(Text.of("/plot sale <price> [name]")) 
                .permission("genesys.plot.sale") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.integer(Text.of("price"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotSale()) 
                .build(); 
        
        public CommandSpec CommandPlotAddplayer = CommandSpec.builder()
                .description(Text.of("/plot addplayer <player> [name]")) 
                .permission("genesys.plot.addplayer") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotAddplayer()) 
                .build();
        
        public CommandSpec CommandPlotRemoveplayer = CommandSpec.builder()
                .description(Text.of("/plot removeplayer <player> [name]")) 
                .permission("genesys.plot.removeplayer") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotRemoveplayer()) 
                .build(); 
        
        public CommandSpec CommandPlotOwnerset = CommandSpec.builder()
                .description(Text.of("/plot ownerset <player> [name]")) 
                .permission("genesys.plot.ownerset") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPlotOwnerset()) 
                .build(); 
        
        public CommandSpec CommandPlotList = CommandSpec.builder()
                .description(Text.of("/plot list [player]")) 
                .permission("genesys.plot.list") 
                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("tplayer"))))
                .executor(new CommandPlotList()) 
                .build(); 
        
        public CommandSpec CommandPlotExpand = CommandSpec.builder()
                .description(Text.of("/plot expand <value>")) 
                .permission("genesys.plot.expand") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.integer(Text.of("value"))),
                        GenericArguments.optional(GenericArguments.integer(Text.of("point"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("axe")))))
                .executor(new CommandPlotExpand()) 
                .build(); 
        
        public CommandSpec CommandPlotMsg = CommandSpec.builder()
                .description(Text.of("/plot msg [message]"))
                .permission("genesys.plot.msg")
                .arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("arguments"))))
                .executor(new CommandPlotMsg())
                .build();
        
        public CommandSpec CommandPlotTP = CommandSpec.builder()
                .description(Text.of("/plot tp <name>"))
                .permission("genesys.plot.tp")
                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("name"))))
                .executor(new CommandPlotTP())
                .build();
        
        public CommandSpec CommandPlotLevel = CommandSpec.builder()
                .description(Text.of("/plot level [level]")) 
                .permission("genesys.plot.level") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.string(Text.of("naem"))),
                        GenericArguments.optional(GenericArguments.integer(Text.of("level")))))
                .executor(new CommandPlotLevel()) 
                .build(); 
        
        public CommandSpec CommandPlot = CommandSpec.builder()
                .description(Text.of("/plot")) 
                .permission("genesys.plot") 
                .child(CommandPlotCreate, "create")
                .child(CommandPlotCreateOK, "createok")
                .child(CommandPlotExpand, "expand")
                .child(CommandPlotList, "list")
                .child(CommandPlotFlag, "flag")
                .child(CommandPlotFlaglist, "flaglist")
                .child(CommandPlotRemove, "remove")
                .child(CommandPlotSale, "sale")
                .child(CommandPlotAddplayer, "addplayer")
                .child(CommandPlotRemoveplayer, "removeplayer")
                .child(CommandPlotOwnerset, "ownerset")
                .child(CommandPlotMsg, "msg")
                .child(CommandPlotTP, "tp")
                .child(CommandPlotLevel, "level")
                .executor(new CommandPlot())
                .build();

        public CommandSpec CommandFly = CommandSpec.builder()
                .description(Text.of("/fly"))
                .permission("genesys.fly")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new CommandFly())
                .build();
        
        public CommandSpec CommandSetHome = CommandSpec.builder()
                .description(Text.of("/sethome [home]"))
                .permission("genesys.sethome")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("home")))))
                .executor(new CommandSetHome())
                .build();
        
        public CommandSpec CommandHome = CommandSpec.builder()
                .description(Text.of("/home [home]"))
                .permission("genesys.home")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("home")))))
                .executor(new CommandHome())
                .build();
        
        public CommandSpec CommandDelhome = CommandSpec.builder()
                .description(Text.of("/delhome [home]"))
                .permission("genesys.delhome")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("home")))))
                .executor(new CommandDelhome())
                .build();
        
        public CommandSpec CommandBack = CommandSpec.builder()
                .description(Text.of("/back"))
                .permission("genesys.back")
                .executor(new CommandBack())
                .build();
        
        public CommandSpec CommandLevel = CommandSpec.builder()
                .description(Text.of("/level <level> [player]"))
                .permission("genesys.admin.level")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.integer(Text.of("level"))),
                    GenericArguments.optional(GenericArguments.player(Text.of("player")))))
                .executor(new CommandLevel())
                .build();
        
        public CommandSpec CommandHead = CommandSpec.builder()
                .description(Text.of("/head [head]"))
                .permission("genesys.head")
                .arguments(GenericArguments.optional(
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("head")))))
                .executor(new CommandHead())
                .build();
        
        public CommandSpec CommandWorldCreate = CommandSpec.builder()
                .description(Text.of("/worldCreate <name> <environment> <gamemode> <difficulty>"))
                .permission("genesys.admin.world.worldcreate")
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.optional(GenericArguments.string(Text.of("name"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("environment"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("gamemode"))),
                        GenericArguments.optional(GenericArguments.string(Text.of("difficulty")))))
                .executor(new CommandWorldCreate())
                .build();
        
        public CommandSpec CommandWorldTP = CommandSpec.builder()
                .description(Text.of("Commande worldtp"))
                .permission("genesys.world.worldtp")
                .arguments(GenericArguments.seq(
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("worldName"))),
                    GenericArguments.optional(GenericArguments.player(Text.of("target")))))
                .executor(new CommandWorldTP())
                .build();
        
        public CommandSpec CommandClearinventory = CommandSpec.builder()
                .description(Text.of("/clearinventory [player]"))
                .permission("genesys.clearinventory")
                .arguments(GenericArguments.optional(GenericArguments.player(Text.of("player"))))
                .executor(new CommandClearinventory())
                .build();
        
        public CommandSpec CommandInvsee = CommandSpec.builder()
                .description(Text.of("/invsee <player>"))
                .permission("genesys.invsee")
                .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("target"))))
                .executor(new CommandInvsee())
                .build();
        
        public CommandSpec CommandPlayerinfo = CommandSpec.builder()
                .description(Text.of("/playerinfo [player]"))
                .permission("genesys.playerinfo")
                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("tplayer"))))
                .executor(new CommandPlayerinfo())
                .build();
        
        public CommandSpec CommandBroadcast = CommandSpec.builder()
                .description(Text.of("/broadcast [hide = 0:1] <message..>"))
                .permission("genesys.broadcast")
                .arguments(GenericArguments.firstParsing( 
                    GenericArguments.flags()
                        .flag("-hide", "h")
                        .buildWith(GenericArguments.remainingJoinedStrings(Text.of("message")))))
                .executor(new CommandBroadcast())
                .build();
         
        public CommandSpec CommandFactionCreate = CommandSpec.builder()
                .description(Text.of("/faction create <name>")) 
                .permission("genesys.faction.create") 
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("name")))
                .executor(new CommandFactionCreate()) 
                .build();
         
        public CommandSpec CommandFactionDelete = CommandSpec.builder()
                .description(Text.of("/faction delete <name>")) 
                .permission("genesys.faction.delete") 
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("name")))
                .executor(new CommandFactionDelete()) 
                .build();
        
        public CommandSpec CommandFactionLeave = CommandSpec.builder()
                .description(Text.of("/faction leave")) 
                .permission("genesys.faction.leave") 
                .executor(new CommandFactionLeave()) 
                .build();
        
        public CommandSpec CommandFactionRename = CommandSpec.builder()
                .description(Text.of("/faction rename <name>")) 
                .permission("genesys.faction.rename") 
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("name")))
                .executor(new CommandFactionRename()) 
                .build();
         
        public CommandSpec CommandFactionList = CommandSpec.builder()
                .description(Text.of("/faction list")) 
                .permission("genesys.faction.list")
                .executor(new CommandFactionList()) 
                .build();
         
        public CommandSpec CommandFactionMemberslist = CommandSpec.builder()
                .description(Text.of("/faction memberslist")) 
                .permission("genesys.faction.memberslist")
                .executor(new CommandFactionMemberslist()) 
                .build();
         
        public CommandSpec CommandFactionInvit = CommandSpec.builder()
                .description(Text.of("/faction invit <player>")) 
                .permission("genesys.faction.invit") 
                .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
                .executor(new CommandFactionInvit()) 
                .build();
         
        public CommandSpec CommandFactionRemoveplayer = CommandSpec.builder()
                .description(Text.of("/faction removeplayer <player>")) 
                .permission("genesys.faction.removeplayer") 
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
                .executor(new CommandFactionRemoveplayer()) 
                .build();
         
        public CommandSpec CommandFactionSetplayergrade = CommandSpec.builder()
                .description(Text.of("/faction setplayergrade <player> <grade>")) 
                .permission("genesys.faction.setplayergrade") 
                .arguments(
                    GenericArguments.seq(
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.onlyOne(GenericArguments.string(Text.of("grade")))))
                .executor(new CommandFactionRemoveplayer()) 
                .build();
        
        public CommandSpec CommandFactionSetowner = CommandSpec.builder()
                .description(Text.of("/faction setowner <player>")) 
                .permission("genesys.faction.setowner") 
                .arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
                .executor(new CommandFactionSetowner()) 
                .build();
        
        public CommandSpec CommandFactionDeposit = CommandSpec.builder()
                .description(Text.of("/faction depot <montant>")) 
                .permission("genesys.faction.deposit") 
                .arguments(GenericArguments.onlyOne(GenericArguments.doubleNum(Text.of("amount"))))
                .executor(new CommandFactionDeposit()) 
                .build();
        
        public CommandSpec CommandFactionWithdrawal = CommandSpec.builder()
                .description(Text.of("/faction retrait <montant>")) 
                .permission("genesys.faction.withdrawal") 
                .arguments(
                    GenericArguments.onlyOne(GenericArguments.doubleNum(Text.of("amount"))))
                .executor(new CommandFactionWithdrawal()) 
                .build();
         
        public CommandSpec CommandFaction = CommandSpec.builder()
                .description(Text.of("Affiche des informations sur votre faction"))
                .permission("genesys.faction")
                .child(CommandFactionCreate, "create", "new", "add", "creer")
                .child(CommandFactionDelete, "delete")
                .child(CommandFactionRename, "rename")
                .child(CommandFactionLeave, "leave", "quit", "quitter")
                .child(CommandFactionMemberslist, "memberslist")
                .child(CommandFactionList, "list")
                .child(CommandFactionInvit, "invit", "inviter", "add")
                .child(CommandFactionRemoveplayer, "removeplayer")
                .child(CommandFactionSetplayergrade, "setplayergrade")
                .child(CommandFactionSetowner, "setowner")
                .child(CommandFactionDeposit, "deposit", "depot")
                .child(CommandFactionWithdrawal, "withdraw", "withdrawal", "retrait")
                .arguments(GenericArguments.flags().flag("-displayaction", "a").buildWith(GenericArguments.none()))
                .executor(new CommandFaction())
                .build();
        
        public CommandSpec CommandTest = CommandSpec.builder()
                .description(Text.of("/test [arg0] [args1]"))
                .permission("genesys.admin.test")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("arg0"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("arg1")))))
                .executor(new CommandTest())
                .build();
        
        public CommandSpec CommandRocket = CommandSpec.builder() 
                .description(Text.of("Rocket Command")) 
                .permission("genesys.rocket.use") 
                .arguments(GenericArguments.firstParsing( 
                    GenericArguments.flags() 
                        .flag("-hard", "h") 
                        .buildWith(GenericArguments.firstParsing(GenericArguments.optional(GenericArguments.player(Text.of("target"))), 
                    GenericArguments.optional(GenericArguments.string(Text.of("targets"))))))) 
                .executor(new CommandRocket()) 
                .build();
        
        public CommandSpec CommandPortalMsg = CommandSpec.builder()
                .description(Text.of("/portal msg <name> [message]"))
                .permission("genesys.admin.portal")
                .arguments(GenericArguments.firstParsing(
                    GenericArguments.optional(GenericArguments.string(Text.of("name"))),
                    GenericArguments.remainingJoinedStrings(Text.of("arguments"))))
                .executor(new CommandPortalMsg())
                .build();
        
        public CommandSpec CommandPortalTPFrom = CommandSpec.builder() 
                .description(Text.of("/portal TPFrom <name>")) 
                .permission("genesys.admin.portal") 
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPortalTPFrom()) 
                .build(); 
        
        public CommandSpec CommandPortalList = CommandSpec.builder() 
                .description(Text.of("/portal list")) 
                .permission("genesys.admin.portal") 
                .executor(new CommandPortalList()) 
                .build(); 
        
        public CommandSpec CommandPortalRemove = CommandSpec.builder() 
                .description(Text.of("/portal remove <name>")) 
                .permission("genesys.admin.portal") 
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPortalRemove()) 
                .build(); 
        
        public CommandSpec CommandPortalCreateOK = CommandSpec.builder() 
                .description(Text.of("/portal create <name>")) 
                .permission("genesys.admin.portal") 
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPortalCreateOK()) 
                .build(); 
        
        public CommandSpec CommandPortalCreate = CommandSpec.builder()
                .description(Text.of("/portal create [name]"))
                .permission("genesys.admin.portal")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("name")))))
                .executor(new CommandPortalCreate())
                .build();
        
        public CommandSpec CommandPortal = CommandSpec.builder()
                .description(Text.of("/portal")) 
                .permission("genesys.admin.portal") 
                .child(CommandPortalCreate, "create", "add")
                .child(CommandPortalCreateOK, "createok")
                .child(CommandPortalRemove, "remove", "rem", "del")
                .child(CommandPortalList, "list")
                .child(CommandPortalTPFrom, "tpfrom", "tpf")
                .child(CommandPortalMsg, "msg")
                .executor(new CommandPortal())
                .build();
               
        public CommandSpec CommandMagicCompass = CommandSpec.builder()
                .description(Text.of("/mc <Direction> <name> <X> <Y> <Z>"))
                .permission("genesys.magiccompass")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.string(Text.of("direction"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("name"))),
                    GenericArguments.optional(GenericArguments.integer(Text.of("x"))),
                    GenericArguments.optional(GenericArguments.integer(Text.of("y"))),
                    GenericArguments.optional(GenericArguments.integer(Text.of("z")))))
                .executor(new CommandMagicCompass())
                .build();
        
        public CommandSpec CommandSignWrite = CommandSpec.builder()
                .description(Text.of("/write <line> <message>"))
                .permission("genesys.sign.write")
                .arguments(GenericArguments.seq(
                    GenericArguments.optional(GenericArguments.integer(Text.of("line"))), 
                    GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("message")))))
                .executor(new CommandSignWrite())
                .build();
        
        public CommandSpec CommandSignHelp = CommandSpec.builder()
                .description(Text.of("/signhelp <name>"))
                .permission("genesys.admin.sign.help")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("name"))))
                .executor(new CommandSignHelp())
                .build();
        
        public CommandSpec CommandSignBank = CommandSpec.builder()
                .description(Text.of("/signbank <type>"))
                .permission("genesys.admin.sign.bank")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("type"))))
                .executor(new CommandSignBank())
                .build();
        
        public CommandSpec CommandShopCreate = CommandSpec.builder()
                .description(Text.of("/shopcreate <locationstring> <transacttype> <price> <qte>"))
                .permission("genesys.admin.shop")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("locationstring"))),
                    GenericArguments.optional(GenericArguments.string(Text.of("transacttype"))),
                    GenericArguments.optional(GenericArguments.doubleNum(Text.of("price"))),
                    GenericArguments.optional(GenericArguments.integer(Text.of("qte"))))
                .executor(new CommandShopCreate())
                .build();
        
        public CommandSpec CommandShopPurchase = CommandSpec.builder()
                .description(Text.of("/shopcreate <locationstring> <transacttype> <price> <qte>"))
                .permission("genesys.admin.shop")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("locationstring"))))
                .executor(new CommandShopPurchase())
                .build();
        
        public CommandSpec CommandShopSell = CommandSpec.builder()
                .description(Text.of("/shopcreate <locationstring> <transacttype> <price> <qte>"))
                .permission("genesys.admin.shop")
                .arguments(
                    GenericArguments.optional(GenericArguments.string(Text.of("locationstring"))))
                .executor(new CommandShopSell())
                .build();
        
        public CommandSpec CommandTPA = CommandSpec.builder()
                .description(Text.of("/tpa <player>"))
                .permission("genesys.tpa")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new CommandTPA())
                .build();
        
        public CommandSpec CommandTPhere = CommandSpec.builder()
                .description(Text.of("/tphere <player>"))
                .permission("genesys.tpa")
                .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new CommandTPhere())
                .build();
        
        public CommandSpec CommandTPaccept = CommandSpec.builder()
                .description(Text.of("/tpaccept"))
                .permission("genesys.tpa")
                .executor(new CommandTPaccept())
                .build();
}
