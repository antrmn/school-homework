package evaluator;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Node;
import lexer.Token;
import tools.MutableInt;

import static guru.nidi.graphviz.model.Factory.node;

public class Unary extends Expr {
    private final Expr operand;
    private final Token operator;

    public Unary(Expr operand, Token operator){
        this.operand = operand;
        this.operator = operator;
    }

    @Override
    public double evaluate() {
        if(operator.type.equals("-")){
            return -operand.evaluate();
        }
        if(operator.type.equals("+")){
            return operand.evaluate();
        }
        return 0;
    }

    public String print() {
        return "( " + operator.type + " " + operand.print() + " )";
    }

    @Override
    public Node draw(MutableInt nodeNumber) {
        Node node = node(String.valueOf(nodeNumber)).with(Label.raw(operator.type));

        return node.link(operand.draw(nodeNumber.increment()));
    }
}