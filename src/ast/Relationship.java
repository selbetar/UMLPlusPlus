package ast;

import library.AttributeStorage;
import library.UmlBuilder;
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
        String actualSubClass = subClass;
        String actualSuperClass = superClass;

        if (AttributeStorage.getInstance().variableMap.containsKey(subClass)) {
            actualSubClass = AttributeStorage.getInstance().variableMap.get(subClass);
        }

        if (AttributeStorage.getInstance().variableMap.containsKey(superClass)) {
            actualSuperClass = AttributeStorage.getInstance().variableMap.get(superClass);
        }

        if (relType == relationshipType.EXTENDS) {
            frame.drawRelation(actualSubClass, actualSuperClass, relationshipType.EXTENDS);
        } else if (relType == relationshipType.IMPLEMENTS) {
            frame.drawRelation(actualSubClass, actualSuperClass, relationshipType.IMPLEMENTS);
        }
    }

    @Override
    public void nameCheck() {
        AttributeStorage storageInstance = AttributeStorage.getInstance();
        if (!storageInstance.declaredClasses.contains(subClass) &&
                !storageInstance.declaredClasses.contains(superClass) &&
                !storageInstance.variableMap.containsKey(subClass) &&
                !storageInstance.variableMap.containsKey(superClass)) {
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
