import java.util.HashMap;

import org.eclipse.swt.widgets.*;

public class Room {
	private HashMap<Integer, Bed> myBeds = new HashMap<Integer, Bed>();
	private TabItem myTab;
	private Group myTabGroup;
	
	public Group getMyTabGroup() {
		return myTabGroup;
	}

	public void setMyTabGroup(Group myTabGroup) {
		this.myTabGroup = myTabGroup;
	}

	public TabItem getMyTab() {
		return myTab;
	}

	public void setMyTab(TabItem Tab) {
		myTab = Tab;
	}

	public HashMap<Integer, Bed> getBeds() {
		return myBeds;
	}
	
	public Bed getBed(int Index) {
		return myBeds.get(Index);
	}
	
	public void addBed(Integer Index, Bed Bed) {
		Bed.setName(Index.toString());
		myBeds.put(Index, Bed);
	}
	
	public int getAmountOfBeds() {
		return myBeds.size();
	}
}
