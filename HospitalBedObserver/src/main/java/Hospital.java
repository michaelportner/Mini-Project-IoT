import java.util.*;

public class Hospital {
	private String myName = new String("");
	private HashMap<Integer, Room> myRooms = new HashMap<Integer, Room>();
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

	public HashMap<Integer, Room> getRooms() {
		return myRooms;
	}
	
	public Room getRoom(int Index) {
		return myRooms.get(Index);
	}

	public void addRoom(int Index, Room Room) {
		myRooms.put(Index, Room);
	}
	
}
