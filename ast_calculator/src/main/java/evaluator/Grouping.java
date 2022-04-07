package evaluator;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Node;
import tools.MutableInt;

import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;

public class Grouping extends Expr {
    private final Expr branch;

    public Grouping(Expr branch) {this.branch = branch;}


    @Override
    public double evaluate() {
        return branch.evaluate();
    }

    public String print() {
        return "( [" + branch.print() + "] )";
    }

    @Override
    public Node draw(MutableInt nodeNumber) {
        Node node = node(String.valueOf(nodeNumber.getValue())).with(Label.raw("()"));

        return node.link(
                to(branch.draw(nodeNumber.increment()))
        );
    }
}
