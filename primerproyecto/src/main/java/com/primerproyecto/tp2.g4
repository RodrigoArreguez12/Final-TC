grammar tp2;

@header {
package com.primerproyecto;
}

fragment LETRA : [A-Za-z];
fragment DIGITO : [0-9];

// TOKENS 

PA   : '(' ;
PC   : ')' ;
CA   : '[' ;
CC   : ']' ;
LA   : '{' ;
LC   : '}' ;

PYC  : ';' ;
COMA : ',' ;

IGUAL : '=' ;

MAYOR  : '>' ;
MAYOR_IGUAL: '>=';
MENOR  : '<' ;
MENOR_IGUAL: '<=';
EQL  : '==';
DISTINTO  : '!=';

SUM  : '+' ;
RES  : '-' ;
MUL  : '*' ;
DIV  : '/' ;
MOD  : '%' ;

OR   : '||' ;
AND  : '&&' ;
NOT  : '!'  ;

FOR  : 'for';
WHILE: 'while';

IF   : 'if' ;
ELSE : 'else' ;

INT     : 'int' ;
CHAR    : 'char' ;
DOUBLE  : 'double' ;
VOID    : 'void' ;

RETURN : 'return';

ID : (LETRA | '_') (LETRA | DIGITO | '_')*;

INTEGER : DIGITO+; //99 
DECIMAL : INTEGER'.'INTEGER;
CHARACTER: '\''(LETRA|OTRO|DIGITO)'\'';

WS : [ \r\n\t] -> skip ;
OTRO : . ;

//Reglas gramaticales

prog: instrucciones EOF; 

instrucciones : instruccion instrucciones 
              |
              ;

bloque : LA instrucciones LC 
       ;

instruccion : declaracion PYC
            | asignacion PYC
            | bucleFor
            | bucleWhile
            | condif
            | declaracionFuncion PYC
            | definicionFuncion
            | llamadaFuncion PYC
            | retorno PYC
            | bloque
            ;

retorno : RETURN opal;

inicializacion: declaracion | asignacion
                       ;
declaracion : tipoDato ID
            | tipoDato ID asign
            ;

asign : IGUAL operacion
      | SUM SUM
      | RES RES 
      ;

tipoDato : INT
         | CHAR
         | DOUBLE
         | VOID
         ;

asignacion  : ID asign
            ;

bucleFor : FOR PA inicializacion PYC operacion PYC ID asign PC instruccion
         ;

bucleWhile : WHILE PA operacion PC bloque
           ;

condif : IF PA operacion PC bloque
       | IF PA operacion PC bloque ELSE bloque
       ;

declaracionFuncion : tipoDato ID PA parametros PC
          ;

definicionFuncion : tipoDato ID PA parametros PC bloque
        ;

parametros : tipoDato ID param
          |
           ;

param : COMA parametros
      |
      ;

llamadaFuncion : ID PA argumentos PC
               ;

argumentos : operacion args
          ;

args  : COMA operacion args
     |
     ;

operacion : opal ;

opal : conjuncion disy ;

disy : OR conjuncion disy
     |
     ;

conjuncion : comparaciones conj
           ;

conj : AND comparaciones conj
     |
     ;

comparaciones : expresion comp
              ;

comp : opcomp expresion
     |
     ;

opcomp : EQL
       | DISTINTO
       | MAYOR
       | MAYOR_IGUAL
       | MENOR
       | MENOR_IGUAL
       ;

expresion : term exp;

exp : SUM term exp
    | RES term exp
    |
    ;

term : factor t ;

t :   MUL factor t
    | DIV factor t
    | MOD factor t
    |
    ;

factor : PA opal PC
       | llamadaFuncion
       | INTEGER
       | DECIMAL
       | CHARACTER
       | ID
       ;
