package solids;

import model.TypeTopology;
import model.Vertex;
import transforms.Point3D;

public class DoublePyramid extends Solid{
	public DoublePyramid() {
        getVertexBuffer().add(new Vertex(new Point3D(-1,0,0)));
        getVertexBuffer().add(new Vertex(new Point3D(-3,0,0)));
        getVertexBuffer().add(new Vertex(new Point3D(-2,2,0)));
        getVertexBuffer().add(new Vertex(new Point3D(-2,1,-2)));
        getVertexBuffer().add(new Vertex(new Point3D(-2,1,2)));

        getIndexBuffer().add(0);
        getIndexBuffer().add(1);
        getIndexBuffer().add(4);

        getIndexBuffer().add(0);
        getIndexBuffer().add(4);
        getIndexBuffer().add(2);

        getIndexBuffer().add(2);
        getIndexBuffer().add(1);
        getIndexBuffer().add(4);

        getIndexBuffer().add(0);
        getIndexBuffer().add(1);
        getIndexBuffer().add(3);
        
        getIndexBuffer().add(0);
        getIndexBuffer().add(2);
        getIndexBuffer().add(3);
        
        getIndexBuffer().add(1);
        getIndexBuffer().add(2);
        getIndexBuffer().add(3);
   
        getParts().add(new model.Part(TypeTopology.TRIANGLES, 6 , 0));
    }
}
