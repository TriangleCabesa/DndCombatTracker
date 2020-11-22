import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class GameMouseListener implements MouseListener 
{
	private boolean initiateClick = false;
	private boolean bottomScrollBarClick = false;
	private boolean sideScrollBarClick = false;
	private boolean zoomInClick = false;
	private boolean zoomOutClick = false;
	private ScrollBar scroll;
	private JFrame display;
	
	public GameMouseListener(ScrollBar s, JFrame frame)
	{
		
		scroll = s;
		display = frame;
		
	}
	
	@Override
    public void mouseClicked(MouseEvent arg0) { 
		
		Point p = MouseInfo.getPointerInfo().getLocation();
		Rectangle r = display.getBounds();
		int x = (int)(p.x-r.getX()-7);
		int y = (int)(p.y-r.getY()-30);
		p = new Point(x,y);
		
		if(scroll.getBottomScroller().contains(p) && !bottomScrollBarClick)
			bottomScrollBarClick = true;
		else if(scroll.getBottomScroller().contains(p) && bottomScrollBarClick)
			bottomScrollBarClick = false;
		else if(scroll.getSideScroller().contains(p) && !sideScrollBarClick)
			sideScrollBarClick = true;
		else if(scroll.getSideScroller().contains(p) && sideScrollBarClick)
			sideScrollBarClick = false;
		else if(scroll.getZoomInButton().contains(p) && !zoomInClick)
			zoomInClick = true;
		else if(scroll.getZoomOutButton().contains(p) && !zoomOutClick)
			zoomOutClick = true;
		else if(initiateClick)
			initiateClick = false;
		else if(!initiateClick)
			initiateClick = true;
			
		System.out.println(p.x + " " + p.y);
		
    }
	
	public boolean getClicked(){return initiateClick;}
	public boolean getBottomScrollClicked(){return bottomScrollBarClick;}
	public boolean getSideScrollClicked(){return sideScrollBarClick;}
	public boolean getZoomIn(){return zoomInClick;}
	public boolean getZoomOut(){return zoomOutClick;}
	public void resetZoom()
	{
		zoomInClick = false;
		zoomOutClick = false;
	}
	
	@Override
    public void mouseEntered(MouseEvent arg0) { }

    @Override
    public void mouseExited(MouseEvent arg0) { }

    @Override
    public void mousePressed(MouseEvent arg0) { }

    @Override
    public void mouseReleased(MouseEvent arg0) { }

}