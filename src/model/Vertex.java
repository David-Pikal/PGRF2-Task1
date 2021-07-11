package model;

import transforms.Col;
import transforms.Point3D;

public class Vertex {

	private final Point3D point;
	private Col color = new Col(Math.random(), Math.random(), Math.random());

	public Vertex(Point3D position) {
		this.point = position;
	}

	public Vertex withPoint(Point3D point) {
		return new Vertex(point);
	}

	public Point3D getPoint() {
		return point;
	}

	public Vertex mul(double d) {
		return new Vertex(point.mul(d));
	}

	public Vertex add(Vertex d) {
		return new Vertex(point.add(d.getPoint()));
	}

	public Vertex dehomog() {
		return this.mul(1 / this.getPoint().getW());
	}

	public Col getColor() {
		return color;
	}

}
