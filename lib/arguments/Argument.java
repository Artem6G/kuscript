package lib.arguments;

public class Argument {
    private final String name;

    public Argument(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
