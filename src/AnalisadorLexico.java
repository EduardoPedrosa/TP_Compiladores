import java.util.ArrayList;

public class AnalisadorLexico {
	private ArrayList<String> palavrasReservadas = new ArrayList<String>();
	public AnalisadorLexico() {
		palavrasReservadas.add("int");
		palavrasReservadas.add("float");
		palavrasReservadas.add("struct");
		palavrasReservadas.add("if");
		palavrasReservadas.add("else");
		palavrasReservadas.add("while");
		palavrasReservadas.add("void");
		palavrasReservadas.add("return");
	}
}
