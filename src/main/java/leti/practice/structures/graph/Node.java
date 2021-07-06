package leti.practice.structures.graph;

import java.util.Objects;

public class Node {
    private final String name;
    public Node(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public Node copy(){
       return new Node(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
