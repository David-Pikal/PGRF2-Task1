package solids;

import model.Vertex;
import transforms.Point3D;

public class Cube extends Solid{
    public Cube() {

        getVertexBuffer().add(new Vertex(new Point3D(3,3,1)));
        getVertexBuffer().add(new Vertex(new Point3D(5,3,1)));
        getVertexBuffer().add(new Vertex(new Point3D(5,5,1)));
        getVertexBuffer().add(new Vertex(new Point3D(3,5,1)));

        getVertexBuffer().add(new Vertex(new Point3D(3,3,3)));
        getVertexBuffer().add(new Vertex(new Point3D(5,3,3)));
        getVertexBuffer().add(new Vertex(new Point3D(5,5,3)));
        getVertexBuffer().add(new Vertex(new Point3D(3,5,3)));

        //0123
        getIndexBuffer().add(0);
        getIndexBuffer().add(1);
        getIndexBuffer().add(2);

        getIndexBuffer().add(0);
        getIndexBuffer().add(2);
        getIndexBuffer().add(3);

        //0145
        getIndexBuffer().add(0);
        getIndexBuffer().add(1);
        getIndexBuffer().add(4);

        getIndexBuffer().add(1);
        getIndexBuffer().add(4);
        getIndexBuffer().add(5);

        //1256
        getIndexBuffer().add(1);
        getIndexBuffer().add(2);
        getIndexBuffer().add(5);

        getIndexBuffer().add(2);
        getIndexBuffer().add(5);
        getIndexBuffer().add(6);

        // 0347
        getIndexBuffer().add(0);
        getIndexBuffer().add(3);
        getIndexBuffer().add(4);

        getIndexBuffer().add(3);
        getIndexBuffer().add(4);
        getIndexBuffer().add(7);

        // 4567
        getIndexBuffer().add(4);
        getIndexBuffer().add(5);
        getIndexBuffer().add(7);

        getIndexBuffer().add(5);
        getIndexBuffer().add(6);
        getIndexBuffer().add(7);

        // 2367
        getIndexBuffer().add(2);
        getIndexBuffer().add(3);
        getIndexBuffer().add(7);

        getIndexBuffer().add(2);
        getIndexBuffer().add(6);
        getIndexBuffer().add(7);


        getParts().add(new model.Part(model.TypeTopology.TRIANGLES, 12 , 0));
    }

}
