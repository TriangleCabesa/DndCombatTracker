import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import javax.swing.*;

public class VisualInputs extends JComponent
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Rectangle bottomScrollBar;
	private Rectangle sideScrollBar;
	private Rectangle zoomInButton;
	private Rectangle zoomOutButton;
	private Rectangle menuOptionsButton;
	private GameMouseListener mouse;
	private boolean firstTime = true;
	private int modifier;
	private JFrame frame;

	private int downShift;
	private int leftShift;

	private Font fontLabel;
	private AffineTransform affinetransform;
	private FontRenderContext frc;
	private String returnToGameLabel;
	private int textWidth;
	private int textHeight;
	private int desiredFontSizeX;
	private int desiredFontSizeY;
	private FontMetrics metrics;

	private Rectangle backToGameButton;
	private Rectangle addPlayerButton;
	private Rectangle choosePlayerSkinButton;
	private Rectangle toggleMapEditButton;
	private Rectangle toggleTurnsButton;
	private Rectangle connectToHostButton;
	private Rectangle hostASessionButton;
	
	public VisualInputs()
	{
		
		modifier = 3;
		zoomInButton = new Rectangle((40*modifier)+10,10,40,40);
		zoomOutButton = new Rectangle((40*(modifier-1))+10,10,40,40);
		menuOptionsButton = new Rectangle((40*(modifier-3))+10,10,40,40);
		
		
		
	}
	
	public void changeScrollerPosition(GameMouseListener m, JFrame display)
	{
		
		frame = display;
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
		if(mouse.getWheelScroll())
			sideScrollBar.setLocation(r.width - 31, (int)sideScrollBar.getLocation().getY() + (mouse.getWheelScrollAmount())*(int)(frame.getWidth() * 0.01));
		mouse.resetScroll();
		
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
	
	
	public void changeScrollerPosition(JFrame display, int scrollAmount)
	{
		frame = display;
		Rectangle r = frame.getBounds();

		//Change the location of the side scroll bar based on mouse wheel movement
		sideScrollBar.setLocation((r.width - 31), (int)sideScrollBar.getLocation().getY()+scrollAmount);
	}
	
	public Rectangle getBottomScroller(){return bottomScrollBar;}
	
	public Point getBottomPosition(){return bottomScrollBar.getLocation();}
	
	public Rectangle getSideScroller(){return sideScrollBar;}
	
	public Point getSidePosition(){return sideScrollBar.getLocation();}
	
	public Rectangle getZoomInButton(){return zoomInButton;}
	
	public Point getZoomInLocation(){return zoomInButton.getLocation();}
	
	public Rectangle getZoomOutButton(){return zoomOutButton;}
	
	public Point getZoomOutLocation(){return zoomOutButton.getLocation();}

	public Rectangle getMenuButton(){return menuOptionsButton;}

	public Point getMenuLocation(){return menuOptionsButton.getLocation();}
	
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
		Rectangle r = new Rectangle((40*(modifier-1))+14,24,32,12);
		g2.fill(r);
		r = new Rectangle((40*modifier)+14,24,32,12);
		g2.fill(r);
		r = new Rectangle((40*modifier)+24,14,12,32);
		g2.fill(r);
		
	}

	public void drawMenu(Graphics2D g2)
	{
		
		g2.setColor(new Color(200,200,200));
		g2.fill(menuOptionsButton);
		g2.setColor(new Color(150,150,150));
		Rectangle r = new Rectangle((40*(modifier-3))+18,17,24,6);
		g2.fill(r);
		r = new Rectangle((40*(modifier-3))+18,27,24,6);
		g2.fill(r);
		r = new Rectangle((40*(modifier-3))+18,37,24,6);
		g2.fill(r);
		if(mouse.getMenuClicked())
		{
			r = frame.getBounds();
			g2.setColor(new Color(100,100,100,150));
			g2.fill(r);
			fontLabel = new Font("Arial Black", Font.PLAIN, 12);
			affinetransform = new AffineTransform();
			frc = new FontRenderContext(affinetransform,true,true);
			returnToGameLabel = "GO BACK TO THE GAME";
			textWidth = (int)(fontLabel.getStringBounds(returnToGameLabel, frc).getWidth());
			textHeight = (int)(fontLabel.getStringBounds(returnToGameLabel, frc).getHeight());
			desiredFontSizeX = (int)(frame.getWidth()*.8);
			desiredFontSizeY = (int)(frame.getHeight()*.1);

			

			int frameWidth = (int)(r.getWidth());
			int frameHeight = (int)(r.getHeight());
			int xMargin1 = (int)(frameWidth*.1);
			int xMargin2 = (int)(frameWidth*.55);
			int buttonSizeX = (int)(frameWidth*.35);
			g2.setColor(new Color(200,200,200));

			backToGameButton = new Rectangle(xMargin1,
								  (int)(frameHeight*.1),
								  (int)(frameWidth*.8),
								  (int)(frameHeight*.1));

			addPlayerButton = new Rectangle(xMargin1,
								  (int)(frameHeight*.3),
								  buttonSizeX,
								  (int)(frameHeight*.1));

			choosePlayerSkinButton = new Rectangle(xMargin1,
								  (int)(frameHeight*.5),
								  buttonSizeX,
								  (int)(frameHeight*.1));
								  
			connectToHostButton = new Rectangle(xMargin1,
								  (int)(frameHeight*.7),
								  buttonSizeX,
								  (int)(frameHeight*.1));
								  
			toggleMapEditButton = new Rectangle(xMargin2,
								  (int)(frameHeight*.3),
								  buttonSizeX,
								  (int)(frameHeight*.1));

			toggleTurnsButton = new Rectangle(xMargin2,
								  (int)(frameHeight*.5),
								  buttonSizeX,
								  (int)(frameHeight*.1));

			hostASessionButton = new Rectangle(xMargin2,
								  (int)(frameHeight*.7),
								  buttonSizeX,
								  (int)(frameHeight*.1));

			g2.fill(backToGameButton);
			g2.fill(addPlayerButton);
			g2.fill(choosePlayerSkinButton);
			g2.fill(connectToHostButton);
			g2.fill(toggleMapEditButton);
			g2.fill(toggleTurnsButton);
			g2.fill(hostASessionButton);

			desiredFontSizeX = (int)(frame.getWidth()*.8);
			g2.setFont(getRequiredFontSize(fontLabel,returnToGameLabel));
			g2.setColor(Color.BLACK);
			g2.drawString(returnToGameLabel, (int)backToGameButton.getCenterX() - leftShift,
											 (int)backToGameButton.getCenterY() + downShift);

			desiredFontSizeX = buttonSizeX;
			desiredFontSizeY = (int)(frame.getHeight()*.1);
			String addPlayerLabel = "ADD A NEW PLAYER";
			fontLabel = new Font("Arial Black", Font.PLAIN, 12);
			g2.setFont(getRequiredFontSize(fontLabel, addPlayerLabel));
			g2.drawString(addPlayerLabel, (int)addPlayerButton.getCenterX() - leftShift,
										  (int)addPlayerButton.getCenterY() + downShift);
			
			String choosePlayerSkinLabel = "CHOOSE PLAYER SKIN";
			fontLabel = new Font("Arial Black", Font.PLAIN, 12);
			g2.setFont(getRequiredFontSize(fontLabel, choosePlayerSkinLabel));
			g2.drawString(choosePlayerSkinLabel, (int)choosePlayerSkinButton.getCenterX() - leftShift,
										  (int)choosePlayerSkinButton.getCenterY() + downShift);
										  
			String connectToHostLabel = "CONNECT TO HOST";
			fontLabel = new Font("Arial Black", Font.PLAIN, 12);
			g2.setFont(getRequiredFontSize(fontLabel, connectToHostLabel));
			g2.drawString(connectToHostLabel, (int)connectToHostButton.getCenterX() - leftShift,
											  (int)connectToHostButton.getCenterY() + downShift);
											  
			String toggleMapEditLabel = "EDIT MAP";
			fontLabel = new Font("Arial Black", Font.PLAIN, 12);
			g2.setFont(getRequiredFontSize(fontLabel, toggleMapEditLabel));
			g2.drawString(toggleMapEditLabel, (int)toggleMapEditButton.getCenterX() - leftShift,
											  (int)toggleMapEditButton.getCenterY() + downShift);

			String toggleTurnsLabel = "TURNS ON/OFF";
			fontLabel = new Font("Arial Black", Font.PLAIN, 12);
			g2.setFont(getRequiredFontSize(fontLabel, toggleTurnsLabel));
			g2.drawString(toggleTurnsLabel, (int)toggleTurnsButton.getCenterX() - leftShift,
											  (int)toggleTurnsButton.getCenterY() + downShift);

			String hostASessionLabel = "HOST GAME";
			fontLabel = new Font("Arial Black", Font.PLAIN, 12);
			g2.setFont(getRequiredFontSize(fontLabel, hostASessionLabel));
			g2.drawString(hostASessionLabel, (int)hostASessionButton.getCenterX() - leftShift,
											  (int)hostASessionButton.getCenterY() + downShift);
			
		}

		

	}

	private Font getRequiredFontSize(Font f, String boxLabel)
	{
		metrics = getFontMetrics(f);
		textWidth = (int)(f.getStringBounds(boxLabel, frc).getWidth());
		textHeight = (int)(f.getStringBounds(boxLabel, frc).getHeight());
		for(int i = 12; textWidth < (int)(desiredFontSizeX*.75); i++)
		{
			f = new Font("Arial Black", Font.PLAIN, i);
			textWidth = (int)(f.getStringBounds(boxLabel, frc).getWidth());
			textHeight = (int)(f.getStringBounds(boxLabel, frc).getHeight());
		}

		for(int i = f.getSize(); textHeight > (int)(desiredFontSizeY*.75); i--)
		{
			f = new Font("Arial Black", Font.PLAIN, i);
			textWidth = (int)(f.getStringBounds(boxLabel, frc).getWidth());
			textHeight = (int)(f.getStringBounds(boxLabel, frc).getHeight());
		}
		metrics = getFontMetrics(f);
		downShift = metrics.getAscent() - metrics.getDescent() - metrics.getLeading() - (desiredFontSizeY/4); 
		leftShift = textWidth/2;
		return f;
	}
	
}