import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;

public class BedInfoWindow {

	protected Shell shlBedInformation;
	protected BedInfo myBedInfo = new BedInfo();

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
		shlBedInformation.setText(myBedInfo.getName() + " information");
		shlBedInformation.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(shlBedInformation, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		composite.setLayoutData(new RowData(437, 186));
		
		List list = new List(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		
		//Add history elements to the List in format: 2016/11/16 12:08:43	State: RED
		if (myBedInfo != null) {
			java.util.List<HistoryEntry> history = myBedInfo.getHistory();
			Collections.sort(history);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			for (HistoryEntry historyEntry:history){
				list.add(dateFormat.format(historyEntry.getDateTime()) + "     |      State: " + historyEntry.getHistoricalState().toString());
			}
		}
		
		Composite composite_1 = new Composite(shlBedInformation, SWT.NONE);
		composite_1.setLayoutData(new RowData(438, 61));
		
		Button btnOkButton = new Button(composite_1, SWT.CENTER);
		btnOkButton.setLocation(164, 10);
		btnOkButton.setSize(119, 38);
		btnOkButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlBedInformation.close();
			}
		});
		btnOkButton.setText("OK");

	}
}
