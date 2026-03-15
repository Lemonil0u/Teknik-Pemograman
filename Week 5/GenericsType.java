public class GenericsType<T> {
    private T t;

    public T get() {
        return this.t;
    }

    public void set(T t1) {
        this.t = t1;
    }

    public static void main(String args[]) {
        GenericsType<String> type = new GenericsType<>();
        type.set("Java"); // valid
        System.out.println("Value in type: " + type.get());

        GenericsType<Object> type1 = new GenericsType<>();
        type1.set("Java"); // valid
        System.out.println("Value in type1 (String): " + type1.get());
        
        type1.set(10); // valid and autoboxing support
        System.out.println("Value in type1 (Integer): " + type1.get());
    }
}