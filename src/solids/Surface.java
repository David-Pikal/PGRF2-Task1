package solids;


import model.Part;
import model.TypeTopology;
import model.Vertex;
import transforms.Bicubic;
import transforms.Cubic;
import transforms.Point3D;

public class Surface extends Solid {
    Bicubic bc;

    public Surface() {
        Point3D[] rb = new Point3D[16];
        rb[0] = new Point3D(2,2,0);
        rb[1] = new Point3D(1,1,1);
        rb[2] = new Point3D(4,1,1);
        rb[3] = new Point3D(3,2,0);
        
        rb[4] = new Point3D(2,2,1);
        rb[5] = new Point3D(1,1,2);
        rb[6] = new Point3D(4,1,2);
        rb[7] = new Point3D(3,2,1);
        
        rb[8] = new Point3D(2,3,1);
        rb[9] = new Point3D(1,4,2);
        rb[10] = new Point3D(4,4,2);
        rb[11] = new Point3D(3,3,1);
        
        rb[12] = new Point3D(2,3,0);
        rb[13] = new Point3D(1,4,1);
        rb[14] = new Point3D(4,4,1);
        rb[15] = new Point3D(3,3,0);

        bc = new Bicubic(Cubic.BEZIER, rb);

        // plocha - parametr v,u  0 - 1
        int index = 0;
        for(float u = 0; u <= 1; u+=0.1){
            for(float v = 0; v <= 1; v+=0.1){
                Vertex vertex = new Vertex(bc.compute(u, v));
                getVertexBuffer().add(vertex);
                getIndexBuffer().add(index++);
            }
            getParts().add(new Part(TypeTopology.POINTS, getIndexBuffer().size() , 0));
            
        }
        
        
    }
}
