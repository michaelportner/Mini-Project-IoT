import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class BedInfoWindow {

	protected Shell shlBedInformation;
	protected BedInfo myBedInfo;

	public BedInfoWindow(BedInfo myBedInfo) {
		this.myBedInfo = myBedInfo;
	}
	
	public BedInfoWindow() {
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BedInfoWindow window = new BedInfoWindow();
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
		shlBedInformation.open();
		shlBedInformation.layout();
		while (!shlBedInformation.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlBedInformation = new Shell();
		shlBedInformation.setSize(460, 300);
		shlBedInformation.setText("Bed information");
		
		Button btnOkButton = new Button(shlBedInformation, SWT.CENTER);
		btnOkButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlBedInformation.close();
			}
		});
		btnOkButton.setBounds(160, 213, 140, 30);
		btnOkButton.setText("OK");

	}

}
