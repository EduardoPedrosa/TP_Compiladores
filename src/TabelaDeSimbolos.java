/* 
  Trabalho de Compiladores
  Analisador Léxico
  Copyright 2019 by Eduardo Miranda Pedrosa Filho,
                    Igor Henrique Torati Ruy,
                    João Pedro Fachini Alvarenga and
                    Matheus Henrique Carvalho de Paiva Resende
*/
import java.util.List;
import java.util.ArrayList;
/*
    Tabela de símbolos, cada linha possui um símbolo
    representada por uma lista
*/

public class TabelaDeSimbolos{
    private static TabelaDeSimbolos instancia;
    private List<Simbolos> tabela;

    private TabelaDeSimbolos(){
        tabela = new ArrayList<Simbolos>();
    }

    public static TabelaDeSimbolos getTabela(){
        if(instancia == null){
            instancia = new TabelaDeSimbolos();
        }
        return instancia;
    }

    public Simbolos getSimbolo(int indice){
        return tabela.get(indice);
    }

    public int encontrarSimbolo(String lexema){ //retorna o indice do lexema se ele 
                                                //ja esta na tabela, se nao retorna -1
        int indice = 0;
        for(Simbolos s : tabela){
            if(s.getNome().equals(lexema)){
                return indice;
            }
            ++indice;
        }
        return -1;
    }

    public int inserirSimbolo(String lexema, CategoriaSimbolo categoria){
        tabela.add(new Simbolos(lexema, categoria));
        return tabela.size()-1;
    }

    public int inserirOuEncontrarNovoSimbolo(String lexema, CategoriaSimbolo categoria){
        int indice = encontrarSimbolo(lexema);
        if (indice == -1){
            return inserirSimbolo(lexema, categoria);
        }
        return indice;
    }

}