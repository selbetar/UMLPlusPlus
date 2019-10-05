package ast;

public interface Node {
    void parse();
    void evaluate();
    void nameCheck();
    void typeCheck();
}
