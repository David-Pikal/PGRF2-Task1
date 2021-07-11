package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import raster.TestVisibility;
import render.Renderer;
import solids.Arrow;
import solids.Cube;
import solids.DoublePyramid;
import solids.Pyramid;
import solids.Solid;
import solids.Surface;
import transforms.Camera;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Mat4OrthoRH;
import transforms.Mat4PerspRH;
import transforms.Mat4RotX;
import transforms.Mat4RotY;
import transforms.Mat4RotZ;
import transforms.Mat4Scale;
import transforms.Mat4Transl;
import transforms.Point3D;
import transforms.Vec3D;

public class App {

	private TestVisibility testVisibility = new TestVisibility(800, 600);
	private Renderer renderer = new Renderer(testVisibility);
	private Mat4 model = new Mat4Identity();
	private Camera camera = new Camera(new Vec3D(-1, -6, 3), Math.toRadians(70), Math.toRadians(-10), 1, true);
	private int xStart, yStart;
	private List<Solid> solids = createSolids();
	private JFrame window;
	private JPanel pnlDraw;
	private boolean changeProjection = true;
	private boolean wireframe = false;
	private BufferedImage bufferedImage;

	public App() {

		window = new JFrame();
		bufferedImage = testVisibility.getBufferedImage();
		renderer.setView(camera.getViewMatrix());
		pnlDraw = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(bufferedImage, 0, 0, null);
			}
		};

		pnlDraw.setPreferredSize(new Dimension(testVisibility.getWidth(), testVisibility.getHeight()));

		pnlDraw.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				xStart = e.getX();
				yStart = e.getY();
			}
		});

		pnlDraw.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					int xEnd = e.getX();
					int yEnd = e.getY();
					double azimuth = camera.getAzimuth();
					double zenith = camera.getZenith();
					if (zenith < (-Math.PI / 2) && zenith > (Math.PI / 2)) {
						zenith = Math.PI / 2;
					}
					if (azimuth < (-Math.PI) && azimuth > (Math.PI)) {
						azimuth = Math.PI;
					}
					camera = camera.withAzimuth(azimuth + ((xStart - xEnd) * Math.PI) / bufferedImage.getWidth());
					camera = camera.withZenith(zenith + ((yStart - yEnd) * Math.PI) / bufferedImage.getHeight());
					renderer.setView(camera.getViewMatrix());
					renderSolids();
					pnlDraw.repaint();
				}
				xStart = e.getX();
				yStart = e.getY();

			}
		});

		window.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(!e.isControlDown()) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_W:
						camera = camera.forward(0.05);
						renderer.setView(camera.getViewMatrix());
						renderSolids();
						break;
					case KeyEvent.VK_S:
						camera = camera.backward(0.05);
						renderer.setView(camera.getViewMatrix());
						renderSolids();
						break;
					case KeyEvent.VK_A:
						camera = camera.left(0.05);
						renderer.setView(camera.getViewMatrix());
						renderSolids();
						break;
					case KeyEvent.VK_D:
						camera = camera.right(0.05);
						renderer.setView(camera.getViewMatrix());
						renderSolids();
						break;
					case KeyEvent.VK_Q:
						camera = camera.up(0.05);
						renderer.setView(camera.getViewMatrix());
						renderSolids();
						break;
					case KeyEvent.VK_Z:
						camera = camera.down(0.05);
						renderer.setView(camera.getViewMatrix());
						renderSolids();
						break;
					case KeyEvent.VK_NUMPAD1:
						model = model.mul(new Mat4RotX(0.1));
						renderer.setModel(model);
						renderSolids();
						break;
					case KeyEvent.VK_NUMPAD4:
						model = model.mul(new Mat4RotX(-0.1));
						renderer.setModel(model);
						renderSolids();
						break;
					case KeyEvent.VK_NUMPAD2:
						model = model.mul(new Mat4RotY(0.1));
						renderer.setModel(model);
						renderSolids();
						break;
					case KeyEvent.VK_NUMPAD5:
						model = model.mul(new Mat4RotY(-0.1));
						renderer.setModel(model);
						renderSolids();
						break;
					case KeyEvent.VK_NUMPAD3:
						model = model.mul(new Mat4RotZ(0.1));
						renderer.setModel(model);
						renderSolids();
						break;
					case KeyEvent.VK_NUMPAD6:
						model = model.mul(new Mat4RotZ(-0.1));
						renderer.setModel(model);
						renderSolids();
						break;	
					case KeyEvent.VK_SUBTRACT:
						model = model.mul(new Mat4Scale(0.9));
						renderer.setModel(model);
						renderSolids();
						break;
					case KeyEvent.VK_ADD:
						model = model.mul(new Mat4Scale(1.1));
						renderer.setModel(model);
						renderSolids();
						break;
					case KeyEvent.VK_P:
						if (changeProjection) {
							renderer.setProjection(new Mat4OrthoRH(5, 5, 1, 200));
							changeProjection = false;
							renderSolids();
						} else {
							renderer.setProjection(
									new Mat4PerspRH(Math.PI / 4, testVisibility.getWidth() / testVisibility.getHeight(), 1, 200));
							changeProjection = true;
							renderSolids();
						}
						break;
					case KeyEvent.VK_O:
						if(wireframe) {
							renderer.setWireframe(false);
							renderSolids();
							wireframe = false;
						} else {
							renderer.setWireframe(true);
							renderSolids();
							wireframe = true;
						}
					}
				} else {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_W:
						model = model.mul(new Mat4Transl(-0.1, 0, 0));
						renderer.setModel(model);
						renderSolids();
						break;
					case KeyEvent.VK_S:
						model = model.mul(new Mat4Transl(0.1, 0, 0));
						renderer.setModel(model);
						renderSolids();
						break;
					case KeyEvent.VK_Q:
						model = model.mul(new Mat4Transl(0, 0, 0.1));
						renderer.setModel(model);
						renderSolids();
						break;
					case KeyEvent.VK_Z:
						model = model.mul(new Mat4Transl(0, 0, -0.1));
						renderer.setModel(model);
						renderSolids();
						break;
					case KeyEvent.VK_A:
						model = model.mul(new Mat4Transl(0, -0.1, 0));
						renderer.setModel(model);
						renderSolids();
						break;
					case KeyEvent.VK_D:
						model = model.mul(new Mat4Transl(0, 0.1, 0));
						renderer.setModel(model);
						renderSolids();
						break;
					}
				}
				pnlDraw.repaint();
			}
		});
		
		window.setTitle("PGRF2 - Uloha 1 - David Pikal");
		window.setResizable(false);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);		
		JPanel pnlControl = new JPanel();
		JLabel labelControl = new JLabel("EN klavesnice-NUM 1,2,3,4,5,6-rotace, QWASDZ-zmena polohy kamery, Ctrl+QWASDZ-translace, LMB-rozhlizeni, P-zmena projekce, O-dratovy model");
		pnlControl.add(labelControl);
		window.add(pnlControl, BorderLayout.SOUTH);
		window.add(pnlDraw, BorderLayout.CENTER);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		renderSolids();
		pnlDraw.repaint();
	}

	private List<Solid> createSolids() {
		List<Solid> solids = new ArrayList<>();
		solids.add(new Cube());
		solids.add(new Surface());
		solids.add(new Pyramid());
		solids.add(new DoublePyramid());
		solids.add(new Arrow(new Point3D(-0.5, 0, 3.5), new Point3D(0.5, 0, 3.5), new Point3D(0, 0, 4)));
		solids.add(new Arrow(new Point3D(-0.5, 3.5, 0), new Point3D(0.5, 3.5, 0), new Point3D(0, 4, 0)));
		solids.add(new Arrow(new Point3D(3.5, -0.5, 0), new Point3D(3.5, 0.5, 0), new Point3D(4, 0, 0)));
		return solids;
	}

	private void renderSolids() {
		testVisibility.clear(0x000000);
		renderer.render(solids);

	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new App());
	}

}
