import java.awt.*;
import javax.swing.*;

public class MapPainter extends JComponent
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int mouseX;
	private int mouseY;
	private int lastClickX;
	private int lastClickY;
	private int firstClickX = 0;
	private int firstClickY = 0;
	private int mapSize;
	private int movementSpeed;
	private GameMouseListener mouse;
	private MapLayout[][] map;
	private int mapScaleX = 20;
	private int mapScaleY = 20;
	private int highlightedX = 0;
	private int highlightedY = 0;
	private double scrollX;
	private double scrollY;
	private Rectangle rectangle;
	private ScrollBar scroll;
	private JFrame frame;
	
	public MapPainter(){}
	
	public void updateInformation(MapLayout[][] ma, int x1, int y1, GameMouseListener e, int mo, Rectangle r, ScrollBar s, JFrame f)
	{
		
		mouseX=x1;
		mouseY=y1;
		map = ma;
		mouse = e;
		lastClickX = 0;
		lastClickY = 0;
		mapSize = map[0].length;
		movementSpeed = mo;
		rectangle = r;
		scroll = s;
		frame = f;
	
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		
		if(mouse.getZoomIn())
		{
			mapScaleX+=1;
			mapScaleY+=1;
			mouse.resetZoom();
		}
		if(mouse.getZoomOut())
		{
			mapScaleX-=1;
			mapScaleY-=1;
			mouse.resetZoom();
		}
			
		
		int unusedFloor = mapSize-(mapSize/2)+1;
		int unusedCeiling = (mapSize*2)-(mapSize/2);
		
		double height = mapScaleY * Math.sqrt(3);
		int width = mapScaleX * 2;
		
		int finalMapWidth = (int)((mapSize*width*.75)+(double)width/2)+width*2;
		int finalMapHeight = (int)((((mapSize)*height)+height/2+(((double)mapSize/2)*height/2))-(int)(height*unusedFloor/2)+height*3);
		
		scrollX = 0;
		scrollY = 0;
		if(finalMapWidth > frame.getWidth())
		{
			
			scroll.changeScrollerPosition(mouse, frame);
			scrollX = (scroll.getBottomPosition().x / 
			    	   (frame.getWidth() - scroll.getBottomScroller().getWidth() - 7))
					   *(finalMapWidth - frame.getWidth());
			
			scroll.drawBottom(g2);
			
		}
		else
		{
			scrollX = ((frame.getWidth() - (double)finalMapWidth)/2) * -1;
		}
		
		if(finalMapHeight > frame.getHeight())
		{
			
			scroll.changeScrollerPosition(mouse, frame);
			scrollY = (scroll.getSidePosition().y / 
			    	   (frame.getHeight() - scroll.getSideScroller().getHeight() - 75))
					   *(finalMapHeight - frame.getHeight());
			
			scroll.drawSide(g2);
			
		}		
		else
		{
			scrollY = (((frame.getHeight()-30) - (double)finalMapHeight)/2) * -1;
		}
		
		for(int i = 1; i <= mapSize; i++)
		{
			for(int j = 1; j <= mapSize; j++)
			{
				if((i+j < unusedFloor || i+j > unusedCeiling)||
				   (getNewHexX(i) > rectangle.width - 15 + width)||
				   (getNewHexY(i, j, unusedFloor) > rectangle.height - 15)||
				   (getNewHexX(i) < 0-width)||
				   (getNewHexY(i, j, unusedFloor) < 0-height))
				{
					continue;
				}
					
				Hexagon hex = this.createHex(i, j, unusedFloor);
				
				g2.setColor(map[i-1][j-1].getColor());
				
				if(hex.getHex().contains(mouseX,mouseY))
					g2.setColor(Color.RED);
				
				if(!mouse.getClicked())
				{
					if(hex.getHex().contains(mouseX,mouseY)){
						highlightedX = i;
						highlightedY = j;
					}
				}
				else
				{
					lastClickX = mouseX;
					lastClickY = mouseY;
				}
				
				g2.fill(hex.getHex());
			}
		}
		
		
		scroll.drawZoom(g2);
		if(highlightedX == 0 || highlightedY == 0)
			return;
		
		int startX = highlightedX - (movementSpeed/5);
		int startY = highlightedY - (movementSpeed/5);
		int endX = highlightedX + (movementSpeed/5)+1;
		int endY = highlightedY + (movementSpeed/5)+1;
		int highlightFloor = (highlightedX + highlightedY) - (movementSpeed/5);
		int highlightCeiling = (highlightedX + highlightedY) + (movementSpeed/5);
		
		for(int i = startX; i < endX; i++)
		{
			for(int j = startY; j < endY; j++)
			{
				Hexagon hex = this.createHex(i, j, unusedFloor);
				if((i+j < unusedFloor || i+j > unusedCeiling)||
				   (i<=0 || j<=0)||
				   (i > mapSize || j > mapSize)||
				   (i+j < highlightFloor || i+j > highlightCeiling))
				{
					continue;
				}
				
				Color color = new Color(255,166,166,200);
				g2.setColor(color);
				
				g2.fill(hex.getHex());
			}
		}
		
		if(finalMapWidth > frame.getWidth())
			scroll.drawBottom(g2);
		if(finalMapHeight > frame.getHeight())
			scroll.drawSide(g2);
		scroll.drawZoom(g2);
		
	}
	
	private Hexagon createHex(int i, int j, int unusedFloor)
	{
		double width = (double)mapScaleX * 2;
		double height = mapScaleY * Math.sqrt(3);
		map[i-1][j-1].setCenter(((int)((i*width*.75)+width/2)) - (int)scrollX,
				                 (int)((j*height)+height/2+(i*height/2))-(int)(height*unusedFloor/2) - (int)scrollY);
		return new Hexagon ((int)((i*width*.75)+width/2) - (int)scrollX,
							(int)((j*height)+height/2+(i*height/2))-(int)(height*unusedFloor/2) - (int)scrollY,
							mapScaleX,
							mapScaleY);
	}
	
	private int getNewHexY(int i, int j, int unusedFloor)
	{
		
		double height = mapScaleY * Math.sqrt(3);
		return (int)((j*height)+height/2+(i*height/2))-(int)(height*unusedFloor/2) - (int)scrollY;
		
	}
	
	private int getNewHexX(int i)
	{
		
		double width =(double)mapScaleX * 2;
		return (int)((i*width*.75)+width/2) - (int)scrollX;
		
	}
	
	public Point getFirstClickedPos(){return new Point(firstClickX,firstClickY);}
	
	public Point getLastClickedPos(){return new Point(lastClickX,lastClickY);}
	
	public boolean isClicked(){return mouse.getClicked();}
	
	
}