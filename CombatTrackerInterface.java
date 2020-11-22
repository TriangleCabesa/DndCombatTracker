import java.awt.*;
import javax.swing.*;


/*
 *
 *  This class is in charge
 *  of calling other classes to draw stuff
 *
*/
public class CombatTrackerInterface
{
	
	private GameMouseListener mouse;
	private JFrame display;
	private MapPainter painter;
	private ScrollBar scroll;
	
	public void intializeDisplay () 
	{
		
		display = new JFrame();
		display.setSize(500,500);
		display.setTitle("Map");
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scroll = new ScrollBar();
		mouse = new GameMouseListener(scroll, display);
		painter = new MapPainter();
		display.addMouseListener(mouse);
		display.add(painter);
		display.setVisible(true);
	}
	
	public void displayUpdate(MapLayout[][] map, int x, int y, DndPlayer player)
	{
		Rectangle r = display.getBounds();
		painter.updateInformation(map, x, y, mouse, player.getSpeed(), r, scroll, display);
		display.validate();
		display.repaint();
		
	}
	
	public int getFrameX(){return display.getLocation().x;}
	
	public int getFrameY(){return display.getLocation().y;}
	
}


