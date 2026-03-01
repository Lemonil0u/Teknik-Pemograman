public class Shape {
    private String color = "red"; // default value
    private boolean filled = true; // default value

    // No-arg constructor
    public Shape() {
        this.color = "red";
        this.filled = true; 
    }

    // Parameterized constructor
    public Shape(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public String toString() {
        return "Shape [color : " + color 
                + ", is " 
                + (filled ? "filled" : "not filled") + "]";
    }
}