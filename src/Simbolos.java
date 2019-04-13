
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