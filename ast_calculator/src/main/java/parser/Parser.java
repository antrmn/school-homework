package parser;
import evaluator.*;
import lexer.Token;
import tools.TreeNode;

import java.util.List;


public class Parser {
    private List<Token> tokens;

    private TreeNode<String> root;
    public TreeNode<String> start;

    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private void parseImplicitMultiplication()
    {
        for (int i=0; i<tokens.size()-1; i++){
            if(tokens.get(i).type != null && tokens.get(i).type.equals(")")){
               if((tokens.get(i + 1).type == null) || tokens.get(i + 1).type.matches("(?i)sin|cos|ln|log|sqrt") || tokens.get(i+1).type.equals("(")){
                   tokens.add(++i, new Token("*"));
               }
            } else if (tokens.get(i).type == null && tokens.get(i+1).type.equals("(")){
                tokens.add(++i, new Token("*"));
            }
        }
    }

    public Expr parse() throws IllegalArgumentException{
        parseImplicitMultiplication();

        root = new TreeNode<>("Start");
        start = root;

        return expression();
    }

    private Expr expression() throws IllegalArgumentException{
        TreeNode<String> node = root.addChild("Expression");
        root = node;
        Expr expr = term();

        root = node;

        while (match("+", "-")) {
            Token operator = previous();

            root.addChild(operator.type);

            Expr right = term();

            root = node;
            expr = new Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr term() throws IllegalArgumentException{
        TreeNode<String> node = root.addChild("Term");
        root = node;
        Expr expr = factor();

        root = node;

        while(match("*", "/")){
            Token operator = previous();

            root.addChild(operator.type);

            Expr right = factor();

            root=node;

            expr = new Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr factor() throws IllegalArgumentException{
        TreeNode<String> node = root.addChild("Factor");
        root = node;


        if (match("-", "+")) {
            Token operator = previous();

            root.addChild(operator.type);

            Expr right = factor();

            root = node;

            return new Unary(right, operator);
        }
        return power();
    }

    private Expr power() throws IllegalArgumentException{
        TreeNode<String> node = root.addChild("Power");
        root = node;

        Expr expr = primary();

        root=node;

        while (match("^")) {

            Token operator = previous();

            root.addChild(operator.type);

            Expr right = power();

            root=node;

            expr = new Binary(expr, operator, right);
        }
        return expr;
    }

    private Expr primary() throws IllegalArgumentException {
        TreeNode<String> node = root.addChild("Primary");
        root = node;

        if (peek().isNumber()){
            advance();
            double value = previous().value;

            root.addChild(String.valueOf(value));

            return new Literal(value);
        }

        if(match("sin","cos","sqrt", "log", "ln")){
            Token function = previous();

            node.addChild(function.type);

            Expr expr = primary();
            root= node;

            return new Function(function, expr);
        }

        if(match("(")){
            node.addChild("(");

            Expr expr = expression();
            root=node;
            node.addChild(")");

            consume(")", "Syntax error: missing closing bracket");
            return new Grouping(expr);
        }
        throw new IllegalArgumentException("Syntax error");

    }



    private boolean match(String... tokens) { //controlla che il token corrente sia uguale a uno degli specificati, poi avanza se true
        for (String token : tokens) {
            if (check(token)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private Token consume(String token, String message) throws IllegalArgumentException { //come match, ma in caso sia false dà errore
        if (check(token)) return advance();

        throw new IllegalArgumentException(message);
    }

    private boolean check(String token) { //controlla che il tipo del token corrente sia quello specificato
        if (isAtEnd()) return false;
        return peek().type != null && peek().type.equals(token);
    }

    private Token advance() { //avanza nella lista e stampa il precedente
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type != null && peek().type.equals("EOF");
    }

    private Token peek() { //ottieni corrente
        return tokens.get(current);
    }

    private Token previous() {  //ottieni precedente
        return tokens.get(current - 1);
    }
}    