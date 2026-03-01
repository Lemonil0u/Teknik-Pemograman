public class Cylinder extends Circle { // Save as "Cylinder.java"
    private double height; // private variable (harus buat get and set)
    
    // Constructor with default color, radius, and height
    public Cylinder() {
        super(); // call superclass no-arg constructor Circle()
        this.height = 1.0;
    }

    // Constructor with with default radius, color but given height
    public Cylinder(double height) {
        super(); // call superclass no-arg constructor Circle()
        this.height = height;
    }

    // Constructor with default raidus, color, but given radius, height
    public Cylinder(double height, double radius){
        super(radius); // call superclass constructor Circle(radius)
        this.height = height;
    }

    // A public method for retreiving the height
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double getArea() {
        return 2 * Math.PI * getRadius() * height + 2 * super.getArea();
    }

    @Override
    public String toString () {
        return "Cylinder[radius=" + getRadius() + " height=" + getHeight() + "color=" + getColor() + "]";
    }
    
    public double getVolume() {
        return super.getArea() * height;
    }

    // A public method for computing the volume of cylinder
    // User superclassmethod getArea() to get the base area
}