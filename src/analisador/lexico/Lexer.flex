package analisador.lexico;
import static analisador.lexico.Token.*;
%%
%class Lexer
%type Token

%line
%column

B = [\n| |\t|\r]
ID = [_|a-z|A-Z][_|a-z|A-Z|0-9]*
NUM = [0-9]

%{
public String lexema;
public int linha;
public int coluna;
%}

%%
{B} {} /* Ignora brancos */
"//".* {} /* Ignora comentários */
"{"[^}]*\}+ {} /* Ignora comentários */
"{"[^}]* {lexema = ""; linha = yyline + 1; coluna = yycolumn + 1; return COMENTARIO_NAO_FECHADO;} /* Ignora comentários */

"read" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_READ;}
"write" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_WRITE;}

"div" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_DIV;}
"and" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_AND;}
"not" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_NOT;}
"or" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_OR;}

"<>" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_DIF;}
"<" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_MENOR;}
">" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_MAIOR;}
">=" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_MAIOR_IGUAL;}
"<=" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_MENOR_IGUAL;}
"==" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_IGUAL;}

"if" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_IF;}
"else" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_ELSE;}
"then" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_THEN;}
"while" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_WHILE;}
"do" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_DO;}

"program" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_PROGRAM;}
"begin" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_BEGIN;}
"end" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_END;}
"." {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_FIM_PROGRAMA;}

"procedure" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_PROCEDURE;}
"var" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_VAR;}
"int" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_INT;}
"boolean" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_BOOLEAN;}
"true" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_TRUE;}
"false" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return RESERVADO_FALSE;}

":=" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return SINAL_ATRIB;} /* Retorna o valor sinal atrib. */
"+" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return SINAL_SOMA;} 
"-" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return SINAL_SUB;}
"/" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return SINAL_DIV;}
"*" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return SINAL_MULT;}
"(" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return ABRE_PARENT;}
")" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return FECHA_PARENT;}
";" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return SINAL_PONTO_VIRGULA;}
"," {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return SINAL_VIRGULA;}
":" {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return ATRIB_VARIAVEL;}

[0-9]{1,10} {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return NUM_INT;}
[0-9]{10,100} {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return ERRO_INT_LONGO;}

[_|a-z|A-Z][_|a-z|A-Z|0-9]{0,9} {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return IDENTIFICADOR;}
[_|a-z|A-Z][_|a-z|A-Z|0-9]{10,1000} {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return ERRO_ID_LONGO;}
. {lexema = yytext(); linha = yyline + 1; coluna = yycolumn + 1; return SIMB_DESCONHECIDO;}
 