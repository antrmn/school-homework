package evaluator;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Node;
import lexer.Token;
import tools.MutableInt;

import java.util.concurrent.atomic.AtomicInteger;

import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;

public class Function extends Expr {
    private final Token function;
    private final Expr argument;

    public Function(Token function, Expr argument){ this.function = function; this.argument = argument; }

    @Override
    public double evaluate() {
        if(function.type.equals("sin")){
            return Math.sin(argument.evaluate());
        }
        if(function.type.equals("cos")){
            return Math.cos(argument.evaluate());
        }
        if(function.type.equals("sqrt")){
            return Math.sqrt(argument.evaluate());
        }
        if(function.type.equals("log")){
            return Math.log10(argument.evaluate());
        }
        if(function.type.equals("ln")){
            return Math.log(argument.evaluate());
        }
        return 0;
    }

    public String print() {
        return "( " + function.type + " " + argument.print() + " )";
    }

    @Override
    public Node draw(MutableInt nodeNumber) {
        Node node = node(String.valueOf(nodeNumber.getValue())).with(Label.raw(function.type));

        return node.link(
                to(argument.draw(nodeNumber.increment()))
        );
    }
}
