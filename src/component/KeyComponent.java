package component;

import java.awt.event.KeyEvent;

public interface KeyComponent {
	
	public boolean handleKeyEvent(KeyEvent k, KeyEventType t);
	public void handleKeyPress(KeyEvent k);
	public void handleKeyRelease(KeyEvent k);

}