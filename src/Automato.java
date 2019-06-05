/* 
  Trabalho de Compiladores
  Analisador Léxico
  Copyright 2019 by Eduardo Miranda Pedrosa Filho,
                    Igor Henrique Torati Ruy,
                    João Pedro Fachini Alvarenga and
                    Matheus Henrique Carvalho de Paiva Resende
*/
import java.util.HashMap;

public class Automato {
	private enum Alfabeto{I,N,T,F,L,O,A,S,R,U,
						  C,E,W,H,V,D,LETRA,DIGITO,AP,FP,
						  ACH,FCH,ACO,FCO,IGUAL,MAIOR,MENOR,MAIS,MENOS,ASTERISCO,
						  BARRA,PONTOVIRGULA,EXCLAMACAO,PONTO,OUTRO,ASPAS,VIRGULA};
	private int estadoAtual;
	private int[][] transicao;
	//Hash que tem como chave um estado final e o valor o tipo do token que esse estado final gera
	//Todo estado final gera um token.
	private HashMap<Integer,Token.TipoToken> estadosFinais;
	//Hash com todas os simbolos válidos lidos
	//A chave é o proprio caractere e o valor é um enum que será usado para a identificação
	//de padrões no automato
	private HashMap<Character, Alfabeto> palavrasDoAlfabeto = new HashMap<Character, Alfabeto>();

	private final void setTransicoesPra47(int estado){ //setar todas as transicoes do estado
		 											   //passado por parametro para 47
		for(int i = 0; i < 18; i++){
			if(transicao[estado][i] == -1){
				transicao[estado][i] = 47;
			}
		}
	}

	public Automato () {
		estadoAtual = 0;
		transicao = new int[102][37];
		for(int i = 0; i < 102; i++){  //preenche a matriz de proximos estados com -1 (estado inválido)
			for(int j = 0; j < 37; j++){
				transicao[i][j] = -1;
			}
		}
		estadosFinais = new HashMap<Integer, Token.TipoToken>();
		//populando o hash com todos os caracteres validos e seus respectivos valores no enum
		palavrasDoAlfabeto.put('a',Alfabeto.A);
		palavrasDoAlfabeto.put('b',Alfabeto.LETRA);
		palavrasDoAlfabeto.put('c',Alfabeto.C);
		palavrasDoAlfabeto.put('d',Alfabeto.D);
		palavrasDoAlfabeto.put('e',Alfabeto.E);
		palavrasDoAlfabeto.put('f',Alfabeto.F);
		palavrasDoAlfabeto.put('g',Alfabeto.LETRA);
		palavrasDoAlfabeto.put('h',Alfabeto.H);
		palavrasDoAlfabeto.put('i',Alfabeto.I);
		palavrasDoAlfabeto.put('j',Alfabeto.LETRA);
		palavrasDoAlfabeto.put('k',Alfabeto.LETRA);
		palavrasDoAlfabeto.put('l',Alfabeto.L);
		palavrasDoAlfabeto.put('m',Alfabeto.LETRA);
		palavrasDoAlfabeto.put('n',Alfabeto.N);
		palavrasDoAlfabeto.put('o',Alfabeto.O);
		palavrasDoAlfabeto.put('p',Alfabeto.LETRA);
		palavrasDoAlfabeto.put('q',Alfabeto.LETRA);
		palavrasDoAlfabeto.put('r',Alfabeto.R);
		palavrasDoAlfabeto.put('s',Alfabeto.S);
		palavrasDoAlfabeto.put('t',Alfabeto.T);
		palavrasDoAlfabeto.put('u',Alfabeto.U);
		palavrasDoAlfabeto.put('v',Alfabeto.V);
		palavrasDoAlfabeto.put('w',Alfabeto.W);
		palavrasDoAlfabeto.put('x',Alfabeto.LETRA);
		palavrasDoAlfabeto.put('y',Alfabeto.LETRA);
		palavrasDoAlfabeto.put('z',Alfabeto.LETRA);
		palavrasDoAlfabeto.put('0',Alfabeto.DIGITO);
		palavrasDoAlfabeto.put('1',Alfabeto.DIGITO);
		palavrasDoAlfabeto.put('2',Alfabeto.DIGITO);
		palavrasDoAlfabeto.put('3',Alfabeto.DIGITO);
		palavrasDoAlfabeto.put('4',Alfabeto.DIGITO);
		palavrasDoAlfabeto.put('5',Alfabeto.DIGITO);
		palavrasDoAlfabeto.put('6',Alfabeto.DIGITO);
		palavrasDoAlfabeto.put('7',Alfabeto.DIGITO);
		palavrasDoAlfabeto.put('8',Alfabeto.DIGITO);
		palavrasDoAlfabeto.put('9',Alfabeto.DIGITO);
		palavrasDoAlfabeto.put('(',Alfabeto.AP);
		palavrasDoAlfabeto.put(')',Alfabeto.FP);
		palavrasDoAlfabeto.put('{',Alfabeto.ACH);
		palavrasDoAlfabeto.put('}',Alfabeto.FCH);
		palavrasDoAlfabeto.put('[',Alfabeto.ACO);
		palavrasDoAlfabeto.put(']',Alfabeto.FCO);
		palavrasDoAlfabeto.put(';',Alfabeto.PONTOVIRGULA);
		palavrasDoAlfabeto.put('=',Alfabeto.IGUAL);
		palavrasDoAlfabeto.put('>',Alfabeto.MAIOR);
		palavrasDoAlfabeto.put('<',Alfabeto.MENOR);
		palavrasDoAlfabeto.put('+',Alfabeto.MAIS);
		palavrasDoAlfabeto.put('-',Alfabeto.MENOS);
		palavrasDoAlfabeto.put('*',Alfabeto.ASTERISCO);
		palavrasDoAlfabeto.put('/',Alfabeto.BARRA);
		palavrasDoAlfabeto.put('.',Alfabeto.PONTO);
		palavrasDoAlfabeto.put('\'',Alfabeto.ASPAS);
		palavrasDoAlfabeto.put(',',Alfabeto.VIRGULA);
		palavrasDoAlfabeto.put(' ',Alfabeto.OUTRO);
		palavrasDoAlfabeto.put('\n',Alfabeto.OUTRO);
		palavrasDoAlfabeto.put('\t',Alfabeto.OUTRO);
		
		//populando o hash de estados finais com os seus TipoToken como valores
		estadosFinais.put(3, Token.TipoToken.IF);
		estadosFinais.put(6, Token.TipoToken.INT);
		estadosFinais.put(12, Token.TipoToken.FLOAT);
		estadosFinais.put(48, Token.TipoToken.ID);
		estadosFinais.put(17, Token.TipoToken.CHAR);
		estadosFinais.put(22, Token.TipoToken.VOID);
		estadosFinais.put(29, Token.TipoToken.STRUCT);
		estadosFinais.put(95, Token.TipoToken.ELSE);
		estadosFinais.put(39, Token.TipoToken.WHILE);
		estadosFinais.put(46, Token.TipoToken.RETURN);
		estadosFinais.put(50, Token.TipoToken.OPAR);
		estadosFinais.put(52, Token.TipoToken.NUM);
		estadosFinais.put(55, Token.TipoToken.NUMFLOAT);
		estadosFinais.put(57, Token.TipoToken.OPAR);
		estadosFinais.put(59, Token.TipoToken.OPMULT);
		estadosFinais.put(65, Token.TipoToken.OPMULT);
		estadosFinais.put(64, Token.TipoToken.OPCOMENT);
		estadosFinais.put(67, Token.TipoToken.OPCOMP);
		estadosFinais.put(69, Token.TipoToken.OPCOMP);
		estadosFinais.put(72, Token.TipoToken.OPCOMP);
		estadosFinais.put(69, Token.TipoToken.OPCOMP);
		estadosFinais.put(73, Token.TipoToken.OPCOMP);
		estadosFinais.put(75, Token.TipoToken.ATRIB);
		estadosFinais.put(77, Token.TipoToken.OPCOMP);
		estadosFinais.put(80, Token.TipoToken.OPCOMP);	
		estadosFinais.put(82, Token.TipoToken.DELIM);	
		estadosFinais.put(84, Token.TipoToken.ACHAVES);
		estadosFinais.put(86, Token.TipoToken.FCHAVES);
		estadosFinais.put(88, Token.TipoToken.ACOLCHETES);
		estadosFinais.put(90, Token.TipoToken.FCOLCHETES);
		estadosFinais.put(92, Token.TipoToken.FPARENTESES);
		estadosFinais.put(94, Token.TipoToken.APARENTESES);
		estadosFinais.put(99, Token.TipoToken.CAR);
		estadosFinais.put(101, Token.TipoToken.VIRGULA);
		
		//Estado x lê "outros" caracteres vai pra estado w
		//outros é indicado por todos os enum da posicao de 18 a 37 no Alfabeto
		for (int i = 18; i < 37; i++){ 
			transicao[2][i] = 3;
			transicao[5][i] = 6;
			transicao[11][i] = 12;
			transicao[16][i] = 17;
			transicao[21][i] = 22;
			transicao[28][i] = 29;
			transicao[33][i] = 95;
			transicao[38][i] = 39;
			transicao[45][i] = 46;
			transicao[47][i] = 48;
			transicao[51][i] = 52;
			transicao[54][i] = 55;
			transicao[1][i] = 48;
			transicao[4][i] = 48;
			transicao[7][i] = 48;
			transicao[8][i] = 48;
			transicao[9][i] = 48;
			transicao[10][i] = 48;
			transicao[13][i] = 48;
			transicao[14][i] = 48;
			transicao[15][i] = 48;
			transicao[18][i] = 48;
			transicao[19][i] = 48;
			transicao[20][i] = 48;
			transicao[23][i] = 48;
			transicao[24][i] = 48;
			transicao[25][i] = 48;
			transicao[26][i] = 48;
			transicao[27][i] = 48;
			transicao[30][i] = 48;
			transicao[31][i] = 48;
			transicao[32][i] = 48;
			transicao[34][i] = 48;
			transicao[35][i] = 48;
			transicao[36][i] = 48;
			transicao[37][i] = 48;
			transicao[40][i] = 48;
			transicao[41][i] = 48;
			transicao[42][i] = 48;
			transicao[43][i] = 48;
			transicao[44][i] = 48;
		}
		//Estado x lê "todos" caracteres vai pra estado w
		//todos é qualquer caractere representado no enum de 0 a 37 no Alfabeto
		for(int i = 0; i < 37; i++){ 
			transicao[49][i] = 50;
			transicao[56][i] = 57;
			transicao[58][i] = 59;
			transicao[63][i] = 64;
			transicao[66][i] = 67;
			transicao[68][i] = 69;
			transicao[70][i] = 73;
			transicao[71][i] = 72;
			transicao[98][i] = 99;
			transicao[74][i] = 75;
			transicao[76][i] = 77;
			transicao[79][i] = 80;
			transicao[81][i] = 82;
			transicao[83][i] = 84;
			transicao[85][i] = 86;
			transicao[87][i] = 88;
			transicao[89][i] = 90;
			transicao[91][i] = 92;
			transicao[93][i] = 94;
			transicao[100][i] = 101;
			if(i != Alfabeto.ASTERISCO.ordinal()){
				transicao[60][i] = 65;
				transicao[61][i] = 61;
			}
			if(i != Alfabeto.BARRA.ordinal()) {
				transicao[62][i] = 61;
			}
			if(i != Alfabeto.ASPAS.ordinal()){
				transicao[96][i] = 97;
			}
		}
		//Estado x lê "letras" vai pra estado w
		//letras é indicado por todos os enum da posicao de 0 a 17 no Alfabeto
		for(int i = 0; i < 17; i++){ 
			transicao[54][i] = 52;
			transicao[61][i] = 62;
		}
		
		//setar transicoes para 47 (estado de de identificação dos "identificadores")
		setTransicoesPra47(47);
		setTransicoesPra47(1);
		setTransicoesPra47(2);
		setTransicoesPra47(4);
		setTransicoesPra47(5);
		setTransicoesPra47(7);
		setTransicoesPra47(8);
		setTransicoesPra47(9);
		setTransicoesPra47(10);
		setTransicoesPra47(11);
		setTransicoesPra47(13);
		setTransicoesPra47(14);
		setTransicoesPra47(15);
		setTransicoesPra47(16);
		setTransicoesPra47(18);
		setTransicoesPra47(19);
		setTransicoesPra47(20);
		setTransicoesPra47(21);
		setTransicoesPra47(23);
		setTransicoesPra47(24);
		setTransicoesPra47(25);
		setTransicoesPra47(26);
		setTransicoesPra47(27);
		setTransicoesPra47(28);
		setTransicoesPra47(30);
		setTransicoesPra47(31);
		setTransicoesPra47(32);
		setTransicoesPra47(33);
		setTransicoesPra47(34);
		setTransicoesPra47(35);
		setTransicoesPra47(36);
		setTransicoesPra47(37);
		setTransicoesPra47(38);
		setTransicoesPra47(40);
		setTransicoesPra47(41);
		setTransicoesPra47(42);
		setTransicoesPra47(43);
		setTransicoesPra47(44);
		setTransicoesPra47(45);
				
		//transicoes especificas do automato 
		transicao[0][Alfabeto.I.ordinal()] = 1;
		transicao[0][Alfabeto.F.ordinal()] = 7;
		transicao[0][Alfabeto.C.ordinal()] = 13;
		transicao[0][Alfabeto.V.ordinal()] = 18;
		transicao[0][Alfabeto.S.ordinal()] = 23;
		transicao[0][Alfabeto.E.ordinal()] = 30;
		transicao[0][Alfabeto.W.ordinal()] = 34;
		transicao[0][Alfabeto.R.ordinal()] = 40;
		transicao[0][Alfabeto.T.ordinal()] = 47;
		transicao[0][Alfabeto.L.ordinal()] = 47;
		transicao[0][Alfabeto.O.ordinal()] = 47;
		transicao[0][Alfabeto.A.ordinal()] = 47;
		transicao[0][Alfabeto.U.ordinal()] = 47;
		transicao[0][Alfabeto.H.ordinal()] = 47;
		transicao[0][Alfabeto.D.ordinal()] = 47;
		transicao[0][Alfabeto.N.ordinal()] = 47;
		transicao[0][Alfabeto.LETRA.ordinal()] = 47;
		transicao[1][Alfabeto.F.ordinal()] = 2;	
		transicao[1][Alfabeto.N.ordinal()] = 4;
		transicao[4][Alfabeto.T.ordinal()] = 5;	
		transicao[7][Alfabeto.L.ordinal()] = 8;
		transicao[8][Alfabeto.O.ordinal()] = 9;
		transicao[9][Alfabeto.A.ordinal()] = 10;
		transicao[10][Alfabeto.T.ordinal()] = 11;
		transicao[13][Alfabeto.H.ordinal()] = 14;
		transicao[14][Alfabeto.A.ordinal()] = 15;
		transicao[15][Alfabeto.R.ordinal()] = 16;
		transicao[18][Alfabeto.O.ordinal()] = 19;
		transicao[19][Alfabeto.I.ordinal()] = 20;
		transicao[20][Alfabeto.D.ordinal()] = 21;
		transicao[23][Alfabeto.T.ordinal()] = 24;
		transicao[24][Alfabeto.R.ordinal()] = 25;
		transicao[25][Alfabeto.U.ordinal()] = 26;
		transicao[26][Alfabeto.C.ordinal()] = 27;
		transicao[27][Alfabeto.T.ordinal()] = 28;
		transicao[30][Alfabeto.L.ordinal()] = 31;
		transicao[31][Alfabeto.S.ordinal()] = 32;
		transicao[32][Alfabeto.E.ordinal()] = 33;
		transicao[34][Alfabeto.H.ordinal()] = 35;
		transicao[35][Alfabeto.I.ordinal()] = 36;
		transicao[36][Alfabeto.L.ordinal()] = 37;
		transicao[37][Alfabeto.E.ordinal()] = 38;
		transicao[40][Alfabeto.E.ordinal()] = 41;
		transicao[41][Alfabeto.T.ordinal()] = 42;
		transicao[42][Alfabeto.U.ordinal()] = 43;
		transicao[43][Alfabeto.R.ordinal()] = 44;
		transicao[44][Alfabeto.N.ordinal()] = 45;
		transicao[0][Alfabeto.MAIS.ordinal()] = 49;
		transicao[0][Alfabeto.DIGITO.ordinal()] = 51;
		transicao[0][Alfabeto.MENOS.ordinal()] = 56;
		transicao[0][Alfabeto.ASTERISCO.ordinal()] = 58;
		transicao[0][Alfabeto.BARRA.ordinal()] = 60;
		transicao[0][Alfabeto.MENOR.ordinal()] = 66;
		transicao[0][Alfabeto.MAIOR.ordinal()] = 70;
		transicao[0][Alfabeto.IGUAL.ordinal()] = 74;
		transicao[0][Alfabeto.EXCLAMACAO.ordinal()] = 78;
		transicao[0][Alfabeto.PONTOVIRGULA.ordinal()] = 81;
		transicao[0][Alfabeto.ACH.ordinal()] = 83;
		transicao[0][Alfabeto.FCH.ordinal()] = 85;
		transicao[0][Alfabeto.ACO.ordinal()] = 87;
		transicao[0][Alfabeto.FCO.ordinal()] = 89;
		transicao[0][Alfabeto.AP.ordinal()] = 93;
		transicao[0][Alfabeto.FP.ordinal()] = 91;
		transicao[0][Alfabeto.ASPAS.ordinal()] = 96;
		transicao[0][Alfabeto.VIRGULA.ordinal()] = 100;
		transicao[51][Alfabeto.DIGITO.ordinal()] = 51;
		transicao[96][Alfabeto.ASPAS.ordinal()] = 98;
		transicao[97][Alfabeto.ASPAS.ordinal()] = 98;
		transicao[51][Alfabeto.PONTO.ordinal()] = 53;
		transicao[53][Alfabeto.DIGITO.ordinal()] = 54;
		transicao[54][Alfabeto.DIGITO.ordinal()] = 54;
		transicao[60][Alfabeto.ASTERISCO.ordinal()] = 61;
		transicao[61][Alfabeto.ASTERISCO.ordinal()] = 62;
		transicao[62][Alfabeto.BARRA.ordinal()] = 63;
		transicao[66][Alfabeto.IGUAL.ordinal()] = 68;
		transicao[70][Alfabeto.IGUAL.ordinal()] = 71;
		transicao[74][Alfabeto.IGUAL.ordinal()] = 76;
		transicao[78][Alfabeto.IGUAL.ordinal()] = 79;
	}

	private boolean isEstadoFinal(int proxEstado) {
		return estadosFinais.containsKey(proxEstado);
	}
	
	public Token.TipoToken executar(char c) {
		Alfabeto palavraAlfabeto = palavrasDoAlfabeto.get(c);
		if(palavraAlfabeto == null) {  //quer dizer que nao é um caractere previsto no alfabeto
			if((estadoAtual != 61) && (estadoAtual != 62)){ //ignorando o conteudo dos comentarios
				return Token.TipoToken.PANIC;
			} else {
				palavraAlfabeto = Alfabeto.OUTRO;
			}
		}
		int idCar = palavraAlfabeto.ordinal();  //indice que representa qual o 
												//caractere lido e a posicao dele na matriz
		int proxEstado = transicao[estadoAtual][idCar];
		//se o proximo estado for final retorno o token desse estado final
		if(isEstadoFinal(proxEstado)) { 
			estadoAtual = 0;
			return estadosFinais.get(proxEstado);
		}
		//se o proximo estado for -1 quer dizer que nao tem transição prevista para o 
		//caractere lido. Isso significa um erro léxico
		if(proxEstado == -1){
			if(!Character.isWhitespace(c)){
				return Token.TipoToken.ERROR;
			} else {
				proxEstado = 0;
			}
		}

		estadoAtual = proxEstado;
		return null;
	}
}
