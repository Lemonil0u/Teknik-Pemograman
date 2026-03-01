public class Test {
    public static void main(String[] args) {
        // Test Shape
        Shape s1 = new Shape();
        System.out.println(s1);
        System.out.println();

        // Test Rectangle
        Rectangle r1 = new Rectangle(2.0, 1.0);
        System.out.println(r1);
        System.out.println();

        // Test Circle default
        Circle c1 = new Circle();
        System.out.println(c1);
        System.out.println();

        // Test Square default
        Square sq1 = new Square();
        System.out.println(sq1);
    }
}