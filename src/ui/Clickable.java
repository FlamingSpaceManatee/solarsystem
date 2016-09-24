package ui;

import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import main.MouseEventType;

public interface Clickable {
	
	public void handleMousePress(MouseEvent e);
	public void handleMouseRelease(MouseEvent e);
	public void handleMouseDrag(MouseEvent e);
	public void handleMouseEvent(MouseEvent e, MouseEventType t);

	public void setOnClick(Consumer<Object> c);

}