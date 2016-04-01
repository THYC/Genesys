package net.teraoctet.genesys.faction;

import static net.teraoctet.genesys.utils.GData.addGFaction;
import static net.teraoctet.genesys.utils.GData.queue;
import static net.teraoctet.genesys.utils.GData.removeGFaction;

public class GFaction {
    private int id_faction;
    private String name;
    private String world;
    private int X;
    private int Y;
    private int Z;
    private double money;
    private int point;
    private int kill;
    private int dead;

    public GFaction(String name, double money, int point) {
        this.name = name;
        this.world = null;
        this.X = 0;
        this.Y = 0;
        this.Z = 0;
        this.money = money;
        this.point = point;
        this.kill = 0;
        this.dead = 0;
    }

    public void insert() {
        queue("INSERT INTO gfactions VALUES (" + id_faction + ", '" + name + "', '" + world + "', " + X + ", " + Y + ", " + Z + ", " + money + ", " + point + ", " + kill + ", " + dead + ")");
        addGFaction(name, this);
    }

    public void update() {
        queue("UPDATE gfactions SET id_faction = " + id_faction + ", name = '" + name + "', world = '" + world + "', X = " + X + ", Y = " + Y + ", Z = " + Z + ", money = " + money + ", point = " + point + ", kill = " + kill + ", dead = " + dead + " WHERE id_faction = " + id_faction);
        removeGFaction(id_faction);
        addGFaction(name, this);
    }

    public void delete() {
        queue("DELETE FROM gfactions WHERE id_faction = " + id_faction);
        removeGFaction(id_faction);
    }
    
    public Integer getID() { return id_faction; }
    public String getName() { return name; }
    public String getWorld() { return world; }
    public Integer getX() { return X; }
    public Integer getY() { return Y; }
    public Integer getZ() { return Z; }
    public Double getMoney() { return money; }
    public Integer getPoint() { return point; }
    public Integer getKill() { return kill; }
    public Integer getDead() { return dead; }
    
    public void setID(Integer id_faction) { this.id_faction = id_faction; }
    public void setName(String name) { this.name = name; }
    public void setWorld(String world) { this.world = world; }
    public void setX(Integer X) { this.X = X; }
    public void setY(Integer Y) { this.Y = Y; }
    public void setZ(Integer Z) { this.Z = Z; }
    public void setMoney(Double money) { this.money = money; }
    public void setPoint(Integer point) { this.point = point; }
    public void setKill(Integer kill) { this.kill = kill; }
    public void setDead(Integer dead) { this.dead = dead; }
}
