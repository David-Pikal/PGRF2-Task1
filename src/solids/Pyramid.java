package solids;

import model.TypeTopology;
import model.Vertex;
import transforms.Point3D;

public class Pyramid extends Solid{
    public Pyramid() {
        getVertexBuffer().add(new Vertex(new Point3D(3,3,2)));
        getVertexBuffer().add(new Vertex(new Point3D(5,3,2)));
        getVertexBuffer().add(new Vertex(new Point3D(4,5,2)));
        getVertexBuffer().add(new Vertex(new Point3D(4,4,5)));

        getIndexBuffer().add(0);
        getIndexBuffer().add(1);
        getIndexBuffer().add(2);

        getIndexBuffer().add(0);
        getIndexBuffer().add(1);
        getIndexBuffer().add(3);

        getIndexBuffer().add(1);
        getIndexBuffer().add(2);
        getIndexBuffer().add(3);

        getIndexBuffer().add(0);
        getIndexBuffer().add(2);
        getIndexBuffer().add(3);

        getParts().add(new model.Part(TypeTopology.TRIANGLES, 4 , 0));
    }
}
