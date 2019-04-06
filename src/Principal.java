import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
public class Principal {
	public static void main(String[] args) {
		AnalisadorLexico analisador = new AnalisadorLexico();
		Scanner entrada = new Scanner(System.in);
		String nomeArq = entrada.nextLine();
		BufferedReader arquivo = null;
		try {
			arquivo = new BufferedReader(new FileReader(nomeArq)); 
			analisador.Analisar(arquivo);
			arquivo.close();
		} catch(IOException e){
			e.printStackTrace();
		}
		entrada.close();
	}
}
