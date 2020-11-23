import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class MapPainter extends JComponent
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int mouseX;
	private int mouseY;
	private int hoverX = 0;
	private int hoverY = 0;
	private int firstClickX = 0;
	private int firstClickY = 0;
	private int mapSize;
	private GameMouseListener mouse;
	private MapLayout[][] map;
	private int mapScaleX = 20;
	private int mapScaleY = 20;
	private int highlightedX = 0;
	private int highlightedY = 0;
	private double scrollX;
	private double scrollY;
	private Rectangle rectangle;
	private VisualInputs visualInputs;
	private JFrame frame;
	private ArrayList<DndPlayer> players = new ArrayList<>();
	private int index = -1;
	
	public MapPainter(){}
	
	public void updateInformation(MapLayout[][] ma, int x1, int y1, GameMouseListener e, ArrayList<DndPlayer> playerList, Rectangle r, VisualInputs s, JFrame f)
	{
		
		mouseX=x1;
		mouseY=y1;
		map = ma;
		mouse = e;
		mapSize = map[0].length;
		players = playerList;
		rectangle = r;
		visualInputs = s;
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
			
			visualInputs.changeScrollerPosition(mouse, frame);
			scrollX = (visualInputs.getBottomPosition().x / 
			    	   (frame.getWidth() - visualInputs.getBottomScroller().getWidth() - 7))
					   *(finalMapWidth - frame.getWidth());
			
			visualInputs.drawBottom(g2);
			
		}
		else
		{
			scrollX = ((frame.getWidth() - (double)finalMapWidth)/2) * -1;
		}
		
		if(finalMapHeight > frame.getHeight())
		{
			
			visualInputs.changeScrollerPosition(mouse, frame);
			scrollY = (visualInputs.getSidePosition().y / 
			    	   (frame.getHeight() - visualInputs.getSideScroller().getHeight() - 75))
					   *(finalMapHeight - frame.getHeight());
			
			visualInputs.drawSide(g2);
			
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
				
				if(!mouse.getClicked() && hex.getHex().contains(mouseX,mouseY))
				{
					highlightedX = i;
					highlightedY = j;
				}
				else if(mouse.getClicked() && hex.getHex().contains(mouseX,mouseY))
				{
					for(int k = 0; k < players.size(); k++)
						if((players.get(k).getX() == i && players.get(k).getY() == j))
							index = k;
				}
				
				g2.fill(hex.getHex());
			}
		}
		
		
		visualInputs.drawZoom(g2);
		
		for (DndPlayer player: players)
		{
			
			PlayerSprite sprite = new PlayerSprite(player);
			if(player.getX()==highlightedX && player.getY()==highlightedY)
				this.drawPlayerDistanceOutline(unusedFloor,unusedCeiling,player,g2);
			sprite.draw(g2,map);
			
		}
		if(!mouse.getClicked() && index >= 0)
		{
			players.get(index).setX(hoverX);
			players.get(index).setY(hoverY);
			index = -1;
		}

		
		if(finalMapWidth > frame.getWidth())
			visualInputs.drawBottom(g2);
		if(finalMapHeight > frame.getHeight())
			visualInputs.drawSide(g2);
		visualInputs.drawZoom(g2);
		
	}

	private void drawPlayerDistanceOutline(int unusedFloor, int unusedCeiling, DndPlayer player, Graphics2D g2)
	{
		int movementSpeed = player.getSpeed();
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
				if((i+j < unusedFloor || i+j > unusedCeiling)||
				   (i<=0 || j<=0)||
				   (i > mapSize || j > mapSize)||
				   (i+j < highlightFloor || i+j > highlightCeiling))
				{
					continue;
				}
				Hexagon hex = this.createHex(i, j, unusedFloor);
				
				Color color = new Color(255,166,166,200);
				g2.setColor(color);
				if(hex.getHex().contains(mouseX,mouseY))
				{
					g2.setColor(Color.RED);
					hoverX = i;
					hoverY = j;
				}
				
				g2.fill(hex.getHex());
			}
		}

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
	
	public Point getLastClickedPos(){return new Point(hoverX,hoverY);}
	
	public boolean isClicked(){return mouse.getClicked();}
	
	
}