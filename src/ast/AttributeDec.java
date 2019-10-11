package ast;

import library.AttributeStorage;
import java.util.ArrayList;

public class AttributeDec extends Statement {
    private AttributeType attributeType;

    private String visibility;
    private String returnType;
    private String attributeName;
    private ArrayList<String> attributes = new ArrayList<>();

    private String className;


    @Override
    public void parse() {
        tokenizer.getAndCheckNext("Add");
        attributeType = tokenizer.checkToken("Fields") ? AttributeType.FIELDS : AttributeType.METHODS;
        tokenizer.getNext();
        tokenizer.getAndCheckNext("\\[");

        while (!tokenizer.checkToken("\\]")) {
            visibility = tokenizer.getNext();
            tokenizer.getAndCheckNext(":");
            returnType = tokenizer.getNext();
            tokenizer.getAndCheckNext(":");
            attributeName = tokenizer.getNext();

            attributes.add(visibility + " " + returnType + ": " + attributeName + "\n");

            if (tokenizer.checkToken(",")) {
                tokenizer.getNext();
            }
        }
        tokenizer.getAndCheckNext("\\]");
        tokenizer.getAndCheckNext("To");
        className = tokenizer.getNext();
    }

    @Override
    public void evaluate() {
        String actualClassName = className;
        if (AttributeStorage.getInstance().variableMap.containsKey(className)) {
            actualClassName = AttributeStorage.getInstance().variableMap.get(className);
        }
        if (attributeType == AttributeType.FIELDS) {
            frame.addField(actualClassName, String.join("", attributes));
        } else {
            frame.addMethod(actualClassName, String.join("", attributes));
        }
    }

    @Override
    public void nameCheck() {

    }

    @Override
    public void typeCheck() {
    }

    public enum AttributeType {
        FIELDS,
        METHODS
    }
}
