import java.awt.*;
import javax.swing.*;

public class ScrollBar extends JComponent
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Rectangle bottomScrollBar;
	private Rectangle sideScrollBar;
	private Rectangle zoomInButton;
	private Rectangle zoomOutButton;
	private GameMouseListener mouse;
	private boolean firstTime = true;
	
	public ScrollBar()
	{
		
		zoomInButton = new Rectangle(50,10,40,40);
		zoomOutButton = new Rectangle(10,10,40,40);
		
		
		
	}
	
	public void changeScrollerPosition(GameMouseListener m, JFrame frame)
	{
		
		Rectangle r = frame.getBounds();
		
		if(firstTime)
		{
			
			firstTime = false;
			mouse = m;
			bottomScrollBar = new Rectangle (0,
										     r.height - 57,
										     (int)(frame.getWidth() * 0.10),
										     14);
										 
			sideScrollBar = new Rectangle (r.width - 31,
										   0,
										   14,
										   (int)(frame.getHeight() * 0.10));
			
		}
		
		Point p = MouseInfo.getPointerInfo().getLocation();
		int x = p.x - (int)(frame.getWidth() * 0.05);
		int y = p.y - (int)(frame.getHeight() * 0.1);
		
		if(x<0)
			x=0;
		if(x>frame.getWidth() - (int)bottomScrollBar.getWidth())
			x = frame.getWidth() - (int)bottomScrollBar.getWidth();
			
		if(y<0)
			y=0;
		if(y>frame.getHeight() - (int)sideScrollBar.getHeight())
			y = frame.getHeight() - (int)sideScrollBar.getHeight();
		
		if(mouse.getBottomScrollClicked())
			bottomScrollBar.setLocation(x,(r.height - 57));
		if(mouse.getSideScrollClicked())
			sideScrollBar.setLocation((r.width - 31),y);
		
		bottomScrollBar = new Rectangle ((int)bottomScrollBar.getX(),
										 r.height - 57,
										 (int)(frame.getWidth() * 0.10),
										 14);
										 
		sideScrollBar = new Rectangle (r.width - 31,
									   (int)sideScrollBar.getY(),
									   14,
									   (int)(frame.getHeight() * 0.10));
		
		if(bottomScrollBar.getX() > frame.getWidth() - (int)bottomScrollBar.getWidth() - 7)
			bottomScrollBar = new Rectangle (frame.getWidth() - (int)bottomScrollBar.getWidth() - 7,
											r.height - 57,
											(int)(frame.getWidth() * 0.10),
											14);
											
		if(sideScrollBar.getY() > frame.getHeight() - (int)sideScrollBar.getHeight() - 75)
			sideScrollBar = new Rectangle (r.width - 31,
											frame.getHeight() - (int)sideScrollBar.getHeight() - 75,
											14,
											(int)(frame.getHeight() * 0.10));
		
	}
	
	public Rectangle getBottomScroller(){return bottomScrollBar;}
	
	public Point getBottomPosition(){return bottomScrollBar.getLocation();}
	
	public Rectangle getSideScroller(){return sideScrollBar;}
	
	public Point getSidePosition(){return sideScrollBar.getLocation();}
	
	public Rectangle getZoomInButton(){return zoomInButton;}
	
	public Point getZoomInLocation(){return zoomInButton.getLocation();}
	
	public Rectangle getZoomOutButton(){return zoomOutButton;}
	
	public Point getZoomOutLocation(){return zoomOutButton.getLocation();}
	
	public void drawBottom(Graphics2D g2) 
	{
		
		g2.setColor(new Color(150,150,150));
		g2.fill(bottomScrollBar);
		
	}
	
	public void drawSide(Graphics2D g2) 
	{
		
		g2.setColor(new Color(150,150,150));
		g2.fill(sideScrollBar);
		
	}
	
	public void drawZoom(Graphics2D g2)
	{
		
		g2.setColor(new Color(200,200,200));
		g2.fill(zoomInButton);
		g2.fill(zoomOutButton);
		g2.setColor(new Color(150,150,150));
		Rectangle r = new Rectangle(14,24,32,12);
		g2.fill(r);
		r = new Rectangle(54,24,32,12);
		g2.fill(r);
		r = new Rectangle(64,14,12,32);
		g2.fill(r);
		
	}
	
}