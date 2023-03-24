package drawings;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Point {
	int x;
	int y;
	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

abstract class Shape extends Object {
	
}

class Circle extends Shape {
	Point center;
	int radius;
	Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}
}

class Polygon extends Shape {
	Point[] vertices;
	Polygon(Point[] vertices) {
		this.vertices = vertices.clone();
	}
}

class Drawing {
	Object[] shapes;
	Drawing(Object[] shapes) { this.shapes = shapes.clone(); }
	
	String toSVG() {
		String result = "<svg xmlns=\"http://w3c.org/2000/SVG\"";
		for (Object shape : shapes) {
			if (shape instanceof Circle circle) { // pattern match expression
				// typecast
				//Circle circle = (Circle)shape;
				result += "<circle x=\"" + circle.center.x + "\" y=\"" + circle.center.y +
						"\" r=\"" + circle.radius + "\">";
			} else if (shape instanceof Polygon polygon){
				result += "<polygon points=\"";
				for (Point p : polygon.vertices) {
					result += p.x + " " + p.y;
				}
				result += ">";
			} else {
				throw new AssertionError("Unexpected case");
			}
		}
		result += "</svg>";
		return result;
	}
}

class DrawingsTest {

	@Test
	void test() {
		Drawing myDrawing = new Drawing(new Shape[] {
			new Circle(new Point(10, 20), 5),
			new Polygon(new Point[] {new Point(10, 20), new Point(20, 20), new Point(20, 10)})
		});
		assertEquals("<svg xmlns=\"http://w3c.org/2000/SVG\"<circle x=\"10\" y=\"20\" r=\"5\"><polygon points=\"10 2020 2020 10></svg>",
				myDrawing.toSVG());
		// 'shape' is a polymorphic variable: it can point to objects of different types
		Shape shape = new Circle(new Point(10, 20), 5);
		shape = new Polygon(new Point[] {new Point(10, 20), new Point(20, 20), new Point(20, 10)});
		
		Object x = 10; // autoboxing
		assertTrue(x instanceof Integer);
		int i = (int)x; // auto-unboxing
		
		Object x2 = new int[] {10, 20, 30};
		Object[] objects = new Shape[] {
				new Circle(new Point(10, 20), 5),
				new Polygon(new Point[] {new Point(10, 20), new Point(20, 20), new Point(20, 10)})
		};
		objects[0] = "Hello!";	// ArrayStoreException	
	}

}
