import java.io.*;
import java.util.*;

public class LexerF {

    public static int line = 1;
    private char peek = ' ';

    Hashtable < String, Word > words = new Hashtable < String, Word > ();
    void reserve(Word w) {
        words.put(w.lexeme, w);
    }
    public LexerF() {
        reserve(new Word(Tag.VAR, "var"));
        reserve(new Word(Tag.PRINT, "print"));
        reserve(new Word(Tag.INTEGER, "integer"));
        reserve(new Word(Tag.BOOLEAN, "boolean"));
        reserve(new Word(Tag.TRUE, "true"));
        reserve(new Word(Tag.FALSE, "false"));
        reserve(new Word(Tag.NOT, "not"));
        reserve(new Word(Tag.IF, "if"));
        reserve(new Word(Tag.THEN, "then"));
        reserve(new Word(Tag.ELSE, "else"));
        reserve(new Word(Tag.WHILE, "while"));
        reserve(new Word(Tag.DO, "do"));
        reserve(new Word(Tag.BEGIN, "begin"));
        reserve(new Word(Tag.END, "end"));
        // ... gestire le altre parole chiave ... //

    }

    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) - 1;
            //ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n' || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
        }

        switch (peek) {
            case ',':
                peek = ' ';
                return Token.comma;

            case ';':
                peek = ' ';
                return Token.semicolon;

            case '+':
                peek = ' ';
                return Token.plus;

            case '(':
                peek = ' ';
                return Token.lpar;

            case ')':
                peek = ' ';
                return Token.rpar;

            case '-':
                peek = ' ';
                return Token.minus;

            case '*':
                peek = ' ';
                return Token.mult;

            case '/':
                peek = ' ';
                return Token.div;

            case ':':
                readch(br);
                if (peek != '=') return Token.colon;
                else {
                    peek = ' ';
                    return Word.assign;
                }

            case '<':
                readch(br);
                if (peek != '>' && peek != '=') return Token.lt;
                else if (peek == '>') {
                    peek = ' ';
                    return Word.ne;
                } else if (peek == '=') {
                    peek = ' ';
                    return Word.le;
                }

            case '>':
                readch(br);
                if (peek != '=') return Token.gt;
                else {
                    peek = ' ';
                    return Word.ge;
                }
                // ... gestire gli altri casi ... //

            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character" + " after & : " + peek);
                    return null;
                }

            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character" + " after | : " + peek);
                    return null;
                }

            case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
                } else {
                    System.err.println("Erroneous character" + " after = : " + peek);
                    return null;
                }

            case '_':
                String s = "";
                readch(br);

                s += peek;

                while (peek == '_') {
                    readch(br);
                    s += peek;
                }

                if (peek == ' ') {
                    System.err.println("Only _ is not accepted ");
                    return null;
                } else if (Character.isLetter(peek) || Character.isDigit(peek)) {

                    do {
                        s += peek;
                        readch(br);
                    } while (Character.isDigit(peek) || Character.isLetter(peek));

                    if ((Word) words.get(s) != null) return (Word) words.get(s);

                    else {
                        Word w = new Word(Tag.ID, s);
                        words.put(s, w);
                        return w;
                    }
                } else {
                    System.err.println("Erroneous character" + " after _ " + peek);
                    return null;
                }
                // ... gestire gli altri casi ... //

            default:
                if (Character.isLetter(peek)) {
                    String s1 = "";
                    do {
                        s1 += peek;
                        readch(br);
                    } while (Character.isDigit(peek) || Character.isLetter(peek));
                    if ((Word) words.get(s1) != null) return (Word) words.get(s1);
                    else {
                        Word w = new Word(Tag.ID, s1);
                        words.put(s1, w);
                        return w;
                    }
                } else if (Character.isDigit(peek)) {
                    // Numeri
                    int x = 0;
                    while (Character.isDigit(peek)) {
                        x = x * 10 + peek - 48;
                        //System.out.println("x: " + x);
                        readch(br);
                    }
                    if (Character.isLetter(peek) || peek == '_') {
                        //System.out.println("peek: " + peek);
                        System.err.println("Variables doesn't begin with number");
                        return null;
                    }
                    Number n = new Number(Tag.NUM, x);
                    return n;
                } else if (peek == (char) - 1) {
                    return new Token(Tag.EOF);
                } else {
                    System.err.println("Erroneous character: " + peek);
                    return null;
                }
        }
    }

    public static void main(String[] args) {
        LexerF lex = new LexerF();
        String path = "input.txt"; //il percorso del file da leggere

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                if(tok != null) System.out.println("Scan: " + tok);
            }
            while (tok != null && tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
 }
