import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;

public class ReinitializeInfoWindow extends Dialog {

	protected Object result;
	protected Shell shell;
	private Label lblProgressText;
	private String progressString;
	private long timeToWaitOnReinitializeMs;


	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 * @param timeToWaitOnReinitializeMs 
	 * @param timeToWaitOnReinitializeMs 
	 */
	public ReinitializeInfoWindow(Shell parent, int style, long timeToWaitOnReinitializeMs) {
		super(parent, style);
		setText("SWT Dialog");
		this.timeToWaitOnReinitializeMs = timeToWaitOnReinitializeMs;
		progressString = String.format("Time remained: %d/%ds", 0, timeToWaitOnReinitializeMs/1000);
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		return result;
	}

	/**
	 * Close Info Window
	 */
	public void close() {
		shell.dispose();
	}
	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.BORDER | SWT.TITLE);
		shell.setSize(450, 300);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group group = new Group(shell, SWT.NONE);
		group.setLayout(null);
		
		Label lblReinitialisationIsIn = new Label(group, SWT.NONE);
		lblReinitialisationIsIn.setBounds(84, 70, 268, 33);
		lblReinitialisationIsIn.setText("Reinitialisation is in progress...");
		
		lblProgressText = new Label(group, SWT.NONE);
		lblProgressText.setBounds(84, 112, 268, 33);

	}
	
	public void setLblProgressText(long passedMs) {
		progressString = String.format("Time remained: %d/%ds", passedMs/1000, this.timeToWaitOnReinitializeMs/1000);
		this.lblProgressText.setText(progressString); 
	}
}
