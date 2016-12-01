package pixelizer.graphics;

public class Screen {
	
	public static int border = 1;
	public static boolean grow = false;
	private int width, height;
	public int[] pixels;
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	public void clear() {
		for(int i=0;i<pixels.length;i++) {
			pixels[i] = 0;
		}
	}
	
	public void renderPicture(Picture p) {
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				pixels[x+y*width] = p.getPixels()[x+y*width];
			}
		}
	}
	

}
