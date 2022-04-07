package evaluator;

import tools.MutableInt;


import guru.nidi.graphviz.model.Node;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Expr {
    abstract public double evaluate();
    abstract public String print();
    abstract public Node draw(MutableInt nodeNumber);
}