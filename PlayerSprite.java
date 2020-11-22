import java.awt.*;
import javax.swing.*;

public class PlayerSprite extends JComponent
{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int playerX;
	private int playerY;
	private DndPlayer player;
	public PlayerSprite(DndPlayer p)
	{
		
		player = p;
		playerX = player.getX();
		playerY = player.getY();
		
	}
	
	public void draw(Graphics2D g2, MapLayout[][] map)
	{
		
		Rectangle r = new Rectangle(map[playerX-1][playerY-1].getDrawnX()-5,map[playerX-1][playerY-1].getDrawnY()-5,10,10);
		g2.setColor(Color.BLUE);
		g2.fill(r);
		
	}
	
	
	
}