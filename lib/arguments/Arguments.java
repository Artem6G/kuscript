package lib.arguments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

public class Arguments implements Iterable<Argument>{
    private final ArrayList<Argument> arguments;

    {
        arguments = new ArrayList<>();
    }

    public void add(String name) {
        arguments.add(new Argument(name));
    }

    public Argument getArgument(int i) {
        return arguments.get(i);
    }

    public int size() {
        return arguments.size();
    }

    @Override
    public Iterator<Argument> iterator() {
        return arguments.iterator();
    }

    @Override
    public String toString() {
        return String.format("(%s)", arguments.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }
}
