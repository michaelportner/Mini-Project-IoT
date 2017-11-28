import java.util.*;

class BedInfo {
	private String name;
	private HashMap<Date, Status> History;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HashMap<Date, Status> getHistory() {
		return History;
	}
	public void addHistoryEntry(Date Datum, Status BedStatus) {
		History.put(Datum, BedStatus);
	}
	
	public Status getHistoryEntry(Date DateTimeKey) {
		return History.get(DateTimeKey);
	}
}
