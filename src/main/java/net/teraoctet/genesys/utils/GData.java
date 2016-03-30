package net.teraoctet.genesys.utils;

import net.teraoctet.genesys.plot.GJail;
import net.teraoctet.genesys.player.GPlayer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
import net.teraoctet.genesys.plot.GPlot;
import net.teraoctet.genesys.plot.PlotManager;
import net.teraoctet.genesys.portal.GPortal;
import net.teraoctet.genesys.world.GWorld;

import static org.spongepowered.api.Sponge.getGame;
import org.spongepowered.api.service.sql.SqlService;

public class GData {
	public static SqlService sql;
	public static DataSource datasource;
	public static List<String> queue = new ArrayList<>();
	
	public static void setup() {
            try {
                sql = getGame().getServiceManager().provide(SqlService.class).get();
                if(!GConfig.MYSQL_USE()) { 
                    datasource = sql.getDataSource("jdbc:sqlite:config/genesys/genesys.db");
                } else {
                        String host = GConfig.MYSQL_HOST();
                        String port = String.valueOf(GConfig.MYSQL_PORT());
                        String username = GConfig.MYSQL_USERNAME();
                        String password = GConfig.MYSQL_PASSWORD();
                        String database = GConfig.MYSQL_DATABASE();
                        datasource = sql.getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" 
                                + username + "&password=" + password);
                }
                DatabaseMetaData metadata = datasource.getConnection().getMetaData();
                ResultSet resultset = metadata.getTables(null, null, "%", null);
                List<String> tables = new ArrayList<>();		
                while(resultset.next()) { tables.add(resultset.getString(3));}

                if(!tables.contains("gjails")) {
                        execute("CREATE TABLE gjails ("
                                + "uuid TEXT, "
                                + "player TEXT, "
                                + "jail TEXT, "
                                + "reason TEXT, "
                                + "time DOUBLE, "
                                + "duration DOUBLE)");
                }

                if(!tables.contains("ghomes")) {
                        execute("CREATE TABLE ghomes ("
                                + "uuid TEXT, "
                                + "name TEXT, "
                                + "world TEXT, "
                                + "x DOUBLE, "
                                + "y DOUBLE, "
                                + "z DOUBLE)");
                }

                if(!tables.contains("gmutes")) {
                        execute("CREATE TABLE gmutes ("
                                + "uuid TEXT, "
                                + "duration DOUBLE, "
                                + "reason TEXT)");
                }

                if(!tables.contains("gplayers")) {
                        execute("CREATE TABLE gplayers ("
                                + "uuid TEXT, "
                                + "level INT, "
                                + "name TEXT, "
                                + "godmode TEXT, "
                                + "flymode DOUBLE, "
                                + "mails TEXT, "
                                + "money DOUBLE, "
                                + "lastposition TEXT, "
                                + "lastdeath TEXT, "
                                + "onlinetime DOUBLE, "
                                + "lastonline DOUBLE, "
                                + "jail TEXT, "
                                + "timejail DOUBLE)");
                }

                if(!tables.contains("gspawns")) {
                        execute("CREATE TABLE gspawns ("
                                + "name TEXT, "
                                + "world TEXT, "
                                + "x DOUBLE, "
                                + "y DOUBLE, "
                                + "z DOUBLE, "
                                + "message TEXT)");
                }

                if(!tables.contains("gtickets")) {
                        execute("CREATE TABLE gtickets ("
                                + "id DOUBLE, "
                                + "uuid TEXT, "
                                + "message TEXT, "
                                + "time DOUBLE, "
                                + "comments TEXT, "
                                + "world TEXT, x, "
                                + "y DOUBLE, "
                                + "z DOUBLE, "
                                + "assigned TEXT, "
                                + "priority TEXT, "
                                + "status TEXT)");
                }

                if(!tables.contains("gwarps")) {
                        execute("CREATE TABLE gwarps ("
                                + "name TEXT, "
                                + "world TEXT, "
                                + "x DOUBLE, "
                                + "y DOUBLE, "
                                + "z DOUBLE, "
                                + "owner TEXT, "
                                + "invited TEXT, "
                                + "private TEXT, "
                                + "message TEXT)");
                }

                if(!tables.contains("gplot")) {
                        execute("CREATE TABLE gplot ("
                                + "plotName TEXT, "
                                + "level INT, "
                                + "world TEXT, "
                                + "X1 INT, "
                                + "Y1 INT, "
                                + "Z1 INT, "
                                + "X2 INT, "
                                + "Y2 INT, "
                                + "Z2 INT, "
                                + "jail INT, "
                                + "noEnter INT, "
                                + "noFly INT, "
                                + "noBuild INT, "
                                + "noBreak INT, "
                                + "noTeleport INT, "
                                + "noInteract INT, "
                                + "noFire INT, "
                                + "message TEXT, "
                                + "mode INT, "
                                + "noMob INT, "
                                + "noTNT INT, "
                                + "noCommand INT, "
                                + "uuidOwner TEXT, "
                                + "uuidAllowed TEXT)");
                }
                
                if(!tables.contains("gplsale")) {
                        execute("CREATE TABLE gplsale ("
                                + "plotName TEXT, "
                                + "location TEXT)");
                }
                
                if(!tables.contains("gportal")) {
                        execute("CREATE TABLE gportal ("
                                + "portalname TEXT, "
                                + "level INT, "
                                + "world TEXT, "
                                + "x1 INT, "
                                + "y1 INT, "
                                + "z1 INT, "
                                + "x2 INT, "
                                + "y2 INT, "
                                + "z2 INT, "
                                + "toworld TEXT, "
                                + "tox INT, "
                                + "toy INT, "
                                + "toz INT, "
                                + "message TEXT)");
                }
                                
            } catch (SQLException e) {}
	}
	
	public static void load() {
            try {
                Connection c = datasource.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM gjail");
                while(rs.next()) {
                    GJail gjail = new GJail(
                        rs.getString("uuid"), 
                        rs.getString("player"),
                        rs.getString("jail"),
                        rs.getString("reason"), 
                        rs.getDouble("time"), 
                        rs.getDouble("duration"));
                    GData.addJail(gjail.getUUID(), gjail);
                }
                s.close();
                c.close();
            } catch (SQLException e) {}

            try {
                Connection c = datasource.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM gplayers");

                while(rs.next()) {
                    GPlayer player = new GPlayer(
                        rs.getString("uuid"), 
                        rs.getInt("level"),    
                        rs.getString("name"), 
                        rs.getString("godmode"), 
                        rs.getDouble("flymode"), 
                        rs.getString("mails"), 
                        rs.getDouble("money"),
                        rs.getString("lastposition"), 
                        rs.getString("lastdeath"),
                        rs.getDouble("onlinetime"), 
                        rs.getDouble("lastonline"));
                    GData.addGPlayer(player.getUUID(), player);
                    GData.addUUID(player.getName(), player.getUUID());
                }   
            } catch (SQLException e) {}
		
            try {
                Connection c = datasource.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM ghomes");
                while(rs.next()) {
                    GHome home = new GHome(
                        rs.getString("uuid"), 
                        rs.getString("name"), 
                        rs.getString("world"), 
                        rs.getDouble("x"),
                        rs.getDouble("y"),  
                        rs.getDouble("z"));
                        GPlayer gplayer = getGPlayer(home.getUUID());
                        gplayer.setHome(home.getName(), home);
                        GData.removeGPlayer(home.getUUID());
                        GData.addGPlayer(home.getUUID(), gplayer);
                }
                s.close();
                c.close();
            } catch (SQLException e) {}
                
            try {
                Connection c = datasource.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM gplot");
                while(rs.next()) {
                    GPlot gplot = new GPlot(
                        rs.getString("plotName"),
                        rs.getInt("level"),
                        rs.getString("world"),
                        rs.getInt("X1"), 
                        rs.getInt("Y1"),
                        rs.getInt("Z1"),
                        rs.getInt("X2"),
                        rs.getInt("Y2"),
                        rs.getInt("Z2"),
                        rs.getInt("jail"),
                        rs.getInt("noEnter"), 
                        rs.getInt("noFly"),
                        rs.getInt("noBuild"),
                        rs.getInt("noBreak"),
                        rs.getInt("noTeleport"),
                        rs.getInt("noInteract"), 
                        rs.getInt("noFire"),
                        rs.getString("message"),
                        rs.getInt("mode"),
                        rs.getInt("noMob"),
                        rs.getInt("noTNT"),
                        rs.getInt("noCommand"),
                        rs.getString("uuidOwner"), 
                        rs.getString("uuidAllowed"));
                    if(gplot.getJail()==1){jails.add(gplot);}
                    else{plots.add(gplot);}   
                }
                s.close();
                c.close();
            } catch (SQLException e) {}
            
            try {
                Connection c = datasource.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM gportal");
                while(rs.next()) {
                    GPortal gportal = new GPortal(
                        rs.getString("portalname"),
                        rs.getInt("level"),
                        rs.getString("world"),
                        rs.getInt("x1"), 
                        rs.getInt("y1"),
                        rs.getInt("z1"),
                        rs.getInt("x2"),
                        rs.getInt("y2"),
                        rs.getInt("z2"),
                        rs.getString("toworld"),
                        rs.getInt("tox"), 
                        rs.getInt("toy"),
                        rs.getInt("toz"),
                        rs.getString("message"));
                    gportals.add(gportal);  
                }
                s.close();
                c.close();
            } catch (SQLException e) {}
		/*try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM spawns");
			while(rs.next()) {
				SPAWN spawn = new SPAWN(rs.getString("name"), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"), rs.getString("message"));
				Gdata.addSpawn(spawn.getName(), spawn);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		
		/*try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM tickets");
			while(rs.next()) {
				TICKET ticket = new TICKET((int)rs.getDouble("id"), rs.getString("uuid"), rs.getString("message"), rs.getDouble("time"), DESERIALIZE.messages(rs.getString("comments")), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"), rs.getString("assigned"), rs.getString("priority"), rs.getString("status"));
				Gdata.addTicket(ticket.getID(), ticket);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		
		/*try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM warps");
			while(rs.next()) {
				WARP warp = new WARP(rs.getString("name"), rs.getString("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"), rs.getString("owner"), DESERIALIZE.list(rs.getString("invited")), rs.getString("private"), rs.getString("message"));
				Gdata.addWarp(warp.getName(), warp);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
	
	public static void execute(String execute) {	
            try {
                Connection connection = datasource.getConnection();
                Statement statement = connection.createStatement();
                statement.execute(execute);
                statement.close();
                connection.close();
            } catch (SQLException e) {e.printStackTrace();}
	}
	
	public static void execute(List<String> execute) {	
            try {
                Connection connection = datasource.getConnection();
                Statement statement = connection.createStatement();
                for(String e : execute) statement.execute(e);
                statement.close();
                connection.close();
            } catch (SQLException e) {e.printStackTrace();}
	}
	
	public static void commit() {
		if(queue.isEmpty()) return;
		execute(queue);
		queue.clear();
	}
	
	public static void queue(String queue) { GData.queue.add(queue); }
	
        private static final HashMap<String, GWorld> worlds = new HashMap<>();
	public static void addWorld(String name, GWorld world) { if(!worlds.containsKey(name)) worlds.put(name, world); }
	public static void removeWorld(String name) { if(worlds.containsKey(name)) worlds.remove(name); }
	public static GWorld getWorld(String name) { return worlds.containsKey(name) ? worlds.get(name) : null; }
        public static GWorld getWorldUUID( String uuid) { return worlds.containsKey(uuid) ? worlds.get(uuid) : null; }
	public static HashMap<String, GWorld> getWorlds() { return worlds; }
        
	private static final HashMap<String, GJail> gjails = new HashMap<>();
	public static void addJail(String uuid, GJail gjail) { if(!gjails.containsKey(uuid)) gjails.put(uuid, gjail); }
	public static void removeJail(String uuid) { if(gjails.containsKey(uuid)) gjails.remove(uuid); }
	public static GJail getJail(String uuid) { return gjails.containsKey(uuid) ? gjails.get(uuid) : null; }
	public static HashMap<String, GJail> getBans() { return gjails; }
	
	private static final HashMap<String, GPlayer> players = new HashMap<>();
	public static void addGPlayer(String uuid, GPlayer gplayer) { if(!players.containsKey(players)) players.put(uuid, gplayer); }
	public static void removeGPlayer(String uuid) { if(players.containsKey(uuid)) players.remove(uuid); }
	public static GPlayer getGPlayer(String uuid) { return players.containsKey(uuid) ? players.get(uuid) : null; }
	public static HashMap<String, GPlayer> getPlayers() { return players; }
	
	private static final HashMap<String, String> uuids = new HashMap<>();
	public static void addUUID(String name, String uuid) { uuids.put(name, uuid); }
	public static void removeUUID(String name) { if(uuids.containsKey(name)) uuids.remove(name); }
	public static String getUUID(String name) { return uuids.containsKey(name) ? uuids.get(name) : null; }
        
        public static HashMap<String, PlotManager> setts = new HashMap();
        public static final ArrayList<GPlot> plots = new ArrayList<>();
        public static final ArrayList<GPlot> jails = new ArrayList<>();
        public static void addPlot(GPlot gplot) { if(!plots.contains(gplot)) plots.add(gplot); }
        public static void addJail(GPlot gplot) { if(!jails.contains(gplot)) jails.add(gplot); }
        public static void removePlot(GPlot gplot) { if(plots.contains(gplot)) plots.remove(gplot); }
        public static void removeJail(GPlot gplot) { if(jails.contains(gplot)) jails.remove(gplot); }

        public static final ArrayList<GPortal> gportals = new ArrayList<>();
        public static void addPortal(GPortal gportal) { if(!gportals.contains(gportal)) gportals.add(gportal); } 
        public static void removePortal(GPortal gportal) { if(gportals.contains(gportal)) gportals.remove(gportal); } 

	
	//private static HashMap<String, SPAWN> spawns = new HashMap<String, SPAWN>();
	//public static void addSpawn(String name, SPAWN spawn) { if(!spawns.containsKey(name)) spawns.put(name, spawn); }
	//public static void removeSpawn(String name) { if(spawns.containsKey(name)) spawns.remove(name); }
	//public static SPAWN getSpawn(String name) { return spawns.containsKey(name) ? spawns.get(name) : null; }
	//public static HashMap<String, SPAWN> getSpawns() { return spawns; }
	
	//private static HashMap<Integer, TICKET> tickets = new HashMap<Integer, TICKET>();
	//public static void addTicket(int id, TICKET ticket) { if(!tickets.containsKey(id)) tickets.put(id, ticket); }
	//public static void removeTicket(int id) { if(tickets.containsKey(id)) tickets.remove(id); }
	//public static TICKET getTicket(int id) { return tickets.containsKey(id) ? tickets.get(id) : null; }
	//public static HashMap<Integer, TICKET> getTickets() { return tickets; }
	//public static void clearTickets() { tickets.clear(); }
	
	//private static HashMap<String, WARP> warps = new HashMap<String, WARP>();
	//public static void addWarp(String name, WARP warp) { if(!warps.containsKey(name)) warps.put(name, warp); }
	//public static void removeWarp(String name) { if(warps.containsKey(name)) warps.remove(name); }
	//public static WARP getWarp(String name) { return warps.containsKey(name) ? warps.get(name) : null; }
	//public static HashMap<String, WARP> getWarps() { return warps; }
        

}

