package ast;

import library.UmlBuilder;
import library.exceptions.InvalidGrammarException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassDec extends Statement {

    private Map<String, ArrayList> attributeMap;
    private Map<String, ArrayList> methodMap;
    private classType ctype;
    private String className;
    private String classAlias;

    public ClassDec() {
        this.attributeMap = new HashMap<String, ArrayList>();
        this.methodMap = new HashMap<String, ArrayList>();
    }

    @Override
    public void parse() {
        if (tokenizer.checkToken("Declare")) {
            tokenizer.getNext();
            if (tokenizer.checkToken("Interface")) {
                ctype = classType.INTERFACE;
            } else if (tokenizer.checkToken("Class")) {
                ctype = classType.CLASS;
            } else if (tokenizer.checkToken("Abstract")) {
                tokenizer.getNext();
                if (tokenizer.checkToken("Class")) {
                    ctype = classType.ABSTRACT_CLASS;
                }
            }
            if (tokenizer.moreTokens()) {
                className = tokenizer.getNext();
                if (tokenizer.moreTokens()) {
                    tokenizer.getNext();
                    classAlias = tokenizer.getNext();
                }
            }
        } else {
            throw new InvalidGrammarException();
        }
    }

    @Override
    public void evaluate() {
        UmlBuilder frame = UmlBuilder.getInstance();
        if (!classAlias.isEmpty()) {
            className = className + "/" + classAlias;
        }
        frame.drawClass(ctype, className);
    }

    @Override
    public void nameCheck() {
        if (ctype == null) {
            throw new UnspecifiedClassTypeException();
        }
        // TODO check if name is in list of keywords
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
