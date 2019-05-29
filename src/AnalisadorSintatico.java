import java.util.List;
import java.util.ArrayList;

public class AnalisadorSintatico {
    private List<Token> tokens;
    private int indexToken;

    public AnalisadorSintatico(AnalisadorLexico lexico){
        tokens = lexico.getTokens();
        indexToken = 0;
    }

    public void executarAnalizadorSintatico(){
        Programa();
    }

    private Token getTokenAtual(){
        return tokens.get(indexToken);
    }

    private void Erro(){
        System.out.println("Erro sintatico");
    }

    private void match(Token esperado){
        Token atual = getTokenAtual();
        if(atual.getTipoToken() == esperado.getTipoToken()){
            indexToken++;
        } else {
            System.out.println("Tipo de token nao esperado");
        }
    }

    private boolean ehProximo(TipoToken tipo){
        Token token = tokens.get(indexToken+1);
        if(token.getTipoToken() == tipo){
            return true;
        }
        return false;
    }

    //<programa> ::= <declaração-lista>
    private void Programa(){
        DeclaracaoLista();
    }

    //<declaração-lista> ::= <declaração> {<declaração>}
    private void DeclaracaoLista(){
        Declaracao();
        if((EhProximo(Token.TipoToken.INT)) || (EhProximo(Token.TipoToken.FLOAT)) || (EhProximo(Token.TipoToken.CHAR)) || (EhProximo(Token.TipoToken.VOID)) || (EhProximo(Token.TipoToken.STRUCT))){
            DeclaracaoLista();
        }
    }

    private void Declaracao(){
        TipoEspecificador();
        Ident();
        Declaracao2();
    }

    private void Declaracao2(){
        if(ehProximo(Token.TipoToken.DELIM)){
            VarDeclaracao();
        } else if((ehProximo(Token.TipoToken.APARENTESES) || ehProximo(Token.TipoToken.ACOLCHETES))){
            FunDeclaracao();
        } else {
            Erro();
        }
    }

    private void VarDeclaracao(){
        if(ehProximo(Token.TipoToken.DELIM)){
            match(Token.TipoToken.DELIM);
        } else if(ehProximo(Token.TipoToken.ACOLCHETES)){
            match(Token.TipoToken.ACOLCHETES);
            NumInt();
            match(Token.TipoToken.FCOLCHETES);
            if(ehProximo(Token.TipoToken.ACOLCHETES)){
                VarDeclaracao();
            }
            match(Token.TipoToken.DELIM);
        } else {
            Erro();
        }
    }

    private void TipoEspecificador(){
        if(prox(Token.TipoToken.INT)){
            match(Token.TipoToken.INT);
        } else if(prox(Token.TipoToken.FLOAT)){
            match(Token.TipoToken.FLOAT);
        } else if(prox(Token.TipoToken.CHAR)){
            match(Token.TipoToken.CHAR);
        } else if(prox(Token.TipoToken.VOID)){
            match(Token.TipoToken.VOID);
        } else if(prox(Token.TipoToken.STRUCT)){
            match(Token.TipoToken.STRUCT);
            Ident();
            match(Token.TipoToken.ACHAVES);
            DeclaracaoLista();
            match(Token.TipoToken.FCHAVES);
        } else {
            Erro();
        }
    }

    private void FunDeclaracao(){
        match(Token.TipoToken.APARENTESES);
        Params();
        match(Token.TipoToken.FPARENTESES);
        CompostoDecl();
    }

    private void Params(){
        if(ehProximo(Token.TipoToken.VOID)){
            match(Token.TipoToken.VOID);
        } else {
            ParamLista();
        }
    }

    private void ParamLista(){
        Param();
        if(ehProximo(Token.TipoToken.DELIM)){  //colocar virgula no automato
            match(Token.TipoToken.DELIM); 
            ParamLista();
        }
    }

    private void Param(){
        TipoEspecificador();
        Ident();
        Param2();
    }

    private void Param2(){
        if(ehProximo(Token.TipoToken.ACOLCHETES)){
            match(Token.TipoToken.ACOLCHETES);
            match(Token.Tipo.FCOLCHETES);
        }
    }

    private void CompostoDecl(){
        match(Token.TipoToken.ACHAVES);
        LocalDeclaracoes();
        ComandoLista();
        match(Token.TipoToken.FCHAVES);
    }

    private void LocalDeclaracoes(){
        if((EhProximo(Token.TipoToken.INT)) || (EhProximo(Token.TipoToken.FLOAT)) || (EhProximo(Token.TipoToken.CHAR)) || (EhProximo(Token.TipoToken.VOID)) || (EhProximo(Token.TipoToken.STRUCT))){
            Declaracao();
            LocalDeclaracoes();
        }
    }
    
}