import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class AnalisadorLexico {
	private enum Palavras{INT, FLOAT, CHAR, STRUCT, IF, ELSE, WHILE, VOID, RETURN};
	private HashMap<String, Palavras> palavrasReservadas = new HashMap<String,Palavras>();
	private Automato automato;
	private List<Token> tokens;

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
		tokens = new ArrayList<Token>();
	}
	
	public void Analisar(BufferedReader arquivo) throws IOException{
		String linha = "";
		String palavra = "";
		int numLinha = 0;
		while((linha = arquivo.readLine()) != null) {
			linha += " ";
			++numLinha;
			int numColuna = 0;
			for(int i = 0; i < linha.length(); i++) {
				numColuna++;
				Token.TipoToken tipoToken = automato.executar(linha.charAt(i));
				if (tipoToken != null) {
					if(tipoToken == Token.TipoToken.PANIC){
						System.out.println("Erro lexico na linha " + numLinha + " e coluna " + numColuna + " - tratado com panic mode");
					} else if(tipoToken == Token.TipoToken.ERROR){
						System.out.println("Erro lexico na linha " + numLinha + " e coluna " + numColuna);
					} else {
						// INSERINDO NA TABELA DE SIMBOLOS CASO FOR IDENTIFICADOR OU CONSTANTE
						int indiceTS = -1;
						if (tipoToken != Token.TipoToken.OPCOMENT) { //SE NAO FOR COMENTARIO, SEGUIR EM FRENTE, SE FOR, APENAS IGNORAR
							if (tipoToken == Token.TipoToken.ID) {
								TabelaDeSimbolos ts = TabelaDeSimbolos.getTabela();
								indiceTS = ts.inserirOuEncontrarNovoSimbolo(palavra, CategoriaSimbolo.IDENTIFICADOR);
							} else if ((tipoToken == Token.TipoToken.NUM) || (tipoToken == Token.TipoToken.NUMFLOAT) ||
									tipoToken == Token.TipoToken.CAR) {
								TabelaDeSimbolos ts = TabelaDeSimbolos.getTabela();
								indiceTS = ts.inserirOuEncontrarNovoSimbolo(palavra, CategoriaSimbolo.CONSTANTE);
							}
							//CRIANDO TOKEN
							Token novoToken;
							if (indiceTS != -1)
								novoToken = new Token(tipoToken, palavra, indiceTS);
							else
								novoToken = new Token(tipoToken, palavra);
							tokens.add(novoToken);
						}
						palavra = "";
						i--; //para verificar o ultimo caracter da iteração que seria descartado
						numColuna--;
					}
				} else {
					if (!Character.isWhitespace(linha.charAt(i))) {
						palavra += linha.charAt(i);
					}
				}
			}
		}
		//IMPRIMINDO TOKENS
		for (Token t : tokens){
			System.out.println(t);
		}
	}
}
