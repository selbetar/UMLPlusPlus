package ast;

import java.util.HashMap;

import library.Tokenizer;
import ast.ClassDec;

public interface Node {
    Tokenizer tokenizer = Tokenizer.getTokenizer();
    public static ArrayList<String> classes = new ArrayList<>();

    // Key = Superclass; Value = Subclass;
    public static HashMap<String, String> relationshipImplement = new HashMap<>();
    public static HashMap<String, String> relationshipExtend = new HashMap<>();
    void parse();
    void evaluate();
    void nameCheck();
    void typeCheck();
}
