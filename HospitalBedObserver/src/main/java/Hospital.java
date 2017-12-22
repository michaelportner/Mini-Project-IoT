import java.util.*;

public class Hospital {
	private static String myName = new String("");
	private Map<Integer, Room> myRooms = new TreeMap<Integer, Room>();
    private static final Hospital myHospital = new Hospital(); 
    private Hospital() {
	}
    
     //Singleton   
    public static Hospital getInstance(String name) {
    	myName = name;
    	return myHospital;
    } 
    
    //Singleton   
   public static Hospital getInstance() {
   	return myHospital;
   } 
	
	public int getAmountOfRooms() {
		return this.myRooms.size();
	}
	
	public String getMyName() {
		return Hospital.myName;
	}

	public void setMyName(String myName) {
		Hospital.myName = myName;
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
