package network;

import java.io.Serializable;
import java.sql.ResultSet;

import gui.ScheduleController;

public class NetworkData implements Serializable {

	/**
	 * Auto-generated serial ID
	 */
	private static final long serialVersionUID = 1L;
	private String tag;
	private String username;
	private String query;
	private String dayOfRequest;
	private ResultSet rs;
	private boolean isFree;
	public static String CONNECT_TAG = "CONNECTION REQUEST";
	public static String MEETING_TAG = "MEETING REQUEST";
	public static String SCHEDULE_TAG = "SCHEDULE REQUEST";

	public NetworkData(String tag) {
		this.tag = tag;
		this.username = ScheduleController.USERNAME;
	}

	public ResultSet getResultSet() {
		return this.rs;
	}

	public void setResultSet(ResultSet results) {
		this.rs = results;
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

	public void setDayOfRequest(String day) {
		this.dayOfRequest = day;
	}

	public String getDayOfRequest() {
		return this.dayOfRequest;
	}

	public String getQuery() {
		return this.query;
	}
}
