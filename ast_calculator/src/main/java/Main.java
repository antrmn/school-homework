import evaluator.Expr;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import lexer.Lexer;
import parser.Parser;
import tools.MutableInt;
import tools.ParseTreeDrawer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.node;

public class Main {
    private static boolean first = true;

    private static JFrame frame_parse;
    private static JFrame frame_expression;

    private static Frame parse;
    private static Frame expr;

    private static File treeFile = new File("tmp/tree.png");
    private static File parseTreeFile = new File("tmp/parsetree.png");

    private static BufferedImage picExpr;
    private static BufferedImage picParse;

    public static void main(String[] args) throws IOException {
        PrintStream originalStream = System.out;
        PrintStream dummyStream = new PrintStream(new OutputStream(){
            public void write(int b) {
                // Per evitare che graphviz Api intasi la CLI
            }
        });

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Valid operator: [+ | - | * | / ]");
        System.out.println("Valid functions: [sin() | cos() | sqrt() | ln() | log()]");

        do {
            try {
                System.out.print(">> ");
                String expression = br.readLine();
                Parser parser = new Parser(Lexer.tokenize(expression));
                Expr expr = parser.parse();

                System.setOut(dummyStream);
                MutableGraph g = mutGraph("ParseTree").setDirected(true).add(ParseTreeDrawer.generateTree(parser.start, new MutableInt(0)));
                MutableGraph e = mutGraph("ExpressionTree").setDirected(true).add(expr.draw(new MutableInt(0)));
                g.graphAttrs().add().nodes().forEach(node -> node.add(Shape.NONE));

                Graphviz.fromGraph(e).width(300).render(Format.PNG).toFile(treeFile);
                Graphviz.fromGraph(g).width(250).render(Format.PNG).toFile(parseTreeFile);

                System.setOut(originalStream);
                System.out.println(expr.evaluate());
                processImage();
            }catch (RuntimeException e){
                System.err.println(e.getMessage());
            }
        } while (true);
    }

    private static void processImage() throws IOException {
        picExpr = ImageIO.read(treeFile);
        picParse = ImageIO.read(parseTreeFile);

        if(first) {
            frame_parse = new JFrame("Parse tree");
            frame_expression = new JFrame("Expression tree");

            parse = new Frame(frame_parse, picParse, parseTreeFile, Frame.Position.UPPER_RIGHT);
            expr = new Frame(frame_expression, picExpr, treeFile, Frame.Position.UPPER_LEFT);

            new Thread(parse).start();
            new Thread(expr).start();

            first = false;
        } else {
            parse.refresh();
            expr.refresh();
        }
    }
}