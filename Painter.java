/* Dr Mazen: replace the all the non-useless code of setting the color and solidness, fill, etc in every shape
 just at the start of the code and depending on the color the user choose create the object, also replace the ifs with
 case to look better and remove dispose*/
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

abstract class Shape{
	private int[] points = new int[4];
	private char color;
	private boolean solid;
	private boolean filled;
	
	
	public void setStart(int p1, int p2){
		points[0] = p1;
		points[1] = p2;
	}
	
	public void setEnd(int p3, int p4){
		points[2] = p3;
		points[3] = p4;
	}
	
	public void setAttributes(char col, boolean sol){
		color = col;
		solid = sol;
	}
	
	public void setAttributes(char col, boolean sol, boolean fill){
		color = col;
		solid = sol;
		filled = fill;
	}
	
	public int[] getPoints(){
		return points;
	}
	
	public char getColor(){
		return color;
	}
	
	public boolean getSolidState(){
		return solid;
	}
	
	public boolean getFilledState(){
		return filled;
	}
	
	public abstract void draw(Graphics g);
}




public class Painter extends Applet{

	private Button pencilButton, lineButton, ovalButton, rectButton; 
	private Checkbox fillBox, dottedBox;
	private Button redButton, greenButton, blueButton;
	private Button eraseButton, clearAllButton;
	private Button load;
	private char currColor, currShape;
	private boolean fillChk = false, solidChk = true, eraseChk = false, clearAll = false;
	private boolean dragged = false, eraseHere = false, doddled = false;
	private boolean loaded = false;
	private int currX, currY;
	private int minX, minY, endX, endY;
	private Image img;
	
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	Shape pencil = new Line();
	Shape line = new Line();
	Shape rect = new Rectangle();
	Shape rectEraser = new Rectangle();
	Shape oval = new Oval();
	
	public void init(){
		
		pencilButton = new Button(" Pencil ");
		lineButton = new Button(" Line ");
		ovalButton = new Button(" Oval ");
		rectButton = new Button(" Rect ");
		fillBox = new Checkbox(" Fill ");
		dottedBox = new Checkbox(" Dotted ");
		redButton = new Button(" RED ");
		greenButton = new Button(" GREEN ");
		blueButton = new Button(" BLUE ");
		eraseButton = new Button(" Eraser ");
		clearAllButton = new Button(" Clear All ");
		load = new Button(" Load ");
		
		
		pencilButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currShape = 'P';
				eraseChk = false;}
			});
		
		lineButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currShape = 'L';
				eraseChk = false;}
			});
				
		ovalButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currShape = 'O';
				eraseChk = false;}
			});
				
		rectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currShape = 'R';
				eraseChk = false;}
			});
		
		fillBox.addItemListener(new ItemListener() {    
            public void itemStateChanged(ItemEvent e) {                 
                fillChk = e.getStateChange()==1? true : false;
				eraseChk = false;}
			}); 
		
		dottedBox.addItemListener(new ItemListener() {    
            public void itemStateChanged(ItemEvent e) {
				solidChk = e.getStateChange()==1? false : true;}    
			});
		
		redButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currColor = 'R';}
			});
		
		greenButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currColor = 'G';}
			});
		
		blueButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currColor = 'B';}
			});
		
		eraseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				eraseChk = true;
				currShape = 'n';}
			});
				
		clearAllButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clearAll = true;
				eraseChk = false;
				repaint();}
			});
		
		load.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				loaded = true;
				img = getImage(getDocumentBase(),"pain.jpg");
				repaint();}
			});

		
		add(pencilButton);
		add(lineButton);
		add(ovalButton);
		add(rectButton);
		add(dottedBox);
		add(fillBox);
		add(redButton);
		add(greenButton);
		add(blueButton);
		add(eraseButton);
		add(clearAllButton);
		add(load);
		
		this.addMouseListener(new MyMouseAdapter());	
		this.addMouseMotionListener(new MyMouseAdapter());
	}
	
	
	class Line extends Shape{
		
		public Line(){
			
		}
		
		public Line(Shape line){
			int[] pts = line.getPoints();
			char col = line.getColor();
			boolean sol = line.getSolidState();
			setStart(pts[0], pts[1]);
			setEnd(pts[2], pts[3]);
			setAttributes(col, sol);
		}
		
		
		public void draw(Graphics g){
			
			int[] pts = getPoints();
			char col = getColor();
			boolean sol = getSolidState();
			Graphics2D g2d = (Graphics2D) g.create();
			
			switch (col){
				case 'R':
					g2d.setColor(Color.RED);
					break;
				case 'G':
					g2d.setColor(Color.GREEN);
					break;
				case 'B':
					g2d.setColor(Color.BLUE);
					break;
			}
			
			if (sol == false){
				Stroke dashed = new BasicStroke(3, BasicStroke.CAP_ROUND, 
									BasicStroke.JOIN_ROUND, 1, new float[]{0, 6}, 0);
				g2d.setStroke(dashed);
			}
				
			g2d.drawLine(pts[0],pts[1],pts[2],pts[3]);
			g2d.dispose();
		}
	}
	
	
	class Rectangle extends Shape{
		
		public Rectangle(){
			
		}
		
		public Rectangle(Shape rect){
			int[] pts = rect.getPoints();
			char col = rect.getColor();
			boolean sol = rect.getSolidState();
			boolean fill = rect.getFilledState();
			setStart(pts[0], pts[1]);
			setEnd(pts[2], pts[3]);
			setAttributes(col, sol, fill);
		}
		
		public void draw(Graphics g){
			
			int[] pts = getPoints();
			char col = getColor();
			boolean sol = getSolidState();
			boolean fill = getFilledState();
			Graphics2D g2d = (Graphics2D) g.create();
			
			switch (col){
				case 'R':
					g2d.setColor(Color.RED);
					break;
				case 'G':
					g2d.setColor(Color.GREEN);
					break;
				case 'B':
					g2d.setColor(Color.BLUE);
					break;
				case 'W':
					g2d.setColor(Color.WHITE);
					break;
			}
			
			if (sol == false){
				Stroke dashed = new BasicStroke(2, BasicStroke.CAP_SQUARE, 
									BasicStroke.JOIN_BEVEL, 1, new float[]{2, 5}, 0);
				g2d.setStroke(dashed);
			}
			
			if (fill == true)
				g2d.fillRect(pts[0],pts[1],pts[2],pts[3]);
			else
				g2d.drawRect(pts[0],pts[1],pts[2],pts[3]);
			
			g2d.dispose();
		}
	}
	
	class Oval extends Shape{
		
		public Oval(){
			
		}
		
		public Oval(Shape oval){
			int[] pts = oval.getPoints();
			char col = oval.getColor();
			boolean sol = oval.getSolidState();
			boolean fill = oval.getFilledState();
			setStart(pts[0], pts[1]);
			setEnd(pts[2], pts[3]);
			setAttributes(col, sol, fill);
		}
		
		public void draw(Graphics g){
			
			int[] pts = getPoints();
			char col = getColor();
			boolean sol = getSolidState();
			boolean fill = getFilledState();
			Graphics2D g2d = (Graphics2D) g.create();
			
			switch (col){
				case 'R':
					g2d.setColor(Color.RED);
					break;
				case 'G':
					g2d.setColor(Color.GREEN);
					break;
				case 'B':
					g2d.setColor(Color.BLUE);
					break;
			}
			
			if (sol == false){
				Stroke dashed = new BasicStroke(3, BasicStroke.CAP_ROUND, 
									BasicStroke.JOIN_ROUND, 1, new float[]{2, 5}, 0);
				g2d.setStroke(dashed);
			}
			
			if (fill == true)
				g2d.fillOval(pts[0],pts[1],pts[2],pts[3]);
			else
				g2d.drawOval(pts[0],pts[1],pts[2],pts[3]);
			
			g2d.dispose();
			
		}
	}
	
	class MyMouseAdapter extends MouseAdapter{
		
		public void mousePressed(MouseEvent e){
			currX = e.getX();
			currY = e.getY();
			if(eraseChk)
				eraseHere = true;
		}
		
		public void mouseMoved(MouseEvent e){
			if(eraseChk){
				currX = e.getX();
				currY = e.getY();
				eraseHere = false;
				repaint();
			}
		}
		
		public void mouseDragged(MouseEvent e){
			dragged = true;
			if(eraseChk){
				eraseHere = true;
				currX = e.getX();
				currY = e.getY();
				repaint();
			}
			else if(currShape == 'R' || currShape == 'O'){
				minX = Math.min(e.getX(), currX);
				minY = Math.min(e.getY(), currY);
				endX = Math.abs(e.getX() - currX);
				endY = Math.abs(e.getY() - currY);
				repaint();
			}
			else if (currShape == 'L'){
				endX = e.getX();
				endY = e.getY();
				repaint();
			}
			else if(currShape == 'P'){
				if (doddled){
					currX = endX;
					currY = endY;
				}
				endX = e.getX();
				endY = e.getY();
				repaint();
				shapes.add(new Line(pencil));
				doddled = true;
			}
			
			if(eraseHere)
				shapes.add(new Rectangle(rectEraser));
		}
		
		public void mouseReleased(MouseEvent e){
			if (dragged == true & currShape != 'n'){
				switch (currShape){
					case 'P':
						doddled = false;
						break; 
					case 'L':
						shapes.add(new Line(line));
						break;
					case 'O':
						shapes.add(new Oval(oval));
						break;
					case 'R':
						shapes.add(new Rectangle(rect));
						break;
				}
			}
			else if (eraseChk && eraseHere){
				repaint();
				shapes.add(new Rectangle(rectEraser));
				eraseHere = false;
			}
			dragged = false;
		}
	}

	public void paint(Graphics g){
		
		//Line 160 (image name)
		if(loaded){
			g.drawImage(img, 50, 100, 216, 120, this);
		}
			
		if (!clearAll)
			for(Shape s:shapes)
				s.draw(g);
		
		if(clearAll){
			Dimension d = getSize();
			rect.setStart(0, 0);
			rect.setEnd(d.width, d.height);
			rect.setAttributes('W', true, true);
			rect.draw(g);
			shapes.clear();
			loaded = false;
			clearAll = false;
		}
		else if(eraseChk){
			rectEraser.setStart(currX-5, currY-5);
			rectEraser.setEnd(25, 25);
			rectEraser.setAttributes('W', true, true);
			g.drawRect(currX-5, currY-5, 26, 26);
			if(eraseHere){
				rectEraser.draw(g);
			}
		}
		else if(currShape == 'P'){
			pencil.setStart(currX, currY);
			pencil.setEnd(endX, endY);
			pencil.setAttributes(currColor, solidChk);
			pencil.draw(g);
		}
		else if(currShape == 'L'){
			line.setStart(currX, currY);
			line.setEnd(endX, endY);
			line.setAttributes(currColor, solidChk);
			line.draw(g);
		}
		else if(currShape == 'O'){
			oval.setStart(minX, minY);
			oval.setEnd(endX, endY);
			oval.setAttributes(currColor, solidChk, fillChk);
			oval.draw(g);
		}
		else if(currShape == 'R'){
			rect.setStart(minX, minY);
			rect.setEnd(endX, endY);
			rect.setAttributes(currColor, solidChk, fillChk);
			rect.draw(g);
		}
		
	}
}
