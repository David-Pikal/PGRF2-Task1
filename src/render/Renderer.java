package render;

import solids.Arrow;
import solids.Solid;

import java.util.List;

import model.Part;
import model.TypeTopology;
import model.Vertex;
import raster.TestVisibility;
import transforms.Col;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Mat4PerspRH;
import transforms.Point3D;

public class Renderer {
	private Mat4 model = new Mat4Identity();
	private Mat4 view;
	private Mat4 projection;
	private RasterizerTriangle rasterizerTriangle;
	private RasterizerLineDDA rasterizerLine;
	private RasterizerPoint rasterizerPoint;
	private boolean wireframe = false;



	/**
	 * 
	 * @param testVisibility
	 */
	public Renderer(TestVisibility testVisibility) {
		rasterizerTriangle = new RasterizerTriangle(testVisibility);
		rasterizerLine = new RasterizerLineDDA(testVisibility);
		rasterizerPoint = new RasterizerPoint(testVisibility);
		projection = new Mat4PerspRH(Math.PI / 4, testVisibility.getWidth() / testVisibility.getHeight(), 1, 200);
	}

	public void render(List<Solid> solids) {
		for (Solid solid : solids) {
			for (Part parts : solid.getParts()) {
				if (parts.getType() == TypeTopology.TRIANGLES) {
					Vertex a, b, c;
					Col color;
					for (int i = 0; i < parts.getCount(); i++) {

						a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(3 * i + parts.getStart()));
						b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(3 * i + parts.getStart() + 1));
						c = solid.getVertexBuffer().get(solid.getIndexBuffer().get(3 * i + parts.getStart() + 2));

						double rA = a.getColor().getR();
						double rB = b.getColor().getR();
						double rC = c.getColor().getR();

						double gA = a.getColor().getG();
						double gB = b.getColor().getG();
						double gC = c.getColor().getG();

						double bA = a.getColor().getB();
						double bB = b.getColor().getB();
						double bC = c.getColor().getB();

						color = new Col((rA + rB + rC) / 3, (gA + gB + gC) / 3, (bA + bB + bC) / 3);
						
						if(solid instanceof Arrow){
	                           Point3D aP = a.getPoint();
	                           aP = aP.mul(view).mul(projection);
	                           a = a.withPoint(aP);

	                           Point3D bP = b.getPoint();
	                           bP = bP.mul(view).mul(projection);
	                           b = b.withPoint(bP);

	                           Point3D cP = c.getPoint();
	                           cP = cP.mul(view).mul(projection);
	                           c = c.withPoint(cP);
	                       } else {
	                           Point3D aP = a.getPoint();
	                           aP = aP.mul(model).mul(view).mul(projection);
	                           a = a.withPoint(aP);

	                           Point3D bP = b.getPoint();
	                           bP = bP.mul(model).mul(view).mul(projection);
	                           b = b.withPoint(bP);

	                           Point3D cP = c.getPoint();
	                           cP = cP.mul(model).mul(view).mul(projection);
	                           c = c.withPoint(cP);
	                       }

						if (wireframe) {

							renderLine(a, b, color);
							renderLine(c, b, color);
							renderLine(c, a, color);

						} else {
							renderTriangle(a, b, c, color);
						}

					}
				} else if (parts.getType() == TypeTopology.LINES) {
					Vertex a, b;
					Col color;
					for (int i = 0; i < parts.getCount(); i++) {
						a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(parts.getStart() + i * 2));
						b = solid.getVertexBuffer().get(solid.getIndexBuffer().get(parts.getStart() + i * 2 + 1));

						double rA = a.getColor().getR();
						double rB = b.getColor().getR();

						double gA = a.getColor().getG();
						double gB = b.getColor().getG();

						double bA = a.getColor().getB();
						double bB = b.getColor().getB();

						color = new Col((rA + rB) / 2, (gA + gB) / 2, (bA + bB) / 2);

						if(solid instanceof Arrow){
	                           Point3D aP = a.getPoint();
	                           aP = aP.mul(view).mul(projection);
	                           a = a.withPoint(aP);

	                           Point3D bP = b.getPoint();
	                           bP = bP.mul(view).mul(projection);
	                           b = b.withPoint(bP);
	                       } else {
	                           Point3D aP = a.getPoint();
	                           aP = aP.mul(model).mul(view).mul(projection);
	                           a = a.withPoint(aP);

	                           Point3D bP = b.getPoint();
	                           bP = bP.mul(model).mul(view).mul(projection);
	                           b = b.withPoint(bP);

	                       }
				

						renderLine(a, b, color);
					}
				} else if (parts.getType() == TypeTopology.POINTS) {
					Vertex a;
					for (int i = 0; i < parts.getCount(); i++) {

						a = solid.getVertexBuffer().get(solid.getIndexBuffer().get(i + parts.getStart()));
						a = a.withPoint(a.getPoint().mul(model).mul(view).mul(projection));

						renderPoint(a);
					}
				}

			}

		}
	}

	private void renderTriangle(Vertex a, Vertex b, Vertex c, Col color) {
		// orez
		// podle X
		if (a.getPoint().getX() > a.getPoint().getW() && b.getPoint().getX() > b.getPoint().getW()
				&& c.getPoint().getX() > c.getPoint().getW()) {
			return;
		}
		if (a.getPoint().getX() < -a.getPoint().getW() && b.getPoint().getX() < -b.getPoint().getW()
				&& c.getPoint().getX() < -c.getPoint().getW()) {
			return;
		}
		// podle Y
		if (a.getPoint().getY() > a.getPoint().getW() && b.getPoint().getY() > b.getPoint().getW()
				&& c.getPoint().getY() > c.getPoint().getW()) {
			return;
		}
		if (a.getPoint().getY() < -a.getPoint().getW() && b.getPoint().getY() < -b.getPoint().getW()
				&& c.getPoint().getY() < -c.getPoint().getW()) {
			return;
		}
		// podle Z
		if (a.getPoint().getZ() > a.getPoint().getW() && b.getPoint().getZ() > b.getPoint().getW()
				&& c.getPoint().getZ() > c.getPoint().getW()) {
			return;
		}
		if (a.getPoint().getZ() < 0 && b.getPoint().getZ() < 0 && c.getPoint().getZ() < 0) {
			return;
		}

		// serazeni podle z: a > b > c
		if (a.getPoint().getZ() < b.getPoint().getZ()) {
			Vertex temp = a;
			a = b;
			b = temp;
		}
		if (a.getPoint().getZ() < c.getPoint().getZ()) {
			Vertex temp = a;
			a = c;
			c = temp;
		}
		if (b.getPoint().getZ() < c.getPoint().getZ()) {
			Vertex temp = b;
			b = c;
			c = temp;
		}

		if (a.getPoint().getZ() < 0) {
			return;
		}
		if (b.getPoint().getZ() < 0) {
			double t1 = a.getPoint().getZ() / (a.getPoint().getZ() - b.getPoint().getZ());
			double t2 = a.getPoint().getZ() / (a.getPoint().getZ() - c.getPoint().getZ());

			// vertex na hrane ab
			Vertex ab = a.mul(1 - t1).add(b.mul(t1));
			// vertex na hrane ac
			Vertex ac = a.mul(1 - t2).add(c.mul(t2));
			rasterizerTriangle.rasterize(a, ab, ac, color);
		}
		if (c.getPoint().getZ() < 0) {

			double t1 = b.getPoint().getZ() / (b.getPoint().getZ() - c.getPoint().getZ());
			double t2 = a.getPoint().getZ() / (a.getPoint().getZ() - c.getPoint().getZ());

			// vertex na hrane bc
			Vertex bc = b.mul(1 - t1).add(c.mul(t1));
			// vertex na hrane ac
			Vertex ac = a.mul(1 - t2).add(c.mul(t2));

			rasterizerTriangle.rasterize(a, b, ac, color);
			rasterizerTriangle.rasterize(bc, b, ac, color);
			return;
		}

		if (c.getPoint().getZ() >= 0) {
			rasterizerTriangle.rasterize(a, b, c, color);
		}

	}

	private void renderLine(Vertex a, Vertex b, Col color) {
		// orez
		// podle X
		if (a.getPoint().getX() > a.getPoint().getW() && b.getPoint().getX() > b.getPoint().getW()) {
			return;
		}
		if (a.getPoint().getX() < -a.getPoint().getW() && b.getPoint().getX() < -b.getPoint().getW()) {
			return;
		}
		// podle Y
		if (a.getPoint().getY() > a.getPoint().getW() && b.getPoint().getY() > b.getPoint().getW()) {
			return;
		}
		if (a.getPoint().getY() < -a.getPoint().getW() && b.getPoint().getY() < -b.getPoint().getW()) {
			return;
		}
		// podle Z
		if (a.getPoint().getZ() > a.getPoint().getW() && b.getPoint().getZ() > b.getPoint().getW()) {
			return;
		}
		if (a.getPoint().getZ() < 0 && b.getPoint().getZ() < 0) {
			return;
		}

		// usporadani podle z: a > b
		if (a.getPoint().getZ() < b.getPoint().getZ()) {
			Vertex temp = a;
			a = b;
			b = temp;
		}

		if (a.getPoint().getZ() < 0) {
			return;
		}

		if (b.getPoint().getZ() < 0) {
			double t = a.getPoint().getZ() / (a.getPoint().getZ() - b.getPoint().getZ());
			Vertex ab = a.mul(1 - t).add(b.mul(t));
			rasterizerLine.rasterize(a, ab, color);
			return;
		}

		if (b.getPoint().getZ() >= 0) {
			rasterizerLine.rasterize(a, b, color);
			return;
		}

	}

	private void renderPoint(Vertex a) {
		// podle X
		if (a.getPoint().getX() > a.getPoint().getW()) {
			return;
		}

		if (a.getPoint().getX() < -a.getPoint().getW()) {
			return;
		}

		// podle Y
		if (a.getPoint().getY() > a.getPoint().getW()) {
			return;
		}

		if (a.getPoint().getY() < -a.getPoint().getW()) {
			return;
		}

		// podle Z
		if (a.getPoint().getZ() > a.getPoint().getW()) {
			return;
		}

		if (a.getPoint().getZ() < 0) {
			return;
		}

		if (a.getPoint().getZ() >= 0) {
			rasterizerPoint.rasterize(a);
		}
	}

	public Mat4 getModel() {
		return model;
	}

	public void setModel(Mat4 model) {
		this.model = model;
	}

	public Mat4 getView() {
		return view;
	}

	public void setView(Mat4 view) {
		this.view = view;
	}

	public Mat4 getProjection() {
		return projection;
	}

	public void setProjection(Mat4 projection) {
		this.projection = projection;
	}
	
	public boolean isWireframe() {
		return wireframe;
	}

	public void setWireframe(boolean wireframe) {
		this.wireframe = wireframe;
	}

}
