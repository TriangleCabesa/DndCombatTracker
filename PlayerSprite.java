import java.awt.*;
import javax.swing.*;

public class PlayerSprite extends JComponent
{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private DndPlayer player;
	public PlayerSprite(DndPlayer p)
	{
		
		player = p;
		
	}
	
	public void draw(Graphics2D g2, MapLayout[][] map)
	{
		
		Rectangle r = new Rectangle(map[player.getX()-1][player.getY()-1].getDrawnX()-5,
									map[player.getX()-1][player.getY()-1].getDrawnY()-5,
									11,
									11);
		g2.setColor(Color.BLUE);
		g2.fill(r);
		
	}
	
	
	
}