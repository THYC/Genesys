package net.teraoctet.genesys.utils;

public class GHome {
	
	private String uuid;
	private String name;
	private String world;
	private double x;
	private double y;
	private double z;
	
	public GHome(String uuid, String name, String world, double x, double y, double z) {
		this.uuid = uuid;
		this.name = name;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void insert() {
		GData.queue("INSERT INTO ghomes VALUES ('" + uuid + "', '" + name + "', '" + world + "', " + x + ", " + y + ", " + z + ")");
	}
	
	public void update() {
		GData.queue("UPDATE ghomes SET world = '" + world + "', x = " + x + ", y = " + y + ", z = " + z + " WHERE uuid = '" + uuid + "' AND name = '" + name + "'");
	}
	
	public void delete() {
		GData.queue("DELETE FROM ghomes WHERE uuid = '" + uuid + "' AND name = '" + name + "'");
	}
	
	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setName(String name) { this.name = name; }
	public void setWorld(String world) { this.world = world; }
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public void setZ(double z) { this.z = z; }
	
	public String getUUID() { return uuid; }
	public String getName() { return name; }
	public String getWorld() { return world; }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }

}
