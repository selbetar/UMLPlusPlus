package library.exceptions;

public class NameCheckException extends RuntimeException {
    public ClassNameCheckException(String className){
        System.out.println("Name check fail! Attribute cannot be added to null class\n");
        System.out.println("You forgot to declare class " +className);
    }
}