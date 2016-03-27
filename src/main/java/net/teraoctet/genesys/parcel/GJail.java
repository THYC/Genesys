package net.teraoctet.genesys.parcel;

import net.teraoctet.genesys.utils.GData;

public class GJail {
	private String uuid;
	private String player;
        private String jail;
	private String reason;
	private double time;
	private double duration;
	
	public GJail(String uuid, String player, String jail, String reason, double time, double duration) {
		this.uuid = uuid;
		this.player = player;
                this.jail = jail;
		this.reason = reason;
		this.time = time;
		this.duration = duration;
	}
	
	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setPlayer(String sender) { this.player = player; }
        public void setJail(String sender) { this.jail = jail; }
	public void setReason(String reason) { this.reason = reason; }
	public void setTime(double time) { this.time = time; }
	public void setDuration(double duration) { this.duration = duration; }
	
	public String getUUID() { return uuid; }
	public String getPlayer() { return player; }
        public String getJail() { return jail; }
	public String getReason() { return reason; }
	public double getTime() { return time; }
	public double getDuration() { return duration; }
	
	public void insert() {
		GData.queue("INSERT INTO gjail VALUES ('" + uuid + "', '" + player + "', '" + jail + "', '" + reason + "', " + time + ", " + duration + ")");
		GData.addJail(uuid, this);
	}
	
	public void update() {
		GData.queue("UPDATE gjail SET sender = '" + player + "', reason = '" + reason + "', time = " + time + ", duration = " + duration + " WHERE uuid = '" + uuid + "'");
		GData.removeJail(uuid);
		GData.addJail(uuid, this);
	}
	
	public void delete() {
		GData.queue("DELETE FROM gjail WHERE uuid = '" + uuid + "'");
		GData.removeJail(uuid);
	}
	
}
