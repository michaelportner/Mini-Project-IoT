import java.util.*;

class BedInfo {
	private String name = "";
	private List<HistoryEntry> History = new ArrayList<HistoryEntry>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<HistoryEntry> getHistory() {
		return History;
	}
	
	public void addHistoryEntry(long myDateMs, State BedState) {
		Date myDate = new Date();
		myDate.setTime(myDateMs);
		History.add(new HistoryEntry(myDate, BedState));
	}
	
	public State getHistoryEntryState(Date DateTimeKey) {
		for (int i = 0; i < History.size(); ++i) {
			if(History.get(i).getDateTime() == DateTimeKey) {
				return History.get(i).getHistoricalState();
			}				
		}
		return null;
	}
}
