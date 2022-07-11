import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class GameMouseListener implements MouseListener, MouseWheelListener 
{
	private boolean initiateClick = false;
	private boolean bottomScrollBarClick = false;
	private boolean sideScrollBarClick = false;
	private boolean zoomInClick = false;
	private boolean zoomOutClick = false;
	private boolean menuClick = false;
	private boolean wheelScroll = false;
	private int wheelSCrollAmount = 0;
	private VisualInputs visualInputs;
	private JFrame display;
	
	public GameMouseListener(VisualInputs s, JFrame frame)
	{
		
		visualInputs = s;
		display = frame;
		
	}
	
	@Override
    public void mouseClicked(MouseEvent arg0) { 
		
		Point p = MouseInfo.getPointerInfo().getLocation();
		Rectangle r = display.getBounds();
		int x = (int)(p.x-r.getX()-7);
		int y = (int)(p.y-r.getY()-30);
		p = new Point(x,y);
		

		if(visualInputs.getMenuButton().contains(p) && !menuClick)
		{
			menuClick = true;
		}
		else if(visualInputs.getMenuButton().contains(p) && menuClick)
		{
			menuClick = false;
			return;
		}

		if(menuClick)
			return;
		
		if(visualInputs.getBottomScroller().contains(p) && !bottomScrollBarClick)
			return;
		else if(visualInputs.getBottomScroller().contains(p) && bottomScrollBarClick)
			return;
		else if(visualInputs.getSideScroller().contains(p) && !sideScrollBarClick)
			return;
		else if(visualInputs.getSideScroller().contains(p) && sideScrollBarClick)
			return;
		else if(visualInputs.getZoomInButton().contains(p) && !zoomInClick)
			zoomInClick = true;
		else if(visualInputs.getZoomOutButton().contains(p) && !zoomOutClick)
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
	public boolean getMenuClicked(){return menuClick;}
	public boolean getWheelScroll(){return wheelScroll;}
	public int getWheelScrollAmount(){return wheelSCrollAmount;}
	public void resetZoom()
	{
		zoomInClick = false;
		zoomOutClick = false;
	}
	public void resetScroll()
	{
		wheelSCrollAmount = 0;
		wheelScroll = false;
	}
	
	@Override
    public void mouseEntered(MouseEvent arg0) { }

    @Override
    public void mouseExited(MouseEvent arg0) { }

    @Override
	public void mousePressed(MouseEvent arg0) 
	{
		Point p = MouseInfo.getPointerInfo().getLocation();
		Rectangle r = display.getBounds();
		int x = (int)(p.x-r.getX()-7);
		int y = (int)(p.y-r.getY()-30);
		p = new Point(x,y);

		if(visualInputs.getBottomScroller().contains(p) && !bottomScrollBarClick)
			bottomScrollBarClick = true;
		else if(visualInputs.getSideScroller().contains(p) && !sideScrollBarClick)
			sideScrollBarClick = true;


	}

    @Override
	public void mouseReleased(MouseEvent arg0) 
	{ 
		bottomScrollBarClick = false;
		sideScrollBarClick = false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) 
	{
		wheelScroll = true;
		wheelSCrollAmount = arg0.getWheelRotation();
	}

}