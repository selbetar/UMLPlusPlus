# UML++
A simple domain specific language with easy syntax that generates uml class diagrams from a few line of codes.

## Grammar

```
PROGRAM::= STATEMENT*
STATEMENT::=  CLASSDEC | ATTRIBUTEDEC | RELATIONSHIP
CLASSDEC::= “Declare” CTYPE CLASS [","CLASS]*
CLASS::= CLASSNAME ALIAS?
ATTRIBUTEDEC::=”Add“ ATYPE "[" ATTRIBUTES ","? "]" “To” NAME
ATTRIBUTE::= VISIBILITY ":" RETTYPE ":" NAME
RELATIONSHIP::= RELTYPE NAME “>” NAME
ALIAS::=  “As” VARNAME
CTYPE::= [“Interface” || “Class” || “Abstract Class”]
RELTYPE::= [“Implements” || “Extends”]
VISIBILITY::= ["+" || "-" || "#"]
ATYPE::= [“Fields” || “Methods”]
NAME::= CLASSNAME | VARNAME
RETTYPE::= STRING
ATTNAME::= STRING
CLASSNAME::= STRING
```

## Sample Program
```
Declare Class Zoo
Add Fields [+:List<String>:Shops, +:String:Name] To Zoo
Declare Interface Animals
Declare Class Fish, Zebra, Guest, Employee
Declare Class Manager
Extends Manager > Employee
Implements Fish > Animals
Implement Zebra > Animals
```
