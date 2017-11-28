import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;
import java.util.*;

public class Bed {
	private Status myStatus = Status.RED;
	private Group myGroup;
	private Button myInfoButton;
	private BedInfo myBedInfo;
	
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

	public Status getMyStatus() {
		return myStatus;
	}
	
	public BedInfo getMyBedInfo() {
		return myBedInfo;
	}
	
	public void setMyBedInfo(BedInfo BedInfo) {
		this.myBedInfo = BedInfo;
	}
	

	public void setMyStatus(Status myStatus) {
		Date DateTimeNow = new Date();
		this.myStatus = myStatus;
		this.myBedInfo.addHistoryEntry(DateTimeNow.getTime(), myStatus);
		switch (myStatus){
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
			myGroup.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
			break;			
		}
	}	
	
}
