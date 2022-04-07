package tools;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Node;

import static guru.nidi.graphviz.model.Factory.node;

public class ParseTreeDrawer {
    public static Node generateTree(TreeNode<String> node, MutableInt i){
        Node n = node(String.valueOf(i.increment().getValue())).with(Label.raw(node.data));
        for(TreeNode<String> child : node.children){
            n = n.link(generateTree(child, i));
        }
        return n;
    }
}
