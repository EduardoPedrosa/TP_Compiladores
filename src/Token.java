
public class Token {
	public static enum TipoToken{ID,NUM,NUMFLOAT,CAD,OPAR,OPCOMP,OPCOMENT,DELIM,PAL,ATRIB};
	private TipoToken tipo;
	private String valor;
	
	public Token(TipoToken tipo, String valor) {
		this.tipo = tipo;
		this.valor = valor;
	}
}
