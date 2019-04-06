import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class AnalisadorLexico {
	private enum Palavras{INT,FLOAT,STRUCT,IF,ELSE,WHILE,VOID,RETURN};
	private HashMap<String, Palavras> palavrasReservadas = new HashMap<String,Palavras>();
	public AnalisadorLexico() {
		palavrasReservadas.put("int",Palavras.INT);
		palavrasReservadas.put("float",Palavras.FLOAT);
		palavrasReservadas.put("struct",Palavras.STRUCT);
		palavrasReservadas.put("if",Palavras.IF);
		palavrasReservadas.put("else",Palavras.ELSE);
		palavrasReservadas.put("while",Palavras.WHILE);
		palavrasReservadas.put("void",Palavras.VOID);
		palavrasReservadas.put("return",Palavras.RETURN);
	}
	
	public void Analisar(BufferedReader arquivo) throws IOException {
		char caracter = ' ';
		while(arquivo.ready()) { //ENTRANDO EM LOOP INFINITO PORQUE O READY TA SEMPRE ENTRANDO
			String palavra = "";
			boolean encontrouPalavra = false;
			if(caracter == ' ' || caracter == '\n') {
				caracter = (char) arquivo.read();
			}
			boolean primeiroCaracter = true;
			if(caracter == 'i') {
				palavra += caracter;
				primeiroCaracter = false;
				caracter = (char) arquivo.read();
				if(caracter == 'f') {
					palavra += caracter;
					caracter = (char) arquivo.read();
					if(Character.isDigit(caracter) || Character.isLetter(caracter)) {
						palavra += caracter;
					} else {
						encontrouPalavra = true;
						//criar token
					}
				}
				if(caracter == 'n') {
					palavra += caracter;
					caracter = (char) arquivo.read();
					if(caracter == 't') {
						palavra += caracter;
						caracter = (char) arquivo.read();
						if(Character.isDigit(caracter) || Character.isLetter(caracter)) {
							palavra += caracter;
						} else {
							encontrouPalavra = true;
							//criar token
						}
					}
				}
			} else if(caracter == 'f'){
				palavra += caracter;
				primeiroCaracter = false;
				caracter = (char) arquivo.read();
				if(caracter == 'l') {
					palavra += caracter;
					caracter = (char) arquivo.read();
					if(caracter == 'o') {
						palavra += caracter;
						caracter = (char) arquivo.read();
						if(caracter == 'a') {
							palavra += caracter;
							caracter = (char) arquivo.read();
							if(caracter == 't') {
								palavra += caracter;
								caracter = (char) arquivo.read();
								if(Character.isDigit(caracter) || Character.isLetter(caracter)) {
									palavra += caracter;
								} else {
									encontrouPalavra = true;
									//criar token
								}
							}
						}
					}
				}
			} else if(caracter == 's') {
				palavra += caracter;
				primeiroCaracter = false;
				caracter = (char) arquivo.read();
				if(caracter == 't') {
					palavra += caracter;
					caracter = (char) arquivo.read();
					if(caracter == 'r') {
						palavra += caracter;
						caracter = (char) arquivo.read();
						if(caracter == 'u') {
							palavra += caracter;
							caracter = (char) arquivo.read();
							if(caracter == 'c') {
								palavra += caracter;
								caracter = (char) arquivo.read();
								if(caracter == 't') {
									palavra += caracter;
									caracter = (char) arquivo.read();
									if(Character.isDigit(caracter) || Character.isLetter(caracter)) {
										palavra += caracter;
									} else {
										encontrouPalavra = true;
										//criar token
									}
								}
							}
						}
					}
				}
			} else if(caracter == 'e') {
				palavra += caracter;
				primeiroCaracter = false;
				caracter = (char) arquivo.read();
				if(caracter == 'l') {
					palavra += caracter;
					caracter = (char) arquivo.read();
					if(caracter == 's') {
						palavra += caracter;
						caracter = (char) arquivo.read();
						if(caracter == 'e') {
							palavra += caracter;
							caracter = (char) arquivo.read();
							if(Character.isDigit(caracter) || Character.isLetter(caracter)) {
								palavra += caracter;
							} else {
								encontrouPalavra = true;
								//criar token
							}
						}
					}
				}
			} else if(caracter == 'w') {
				palavra += caracter;
				primeiroCaracter = false;
				caracter = (char) arquivo.read();
				if(caracter == 'h') {
					palavra += caracter;
					caracter = (char) arquivo.read();
					if(caracter == 'i') {
						palavra += caracter;
						caracter = (char) arquivo.read();
						if(caracter == 'l') {
							palavra += caracter;
							caracter = (char) arquivo.read();
							if(caracter == 'e') {
								palavra += caracter;
								caracter = (char) arquivo.read();
								if(Character.isDigit(caracter) || Character.isLetter(caracter)) {
									palavra += caracter;
								} else {
									encontrouPalavra = true;
									//criar token
								}
							}
						}
					}
				}
			} else if(caracter == 'v') {
				palavra += caracter;
				primeiroCaracter = false;
				caracter = (char) arquivo.read();
				if(caracter == 'o') {
					palavra += caracter;
					caracter = (char) arquivo.read();
					if(caracter == 'i') {
						palavra += caracter;
						caracter = (char) arquivo.read();
						if(caracter == 'd') {
							palavra += caracter;
							caracter = (char) arquivo.read();
							if(Character.isDigit(caracter) || Character.isLetter(caracter)) {
								palavra += caracter;
							} else {
								encontrouPalavra = true;
								//criar token
							}
						}
					}
				}
			} else if(caracter == 'r') {
				palavra += caracter;
				primeiroCaracter = false;
				caracter = (char) arquivo.read();
				if(caracter == 'e') {
					palavra += caracter;
					caracter = (char) arquivo.read();
					if(caracter == 't') {
						palavra += caracter;
						caracter = (char) arquivo.read();
						if(caracter == 'u') {
							palavra += caracter;
							caracter = (char) arquivo.read();
							if(caracter == 'r') {
								palavra += caracter;
								caracter = (char) arquivo.read();
								if(caracter == 'n') {
									palavra += caracter;
									caracter = (char) arquivo.read();
									if(Character.isDigit(caracter) || Character.isLetter(caracter)) {
										palavra += caracter;
									} else {
										encontrouPalavra = true;
										//criar token
									}
								}
							}
						}
					}
				}
			} 
			if(!encontrouPalavra) {
				while((Character.isDigit(caracter) && !primeiroCaracter) || Character.isLetter(caracter)) { //se for o primeiro caracter precisa ser letra se nao pode ser letra ou digito
					palavra += caracter;
					caracter = (char) arquivo.read();
					if(Character.isDigit(caracter) || Character.isLetter(caracter)) {
						palavra += caracter;
					}
				}
				//criar token de identificador
			}
		}
	}
}
