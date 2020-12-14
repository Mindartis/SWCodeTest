//Built in Eclipse 20-09
//Java 15.0.1
//leveraging WindowBuilder 1.9.4 stable
package codeTest;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class HomeFrame {

	protected Shell mainWindow;
	private Text inputTextBox;
	boolean is_Y_Vowel = false;
	boolean systemUpdate = false;
	private Button reverseTextButton;
	private Button paddingButton;
	private String tbox;
	boolean debug = false;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main( String[] args ) {
		try {
			HomeFrame window = new HomeFrame();
			window.open();
		} catch( Exception e ){
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		mainWindow.open();
		mainWindow.layout();
		while( !mainWindow.isDisposed() ) {
			if( !display.readAndDispatch() ){
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		mainWindow = new Shell();
		mainWindow.setSize( 439, 302 );
		mainWindow.setText( "Sherwin-Williams Coding Test" );
		if( debug ) System.out.println( "Window Opening" );

		/**
		 * Adds input verification to the text box, only allowing character inputs
		 * 		and limiting size to 10 total characters
		 */
		inputTextBox = new Text( mainWindow, SWT.BORDER );
		inputTextBox.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseDown( MouseEvent e ){
				systemUpdate = true;
				inputTextBox.setText( "" );
				tbox = "";
				if( debug ) System.out.println("TextBox has been cleared");
				systemUpdate = false;
			}
		});
		/**
		 * Verifies text box input to ensure only valid data is displayed
		 */
		inputTextBox.addVerifyListener( new VerifyListener() {
			@Override
			public void verifyText( VerifyEvent e ) {
				if( Character.isLetter( e.character ) || systemUpdate ) 
				{
					if( debug )
						if( !systemUpdate ) System.out.println( "Input is a character, adding to string" );
						else System.out.println( "Updated textBox with button press" );
					systemUpdate = false;
					e.doit = true;
				}else
				{
					if( debug ) System.out.println( "Input is not a character, will not add to string" );
					systemUpdate = false;
					e.doit = false;
				}
				
			}
		});
		inputTextBox.setToolTipText( "Input up to 10 alphabetic characters, click to clear." );
		inputTextBox.setBounds( 150, 50, 101, 21 );
		inputTextBox.setTextLimit( 10 );
				
		/**
		 * Adds a case change to text in text box, Vowels to upper case, consonants to lower case
		 * Further clicks will not modify text in the text box.
		 */
		Button switchCaseButton = new Button( mainWindow, SWT.NONE );
		switchCaseButton.setToolTipText("Consonants in the text box will be lower case, Vowels will be upper case.");
		switchCaseButton.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseDown( MouseEvent e ) {
				tbox = inputTextBox.getText();
				tbox = tbox.toLowerCase();
				tbox = tbox.replace('a', 'A');
				tbox = tbox.replace('e', 'E');
				tbox = tbox.replace('i', 'I');
				tbox = tbox.replace('o', 'O');
				tbox = tbox.replace('u', 'U');
				if ( is_Y_Vowel )
					tbox = tbox.replace('y', 'Y');
				if( debug ) System.out.println( "Switch Case activated, new box contents: " + tbox );
				systemUpdate = true;
				inputTextBox.setText( tbox );
				systemUpdate = false;
			}
		});
		switchCaseButton.setBounds( 82, 98, 75, 25 );
		switchCaseButton.setText( "Switch Case" );
		
		/**
		 * Changes the orientation of the string inside input text box
		 * multiple key presses will continue to flip the text input.
		 */
		reverseTextButton = new Button( mainWindow, SWT.NONE );
		reverseTextButton.setToolTipText("The letters inside the text box will switch order.");
		reverseTextButton.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseDown( MouseEvent e ) {
				tbox = inputTextBox.getText();
				StringBuilder reverseString = new StringBuilder( tbox );
				reverseString.reverse();
				tbox=reverseString.toString();
				if ( debug ) System.out.println( "Reverse String activated, new box contents: " + tbox );
				systemUpdate = true;
				inputTextBox.setText( tbox );
				systemUpdate = false;
			}
		});
		reverseTextButton.setBounds( 163, 98, 75, 25 );
		reverseTextButton.setText( "Reverse Text" );
		
		/**
		 * Adds padding to the front or rear of the text inside the text box up to a maximum of 10 characters.
		 */
		paddingButton = new Button( mainWindow, SWT.NONE );
		paddingButton.setToolTipText("'A' characters will padd the beginning or end of the text if there is room. ");
		paddingButton.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseDown( MouseEvent e ) {
				tbox=inputTextBox.getText();
				if( tbox.length() == 10 )
				{
					if( debug ) System.out.println( "Padding Button activated, string size is equal to 10" );
					int lastA;
					//check if A is the starter (A padded to the front)
					if( tbox.charAt(0)=='A' ) {
						lastA = -1;
						for( int i=1; i<10; i++ ) {
							if( tbox.charAt(i) == 'A' )
								lastA = i;
							else
								break; 
						}
						if( debug ) System.out.println( "Padding Button activated for Back Padding, text box before update: " + tbox.substring(lastA+1, tbox.length()) );
						tbox = tbox.substring(lastA+1, tbox.length());
						tbox = String.format( "%1$-" + 10 + "s",tbox ).replace( ' ', 'A' );
						if( debug ) System.out.println( "Padding Button activated for Back Padding, text box after update: " + tbox );

						systemUpdate = true;
						inputTextBox.setText( tbox );
						systemUpdate = false;
					
					
					}else if( debug ) System.out.println( "Padding Button activated, Full string without \'A\', unable to pad" );
				}else {
					if( debug ) System.out.println( "Padding Button activated for Front padding, text before update: " + tbox );
					tbox = String.format( "%1$" + 10 + "s",tbox ).replace( ' ', 'A' );
					if( debug ) System.out.println( "Padding Button activated for Front padding, text after update: " + tbox );
					
					systemUpdate = true;
					inputTextBox.setText( tbox );
					systemUpdate = false;
				}
			}
		});
		paddingButton.setBounds( 244, 98, 75, 25 );
		paddingButton.setText( "'A' Padding" );

	}
}
