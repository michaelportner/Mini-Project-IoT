import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;
import java.util.*;

public class Bed {
	private State myState = State.UNKNOWN;
	private Group myGroup;
	private Button myInfoButton;
	private BedInfo myBedInfo = new BedInfo();
	
	public void setName(String name){
		myBedInfo.setName(name);
	}
	
	public Group getMyGroup() {
		return myGroup;
	}

	public Button getMyInfoButton() {
		return myInfoButton;
	}

	public void setMyInfoButton(Button myInfoButton) {
		this.myInfoButton = myInfoButton;
	}

	public void setMyGroup(Group Group) {
		myGroup = Group;
	}

	public State getMyState() {
		return myState;
	}
	
	public BedInfo getMyBedInfo() {
		return myBedInfo;
	}
	
	public void setMyBedInfo(BedInfo BedInfo) {
		this.myBedInfo = BedInfo;
	}
	

	public void setMyState(State myState) {
		Date DateTimeNow = new Date();
		this.myState = myState;
		this.myBedInfo.addHistoryEntry(DateTimeNow.getTime(), myState);
		switch (myState){
		case RED:
			myGroup.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
			break;
		case ORANGE:
			myGroup.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
			break;
		case GREEN:
			myGroup.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
			break;
		default:
			myGroup.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
			break;			
		}
	}	
	
}
