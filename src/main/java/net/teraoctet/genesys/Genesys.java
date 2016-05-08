package net.teraoctet.genesys;

import net.teraoctet.genesys.player.PlayerListener;
import com.google.inject.Inject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.teraoctet.genesys.bookmessage.BookManager;
import net.teraoctet.genesys.plot.PlotListener;
import net.teraoctet.genesys.plot.PlotManager;
import net.teraoctet.genesys.portal.PortalListener;
import net.teraoctet.genesys.portal.PortalManager;
import net.teraoctet.genesys.utils.GConfig;
import net.teraoctet.genesys.utils.GData;
import net.teraoctet.genesys.utils.MessageManager;
import net.teraoctet.genesys.world.WorldListener;
import net.teraoctet.genesys.world.WorldManager;
import net.teraoctet.genesys.commands.CommandManager;
import net.teraoctet.genesys.economy.ItemShopManager;
import net.teraoctet.genesys.economy.EconomyListener;
import net.teraoctet.genesys.faction.FactionManager;
import net.teraoctet.genesys.utils.CountdownToTP;
import net.teraoctet.genesys.utils.ServerManager;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameLoadCompleteEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

@Plugin(id = PluginInfo.ID, name = PluginInfo.NAME, version = PluginInfo.INFORMATIVE_VERSION, description = PluginInfo.DESCRIPTION, authors = {"THYC", "Votop"})
public class Genesys {
    
    @Inject private Logger logger;
    public static ServerManager serverManager = new ServerManager();
    public static PlotManager plotManager = new PlotManager();
    public static PortalManager portalManager = new PortalManager();
    public static FactionManager factionManager = new FactionManager();
    public static BookManager bookManager = new BookManager();
    public static ItemShopManager itemShopManager = new ItemShopManager();
    public Logger getLogger(){return logger;}  
    public static Game game;
    public static PluginContainer plugin;
    public static Map<Player, CountdownToTP> mapCountDown = new HashMap<>();
    public static Map<Player,Boolean>modInput = new HashMap<>();
    public static Map<Player,Double>inputDouble = new HashMap<>();
                
    @Listener
    public void onServerInit(GameInitializationEvent event) throws ObjectMappingException {
	        
        File folder = new File("config/genesys");
    	if(!folder.exists()) folder.mkdir();
    	GConfig.setup();
    	GData.setup();
    	GData.load();
        MessageManager.init();
        BookManager.init();
        ItemShopManager.init();

        getGame().getEventManager().registerListeners(this, new PlotListener());
        getGame().getEventManager().registerListeners(this, new PortalListener());
        getGame().getEventManager().registerListeners(this, new PlayerListener());
        getGame().getEventManager().registerListeners(this, new WorldListener());
        getGame().getEventManager().registerListeners(this, new EconomyListener());
         
	getGame().getCommandManager().register(this, new CommandManager().CommandKill, "kill", "tue");
	getGame().getCommandManager().register(this, new CommandManager().CommandSun, "sun", "soleil");
	getGame().getCommandManager().register(this, new CommandManager().CommandRain, "rain", "pluie");
	getGame().getCommandManager().register(this, new CommandManager().CommandStorm, "storm", "orage");
	getGame().getCommandManager().register(this, new CommandManager().CommandDay, "day", "timeday", "jour");
	getGame().getCommandManager().register(this, new CommandManager().CommandNight, "night", "timenight", "nuit");
        getGame().getCommandManager().register(this, new CommandManager().CommandPlot, "plot", "parcel", "parcelle", "p", "protege");
	getGame().getCommandManager().register(this, new CommandManager().CommandFly, "fly", "vole");
	getGame().getCommandManager().register(this, new CommandManager().CommandSetHome, "sethome", "homeset");
	getGame().getCommandManager().register(this, new CommandManager().CommandHome, "home", "maison");
        getGame().getCommandManager().register(this, new CommandManager().CommandDelhome, "delhome", "removehome");
	getGame().getCommandManager().register(this, new CommandManager().CommandBack, "back", "gsback", "reviens", "retour");
	getGame().getCommandManager().register(this, new CommandManager().CommandLevel, "level");
        getGame().getCommandManager().register(this, new CommandManager().CommandWorldCreate, "worldcreate", "createworld", "newworld");
	getGame().getCommandManager().register(this, new CommandManager().CommandWorldTP, "worldtp", "tpworld");
        getGame().getCommandManager().register(this, new CommandManager().CommandClearinventory, "clearinventory", "ci", "clear");
        getGame().getCommandManager().register(this, new CommandManager().CommandInvsee, "invsee", "is");
        getGame().getCommandManager().register(this, new CommandManager().CommandPlayerinfo, "playerinfo", "pi", "info");
        getGame().getCommandManager().register(this, new CommandManager().CommandBroadcast, "broadcastmessage", "broadcast", "bm", "b");
        getGame().getCommandManager().register(this, new CommandManager().CommandFaction, "faction", "f", "guilde", "g", "horde");
	getGame().getCommandManager().register(this, new CommandManager().CommandTest, "test");
        getGame().getCommandManager().register(this, new CommandManager().CommandRocket, "rocket");
        getGame().getCommandManager().register(this, new CommandManager().CommandPortal, "portal", "portail", "pl", "po");
        getGame().getCommandManager().register(this, new CommandManager().CommandHead, "head", "skull", "tete");
        getGame().getCommandManager().register(this, new CommandManager().CommandMagicCompass, "mc", "magic", "compass", "boussole");
        getGame().getCommandManager().register(this, new CommandManager().CommandSignWrite, "write", "ecrire", "signwrite", "sw", "print");
        getGame().getCommandManager().register(this, new CommandManager().CommandSignHelp, "signhelp", "sh");
        getGame().getCommandManager().register(this, new CommandManager().CommandSignBank, "signbank", "sb");
        getGame().getCommandManager().register(this, new CommandManager().CommandSetName, "setname", "sn", "dn");
        getGame().getCommandManager().register(this, new CommandManager().CommandShopCreate, "shopcreate", "shopc");
        getGame().getCommandManager().register(this, new CommandManager().CommandShopPurchase, "shoppurchase", "shopp");
        getGame().getCommandManager().register(this, new CommandManager().CommandShopSell, "shopsell", "shops");

        getLogger().info("-----------------------------"); 
	getLogger().info("...........Genesys..........."); 
        getLogger().info("developped by THYC and Votop"); 
        getLogger().info("-----------------------------"); 
    }
        
    @Listener
    public void onDisable(GameStoppingServerEvent event) {
    	GData.commit();
    }

    @Listener
    public void onServerLoadComplete(GameLoadCompleteEvent event)
    {
        getLogger().info("Genesys: charging is complete");
    }  
    
    @Listener
    public void onServerStarted(GameStartedServerEvent event)
    {
        game = Sponge.getGame();    	
    	plugin = Sponge.getPluginManager().getPlugin("net.teraoctet.genesys").get();
        WorldManager.init();
    }   
}
