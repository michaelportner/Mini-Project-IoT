import java.util.Date;

public class HistoryEntry implements Comparable<HistoryEntry> {

	private Date dateTime;
	private State historicalState;
	
	public HistoryEntry(Date myDate, State State) {
		dateTime = myDate;
		historicalState = State;
	}
	
	public State getHistoricalState() {
		return historicalState;
	}
	
	public void setHistoricalState(State historicalState) {
		this.historicalState = historicalState;
	}
	
	
	public Date getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(Date datetime) {
		this.dateTime = datetime;
	}
	
	public int compareTo(HistoryEntry o) {
		if (getDateTime() == null || o.getDateTime() == null) {
			return 0;
		}
		return getDateTime().compareTo(o.getDateTime());
	}
}
