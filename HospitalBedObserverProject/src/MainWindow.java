import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

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
		shlBedObserver.setSize(533, 414);
		shlBedObserver.setText("Bed Observer");
		
		Menu menu = new Menu(shlBedObserver, SWT.BAR);
		shlBedObserver.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.NONE);
		mntmFile.setText("File");
		
		MenuItem mntmHelp = new MenuItem(menu, SWT.NONE);
		mntmHelp.setText("Help");
		
		Group grpBed_1 = new Group(shlBedObserver, SWT.NONE);
		grpBed_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpBed_1.setText("Bed 1");
		grpBed_1.setBounds(10, 10, 149, 63);
		
		Button btnInfoBed_1 = new Button(grpBed_1, SWT.NONE);
		btnInfoBed_1.setBounds(49, 23, 90, 30);
		btnInfoBed_1.setText("Info Bed 1");
		
		Group grpBed_2 = new Group(shlBedObserver, SWT.NONE);
		grpBed_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpBed_2.setText("Bed 2");
		grpBed_2.setBounds(181, 10, 149, 63);
		
		Button btnInfoBed_2 = new Button(grpBed_2, SWT.NONE);
		btnInfoBed_2.setText("Info Bed 2");
		btnInfoBed_2.setBounds(49, 23, 90, 30);
		
		Group grpBed_3 = new Group(shlBedObserver, SWT.NONE);
		grpBed_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpBed_3.setText("Bed 3");
		grpBed_3.setBounds(351, 10, 149, 63);
		
		Button btnInfoBed_3 = new Button(grpBed_3, SWT.NONE);
		btnInfoBed_3.setText("Info Bed 3");
		btnInfoBed_3.setBounds(49, 23, 90, 30);
		
		Group grpBed_4 = new Group(shlBedObserver, SWT.NONE);
		grpBed_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpBed_4.setText("Bed 4");
		grpBed_4.setBounds(10, 93, 149, 63);
		
		Button btnInfoBed_4 = new Button(grpBed_4, SWT.NONE);
		btnInfoBed_4.setText("Info Bed 4");
		btnInfoBed_4.setBounds(49, 23, 90, 30);
		
		Group grpBed_5 = new Group(shlBedObserver, SWT.NONE);
		grpBed_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpBed_5.setText("Bed 5");
		grpBed_5.setBounds(181, 93, 149, 63);
		
		Button btnInfoBed_5 = new Button(grpBed_5, SWT.NONE);
		btnInfoBed_5.setText("Info Bed 5");
		btnInfoBed_5.setBounds(49, 23, 90, 30);
		
		Group grpBed_6 = new Group(shlBedObserver, SWT.NONE);
		grpBed_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpBed_6.setText("Bed 6");
		grpBed_6.setBounds(351, 93, 149, 63);
		
		Button btnInfoBed_6 = new Button(grpBed_6, SWT.NONE);
		btnInfoBed_6.setText("Info Bed 6");
		btnInfoBed_6.setBounds(49, 23, 90, 30);
		
		Group grpBed_10 = new Group(shlBedObserver, SWT.NONE);
		grpBed_10.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpBed_10.setText("Bed 10");
		grpBed_10.setBounds(10, 259, 149, 63);
		
		Button btnInfoBed_10 = new Button(grpBed_10, SWT.NONE);
		btnInfoBed_10.setText("Info Bed 10");
		btnInfoBed_10.setBounds(49, 23, 90, 30);
		
		Group grpBed_11 = new Group(shlBedObserver, SWT.NONE);
		grpBed_11.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpBed_11.setText("Bed 11");
		grpBed_11.setBounds(181, 259, 149, 63);
		
		Button btnInfoBed_11 = new Button(grpBed_11, SWT.NONE);
		btnInfoBed_11.setText("Info Bed 11");
		btnInfoBed_11.setBounds(49, 23, 90, 30);
		
		Group grpBed_12 = new Group(shlBedObserver, SWT.NONE);
		grpBed_12.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpBed_12.setText("Bed 12");
		grpBed_12.setBounds(351, 259, 149, 63);
		
		Button btnInfoBed_12 = new Button(grpBed_12, SWT.NONE);
		btnInfoBed_12.setText("Info Bed 12");
		btnInfoBed_12.setBounds(49, 23, 90, 30);
		
		Group grpBed_9 = new Group(shlBedObserver, SWT.NONE);
		grpBed_9.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpBed_9.setText("Bed 9");
		grpBed_9.setBounds(351, 176, 149, 63);
		
		Button btnInfoBed_9 = new Button(grpBed_9, SWT.NONE);
		btnInfoBed_9.setText("Info Bed 9");
		btnInfoBed_9.setBounds(49, 23, 90, 30);
		
		Group grpBed_8 = new Group(shlBedObserver, SWT.NONE);
		grpBed_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpBed_8.setText("Bed 8");
		grpBed_8.setBounds(181, 176, 149, 63);
		
		Button btnInfoBed_8 = new Button(grpBed_8, SWT.NONE);
		btnInfoBed_8.setText("Info Bed 8");
		btnInfoBed_8.setBounds(49, 23, 90, 30);
		
		Group grpBed_7 = new Group(shlBedObserver, SWT.NONE);
		grpBed_7.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		grpBed_7.setText("Bed 7");
		grpBed_7.setBounds(10, 176, 149, 63);
		
		Button btnInfoBed_7 = new Button(grpBed_7, SWT.NONE);
		btnInfoBed_7.setText("Info Bed 7");
		btnInfoBed_7.setBounds(49, 23, 90, 30);

	}
}
