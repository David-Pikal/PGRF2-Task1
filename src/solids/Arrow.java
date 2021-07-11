package solids;

import model.Part;
import model.TypeTopology;
import model.Vertex;
import transforms.Point3D;

public class Arrow extends Solid {

	public Arrow(Point3D a, Point3D b, Point3D c) {
		getVertexBuffer().add(new Vertex(new Point3D(0, 0, 0)));
		getVertexBuffer().add(new Vertex(a));
		getVertexBuffer().add(new Vertex(b));
		getVertexBuffer().add(new Vertex(c)); // spicka

		getIndexBuffer().add(3);
		getIndexBuffer().add(1);
		getIndexBuffer().add(2);

		getIndexBuffer().add(3);
		getIndexBuffer().add(0);

		getParts().add(new Part(TypeTopology.LINES, 1, 3));
		getParts().add(new Part(TypeTopology.TRIANGLES, 1, 0));
	}

}
