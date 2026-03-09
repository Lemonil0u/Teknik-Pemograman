public abstract class Shape {
    protected String shapeName;

    // Constructor to declare shape name
    public Shape(String name) {
        shapeName = name;
    }

    // Abstract method that needs to be filled with sub class 
    public abstract double area();

    // Returning shape name
    public String toString() {
        return shapeName;
    }
}