package ast;

import library.AttributeStorage;
import library.exceptions.InvalidGrammarException;

public class ClassDec extends Statement {

    private ClassType cType;
    private String className;
    private String classAlias;


    @Override
    public void parse() {
        tokenizer.getAndCheckNext("Declare");
        if (tokenizer.checkToken("Interface")) {
            cType = ClassType.INTERFACE;
        } else if (tokenizer.checkToken("Class")) {
            cType = ClassType.CLASS;
        } else if (tokenizer.checkToken("Abstract")) {
            tokenizer.getNext();
            if (tokenizer.checkToken("Class")) {
                cType = ClassType.ABSTRACT_CLASS;
            }
        } else {
            throw new InvalidGrammarException();
        }
        tokenizer.getNext();
        className = tokenizer.getNext();
        AttributeStorage.getInstance().declaredClasses.add(className);

        if (tokenizer.checkToken("As")) {
            tokenizer.getNext();
            classAlias = tokenizer.getNext();
            AttributeStorage.getInstance().variableMap.put(classAlias, className);
        }
    }

    @Override
    public void evaluate() {
        frame.drawClass(cType, className);
    }

    @Override
    public void nameCheck() {

    }

    @Override
    public void typeCheck() {

    }

    public enum ClassType {
        CLASS,
        INTERFACE,
        ABSTRACT_CLASS
    }
}
