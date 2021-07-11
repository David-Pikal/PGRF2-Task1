package render;

import model.Vertex;
import raster.TestVisibility;
import transforms.Col;

public class RasterizerPoint {

	private int width;
	private int height;
	private TestVisibility testVisibility;

	public RasterizerPoint(TestVisibility testVisibility) {
		this.height = testVisibility.getHeight();
		this.width = testVisibility.getWidth();
		this.testVisibility = testVisibility;
	}

	public void rasterize(Vertex a) {
		a = a.dehomog();

		double xA = (width - 1) * (a.getPoint().getX() + 1) / 2;
		double yA = (height - 1) * (-a.getPoint().getY() + 1) / 2;
		double zA = a.getPoint().getZ();

		testVisibility.render((int) xA + 1, (int) yA + 1, zA, new Col(0x00ff00));

	}

}
