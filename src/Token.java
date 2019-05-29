/* 
  Trabalho de Compiladores
  Analisador Léxico
  Copyright 2019 by Eduardo Miranda Pedrosa Filho,
                    Igor Henrique Torati Ruy,
                    João Pedro Fachini Alvarenga and
                    Matheus Henrique Carvalho de Paiva Resende
*/
public class Token {
	public static enum TipoToken{ID,NUM,NUMFLOAT,CAR,OPAR,OPCOMP,OPCOMENT,DELIM,ATRIB, INT, FLOAT, STRUCT, IF, ELSE, WHILE, VOID, RETURN,
		APARENTESES, FPARENTESES, ACHAVES, FCHAVES, ACOLCHETES, FCOLCHETES,
		ERROR,PANIC}; 
			//Os tipos ERROR e PANIC é so pra dar o feedback de erro no analisador léxico
	private TipoToken tipo;
	private String valor;
	private int indiceTS;
	
	public Token(TipoToken tipo, String valor) {
		this.tipo = tipo;
		this.valor = valor;
		this.indiceTS = -1;
	}

	public Token(TipoToken tipo, String valor, int indiceTS){
		this.tipo = tipo;
		this.valor = valor;
		this.indiceTS = indiceTS;
	}

	public TipoToken getTipoToken(){
		return tipo;
	}

	@Override
	public String toString(){
		if(indiceTS != -1){
			return "<"+tipo+", "+valor+", "+indiceTS+">";
		}
		return "<"+tipo+", "+valor+">";
	}
}