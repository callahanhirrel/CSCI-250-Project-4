package network;

import java.io.Serializable;

import gui.ScheduleController;

public class NetworkData implements Serializable {

	/**
	 * Auto-generated serial ID
	 */
	private static final long serialVersionUID = 1L;
	private String tag;
	private String username;
	private String query;
	private boolean isFree;
	public static String CONNECT_TAG = "CONNECTION REQUEST";
	public static String MEETING_TAG = "MEETING REQUEST";
	public static String SCHEDULE_TAG = "SCHEDULE REQUEST";

	public NetworkData(String tag) {
		this.tag = tag;
		this.username = ScheduleController.USERNAME;
	}

	public String getTag() {
		return this.tag;
	}

	public void setQuery(String q) {
		this.query = q;
	}

	public void setIsFree(boolean bool) {
		this.isFree = bool;
	}

	public boolean getIsFree() {
		return this.isFree;
	}


	public String getUsername() {
		return this.username;
	}
}
