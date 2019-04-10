import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class AnalisadorLexico {
	private enum Palavras{INT, FLOAT, STRUCT, IF, ELSE, WHILE, VOID, RETURN};
	private HashMap<String, Palavras> palavrasReservadas = new HashMap<String,Palavras>();
	private Automato automato;
	public AnalisadorLexico() {
		automato = new Automato();
		palavrasReservadas.put("int",Palavras.INT);
		palavrasReservadas.put("float",Palavras.FLOAT);
		palavrasReservadas.put("struct",Palavras.STRUCT);
		palavrasReservadas.put("if",Palavras.IF);
		palavrasReservadas.put("else",Palavras.ELSE);
		palavrasReservadas.put("while",Palavras.WHILE);
		palavrasReservadas.put("void",Palavras.VOID);
		palavrasReservadas.put("return",Palavras.RETURN);
		
		
		
	}
	
	public void Analisar(BufferedReader arquivo){
		
	}
}
