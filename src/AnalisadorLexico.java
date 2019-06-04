/* 
  Trabalho de Compiladores
  Analisador Léxico
  Copyright 2019 by Eduardo Miranda Pedrosa Filho,
                    Igor Henrique Torati Ruy,
                    João Pedro Fachini Alvarenga and
                    Matheus Henrique Carvalho de Paiva Resende
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class AnalisadorLexico {
	private Automato automato;
	private List<Token> tokens;
	boolean erro;

	public AnalisadorLexico() {
		automato = new Automato();
		tokens = new ArrayList<Token>();
		erro = false;
	}
	
	public void Analisar(BufferedReader arquivo) throws IOException{
		String linha = "";
		String palavra = "";
		int numLinha = 0; //contador da linha de leitura no arquivo 
		int numColuna = 0; //contador da coluna de leitura no arquivo
		boolean achouErroLexico = false;
		while((linha = arquivo.readLine()) != null) { //lê cada linha até que nao se encontra mais nenhuma linha
			linha += " ";
			++numLinha;
			numColuna = 0; //contador da coluna de leitura no arquivo
			for(int i = 0; i < linha.length(); i++) {
				numColuna++;
				Token.TipoToken tipoToken = automato.executar(linha.charAt(i));
				if (tipoToken != null) {
					if(tipoToken == Token.TipoToken.PANIC){
						System.out.println("Warning - linha " + numLinha + " e coluna " + numColuna + " - tratado com panic mode");
						erro = true;
					} else if(tipoToken == Token.TipoToken.ERROR){
						achouErroLexico = true;
						System.out.println("Erro lexico na linha " + numLinha + " e coluna " + numColuna);
						erro = true;
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
							if(!achouErroLexico){
								//CRIANDO TOKEN
								Token novoToken;
								if (indiceTS != -1)
									novoToken = new Token(tipoToken, palavra, indiceTS, numLinha, numColuna-1);
								else
									novoToken = new Token(tipoToken, palavra, numLinha, numColuna-1);
								tokens.add(novoToken);
							}
						}
						palavra = "";
						i--; //para verificar o ultimo caracter da iteração que seria descartado
						numColuna--;
					}
				} else {
					if (!Character.isWhitespace(linha.charAt(i))) { //se o caractere for branco, nao adicionar ele na palavra
						palavra += linha.charAt(i);
					}
				}
			}
		}
		//IMPRIMINDO TOKENS
		/*if(!achouErroLexico){
			for (Token t : tokens){
				System.out.println(t);
			}
		}*/
		tokens.add(new Token(Token.TipoToken.FIM,"",numLinha,numColuna));
		if(!erro){
			System.out.println("Analise lexica realizada com sucesso");
		}
	}
	public List<Token> getTokens(){
		return tokens;
	}
}
