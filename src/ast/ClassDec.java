package ast;

import library.AttributeStorage;
import library.exceptions.InvalidGrammarException;

import java.util.ArrayList;

public class ClassDec extends Statement {

    private ClassType cType;
    private String classAlias;
    private ArrayList<String> decList = new ArrayList<>();


    @Override
    public void parse() {
        tokenizer.getAndCheckNext("Declare");
        if (tokenizer.checkToken("Interface") || tokenizer.checkToken("Class")) {
        String type = tokenizer.getNext();
        cType = getClassType(type);
        String className = tokenizer.getNext();
        parseAlias((className));
        decList.add(className);
        parseClasses();
        } else if (tokenizer.checkToken("Abstract") && tokenizer.checkNextToken("Class")){
            tokenizer.getNext();
            tokenizer.getNext();
            String className = tokenizer.getNext();
            cType = getClassType("Abstract");
            parseAlias((className));
            decList.add(className);
            parseClasses();
        } else {
            throw new InvalidGrammarException();
        }
        AttributeStorage.getInstance().declaredClasses.addAll(decList);
    }

    private void parseClasses() {
        String className = "";
        while (tokenizer.checkToken("\\,")) {
            tokenizer.getNext();
            className = tokenizer.getNext();
            decList.add(className);
            parseAlias((className));
        }
    }
    private void parseAlias(String className) {
        if (tokenizer.checkToken("As")) {
            tokenizer.getNext();
            classAlias = tokenizer.getNext();
            AttributeStorage.getInstance().variableMap.put(classAlias, className);
        }
    }
    private ClassType getClassType(String type){
        if (type.equals("Class")) return ClassType.CLASS;
        if (type.equals("Interface")) return ClassType.INTERFACE;
        return ClassType.ABSTRACT_CLASS;
    }

    @Override
    public void evaluate() {
        for (String className : decList){
            frame.drawClass(cType, className);
        }
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
        ABSTRACT_CLASS;

        @Override
        public String toString() {
            switch (this) {
                case CLASS: return "Class";
                case INTERFACE: return "Interface";
                case ABSTRACT_CLASS: return "Abstract Class";
                default: throw new IllegalArgumentException();
            }
        }
    }

}
