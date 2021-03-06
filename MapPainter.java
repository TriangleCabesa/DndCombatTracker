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
				else if(map[i-1][j-1].getBlocked())
					for(DndPlayer player: players)
					{
						if(player.getX() == i && player.getY() == j)
						{
							g2.setColor(map[i-1][j-1].getColor());
							break;
						}
						g2.setColor(Color.GRAY);
					}
				 
				if(!mouse.getClicked() && hex.getHex().contains(mouseX,mouseY))
				{
					highlightedX = i;
					highlightedY = j;
				
				}
				else if(mouse.getClicked() && hex.getHex().contains(mouseX,mouseY) && index < 0)
				{
					for(int k = 0; k < players.size(); k++)
						if((players.get(k).getX() == i && players.get(k).getY() == j))
							index = k;
				}
				
				g2.fill(hex.getHex());
			}
		}
		
		
		visualInputs.drawZoom(g2);
		
		if(!mouse.getClicked() && index >= 0)
		{
			map[players.get(index).getX()-1][players.get(index).getY()-1].setBlocked(false);
			players.get(index).setX(hoverX);
			players.get(index).setY(hoverY);
			map[players.get(index).getX()-1][players.get(index).getY()-1].setBlocked(true);
			index = -1;
		}
		for (DndPlayer player: players)
			if(player.getX()==highlightedX && player.getY()==highlightedY)
				this.drawPlayerDistanceOutline(unusedFloor,unusedCeiling,player,g2);
		
		for(DndPlayer player: players)
		{
			map[player.getX()-1][player.getY()-1].setBlocked(true);
			if((getNewHexX(player.getX()) > rectangle.width - 15 + width)||
			   (getNewHexY(player.getX(), player.getY(), unusedFloor) > rectangle.height - 15)||
			   (getNewHexX(player.getX()) < 0-width)||
			   (getNewHexY(player.getX(), player.getY(), unusedFloor) < 0-height))
			   	continue;
			PlayerSprite sprite = new PlayerSprite(player);
			sprite.draw(g2,map,mapScaleX,mapScaleY);
		}
		

		
		if(finalMapWidth > frame.getWidth())
			visualInputs.drawBottom(g2);
		if(finalMapHeight > frame.getHeight())
			visualInputs.drawSide(g2);
		visualInputs.drawZoom(g2);
		visualInputs.drawMenu(g2);
		
	}

	private void drawPlayerDistanceOutline(int unusedFloor, int unusedCeiling, DndPlayer player, Graphics2D g2)
	{
		int movementSpeed = player.getSpeed()+5;
		int startX = highlightedX - (movementSpeed/5);
		int startY = highlightedY - (movementSpeed/5);
		int endX = highlightedX + (movementSpeed/5)+1;
		int endY = highlightedY + (movementSpeed/5)+1;
		int highlightFloor = (highlightedX + highlightedY) - (movementSpeed/5);
		int highlightCeiling = (highlightedX + highlightedY) + (movementSpeed/5);

		ArrayList<MapLayout> canReachList = new ArrayList<>();
		canReachList.add(map[highlightedX-1][highlightedY-1]);
		
		for(int k = 1; k < movementSpeed/5; k++)
		{
			ArrayList<MapLayout> itemsToAdd = new ArrayList<>();
			for(MapLayout hex: canReachList)
			{
				int x = hex.getX();
				int y = hex.getY();
				if(x<1)
					x=1;
				if(y<1)
					y=1;
				if(x>=mapSize-1)
					x=mapSize-2;
				if(y>=mapSize-1)
					y=mapSize-2;

				if(!canReachList.contains(map[x-1][y]) && 
				   !itemsToAdd.contains(map[x-1][y]) && 
				   !map[x-1][y].getBlocked())
					itemsToAdd.add(map[x-1][y]);

				if(!canReachList.contains(map[x-1][y+1])&&
				   !itemsToAdd.contains(map[x-1][y+1])&&
				   !map[x-1][y+1].getBlocked())
					itemsToAdd.add(map[x-1][y+1]);

				if(!canReachList.contains(map[x][y-1])&&
				   !itemsToAdd.contains(map[x][y-1])&&
				   !map[x][y-1].getBlocked())
					itemsToAdd.add(map[x][y-1]);
				
				if(!canReachList.contains(map[x][y+1])&&
				   !itemsToAdd.contains(map[x][y+1])&&
				   !map[x][y+1].getBlocked())
					itemsToAdd.add(map[x][y+1]);
				
				if(!canReachList.contains(map[x+1][y-1])&&
				   !itemsToAdd.contains(map[x+1][y-1])&&
				   !map[x+1][y-1].getBlocked())
					itemsToAdd.add(map[x+1][y-1]);
				
				if(!canReachList.contains(map[x+1][y])&&
				   !itemsToAdd.contains(map[x+1][y])&&
				   !map[x+1][y].getBlocked())
					itemsToAdd.add(map[x+1][y]);
			}
			for(MapLayout hex: itemsToAdd)
				canReachList.add(hex);
		}
		
		for(int i = startX; i < endX; i++)
		{
			for(int j = startY; j < endY; j++)
			{
				if(i<=0||j<=0||
				(i > mapSize || j > mapSize)||
				!canReachList.contains(map[i-1][j-1])||
				  (i+j < unusedFloor || i+j > unusedCeiling)||
				  (i<=0 || j<=0)||
				  (i+j < highlightFloor || i+j > highlightCeiling))
					continue;

				Hexagon hex = this.createHex(i, j, unusedFloor);
				Color color = new Color(255,166,166,200);
				g2.setColor(color);
				if(hex.getHex().contains(mouseX,mouseY)){
					g2.setColor(Color.RED);
					if(!map[i-1][j-1].getBlocked())
					{
						hoverX = i;
						hoverY = j;
					}
					else
					{
						hoverX = player.getX();
						hoverY = player.getY();
					}
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