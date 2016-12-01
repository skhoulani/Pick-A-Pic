package pixelizer.graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Picture {
	
	private int[] correctPixels, backUpPixels;
	private BufferedImage image;
	private String path;

	public static Picture monaLisa = new Picture("/images/monaLisa.jpg");
	public static Picture doby = new Picture("/images/doby.jpg");
	public static Picture nh = new Picture("/images/nh.jpg");
	public static Picture me = new Picture("/images/me.png");
	public static Picture james = new Picture("/images/james.jpg");
	public static Picture george = new Picture("/images/george.jpg");
	public static Picture john = new Picture("/images/john.jpg");
	public static Picture bananas = new Picture("/images/bananas.jpg");
	public static Picture york = new Picture("/images/york.jpg");
	public static Picture brady = new Picture("/images/brady.png");
	public static Picture trump = new Picture("/images/trump.png");
	public static Picture eiffel = new Picture("/images/eiffel.png");
	public static Picture pat = new Picture("/images/pat.png");
	public static Picture beatles = new Picture("/images/beatles.png");
	public static Picture et = new Picture("/images/et.png");
	public static Picture joker = new Picture("/images/joker.png");
	public static Picture silk = new Picture("/images/silk.png");
	
	public Picture(String path) {
		this.path = path;
		load();
	}
	
	private void load() {
		try {
			this.image = ImageIO.read(Picture.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			this.correctPixels = new int[w * h];
			image.getRGB(0, 0, w, h, correctPixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
		backUpPixels = new int[correctPixels.length];
		for(int i = 0; i < correctPixels.length; i++) {
			backUpPixels[i] = correctPixels[i];
		}
	}
	
	public void reset() {
		for(int i = 0; i < correctPixels.length; i++) {
			correctPixels[i] = backUpPixels[i];
		}
	}
	
	public int[] getPixels() {
		return correctPixels;
	}

	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}
}
