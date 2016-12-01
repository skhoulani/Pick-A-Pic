package pixelizer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFrame;

import pixelizer.graphics.Picture;
import pixelizer.graphics.Screen;
import pixelizer.graphics.input.Keyboard;
import pixelizer.graphics.input.Mouse;

public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private Thread thread;
	private JFrame frame;
	private Screen screen;
	private Keyboard keyboard;
	private Mouse mouse;
	private static String title = "Pixelizer";
	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	public static boolean running = false;
	Random rand = new Random();
	public Picture pic;
	
	private BufferedImage image;
	private int[] pixels;
	private int[] emailPixels;
	
	public Display() {
		keyboard = new Keyboard();
		mouse = new Mouse();
		pic = Picture.brady;
		WIDTH = pic.getWidth();
		HEIGHT = pic.getHeight();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		emailPixels = new int[pixels.length];
		frame = new JFrame();
		
		screen = new Screen(WIDTH, HEIGHT);
		
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		
		this.addKeyListener(keyboard);
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
	}
	
	public static void main(String[]args) {
		Display game = new Display();
		game.frame.setResizable(false);
		game.frame.setTitle(title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60;
		double delta = 0;
		int frames = 0;
		@SuppressWarnings("unused")
		int updates = 0;
		requestFocus();
				
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		screen.renderPicture(pic);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		if(!squareDrawing) g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		for(int i = 0; i < points.size(); i+=2) {
			Point p1 = points.get(i);
			Point p2 = points.get(i+1);
			Point pA = new Point(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y));
			Point pB = new Point(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y));
			g.setColor(colors.get(i/2));
			g.fillRect(pA.x, pA.y, pB.x-pA.x+1, pB.y-pA.y+1);
		}
		if(drawing) {
			Point pA = new Point(Math.min(currentPoint.x, Mouse.getX()), Math.min(currentPoint.y, Mouse.getY()));
			Point pB = new Point(Math.max(currentPoint.x, Mouse.getX()), Math.max(currentPoint.y, Mouse.getY()));
			g.setColor(getColor(currentPoint, new Point(Mouse.getX(), Mouse.getY())));
			g.fillRect(pA.x, pA.y, pB.x-pA.x+1, pB.y-pA.y+1);
			g.setColor(Color.GREEN);
			g.drawRect(pA.x, pA.y, pB.x-pA.x+1, pB.y-pA.y+1);
		}
//		g.setFont(new Font("Verdana", 0, 22));
		g.dispose();
		bs.show();
	}
	
	int anim = 0;
	int speed = 10; // Smaller = Faster
	int speedSquare = 1; // Smaller = Faster
	int pixelSize = 1;
	int squaresMade = 0;
	boolean goBack = false;
	boolean drawing = false;
	boolean squareDrawing = false;
	ArrayList<Point> points = new ArrayList<Point>();
	ArrayList<Color> colors = new ArrayList<Color>();
	Point currentPoint;
	
	public void update() {
		keyboard.update();
		anim++;
		if(squareDrawing && anim % speedSquare == 0) {
			points.clear();
			colors.clear();
			int maxSize = 100-squaresMade/100;
			int size = rand.nextInt(Math.max(1, maxSize))+1;
			int x = rand.nextInt(WIDTH-size);
			int y = rand.nextInt(HEIGHT-size);
			Point p1 = new Point(x, y);
			Point p2 = new Point(x+size, y+size);
			Color c = getColor(p1, p2);
			points.add(p1);
			points.add(p2);
			colors.add(c);
			for(int y2 = y; y2 < p2.y; y2++) {
				for(int x2 = x; x2 < p2.x; x2++) {
					emailPixels[x2+y2*WIDTH] = c.getRGB();
				}				
			}
			squaresMade++;
			if(points.size() > 1000) {
				points.remove(0);
				points.remove(0);
				colors.remove(0);
			}
		}
		if(keyboard.getSpace() && !drawing) {
			squareDrawing = !squareDrawing;
			if(!squareDrawing) {
				for(int i = 0; i < emailPixels.length; i++) {
					emailPixels[i] = 0;
				}
				points.clear();
				colors.clear();
			}
			else {
				pixelSize = 1;
				squaresMade = 0;
				pic.reset();
				pixelize();
			}
		}
		if(keyboard.getE()) {
			sendEmail();
		}
		if(!squareDrawing) {
			if(goBack && (anim % speed == 0 || anim == 1)) {
				
				if(pixelSize > 1) {
					pixelSize--;
					pic.reset();
					pixelize();
				}
				else {
					goBack = false;
				}
			}
			if(keyboard.getLeft()) {
				if(pixelSize > 1) {
					pixelSize--;
					pic.reset();
					pixelize();
				}
			}
			else if(keyboard.getRight()) {
				pixelSize++;
				pic.reset();
				pixelize();
			}
			if(keyboard.getBack()) {
				goBack = true;
			}
			if(keyboard.getForward()) {
				pixelSize = 600 / speed;
				pic.reset();
				pixelize();
			}
			if(Mouse.getButton() == 1) {
				if(drawing) {
					points.add(currentPoint);
					Point p = new Point(Mouse.getX(), Mouse.getY());
					points.add(p);
					colors.add(getColor(currentPoint, p));
				}
				else {
					currentPoint = new Point(Mouse.getX(), Mouse.getY());
				}
				drawing = !drawing;
			}
			else if(Mouse.getButton() == 3) {
				points.clear();
				colors.clear();
			}
		}
		Mouse.resetButton();
	}
	
	public Color getColor(Point p1, Point p2) {
		int r, g, b;
		r = b = g = 0;
		Point pA = new Point(Math.min(p1.x, p2.x), Math.min(p1.y, p2.y));
		Point pB = new Point(Math.max(p1.x, p2.x), Math.max(p1.y, p2.y));
		for(int y = pA.y; y <= pB.y; y++) {
			for(int x = pA.x; x <= pB.x; x++) {
				Color c = new Color(pic.getPixels()[x+y*pic.getWidth()]);
				r += c.getRed();
				g += c.getGreen();
				b += c.getBlue();
			}
		}
		int numVals = (pB.x-pA.x+1) * (pB.y-pA.y+1);
		r /= numVals;
		g /= numVals;
		b /= numVals;
		return new Color(r, g, b);
	}

	public void pixelize() {
		for(int y = 0; y < pic.getHeight(); y += pixelSize) {
			for(int x = 0; x < pic.getWidth(); x += pixelSize) {
				int r, g, b;
				r = b = g = 0;
				int pixelCount = 0;
				for(int i = 0; i < ((x + pixelSize >= pic.getWidth()) ? pic.getWidth() -x: pixelSize); i++) {
					for(int j = 0; j < ((y + pixelSize >= pic.getHeight()) ? pic.getHeight()-y : pixelSize); j++) {
						Color c = new Color(pic.getPixels()[(x+i)+(y+j)*pic.getWidth()]);
						r += c.getRed();
						g += c.getGreen();
						b += c.getBlue();
						pixelCount++;
					}
				}
				r /= pixelCount;
				g /= pixelCount;
				b /= pixelCount;
				double factor = 1;
				int c_val = (new Color(Math.min(255, (int)(r*factor)), Math.min(255, (int)(g*factor)), Math.min(255, (int)(b*factor)))).getRGB();
				for(int i = 0; i < ((x + pixelSize >= pic.getWidth()) ? pic.getWidth() -x: pixelSize); i++) {
					for(int j = 0; j < ((y + pixelSize >= pic.getHeight()) ? pic.getHeight()-y : pixelSize); j++) {
						pic.getPixels()[(x+i)+(y+j)*pic.getWidth()] = c_val;
					}
				}
			}
		}
	}
	
	private void sendEmail() {
		try {
			String currentDir = new File("").getAbsolutePath() + "/res/results/" + System.currentTimeMillis() + ".PNG";
			System.out.println ("Current directory: " + currentDir);
			BufferedImage currentImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			for(int y = 0; y < HEIGHT; y++) {
				for(int x = 0; x < WIDTH; x++) {
					if(drawing) currentImage.setRGB(x, y, emailPixels[x+y*WIDTH]);
					else currentImage.setRGB(x, y, pixels[x+y*WIDTH]);
				}
			}
			ImageIO.write(currentImage, "PNG", new File(currentDir));
			
			
			
			Properties properties = new Properties();
		      Date date = new Date();
		      properties.put("mail.smtp.auth", "true");
		      properties.put("mail.smtp.starttls.enable", "true");
		      properties.put("mail.smtp.host", "smtp.gmail.com");
		      properties.put("mail.smtp.port", "587");
		      
		      final String username = "pikapikapower24@gmail.com";
		      final String password = "aacarib24";
		      String from = username;
		      String[] to = {"Connor_McGrory@student.uml.edu"};
		      String subject = "Pixelizer";
		      
		      Session session = Session.getDefaultInstance(properties, new Authenticator() {
		    	  @Override
		    	  protected PasswordAuthentication getPasswordAuthentication() {
		    		  return new PasswordAuthentication(username, password);
		    	  }
		      });
		      
		      try {
		    	  Address[] recipients = new Address[to.length];
		    	  for(int i=0;i<to.length;i++) {
		    		  recipients[i] = new InternetAddress(to[i]);
		    	  }
		    	  Message message = new MimeMessage(session);
		    	  message.setFrom(new InternetAddress(from));
		    	  message.setRecipients(Message.RecipientType.TO, recipients);
		    	  message.setSubject(subject);
		    	  BodyPart messageBodyPart = new MimeBodyPart();

		          // Now set the actual message
		          messageBodyPart.setText("New picture recieved!\n" + date.toString());

		          // Create a multipar message
		          Multipart multipart = new MimeMultipart();

		          // Set text message part
		          multipart.addBodyPart(messageBodyPart);

		          // Part two is attachment
		          messageBodyPart = new MimeBodyPart();
		          DataSource source = new FileDataSource(currentDir);
		          messageBodyPart.setDataHandler(new DataHandler(source));
		          messageBodyPart.setFileName(currentDir);
		          multipart.addBodyPart(messageBodyPart);

		          // Send the complete message parts
		          message.setContent(multipart);

		          // Send message
		          Transport.send(message);
		    	  
		      } catch (MessagingException e) {
		    	  throw new RuntimeException(e);
		      }
			
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
