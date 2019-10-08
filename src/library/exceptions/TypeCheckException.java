package library.exceptions;

public class TypeCheckException extends RuntimeException {
    public AttributeTypeCheckException(String attribute) {
        System.out.print("Type check fail! Not a field or a method!\n");
        System.out.print("You're trying to add a/an " +attribute);
    }
}