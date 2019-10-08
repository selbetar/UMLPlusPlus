package ast;

import library.UmlBuilder;
import library.AttributeStorage;
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
        UmlBuilder frame = UmlBuilder.getInstance();
        if (relationshipType == relationshipType.EXTENDS) {
            frame.drawRelation(subClass, superClass, relationshipType.EXTENDS);
        } else if (relationshipType == relationshipType.IMPLEMENTS) {
            frame.drawRelation(subClass, superClass, relationshipType.IMPLEMENTS);
        }
    }

    @Override
    public void nameCheck() {
        boolean subClassExists = false;
        boolean superClassExists = false;
        AttributeStorage storageInstance = AttributeStorage.getInstance();
        for (String key : storageInstance.methodMap.keySet()) {
            if (key == subClass) { 
                subClassExists = true;
            }

            if (key == superClass) {
                superClassExists = true;
            }
        }

        if (!subClassExists) {
            throw new NameCheckException(subClass);
        }

        if (!superClassExists) {
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
