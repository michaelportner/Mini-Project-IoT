import java.util.*;

class BedInfo {
	private String name = "";
	private HashMap<Date, Status> History = new HashMap<Date, Status>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HashMap<Date, Status> getHistory() {
		return History;
	}
	public void addHistoryEntry(long DatumMs, Status BedStatus) {
		Date Datum = new Date();
		Datum.setTime(DatumMs);
		History.put(Datum, BedStatus);
	}
	
	public Status getHistoryEntry(Date DateTimeKey) {
		return History.get(DateTimeKey);
	}
}
