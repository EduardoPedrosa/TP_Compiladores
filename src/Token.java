
public class Token {
	public static enum TipoToken{ID,NUM,NUMFLOAT,CAD,OPAR,OPCOMP,OPCOMENT,DELIM,PAL,ATRIB,ERROR, PANIC};
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

	@Override
	public String toString(){
		if(indiceTS != -1){
			return "<"+tipo+", "+valor+", "+indiceTS+">";
		}
		return "<"+tipo+", "+valor+">";
	}
}