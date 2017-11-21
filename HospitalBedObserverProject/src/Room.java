
public class Room {
	private BedCollector myBeds = new BedCollector();
	
	public BedCollector getBedInformations() {
		return myBeds;
	}
	
	public void addBedInformation(Bed BedInfo) {
		myBeds.addBedInformation(BedInfo);
	}
}
