import java.util.HashMap;
/*
    Criei uma ideia geral pra uma tabela de símbolos.
    Que eu me lembre o valor dos identificadores é guardado
    na tabela de símblos e não no token. Quando reunirmos
    Conversamos melhor a respeito
    -- Matheus
*/
public class TabelaDeSimbolos{
    private static TabelaDeSimbolos tabela;
    private HashMap<Integer, String> simbolos;
    

    public TabelaDeSimbolos(){
        simbolos = new HashMap<Integer, String>();
    }
    public static TabelaDeSimbolos getTabela(){
        if(tabela == null){
            tabela = new TabelaDeSimbolos();
        }
        return tabela;
    }
}