package ui;

import java.awt.event.MouseEvent;

public interface Clickable {
	
	public void handleMousePress(MouseEvent e);
	public void handleMouseRelease(MouseEvent e);
	public void handleMouseDrag(MouseEvent e);
	public void handleMouseEvent(MouseEvent e);
	
}