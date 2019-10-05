package ast;

import library.Tokenizer;

public interface Node {
    Tokenizer tokenizer = Tokenizer.getTokenizer();
    void parse();
    void evaluate();
    void nameCheck();
    void typeCheck();
}
