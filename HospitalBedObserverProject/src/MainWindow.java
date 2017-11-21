import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.layout.FillLayout;

public class MainWindow {

	protected Shell shlBedObserver;

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
		shlBedObserver.setSize(738, 453);
		shlBedObserver.setText("Bed Observer");
		shlBedObserver.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Menu menu = new Menu(shlBedObserver, SWT.BAR);
		shlBedObserver.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.NONE);
		mntmFile.setText("File");
		
		MenuItem mntmHelp = new MenuItem(menu, SWT.NONE);
		mntmHelp.setText("Help");
		
		TabFolder tabFolder = new TabFolder(shlBedObserver, SWT.NONE);
		
		TabItem tbtmRoom = new TabItem(tabFolder, SWT.NONE);
		tbtmRoom.setText("Room 1");
		
		Group groupBed_1 = new Group(tabFolder, SWT.NONE);
		tbtmRoom.setControl(groupBed_1);
		
		Group grpBed = new Group(groupBed_1, SWT.NONE);
		grpBed.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpBed.setText("Bed 1");
		grpBed.setBounds(10, 20, 190, 107);
		
		Button btnBedInfo_1 = new Button(grpBed, SWT.NONE);
		btnBedInfo_1.setBounds(90, 67, 90, 30);
		btnBedInfo_1.setText("Bed 1 Info");
		
		Group group = new Group(groupBed_1, SWT.NONE);
		group.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		group.setText("Bed 1");
		group.setBounds(224, 20, 190, 107);
		
		Button button = new Button(group, SWT.NONE);
		button.setText("Bed 1 Info");
		button.setBounds(90, 67, 90, 30);
		
		Group group_1 = new Group(groupBed_1, SWT.NONE);
		group_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		group_1.setText("Bed 1");
		group_1.setBounds(438, 20, 190, 107);
		
		Button button_1 = new Button(group_1, SWT.NONE);
		button_1.setText("Bed 1 Info");
		button_1.setBounds(90, 67, 90, 30);
		
		Group group_2 = new Group(groupBed_1, SWT.NONE);
		group_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		group_2.setText("Bed 1");
		group_2.setBounds(438, 144, 190, 107);
		
		Button button_2 = new Button(group_2, SWT.NONE);
		button_2.setText("Bed 1 Info");
		button_2.setBounds(90, 67, 90, 30);
		
		Group group_3 = new Group(groupBed_1, SWT.NONE);
		group_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		group_3.setText("Bed 1");
		group_3.setBounds(224, 144, 190, 107);
		
		Button button_3 = new Button(group_3, SWT.NONE);
		button_3.setText("Bed 1 Info");
		button_3.setBounds(90, 67, 90, 30);
		
		Group group_4 = new Group(groupBed_1, SWT.NONE);
		group_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		group_4.setText("Bed 1");
		group_4.setBounds(10, 144, 190, 107);
		
		Button button_4 = new Button(group_4, SWT.NONE);
		button_4.setText("Bed 1 Info");
		button_4.setBounds(90, 67, 90, 30);
		
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("New Item");
		
		TabItem tbtmNewItem = new TabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("New Item");

	}
}
