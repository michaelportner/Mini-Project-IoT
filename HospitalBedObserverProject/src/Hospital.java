import java.util.*;

public final class Hospital {
	private String myName = new String("");
	private List<Room> myRooms = new ArrayList<Room>();
	
	public Hospital(String name) {
		this.myName = name;
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

	public List<Room> getMyRooms() {
		return myRooms;
	}

	public void setMyRooms(List<Room> myRooms) {
		this.myRooms = myRooms;
	}

	public void addRoom(Room Room) {
		myRooms.add(Room);
	}

	
}
