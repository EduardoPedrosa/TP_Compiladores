/* 
  Trabalho de Compiladores
  Analisador Léxico
  Copyright 2019 by Eduardo Miranda Pedrosa Filho,
                    Igor Henrique Torati Ruy,
                    João Pedro Fachini Alvarenga and
                    Matheus Henrique Carvalho de Paiva Resende
*/
public class Simbolos{
    private CategoriaSimbolo categoria;
    private String nome;

    public Simbolos(String nome, CategoriaSimbolo categoria){
        this.nome = nome;
        this.categoria = categoria;
    }

    public CategoriaSimbolo getCategoria() {
        return categoria;
    }

    public String getNome() {
        return nome;
    }
}