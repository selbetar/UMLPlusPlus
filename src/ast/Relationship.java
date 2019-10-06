package ast;

import library.exceptions.NameCheckException;

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
        if (relationshipType == relationshipType.EXTENDS) {
            Node.relationshipExtend.put(superClass, subClass);
        } else if (relationshipType == relationshipType.IMPLEMENTS) {
            Node.relationshipImplement.put(superClass, subClass);
        }
    }

    @Override
    public void nameCheck() {
        if (!Node.classes.contains(subClass)) {
            throw new NameCheckException(subClass);
        }

        if (!Node.classes.contains(superClass)) {
            throw new NameCheckException(superClass);
        }
    }

    @Override
    public void typeCheck() {

    }

    public enum relationshipType {
        EXTENDS,
        IMPLEMENTS
    }
}
