package lexer;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public static List<Token> tokenize(String s) throws IOException, IllegalArgumentException {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(s));
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');
        List<Token> tokBuf = new ArrayList<>();
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            switch(tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    tokBuf.add(new Token(tokenizer.nval));
                    break;
                case StreamTokenizer.TT_WORD:
                    if(!tokenizer.sval.matches("(?i)sin|cos|ln|log|sqrt"))
                        throw new IllegalArgumentException("Lexical error: \"" + tokenizer.sval + "\" is not valid" );
                    tokBuf.add(new Token(tokenizer.sval));
                    break;
                default:
                    switch((char)tokenizer.ttype){
                        case '+':
                        case '-':
                        case '/':
                        case '^':
                        case '*':
                        case '(':
                        case ')':
                            tokBuf.add(new Token(String.valueOf((char) tokenizer.ttype)));
                            break;
                        default:
                            throw new IllegalArgumentException("Lexical error: invalid operator \"" + (char) tokenizer.ttype + "\"" );
                    }
            }
        }
        tokBuf.add(new Token("EOF"));
        return tokBuf;
    }
}