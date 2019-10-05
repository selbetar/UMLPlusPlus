package ast;

public class Relationship extends Statement {
    private String subClass;
    private String superClass;
    private relationshipType relType;

    @Override

    public void parse() {
        relType = tokenizer.checkToken("Extends") ? relationshipType.EXTENDS : relationshipType.IMPLEMENTS;
        tokenizer.getNext();
        subClass = tokenizer.getNext();
        tokenizer.getAndCheckNext(">");
        superClass = tokenizer.getNext();
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

    public enum relationshipType {
        EXTENDS,
        IMPLEMENTS
    }
}
