import java.util.HashMap;

public class Automato {
	private enum Alfabeto{I,N,T,F,L,O,A,S,R,U,C,E,W,H,V,D,AP,FP,ACH,FCH,ACO,FCO,IGUAL,MAIOR,MENOR,MAIS,MENOS,ASTERISCO,BARRA,PONTOVIRGULA,LETRA,DIGITO,OUTRO};
	private int estadoAtual;
	private int[][] transicao;
	private HashMap<Integer,Token.TipoToken> estadosFinais;
	private HashMap<Character, Alfabeto> palavrasDoAlfabeto = new HashMap<Character, Alfabeto>();
	public Automato () {
		transicao = new int[96][33];
		estadosFinais = new HashMap<Integer, Token.TipoToken>();	
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
		estadoAtual = 0;
		
		estadosFinais.put(3, Token.TipoToken.PAL);
		estadosFinais.put(6, Token.TipoToken.PAL);
		estadosFinais.put(12, Token.TipoToken.PAL);
		estadosFinais.put(48, Token.TipoToken.PAL);
		estadosFinais.put(17, Token.TipoToken.PAL);
		estadosFinais.put(22, Token.TipoToken.PAL);
		estadosFinais.put(29, Token.TipoToken.PAL);
		estadosFinais.put(95, Token.TipoToken.PAL);
		estadosFinais.put(39, Token.TipoToken.PAL);
		estadosFinais.put(46, Token.TipoToken.PAL);
		estadosFinais.put(50, Token.TipoToken.OPAR);
		estadosFinais.put(52, Token.TipoToken.NUM);
		estadosFinais.put(55, Token.TipoToken.NUMFLOAT);
		estadosFinais.put(57, Token.TipoToken.OPAR);
		estadosFinais.put(59, Token.TipoToken.OPAR);
		estadosFinais.put(65, Token.TipoToken.OPAR);
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
		estadosFinais.put(84, Token.TipoToken.DELIM);	
		estadosFinais.put(86, Token.TipoToken.DELIM);	
		estadosFinais.put(88, Token.TipoToken.DELIM);	
		estadosFinais.put(90, Token.TipoToken.DELIM);	
		estadosFinais.put(92, Token.TipoToken.DELIM);	
		estadosFinais.put(94, Token.TipoToken.DELIM);
		
		transicao[0][Alfabeto.I.ordinal()] = 1;
		transicao[0][Alfabeto.F.ordinal()] = 7;
		transicao[0][Alfabeto.C.ordinal()] = 13;
		transicao[0][Alfabeto.V.ordinal()] = 18;
		transicao[0][Alfabeto.S.ordinal()] = 23;
		transicao[0][Alfabeto.E.ordinal()] = 30;
		transicao[0][Alfabeto.W.ordinal()] = 34;
		transicao[0][Alfabeto.R.ordinal()] = 40;		
		transicao[1][Alfabeto.F.ordinal()] = 2;	
		transicao[1][Alfabeto.N.ordinal()] = 4;
		transicao[2][Alfabeto.OUTRO.ordinal()] = 3;	
		transicao[4][Alfabeto.T.ordinal()] = 5;	
		transicao[5][Alfabeto.OUTRO.ordinal()] = 6;
		transicao[7][Alfabeto.L.ordinal()] = 8;
		transicao[8][Alfabeto.O.ordinal()] = 9;
		transicao[9][Alfabeto.A.ordinal()] = 10;
		transicao[10][Alfabeto.T.ordinal()] = 11;
		transicao[11][Alfabeto.OUTRO.ordinal()] = 12;
		transicao[13][Alfabeto.H.ordinal()] = 14;
		transicao[14][Alfabeto.A.ordinal()] = 15;
		transicao[15][Alfabeto.R.ordinal()] = 16;
		transicao[16][Alfabeto.OUTRO.ordinal()] = 17;
		transicao[18][Alfabeto.O.ordinal()] = 19;
		transicao[19][Alfabeto.I.ordinal()] = 20;
		transicao[20][Alfabeto.D.ordinal()] = 21;
		transicao[21][Alfabeto.OUTRO.ordinal()] = 22;
		transicao[23][Alfabeto.T.ordinal()] = 24;
		transicao[24][Alfabeto.R.ordinal()] = 25;
		transicao[25][Alfabeto.U.ordinal()] = 26;
		transicao[26][Alfabeto.C.ordinal()] = 27;
		transicao[27][Alfabeto.T.ordinal()] = 28;
		transicao[28][Alfabeto.OUTRO.ordinal()] = 29;
		transicao[30][Alfabeto.L.ordinal()] = 31;
		transicao[31][Alfabeto.S.ordinal()] = 32;
		transicao[32][Alfabeto.E.ordinal()] = 33;
		transicao[33][Alfabeto.OUTRO.ordinal()] = 95; //corrigir automato
		transicao[34][Alfabeto.H.ordinal()] = 35;
		transicao[35][Alfabeto.I.ordinal()] = 36;
		transicao[36][Alfabeto.L.ordinal()] = 37;
		transicao[37][Alfabeto.E.ordinal()] = 38;
		transicao[38][Alfabeto.OUTRO.ordinal()] = 39;
		transicao[40][Alfabeto.E.ordinal()] = 41;
		transicao[41][Alfabeto.T.ordinal()] = 42;
		transicao[42][Alfabeto.U.ordinal()] = 43;
		transicao[43][Alfabeto.R.ordinal()] = 44;
		transicao[44][Alfabeto.N.ordinal()] = 45;
		transicao[45][Alfabeto.OUTRO.ordinal()] = 46;											
		
		
	}
	
	private boolean isEstadoFinal(int proxEstado) {
		return estadosFinais.containsKey(proxEstado);	
	}
	
	public Token.TipoToken executar(char c) {
		Alfabeto palavraAlfabeto = palavrasDoAlfabeto.get(c);
		if(palavraAlfabeto == null) {
			palavraAlfabeto = Alfabeto.OUTRO;
		}
		int idCar = palavraAlfabeto.ordinal();
		int proxEstado = transicao[estadoAtual][idCar];
		if(isEstadoFinal(proxEstado)) {
			estadoAtual = 0;
			return estadosFinais.get(proxEstado);
		}
		estadoAtual = proxEstado;
		return null;
	}
}
