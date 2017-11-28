import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;

public class MainWindow {

	protected Shell shlBedObserver;
	private TabFolder tabFolder;
	private Hospital myHospital = Hospital.getInstance();
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		
		//BedInfoWindow Test implemented for Bed 1 and 2 in Room 1
		for (int i = 0; i< 100; i++) {
			if (i%10 > 0 && i%10 < 3) {
				myHospital.getRoom(1).getBed(1).setMyState(State.GREEN);
			}else if (i%10 < 7) {
				myHospital.getRoom(1).getBed(1).setMyState(State.ORANGE);
			}else{
				myHospital.getRoom(1).getBed(1).setMyState(State.RED);
			}
		}
		for (int i = 0; i< 100; i++) {
			if (i%10 > 0 && i%10 < 3) {
				myHospital.getRoom(1).getBed(2).setMyState(State.GREEN);
			}else if (i%10 < 7) {
				myHospital.getRoom(1).getBed(2).setMyState(State.ORANGE);
			}else{
				myHospital.getRoom(1).getBed(2).setMyState(State.RED);
			}
		}
		//End BedInfoTest
		
		shlBedObserver.open();
		shlBedObserver.layout();
		while (!shlBedObserver.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlBedObserver = new Shell();
		shlBedObserver.setSize(738, 574);
		shlBedObserver.setText("Bed Observer");
		shlBedObserver.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Menu menu = new Menu(shlBedObserver, SWT.BAR);
		shlBedObserver.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.NONE);

		mntmFile.setText("File");
		
		MenuItem mntmHelp = new MenuItem(menu, SWT.NONE);
		mntmHelp.setText("Help");
		
		Composite composite = new Composite(shlBedObserver, SWT.NONE);
		
				tabFolder = new TabFolder(composite, SWT.NONE);
				tabFolder.setLocation(0, 0);
				tabFolder.setSize(720, 435);
				
				Composite composite_1 = new Composite(shlBedObserver, SWT.NONE);
				composite_1.setLayoutData(new RowData(718, 55));
				
				Button btnRefresh = new Button(composite_1, SWT.NONE);
				btnRefresh.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						refreshAllViews();
					}
				});
				btnRefresh.setBounds(488, 10, 90, 30);
				btnRefresh.setText("Refresh");
				
				Button btnClose = new Button(composite_1, SWT.NONE);
				btnClose.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						shlBedObserver.close();
					}
				});
				btnClose.setBounds(606, 10, 90, 30);
				btnClose.setText("Close");
		
		//Test Block
			myHospital.addRoom(1, new Room());
			myHospital.addRoom(2, new Room());
			myHospital.addRoom(3, new Room());
			
			myHospital.getRoom(1).addBed(1, new Bed());
			myHospital.getRoom(1).addBed(2, new Bed());	
			myHospital.getRoom(1).addBed(3, new Bed());
			myHospital.getRoom(1).addBed(4, new Bed());
			myHospital.getRoom(1).addBed(5, new Bed());
			myHospital.getRoom(1).addBed(6, new Bed());
			myHospital.getRoom(1).addBed(7, new Bed());
			myHospital.getRoom(1).addBed(8, new Bed());
			
			myHospital.getRoom(2).addBed(1, new Bed());
			myHospital.getRoom(2).addBed(2, new Bed());
			myHospital.getRoom(2).addBed(3, new Bed());
			
			myHospital.getRoom(3).addBed(1, new Bed());
			myHospital.getRoom(3).addBed(2, new Bed());
			myHospital.getRoom(3).addBed(3, new Bed());
			myHospital.getRoom(3).addBed(4, new Bed());
			myHospital.getRoom(3).addBed(5, new Bed());
			myHospital.getRoom(3).addBed(6, new Bed());
			
		//End Test Block
			
			createAllViews();
		
		

	}	
	
	public void refreshAllViews() {
		removeAllViews();
		createAllViews();
	}
	
	public void createAllViews() {
	    Iterator<Entry<Integer,Room>> it = myHospital.getRooms().entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Integer,Room> roomEntry = it.next();
			myHospital.getRoom(roomEntry.getKey()).setMyTab(new TabItem(tabFolder, SWT.None));
			myHospital.getRoom(roomEntry.getKey()).getMyTab().setText("Room " + roomEntry.getKey());
			myHospital.getRoom(roomEntry.getKey()).setMyTabGroup(new Group(tabFolder, SWT.NONE));
			myHospital.getRoom(roomEntry.getKey()).getMyTab().setControl(myHospital.getRoom(roomEntry.getKey()).getMyTabGroup());
			Iterator<Entry<Integer,Bed>> bedIt = myHospital.getRoom(roomEntry.getKey()).getBeds().entrySet().iterator();
			int bedCounter = 1;
			while (bedIt.hasNext()) {
		        Map.Entry<Integer, Bed> bedEntry = bedIt.next();
				
				myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).setMyGroup(new Group(myHospital.getRoom(roomEntry.getKey()).getMyTabGroup(), SWT.NONE));
				myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).getMyGroup().setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
				myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).getMyGroup().setText("Bed " + bedEntry.getKey());
				// Height and width of groups
				int groupWidth = 171;
				int groupHeight = 94;
				
				//Set the y-position of the group
				int basePosition = 24;
				int amountOfBedsPerRow = 3;
				int yPosition = basePosition + ((bedEntry.getKey()-1)/amountOfBedsPerRow)*121;
				
				//Set the x-positions of the group
				int xPosition = 0;
				switch (bedCounter % 3) {
					case 1:
						xPosition = 24;
						break;
					case 2:
						xPosition = 237;
						break;
					case 0:
						xPosition = 450;
						break;
					default:
						break;
				}
				
				myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).getMyGroup().setBounds(xPosition, yPosition, groupWidth, groupHeight);
				
				
				
				myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).setMyInfoButton(new Button(myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).getMyGroup(), SWT.NONE));
				myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).getMyInfoButton().addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						BedInfoWindow BedInfoWindow = new BedInfoWindow(myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).getMyBedInfo());
						BedInfoWindow.open();
					}
				});
				myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).getMyInfoButton().setBounds(71, 54, 90, 30);
				myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).getMyInfoButton().setText("Bed " + (bedEntry.getKey()) +  " Info");
				++bedCounter;
			}
			
		}
	    
	}
	
	public void removeAllViews() {
	    Iterator<Entry<Integer,Room>> it = myHospital.getRooms().entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Integer,Room> roomEntry = it.next();
			myHospital.getRoom(roomEntry.getKey()).getMyTab().dispose();
	    }	    
	}
}
