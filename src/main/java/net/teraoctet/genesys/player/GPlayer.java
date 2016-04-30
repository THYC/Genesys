package net.teraoctet.genesys.player;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import static net.teraoctet.genesys.Genesys.serverManager;
import net.teraoctet.genesys.utils.GHome;
import static net.teraoctet.genesys.utils.GData.addGPlayer;
import static net.teraoctet.genesys.utils.GData.addUUID;
import static net.teraoctet.genesys.utils.GData.removeGPlayer;
import static net.teraoctet.genesys.utils.GData.removeUUID;
import net.teraoctet.genesys.utils.ServerManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import static net.teraoctet.genesys.utils.GData.queue;

public class GPlayer{
	
    private String uuid;
    private int level;
    private String name;
    private String godmode;
    private double flymode;
    private String mails;
    private double money;
    private String lastposition;
    private String lastdeath;
    private double onlinetime;
    private double lastonline;
    private String jail;
    private double timejail;
    private int id_faction;
    private int faction_rank;

    private HashMap<String, GHome> homes;
    private String reply;
    HashMap<String, Double> tpa;
    HashMap<String, Double> tpahere;
    HashMap<Integer, List<Text>> pages;
    Text page_title;
    Text page_header;

    public GPlayer(String uuid, int level, String name, String godmode, double flymode, String mails, double money, String lastposition, String lastdeath, double onlinetime, double lastonline, String jail, double timejail, int id_faction, int faction_rank ) {
        this.uuid = uuid;
        this.level = level;
        this.name = name;
        this.godmode = godmode;
        this.flymode = flymode;
        this.mails = mails;
        this.money = money;
        this.lastposition = lastposition;
        this.lastdeath = lastdeath;
        this.onlinetime = onlinetime;
        this.lastonline = lastonline;
        this.jail = jail;
        this.timejail = timejail;
        this.id_faction = id_faction;
        this.faction_rank = faction_rank;

        homes = new HashMap<>();
        reply = "";
        tpa = new HashMap<>();
        tpahere = new HashMap<>();
        pages = new HashMap<>();	
    }
    
    public void insert() {
        queue("INSERT INTO gplayers VALUES ('" + uuid + "', " + level + ", '" + name + "', '" + godmode + "', " + flymode + ", '" + mails + "', " + money + ", '" + lastposition + "', '" + lastdeath + "', " + onlinetime + ", " + lastonline + ", '" + jail + "', " + timejail + ", " + id_faction + ", " + faction_rank + ")");
        addGPlayer(uuid, this);
        addUUID(name, uuid);
    }

    public void update() {
        queue("UPDATE gplayers SET name = '" + name + "', level = " + level + ", godmode = '" + godmode + "', flymode = " + flymode + ", mails = '" + mails + "', money = " + money + ", lastposition = '" + lastposition + "', lastdeath = '" + lastdeath + "', onlinetime = " + onlinetime + ", lastonline = " + lastonline + ", jail = '" + jail + "', timejail = " + timejail + ", id_faction = " + id_faction + ", faction_rank = " + faction_rank + " WHERE uuid = '" + uuid + "'");
        removeGPlayer(uuid);
        removeUUID(name);
        addGPlayer(uuid, this);
        addUUID(name, uuid);
    }

    public void delete() {
        queue("DELETE FROM gplayers WHERE uuid = '" + uuid + "'");
        removeGPlayer(uuid);
        removeUUID(name);
    }

    public void setUUID(String uuid) { this.uuid = uuid; }
    public void setLevel(int level) { this.level = level; }
    public void setName(String name) { this.name = name; }
    public void setGodmode(String godmode) { this.godmode = godmode; }
    public void setFlymode(double flymode) { this.flymode = flymode; }
    public void setMails(String mails) { this.mails = mails; }
    public void setMoney(double money) { this.money = money; }
    public void setLastposition(String lastposition) { this.lastposition = lastposition; }
    public void setLastdeath(String lastdeath) { this.lastdeath = lastdeath; }
    public void setOnlinetime(double onlinetime) { this.onlinetime = onlinetime; }
    public void setLastonline(double lastonline) { this.lastonline = lastonline; }
    public void setJail(String jail) { this.jail = jail; }
    public void setTimejail(double timejail) { this.timejail = timejail; }
    public void setID_faction(int id_faction) { this.id_faction = id_faction; }
    public void setFactionRank(int faction_rank) { this.faction_rank = faction_rank; }

    public void setHome(String name, GHome home) { if(homes == null) homes = new HashMap<>(); homes.put(name, home); }
    public void setHomes(HashMap<String, GHome> homes) { if(homes == null) homes = new HashMap<>(); this.homes = homes; }
    public void setReply(String reply) { this.reply = reply; }
    public void setTPA(HashMap<String, Double> tpa) { this.tpa = tpa; }
    public void setTPAHere(HashMap<String, Double> tpahere) { this.tpahere = tpahere; }
    public void setPages(HashMap<Integer, List<Text>> pages) { this.pages = pages; }
    public void setPageTitle(Text page_title) { this.page_title = page_title; }
    public void setPageHeader(Text page_header) { this.page_header = page_header; }

    public String getUUID() { return uuid; }
    public int getLevel() { return level; }
    public String getName() { return name; }
    public String getGodmode() { return godmode; }
    public double getFlymode() { return flymode; }
    public String getMails() { return mails; }
    public double getMoney() { return money; }
    public String getLastposition() { return lastposition; }
    public String getLastdeath() { return lastdeath; }
    public double getOnlinetime() { return onlinetime; }
    public double getLastonline() { return lastonline; }
    public String getJail() { return jail; }
    public double getTimejail() { return timejail; }
    public int getID_faction() { return id_faction; }
    public int getFactionRank() { return faction_rank; }
    public Optional<GHome> getHome(String name) { 
        if(homes == null) homes = new HashMap<>(); 
        if(homes.containsKey(name)){
            return Optional.of(homes.get(name));
        }else{
            return Optional.empty();
        } 
    }
    public void removeGHome(String name) { if(homes.containsKey(name)) homes.remove(name); }
    public HashMap<String, GHome> getHomes() { if(homes == null) homes = new HashMap<>(); return homes; }
    public String getReply() { return reply; }
    public HashMap<String, Double> getTPA() { return tpa; }
    public HashMap<String, Double> getTPAHere() { return tpahere; }
    public HashMap<Integer, List<Text>> getPages() { return pages; }
    public Text getPageTitle() { return page_title; }
    public Text getPageHeader() { return page_header; }

    public void creditMoney(double amount) {
            amount *= 100; amount = Math.round(amount); amount /= 100;
            this.money += amount;
    }
    public void debitMoney(double amount) {
            amount *= 100; amount = Math.round(amount); amount /= 100;
            this.money -= amount;
    }

    public void sendMessage(Text text)
    {
        if(ServerManager.isOnline(name)){
            Optional<Player> player = serverManager.getPlayer(name);
            if(player.isPresent()){player.get().sendMessage(text);}
        }
    }
}

