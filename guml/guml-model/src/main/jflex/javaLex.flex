package dev.danigpaz.parser.programmingLanguageParsers.java;

import java_cup.runtime.*;
import dev.danigpaz.parser.ProjectTreeAdapter;
import dev.danigpaz.parser.ProgrammingLanguageLexer;


%%

%public
%class JavaLexer
%implements ProgrammingLanguageLexer
%cup

annotation = @[A-Za-z_][A-Za-z_0-9]*(\s*\([^\)]*\))?
baseClassName = [a-zA-Z0-9\<\>\$\_]+[\[\]]*
className = ({baseClassName}\.)*({baseClassName}|[\*])
attValue  = (\s*=\s*[^;]+)?
inLineComment = \/\/[^\n]*
multiLineComment = \/\*([^*]|\*[^/])*\*\/
space = [" "\t]+

%{
    StringBuffer string = new StringBuffer();
        JavaProjectTreeAdapter treeBuilder;

        private Symbol symbol(int type) {
            return new Symbol(type, yyline, yycolumn);
        }

        private Symbol symbol(int type, Object value) {
            return new Symbol(type, yyline, yycolumn, value);
        }

        public ProjectTreeAdapter getTreeBuilder(){
            return treeBuilder;
        }

        public void setTreeBuilder(ProjectTreeAdapter ptBuilder){
            treeBuilder = (JavaProjectTreeAdapter) ptBuilder;
        }
%}

%eofval{
    return symbol(JavaParserSym.EOF);
%eofval}

%state METHODBODY
%state ENUMINSTANCE

%%

{inLineComment}      { /* ignore */ }
{multiLineComment}   { /* ignore */ }

<YYINITIAL>{
"public"        { return symbol(JavaParserSym.PUBLIC);      }
"private"       { return symbol(JavaParserSym.PRIVATE);     }
"protected"     { return symbol(JavaParserSym.PROTECTED);   }
"abstract"      { return symbol(JavaParserSym.ABSTRACT);    }
"static"        { return symbol(JavaParserSym.STATIC);      }
"final"         { return symbol(JavaParserSym.FINAL);       }
"transient"     { return symbol(JavaParserSym.TRANSIENT);   }
"volatile"      { return symbol(JavaParserSym.VOLATILE);    }
"synchronized"  { return symbol(JavaParserSym.SYNCHRONIZED);}
"native"        { return symbol(JavaParserSym.NATIVE);      }
"extends"       { return symbol(JavaParserSym.EXTENDS);     }
"implements"    { return symbol(JavaParserSym.IMPLEMENTS);  }

"class"         { return symbol(JavaParserSym.CLASS);       }
"interface"     { return symbol(JavaParserSym.INTERFACE);   }
"enum"          { return symbol(JavaParserSym.ENUM);        }

"void"          { return symbol(JavaParserSym.TYPE, yytext()); }
"boolean"       { return symbol(JavaParserSym.TYPE, yytext()); }
"byte"          { return symbol(JavaParserSym.TYPE, yytext()); }
"char"          { return symbol(JavaParserSym.TYPE, yytext()); }
"short"         { return symbol(JavaParserSym.TYPE, yytext()); }
"int"           { return symbol(JavaParserSym.TYPE, yytext()); }
"long"          { return symbol(JavaParserSym.TYPE, yytext()); }
"float"         { return symbol(JavaParserSym.TYPE, yytext()); }
"double"        { return symbol(JavaParserSym.TYPE, yytext()); }
"String"        { return symbol(JavaParserSym.TYPE, yytext()); }

{annotation}    { return symbol(JavaParserSym.ANNOTATION); }
"package"{space}
    {className} { return symbol(JavaParserSym.PACKAGE, yytext()); }
"import"{space}
    {className} { return symbol(JavaParserSym.IMPORT, yytext()); }
"import"{space}"static"{space}
    {className} { return symbol(JavaParserSym.IMPORT, yytext()); }
"throws"{space}
    ({className}", "?)+ { /* ignore */ }
}
<YYINITIAL>"{"  {   if(treeBuilder.bracketCount > 0)
                        yybegin(METHODBODY);
                    return symbol(JavaParserSym.LBRACE);
                }
<YYINITIAL>"}"  {   return symbol(JavaParserSym.RBRACE);  }
<METHODBODY>"}" {   treeBuilder.bracketCount--;
                    if (treeBuilder.bracketCount == 0) {
                        yybegin(YYINITIAL);
                        return symbol(JavaParserSym.RBRACE);
                    }
                }
<METHODBODY>"{" {    treeBuilder.bracketCount++;   }
<METHODBODY>[^\{\}]+ { /* ignore */ }

<YYINITIAL>"("    { if(treeBuilder.inEnum) {
                      yybegin(ENUMINSTANCE);
                      treeBuilder.parenthesisCount++;
                    }
                    return symbol(JavaParserSym.LPAREN);  }
<YYINITIAL>")"    { return symbol(JavaParserSym.RPAREN);  }
<ENUMINSTANCE>")" { treeBuilder.parenthesisCount--;
                    if (treeBuilder.parenthesisCount == 0) {
                        yybegin(YYINITIAL);
                        return symbol(JavaParserSym.RPAREN);
                    }
                  }
<ENUMINSTANCE>"(" { treeBuilder.parenthesisCount++; }
<ENUMINSTANCE>[^\(\)]+ {}

"["             { return symbol(JavaParserSym.LBRACKET);  }
"]"             { return symbol(JavaParserSym.RBRACKET);  }
";"             { return symbol(JavaParserSym.SEMICOLON); }
","             { return symbol(JavaParserSym.COMMA);     }

{className}     { return symbol(JavaParserSym.CLASSNAME, yytext()); }
{attValue}      {  return symbol(JavaParserSym.ATTVALUE, yytext()); }
{space}         { /* ignore */ }
\n              { yyline++; yycolumn = 0; }

[^]             { throw new Error("Illegal character <"+yytext()+">"); }
