package evaluator;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Node;
import lexer.Token;
import tools.MutableInt;

import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;

public class Binary extends Expr {
    private final Expr left;
    private final Token operator;
    private final Expr right;

    public Binary(Expr left, Token operator, Expr right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public double evaluate() {
        if(operator.type.equals("+")){
            return left.evaluate() + right.evaluate();
        }
        if(operator.type.equals("-")){
            return left.evaluate() - right.evaluate();
        }
        if(operator.type.equals("*")){
            return left.evaluate() * right.evaluate();
        }
        if(operator.type.equals("/")){
            return left.evaluate() / right.evaluate();
        }
        if(operator.type.equals("^")){
            return Math.pow(left.evaluate(), right.evaluate());
        }
        return 0;
    }

    @Override
    public String print() {
        return "( " + left.print() + " " + operator.type + " " + right.print() + " )";
    }

    @Override
    public Node draw(MutableInt nodeNumber) {
        Node node = node(String.valueOf(nodeNumber.getValue())).with(Label.raw(operator.type));

        return node.link(
                    to(left.draw(nodeNumber.increment())),
                    to(right.draw(nodeNumber.increment()))
        );
    }
}
