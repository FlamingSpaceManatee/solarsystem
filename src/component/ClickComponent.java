package component;

import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public interface ClickComponent {
	
	public void handleMousePress(MouseEvent e);
	public void handleMouseRelease(MouseEvent e);
	public void handleMouseDrag(MouseEvent e);
	public boolean handleMouseEvent(MouseEvent e, MouseEventType t);

	public void setReleasedEvent(Consumer<Object> c);
	public void setPressedEvent(Consumer<Object> c);

}