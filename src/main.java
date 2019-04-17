/* 
  Trabalho de Compiladores
  Analisador Léxico
  Copyright 2019 by Eduardo Miranda Pedrosa Filho,
                    Igor Henrique Torati Ruy,
                    João Pedro Fachini Alvarenga and
                    Matheus Henrique Carvalho de Paiva Resende
*/
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class main {
    public static void main(String[] args) {
        AnalisadorLexico analisadorLex = new AnalisadorLexico();
        try {
            System.out.println("Nome do arquivo de entrada: ");
            Scanner entrada = new Scanner(System.in);
            String nomeArq = entrada.nextLine();
            BufferedReader leitor = new BufferedReader(new FileReader(nomeArq));
            analisadorLex.Analisar(leitor);
            leitor.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}