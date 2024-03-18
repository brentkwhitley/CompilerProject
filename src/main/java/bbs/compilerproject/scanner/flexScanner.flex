// **** User Code **** //

package bbs.compilerproject.scanner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static bbs.compilerproject.scanner.CMinusScanner.StateType.*;
import bbs.compilerproject.scanner.Token.TokenType;
import static bbs.compilerproject.scanner.Token.TokenType.*;

%%

// **** Options and declerations **** //

%class flexScanner
%implements Scanner
%unicode
%line
%column
%state INCOMMENT

%{
  private Token nextToken;

  @Override
  public Token viewNextToken() {
    return nextToken;
  }
%}

LETTER=[A-Za-z]
DIGIT=[0-9]
NUM={DIGIT}+
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\012]
NEWLINE=[\r|\n|\r\n]
IDENTIFIER = {LETTER}+
WHITE_SPACE_CHAR=[\n\r\ \t\b\012]

%%

// **** Lexical Rules **** //

<YYINITIAL> {
  // ignore whitespace
  {NONNEWLINE_WHITE_SPACE_CHAR}+ { }

  // keywords
  "else" { return new Token(ELSE_TOKEN); }
  "if" { return new Token(IF_TOKEN); }
  "int" { return new Token(INT_TOKEN); }
  "return" { return new Token(RETURN_TOKEN); }
  "void" { return new Token(VOID_TOKEN); }
  "while" { return new Token(WHILE_TOKEN); }

  //ERROR TOKEN
  ({DIGIT}+{LETTER}+)|({LETTER}+{DIGIT}+) { return (new Token(ERROR_TOKEN,yytext())); }

  // NUMBER
  {NUM} { return (new Token(NUMBER_TOKEN,yytext())); }

  // IDENTIFIER
  {IDENTIFIER} { return (new Token(IDENT_TOKEN,yytext())); }

  // SYMBOL
  "/*" { yybegin(INCOMMENT); }
  "+" { return (new Token(PLUS_TOKEN,yytext())); }
  "-" { return (new Token(MINUS_TOKEN,yytext())); }
  "*" { return (new Token(MUL_TOKEN,yytext())); }
  "/" { return (new Token(SLASH_TOKEN,yytext())); }
  "<"  { return (new Token(LT_TOKEN,yytext())); }
  "<=" { return (new Token(LTEQ_TOKEN,yytext())); }
  ">"  { return (new Token(GT_TOKEN,yytext())); }
  ">=" { return (new Token(GTEQ_TOKEN,yytext())); }
  "==" { return (new Token(EQTO_TOKEN,yytext())); }
  "!=" { return (new Token(NOTEQ_TOKEN,yytext())); }
  "=" { return (new Token(ASSIGN_TOKEN,yytext())); }
  ";" { return (new Token(SEMICOLON_TOKEN,yytext())); }
  "," { return (new Token(COMMA_TOKEN,yytext())); }
  "(" { return (new Token(LPAREN_TOKEN,yytext())); }
  ")" { return (new Token(RPAREN_TOKEN,yytext())); }
  "[" { return (new Token(LBRACE_TOKEN,yytext())); }
  "]" { return (new Token(RBRACE_TOKEN,yytext())); }
  "{" { return (new Token(LBRACKET_TOKEN,yytext())); }
  "}" { return (new Token(RBRACKET_TOKEN,yytext())); }

  {NEWLINE} { }

}

<INCOMMENT> {
    "*/" { yybegin(YYINITIAL); }
    <<EOF>> { yybegin(YYINITIAL); return new Token(ERROR_TOKEN, yytext()); }
    [^] { /* ignore */ }
}

<<EOF>> {return (new Token(EOF_TOKEN,yytext())); }

// ERROR / other
  .  { return new Token(ERROR_TOKEN, yytext()); }