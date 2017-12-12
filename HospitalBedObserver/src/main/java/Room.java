import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

public class Room {
	private Map<Integer, Bed> myBeds = new TreeMap<Integer, Bed>();
	private TabItem myTab;
	private Group myTabGroup;
	private Group myGroup;
	
	public Group getMyGroup() {
		return myGroup;
	}

	public void setMyGroup(Group myGroup) {
		this.myGroup = myGroup;
	}

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

	public Map<Integer, Bed> getBeds() {
		return myBeds;
	}
	
	public synchronized Bed getBed(int Index) {
		return myBeds.get(Index);
	}
	
	public synchronized void addBed(Integer Index, Bed Bed) {
		Bed.setName(Index.toString());
		myBeds.put(Index, Bed);
	}
	
	public synchronized void removeBed(Integer Index) {
		myBeds.remove(Index);
	}
	
	public synchronized void removeBeds() {
		myBeds.clear();
	}
	
	public void setMyGroupBackgroundColor() {
		Iterator<Entry<Integer,Bed>> bedIt = getBeds().entrySet().iterator();
		while (bedIt.hasNext()) {
	        Map.Entry<Integer, Bed> bedEntry = bedIt.next();
	        if(getBed(bedEntry.getKey()).getMyState() == State.RED) {
	        	myGroup.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
	        	return;
	        }
		}
    	myGroup.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
	}
	
	public int getAmountOfBeds() {
		return myBeds.size();
	}
}
