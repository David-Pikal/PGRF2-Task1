package raster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ZBuffer implements Raster<Double>{
	private int width;
	private int height;
	private List<Double> zBuff = new ArrayList<>();
	
	public ZBuffer(int width, int height) {	
		this.width = width;
		this.height = height;
		for(int i = 0; i < (width*height); i++) {	
			zBuff.add(new Double(1));
		}
	}
	
	public void clear(){
		Collections.fill(zBuff, new Double(1));
    }
	
	@Override
	public Double get(int x, int y) {
		return zBuff.get(y*width+x);
	}

	@Override
	public void set(int x, int y, Double value) {
		//System.out.println(grid.size() + "ssds");
		zBuff.set(y*width+x, value);
	}

	@Override
	public int getW() {
		return this.width;
	}

	@Override
	public int getH() {
		return this.height;
	}

}
