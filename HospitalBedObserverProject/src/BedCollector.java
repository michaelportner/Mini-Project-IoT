import java.util.*;
public class BedCollector  {
	private int MaxIndex = 0;
	private HashMap<Integer, Bed> myBedInformation = new HashMap<Integer, Bed>();
	
	
	
	public HashMap<Integer, Bed> getMyBedInformations() {
		return myBedInformation;
	}



	public void setMyBedInformations(HashMap<Integer, Bed> myBedInformations) {
		this.myBedInformation = myBedInformations;
	}



	public void addBedInformation(Bed BedInfo) {
		myBedInformation.put(MaxIndex, BedInfo);
	}
	

}
