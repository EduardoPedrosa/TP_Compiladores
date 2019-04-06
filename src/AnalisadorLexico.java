import java.util.HashMap;

public class AnalisadorLexico {
	private enum Palavras{INT,FLOAT,STRUCT,IF,ELSE,WHILE,VOID,RETURN};
	private HashMap<String, Palavras> palavrasReservadas = new HashMap<String,Palavras>();
	public AnalisadorLexico() {
		palavrasReservadas.put("int",Palavras.INT);
		palavrasReservadas.put("float",Palavras.FLOAT);
		palavrasReservadas.put("struct",Palavras.STRUCT);
		palavrasReservadas.put("if",Palavras.IF);
		palavrasReservadas.put("else",Palavras.ELSE);
		palavrasReservadas.put("while",Palavras.WHILE);
		palavrasReservadas.put("void",Palavras.VOID);
		palavrasReservadas.put("return",Palavras.RETURN);
	}
}
