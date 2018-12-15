package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Geom.Geom_element;
import Geom.Point3D;


public class MyFrame extends JFrame implements MouseListener
{
	private BufferedImage arielimg;
	private ArrayList<Geom_element> fruits = new ArrayList<>();
	private ArrayList<Geom_element> pac = new ArrayList<>(); 
	private Point3D point;

	
	public MyFrame() 
	{
		 initGUI();		
		this.addMouseListener(this); 
		this.setVisible(true);
		this.setSize(this.arielimg.getWidth(),this.arielimg.getHeight());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

	}

	private void initGUI() 
	{
		 MenuBar menuBar = new MenuBar();
		 Menu input = new Menu("input");
		 Menu game = new Menu("Game"); 
		MenuItem open = new MenuItem("Open");
		MenuItem save = new MenuItem("Save");
		MenuItem frt = new MenuItem("Fruits");
		MenuItem pac = new MenuItem("Pacmans");


		menuBar.add(game);
		game.add(open);
		game.add(save);
		menuBar.add(input);
		input.add(frt);
		input.add(pac);
		this.setMenuBar(menuBar);
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				  JFileChooser chooser = new JFileChooser();
	                chooser.showOpenDialog(null);				
			}
		});

		try {
			arielimg = ImageIO.read(new File("Ariel1.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("reading image err");
		}		
	}

	public void paint(Graphics g)
	{
		g.drawImage(arielimg, 0, 0, getWidth()-10, getHeight()-10,null);
		if(fruits != null) {
			
		Iterator<Geom_element> fit = fruits.iterator();

		while(fit.hasNext()) {
			Point3D fruit = (Point3D) fit.next();
			int r = 20;
			int x = (int) (fruit.x() * getWidth());
			int y = (int) (fruit.y() * getHeight());
			g.setColor(Color.yellow);
			g.fillOval(x-10 , y-8 , r, r);
		}
	}
}
	@Override
	public void mouseClicked(MouseEvent arg) {
		
		System.out.println("("+ arg.getX() + "," + arg.getY() +")");
		fruits.add(new Point3D((double) arg.getX() / getWidth(),(double) arg.getY() / getHeight()));

		repaint();

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
