package ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.*;

import mapGenerator.Coord;
import mapGenerator.Map;
import mapGenerator.MapPosition;

public class UI extends JFrame implements ComponentListener{

	private static final long serialVersionUID = 1L;

	public static final int sleepTime = 50;
	private int width;
	private int height;
	private Map map;
	private Collection<Coord> path;


	public UI(Map map) {
		this.addComponentListener(this);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});      
		this.setSize(400, 400);

		this.setVisible(true);
		this.path = Collections.synchronizedList(new ArrayList<Coord>());
		this.map = map;
	}

	@SuppressWarnings("static-access")
	public void paint(Collection<Coord> path){
		this.path = path;
		try {
			Thread.currentThread().sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.repaint();
	}

	@SuppressWarnings("static-access")
	public void paint(Coord location){
		this.path.add(location);
		try {
			Thread.currentThread().sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {		
		super.paint(g);
		
		width =  getBounds().width;
		height = getBounds().height;
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2D = bufferedImage.createGraphics();

		int offset = 50;
		int totalDrawableSizeY = height - offset * 2;
		int totalDrawableSizeX = width - offset * 2;

		int cellSizeY = totalDrawableSizeX / (map.getHeight()+2);
		int cellSizeX = totalDrawableSizeY / (map.getWidth()+2);



		for (int i = 0; i < map.getHeight()+2; i++){
			for (int j = 0; j < map.getWidth()+2; j++){
				if (  map.getCell(i, j) != MapPosition.EMPTY ){
					drawBlockedCell(g2D, i, j, offset, cellSizeY, cellSizeX, Color.BLACK);
				}
			}
		}

		drawBlockedCell(g2D, map.getFinishCoord().getX(),map.getFinishCoord().getY(), offset, cellSizeY, cellSizeX, Color.RED);

		if ( path != null  ){
			ArrayList<Coord> myWay = new ArrayList<Coord>();
			myWay.addAll(path);
					
			for (Coord el : myWay) {
				drawBlockedCell(g2D, el.getX(), el.getY(), offset, cellSizeY, cellSizeX, Color.GREEN);			
			}  
		}
		Graphics2D g2dComponent = (Graphics2D) g;
	    g2dComponent.drawImage(bufferedImage, null, 0, 0);  
	}

	private void drawBlockedCell(Graphics2D g2D, int i, int j, int offset, int cellSizeX, int cellSizeY, Color color) {
		Shape square = new Rectangle2D.Double(offset + i * cellSizeX, offset + j * cellSizeY, cellSizeX, cellSizeY);

		g2D.setPaint(color);
		g2D.fill(square);
	}

	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e)   {}

	@Override
	public void componentResized(ComponentEvent e) {
		this.repaint();
	}

	@Override
	public void componentShown(ComponentEvent e)  {}

	
	
}

