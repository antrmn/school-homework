package evaluator;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Node;
import tools.MutableInt;

import static guru.nidi.graphviz.model.Factory.node;

public class Literal extends Expr {
    private final Double value;

    public Literal(Double value){ this.value = value; }

    @Override
    public double evaluate() {
        return this.value;
    }

    public String print() {
        return "( " + value + " )";
    }

    @Override
    public Node draw(MutableInt nodeNumber) {
        return node(String.valueOf(nodeNumber.getValue())).with(Label.raw(value.toString()));
    }
}
