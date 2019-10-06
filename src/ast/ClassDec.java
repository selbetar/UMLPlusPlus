package ast;

public class ClassDec extends Statement {
    @Override
    public void parse() {

    }

    @Override
    public void evaluate() {

    }

    @Override
    public void nameCheck() {

    }

    @Override
    public void typeCheck() {

    }

    public enum classType {
        CLASS,
        INTERFACE,
        ABSTRACT_CLASS
    }
}
