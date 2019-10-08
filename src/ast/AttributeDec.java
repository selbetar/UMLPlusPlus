package ast;

import library.AttributeStorage;

public class AttributeDec extends Statement {
    String attributeType;

    String visibility;
    String returnType;
    String attributeName;
    ArrayList<String> attributes = new ArrayList<>();

    String className;
    Boolean end;

    @Override
    public void parse() {
        tokenizer.getAndCheckNext("Add");
        attributeType = tokenizer.getNext();
        tokenizer.getAndCheckNext("[");
        end = false;
        while (!end) {
            visibility = tokenizer.getNext();
            tokenizer.getAndCheckNext(":");
            returnType = tokenizer.getNext();
            tokenizer.getAndCheckNext(":");
            attributeName = tokenizer.getNext();
            attributes.add(visibility + " " + returnType + " " + attributeName + "\n");
            end = tokenizer.getNext();
            if (end == ",") {
                end = false;
            } else if (end == "]") {
                end = true;
            } else {
                // TODO: handle case when not , or ]
            }
        }
        tokenizer.getAndCheckNext("to");
        className = tokenizer.getNext();
    }

    @Override
    public void evaluate() {
        if (attributeType == "field") {
            String field = visibility + " " + returnType + " " + attributeName + "\n";
            fieldMap.get(className).add(field)
        }
        if (attributeType == "method") {
            String method = visibility + " " + returnType + " " + attributeName + "\n";
            fieldMap.get(className).add(method)
        }
    }

    @Override
    public void nameCheck() {
        // TODO: Check if class name is already declared
//        if (!ClassStorage.classes.contains(className)) {
//            throw new ClassNameCheckException(className);
//        }
    }

    @Override
    public void typeCheck() {
        if (attributeType != "field" || attributeType != "method")
            throw new AttributeTypeCheckException(attributeType);
        }
    }
}
