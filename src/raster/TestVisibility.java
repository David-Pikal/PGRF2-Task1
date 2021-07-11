package raster;

import java.awt.image.BufferedImage;
import transforms.Col;

public class TestVisibility {
	private ImageBuffer imgBuffer;
	private ZBuffer zBuffer;
	
	public TestVisibility(int width, int height) {
		zBuffer = new ZBuffer (width, height);
		imgBuffer = new ImageBuffer(width, height);
	}
	
	public void render(int x, int y, double z, Col color) {
		if(z < zBuffer.get(x, y) && z >= 0) {
			imgBuffer.set(x, y, color.getRGB());
			zBuffer.set(x, y, z);
		}
	}
	
	public void init(int color) {
		for(int i = 0; i < zBuffer.getH();i++) {
			for(int j = 0; j < zBuffer.getW(); j++) {
				zBuffer.set(j, i, new Double(1));
				imgBuffer.set(j, i, color);
			}
		}
	}
	
	public void clear(int color) {
		imgBuffer.clear(color);
		zBuffer.clear();
	}
	
	public BufferedImage getBufferedImage() {
		return imgBuffer.getBuff();
	}
	
	public int getWidth() {
		return zBuffer.getW();
	}
	
	public int getHeight() {
		return zBuffer.getH();
	}
	
}
