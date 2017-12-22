import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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
import org.eclipse.swt.layout.FillLayout;
import swing2swt.layout.BorderLayout;
import swing2swt.layout.BoxLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public final class MainWindow {

	protected Shell shlBedObserver;
	private TabFolder tabFolder;
	private TabItem mainTabItem;
	private Group mainTabGroup;
	protected static Hospital myHospital = Hospital.getInstance("MyHospital");
	private static ObserverMqttClient myObserverMqttClient = ObserverMqttClient.getInstance();
	private static final int REFRESH_TIME = 1000;
	private static final int AMOUNT_OF_REFRESH_TILL_RESET = 10;
	private static final long TIME_TO_WAIT_ON_REINITIALIZE_MS = 30000;	
	private static long startRefreshTime = System.currentTimeMillis();
	private static long amountOfRefresh = 0;
	private static boolean isViewRefreshing = false;
    private static String PATH = "C:\\Users\\micha\\OneDrive\\Dokumente\\ZHAW\\Mini-Project-IoT\\HospitalBedObserver\\log\\";

	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			myObserverMqttClient.subscribe();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @throws InterruptedException 
	 */
	public void open() throws InterruptedException {
		Display display = Display.getDefault();
		createContents();
		shlBedObserver.open();
		shlBedObserver.layout();
		refreshViews();
		while (!shlBedObserver.isDisposed()) {
			if ((System.currentTimeMillis()-startRefreshTime) > REFRESH_TIME && !isViewRefreshing)
			{
				Iterator<Entry<Integer,Room>> it = myHospital.getRooms().entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Integer,Room> roomEntry = it.next();
					Iterator<Entry<Integer,Bed>> bedIt = myHospital.getRoom(roomEntry.getKey()).getBeds().entrySet().iterator();
					while (bedIt.hasNext()) {
				        Map.Entry<Integer, Bed> bedEntry = bedIt.next();
						myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).setMyGroupBackgroundColor();
						myHospital.getRoom(roomEntry.getKey()).setMyGroupBackgroundColor();
					}
				 }
				 startRefreshTime = System.currentTimeMillis();
				 //myObserverMqttClient.publishGetStatesRequest();
				 ++amountOfRefresh;
				 
			}
//			if (amountOfRefresh > AMOUNT_OF_REFRESH_TILL_RESET && !isViewRefreshing)
//			{
//				Iterator<Entry<Integer,Room>> it = myHospital.getRooms().entrySet().iterator();
//				while (it.hasNext()) {
//					Map.Entry<Integer,Room> roomEntry = it.next();
//					Iterator<Entry<Integer,Bed>> bedIt = myHospital.getRoom(roomEntry.getKey()).getBeds().entrySet().iterator();
//					while (bedIt.hasNext()) {
//				        Map.Entry<Integer, Bed> bedEntry = bedIt.next();
//						myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).resetBedState();
//					}
//				 }
//				 amountOfRefresh = 0;    
//			}
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
		shlBedObserver.setLayout(new FormLayout());
		
		Menu menu = new Menu(shlBedObserver, SWT.BAR);
		shlBedObserver.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.NONE);

		mntmFile.setText("File");
		
		MenuItem mntmHelp = new MenuItem(menu, SWT.NONE);
		mntmHelp.setText("Help");
		
		Composite composite = new Composite(shlBedObserver, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0, 3);
		fd_composite.left = new FormAttachment(0, 3);
		composite.setLayoutData(fd_composite);

		tabFolder = new TabFolder(composite, SWT.NONE);
		tabFolder.setLocation(0, 0);
		tabFolder.setSize(720, 435);
		mainTabItem = new TabItem(tabFolder, SWT.None);
		mainTabItem.setText("Room Overview");
        mainTabGroup = new Group(tabFolder, SWT.NONE);
        mainTabItem.setControl(mainTabGroup);
		
		Composite composite_1 = new Composite(shlBedObserver, SWT.NONE);
		FormData fd_composite_1 = new FormData();
		fd_composite_1.top = new FormAttachment(0, 441);
		fd_composite_1.left = new FormAttachment(0, 3);
		composite_1.setLayoutData(fd_composite_1);
		
		Button btnRefresh = new Button(composite_1, SWT.NONE);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refreshViews();
			}
		});
		btnRefresh.setBounds(488, 10, 90, 30);
		btnRefresh.setText("Refresh");
		
		Button btnClose = new Button(composite_1, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shutDown();
			}
		});
		btnClose.setBounds(606, 10, 90, 30);
		btnClose.setText("Close");
		
		Button btnSaveBedHistories = new Button(composite_1, SWT.NONE);
		btnSaveBedHistories.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveBedHistoryToLog();
			}
		});
		btnSaveBedHistories.setBounds(10, 10, 141, 30);
		btnSaveBedHistories.setText("Save Bed Histories");
		
		//Test Block
//			myHospital.addRoom(1, new Room());
//			myHospital.addRoom(2, new Room());
//			myHospital.addRoom(3, new Room());
//			
//			myHospital.getRoom(1).addBed(1, new Bed());
//			myHospital.getRoom(1).addBed(2, new Bed());	
//			myHospital.getRoom(1).addBed(3, new Bed());
//			myHospital.getRoom(1).addBed(4, new Bed());
//			myHospital.getRoom(1).addBed(5, new Bed());
//			myHospital.getRoom(1).addBed(6, new Bed());
//			myHospital.getRoom(1).addBed(7, new Bed());
//			myHospital.getRoom(1).addBed(8, new Bed());
//			
//			myHospital.getRoom(2).addBed(1, new Bed());
//			myHospital.getRoom(2).addBed(2, new Bed());
//			myHospital.getRoom(2).addBed(3, new Bed());
//			
//			myHospital.getRoom(3).addBed(1, new Bed());
//			myHospital.getRoom(3).addBed(2, new Bed());
//			myHospital.getRoom(3).addBed(3, new Bed());
//			myHospital.getRoom(3).addBed(4, new Bed());
//			myHospital.getRoom(3).addBed(5, new Bed());
//			myHospital.getRoom(3).addBed(6, new Bed());
//			
		//End Test Block
			
			createAllViews();
		
		

	}	
	
	public void shutDown() {
		shlBedObserver.close();
		myObserverMqttClient.disconnectBroker();
		System.exit(0);
	}
	
	public void createAllViews() {
	    Iterator<Entry<Integer,Room>> it = myHospital.getRooms().entrySet().iterator();
	    int roomCounter = 0;
	    while (it.hasNext()) {
	    	++roomCounter;
	    	
	        final Map.Entry<Integer,Room> roomEntry = it.next();
			myHospital.getRoom(roomEntry.getKey()).setMyGroup(new Group(mainTabGroup, SWT.NONE));
			myHospital.getRoom(roomEntry.getKey()).getMyGroup().setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
			myHospital.getRoom(roomEntry.getKey()).getMyGroup().setText("Room " + roomEntry.getKey());
			myHospital.getRoom(roomEntry.getKey()).getMyGroup().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					tabFolder.setSelection(myHospital.getRoom(roomEntry.getKey()).getMyTab());
				}
			});
			// Height and width of groups
			int groupWidth = 171;
			int groupHeight = 94;
			
			//Set the y-position of the group
			int basePosition = 24;
			int amountOfGroupsPerRow = 3;
			int yPosition = basePosition + ((roomCounter-1)/amountOfGroupsPerRow)*121;
			
			//Set the x-positions of the group
			int xPosition = 0;
			switch (roomCounter % 3) {
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
			myHospital.getRoom(roomEntry.getKey()).getMyGroup().setBounds(xPosition, yPosition, groupWidth, groupHeight);
	        
			myHospital.getRoom(roomEntry.getKey()).setMyTab(new TabItem(tabFolder, SWT.None));
			myHospital.getRoom(roomEntry.getKey()).getMyTab().setText("Room " + roomEntry.getKey());
			myHospital.getRoom(roomEntry.getKey()).setMyTabGroup(new Group(tabFolder, SWT.NONE));
			myHospital.getRoom(roomEntry.getKey()).getMyTab().setControl(myHospital.getRoom(roomEntry.getKey()).getMyTabGroup());
			if (myHospital.getRoom(roomEntry.getKey()).getBeds().isEmpty()) {
				myHospital.getRoom(roomEntry.getKey()).getMyTab().dispose();
				myHospital.getRoom(roomEntry.getKey()).getMyGroup().dispose();
			}else {
				Iterator<Entry<Integer,Bed>> bedIt = myHospital.getRoom(roomEntry.getKey()).getBeds().entrySet().iterator();
				int bedCounter = 0;
				while (bedIt.hasNext()) {
					++bedCounter;
					
			        final Map.Entry<Integer, Bed> bedEntry = bedIt.next();
					
					myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).setMyGroup(new Group(myHospital.getRoom(roomEntry.getKey()).getMyTabGroup(), SWT.NONE));
					myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).getMyGroup().setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
					myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).getMyGroup().setText("Bed " + bedEntry.getKey());
	
					yPosition = basePosition + ((bedCounter-1)/amountOfGroupsPerRow)*121;
					
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
				}
			}
			
		}
	}
	
	public void removeAll() {
		Set<Integer> roomIndices = myHospital.getRooms().keySet();
		for(Integer roomIndex : roomIndices) {
			myHospital.getRoom(roomIndex).getMyTab().dispose();
			myHospital.getRoom(roomIndex).getMyGroup().dispose();
		}
		myHospital.removeAllRooms();
	}
	
	private void refreshViews() {
		isViewRefreshing = true;
		myObserverMqttClient.publishReinitializeRequest();
		removeAll();
		ReinitializeInfoWindow myReinitializeInfoWindow = new ReinitializeInfoWindow(shlBedObserver,SWT.ICON_INFORMATION,TIME_TO_WAIT_ON_REINITIALIZE_MS);
		myReinitializeInfoWindow.open();
		long startReinitializeTime = System.currentTimeMillis();
		long currentTimeMs = startReinitializeTime;
		long miliSecondsRemained = 0;
		while ((currentTimeMs-startReinitializeTime) < TIME_TO_WAIT_ON_REINITIALIZE_MS) {		
			if (miliSecondsRemained != ((currentTimeMs-startReinitializeTime) - (currentTimeMs-startReinitializeTime)%1000)){
				miliSecondsRemained = ((currentTimeMs-startReinitializeTime) - (currentTimeMs-startReinitializeTime)%1000);
				myReinitializeInfoWindow.setLblProgressText(miliSecondsRemained);;
			}
			currentTimeMs = System.currentTimeMillis();
		}
		myReinitializeInfoWindow.close();
		//Test Block
//		myHospital.addRoom(1, new Room());
//		myHospital.addRoom(2, new Room());
//		myHospital.addRoom(3, new Room());
//		
//		myHospital.getRoom(1).addBed(1, new Bed());
//		myHospital.getRoom(1).addBed(2, new Bed());	
//		myHospital.getRoom(1).addBed(3, new Bed());
//		myHospital.getRoom(1).addBed(4, new Bed());
//		
//		myHospital.getRoom(2).addBed(1, new Bed());
//		myHospital.getRoom(2).addBed(2, new Bed());
//		myHospital.getRoom(2).addBed(3, new Bed());
//		myHospital.getRoom(2).addBed(5, new Bed());
//		myHospital.getRoom(2).addBed(6, new Bed());
//		myHospital.getRoom(2).addBed(7, new Bed());
//		myHospital.getRoom(2).addBed(8, new Bed());
//		
		
		//End Test Block
		createAllViews();
		isViewRefreshing = false;
	}
	
	public void saveBedHistoryToLog(){
	    String hospitalDirectoryName = PATH.concat(myHospital.getMyName());
	    String fileName = "";
	    Iterator<Entry<Integer,Room>> it = myHospital.getRooms().entrySet().iterator();
	    File directory = new File(hospitalDirectoryName);
	    if (! directory.exists()){
	        directory.mkdir();
	    }
		while (it.hasNext()) {
			Map.Entry<Integer,Room> roomEntry = it.next();
			Iterator<Entry<Integer,Bed>> bedIt = myHospital.getRoom(roomEntry.getKey()).getBeds().entrySet().iterator();
			while (bedIt.hasNext()) {
		        Map.Entry<Integer, Bed> bedEntry = bedIt.next();
		        fileName = "Room "+ roomEntry.getKey().toString() + " Bed " + bedEntry.getKey().toString() + ".txt";
			    File file = new File(hospitalDirectoryName + "/" + fileName);
			    try{
				    file.createNewFile(); // if file already exists will do nothing 
			        FileWriter fw = new FileWriter(file.getAbsoluteFile());
			        BufferedWriter bw = new BufferedWriter(fw);
						java.util.List<HistoryEntry> history = myHospital.getRoom(roomEntry.getKey()).getBed(bedEntry.getKey()).getMyBedInfo().getHistory();
						Collections.sort(history);
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						for (HistoryEntry historyEntry:history){
							bw.write(dateFormat.format(historyEntry.getDateTime()) + "     |      State: " + historyEntry.getHistoricalState().toString());
							bw.newLine();
						}
			        bw.close();
			    }
			    catch (IOException e){
			        e.printStackTrace();
			        System.exit(-1);
			    }
			}
		 }



	}
}
