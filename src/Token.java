/* 
  Trabalho de Compiladores
  Analisador Léxico
  Copyright 2019 by Eduardo Miranda Pedrosa Filho,
                    Igor Henrique Torati Ruy,
                    João Pedro Fachini Alvarenga and
                    Matheus Henrique Carvalho de Paiva Resende
*/
public class Token {
	public static enum TipoToken{ID,NUM,NUMFLOAT,CAR,OPAR,OPMULT,OPCOMP,OPCOMENT,DELIM,VIRGULA, ATRIB, INT, FLOAT, STRUCT, IF, ELSE, WHILE, VOID, RETURN,
		APARENTESES, FPARENTESES, ACHAVES, FCHAVES, ACOLCHETES, FCOLCHETES,
		ERROR,PANIC,FIM}; 
			//Os tipos ERROR e PANIC é so pra dar o feedback de erro no analisador léxico
	private TipoToken tipo;
	private String valor;
	private int indiceTS;
	private int numLinha;
	private int numColuna;
	
	public Token(TipoToken tipo, String valor, int numLinha, int numColuna) {
		this.tipo = tipo;
		this.valor = valor;
		this.indiceTS = -1;
		this.numLinha = numLinha;
		this.numColuna = numColuna;
	}

	public Token(TipoToken tipo, String valor, int indiceTS, int numLinha, int numColuna){
		this.tipo = tipo;
		this.valor = valor;
		this.indiceTS = indiceTS;
		this.numLinha = numLinha;
		this.numColuna = numColuna;
	}

	public TipoToken getTipoToken(){
		return tipo;
	}

	public int getNumLinha(){
		return numLinha;
	}

	public int getNumColuna(){
		return numColuna;
	}

	@Override
	public String toString(){
		if(indiceTS != -1){
			return "<"+tipo+", "+valor+", "+indiceTS+">";
		}
		return "<"+tipo+", "+valor+">";
	}
}