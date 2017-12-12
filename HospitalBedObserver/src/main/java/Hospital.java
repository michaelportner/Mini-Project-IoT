import java.util.*;

public class Hospital {
	private String myName = new String("");
	private Map<Integer, Room> myRooms = new TreeMap<Integer, Room>();
    private static final Hospital myHospital = new Hospital(); 
    private Hospital() {
	}
    
     //Singleton   
    public static Hospital getInstance() {
      return myHospital;
    } 
	
	public int getAmountOfRooms() {
		return this.myRooms.size();
	}
	
	public String getMyName() {
		return this.myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public Map<Integer, Room> getRooms() {
		return myRooms;
	}
	
	public synchronized Room getRoom(int Index) {
		return myRooms.get(Index);
	}

	public void addRoom(int Index, Room Room) {
		myRooms.put(Index, Room);
	}
	
	public void removeRoom(int Index) {
		myRooms.remove(Index);
	}
	
	public void removeAllRooms() {
		myRooms.clear();
	}
	
}
