package leti.practice.model.graph;

import java.util.Objects;

public class EdgeProperties<T extends Number> {
    private T flow;
    private final T capacity;

    public EdgeProperties(T capacity, T flow){
        this.capacity = capacity;
        this.flow = flow;
    }

    /*public EdgeProperties<T> copy(){
        return new EdgeProperties<T>(capacity, flow);
    }*/
    public T getFlow() {
        return flow;
    }

    public void setFlow(T flow) {
        this.flow = flow;
    }

    public T getCapacity() {
        return capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeProperties<?> that = (EdgeProperties<?>) o;
        return Objects.equals(flow, that.flow) && Objects.equals(capacity, that.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flow, capacity);
    }
}
