grammar Javamm;

@header {
    package pt.up.fe.comp2023;
}

INT : [0] | [1-9][0-9]* ;

ID : [a-zA-Z_$][a-zA-Z_0-9$]* ;

WS : [ \t\n\r\f]+ -> skip ;

COMMENT : '/*' .*? '*/' -> skip;

LINECOMMENT : '//' ~[\r\n]* -> skip;

program
    : (importDeclaration)* classDeclaration EOF
    ;

importDeclaration
    : 'import' value=ID (importAppend)* ';'
    ;

importAppend
    : '.' value1=ID
    ;

classDeclaration
    :  'class' value=ID (extendClass)? '{' ( varDeclaration )* ( methodDeclaration )* '}'
    ;

extendClass
    : 'extends' value=ID
    ;

varDeclaration
    : type value=ID ';'
    ;

methodDeclaration
    : ('public')? 'static' 'void' 'main' '(' 'String' '[' ']' value=ID ')' '{' (varDeclaration)* (statement)* '}' #mainMethodDeclaration
    | ('public')? type name=ID '(' ( parameter )? ')' '{'  (varDeclaration)* (statement)* 'return' expression ';' '}' #otherMethodDeclaration
    ;

parameter
    : type name=ID (parameterAppend)*
    ;

parameterAppend
    : ',' type name=ID
    ;


type
    : 'int' '[' ']' #typeArray
    | 'boolean' #typeBoolean
    | 'int' #typeInt
    | 'char' #typeChar
    | 'String' #typeString
    | 'void' #typeVoid
    | value=ID #typeObject
    ;

statement
    : '{' ( statement )* '}' #bracketStatement
    | 'if' '(' expression ')' statement ('else' statement)? #ifStatement
    | 'while' '(' expression ')' statement #whileStatement
    | 'for' '(' (varDeclaration | statement | expression)? ';' expression? ';' (statement | expression)? ')' statement #forStatement
    | expression ';' #expressionStatement
    | value=ID '=' expression ';' #assignment
    | value=ID '+=' expression ';' #incAssignment
    | value=ID '[' expression ']' '=' expression ';' #arrayAssignment
    ;


expression
    : '(' expression ')' #parentheses
    | expression '[' expression ']' #arrayAccess
    | expression '.' value=ID '(' ( expression ( ',' expression )* )? ')' #methodCall
    | expression '.' 'length'   #length
    | 'new' 'int' '[' expression ']' #arrayCreation
    | 'new' value = ID '(' ')' #objectCreation
    | expression op=('*' | '/' ) expression #binaryOp
    | expression op=('+' | '-' ) expression #binaryOp
    | expression op=( '<' | '>' | '<=' | '>=' ) expression #relationalOp
    | expression '&&' expression #logicalAnd
    | expression '||' expression #logicalOr
    | expression op=('++' | '--' ) #unaryIncDec
    | '!' expression #unaryNot
    | value=INT #integer
    | 'true' #true
    | 'false' #false
    | value=ID #identifier
    | 'this' #this
    ;