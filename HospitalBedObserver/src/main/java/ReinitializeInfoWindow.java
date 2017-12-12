import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;

public class ReinitializeInfoWindow extends Dialog {

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ReinitializeInfoWindow(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @param timeToWaitOnReinitializeMs 
	 * @return the result
	 */
	public Object open(long timeToWaitOnReinitializeMs) {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		long startReinitializeTime = System.currentTimeMillis();
		while (!shell.isDisposed()) {
			if ((System.currentTimeMillis()-startReinitializeTime) < timeToWaitOnReinitializeMs){
				shell.dispose();
			}
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.BORDER | SWT.TITLE);
		shell.setSize(450, 300);
		shell.setText(getText());

	}

}
