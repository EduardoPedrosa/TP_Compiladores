import java.util.List;
import java.util.ArrayList;

public class AnalisadorSintatico {
    private List<Token> tokens;
    private int indexToken;

    public AnalisadorSintatico(AnalisadorLexico lexico){
        tokens = lexico.getTokens();
        indexToken = 0;
    }

    private Token getTokenAtual(){
        return tokens.get(indexToken);
    }

    private void Erro(){
        System.out.println("Erro sintatico");
    }

    public void match(Token esperado){
        Token atual = getTokenAtual();
        if(atual.getTipoToken() == esperado.getTipoToken()){
            indexToken++;
        } else {
            System.out.println("Tipo de token nao esperado");
        }
    }

    public boolean ehProximo(TipoToken tipo){
        Token token = tokens.get(indexToken+1);
        if(token.getTipoToken() == tipo){
            return true;
        }
        return false;
    } 

    public void Programa(){
        DeclaracaoLista();
    }

    public void DeclaracaoLista(){
        Declaracao();
        if((EhProximo(Token.TipoToken.INT)) || (EhProximo(Token.TipoToken.FLOAT)) || (EhProximo(Token.TipoToken.CHAR)) || (EhProximo(Token.TipoToken.VOID)) || (EhProximo(Token.TipoToken.STRUCT))){
            DeclaracaoLista();
        }
    }

    public void Declaracao(){
        TipoEspecificador();
        Ident();
        Declaracao2();
    }

    public void Declaracao2(){
        if(ehProximo(Token.TipoToken.DELIM)){
            VarDeclaracao();
        } else if((ehProximo(Token.TipoToken.APARENTESES) || ehProximo(Token.TipoToken.ACOLCHETES)){
            FunDeclaracao();
        } else {
            Erro();
        }
    }

    public void VarDeclaracao(){
        if(ehProximo(Token.TipoToken.DELIM)){
            match(Token.TipoToken.DELIM);
        } else if(ehProximo(Token.TipoToken.ACOLCHETES)){
            match(Token.TipoToken.ACOLCHETES);
            NumInt();
            match(Token.TipoToken.FCOLCHETES)
            if(ehProximo(Token.TipoToken.ACOLCHETES)){
                VarDeclaracao();
            }
            match(Token.TipoToken.DELIM);
        } else {
            Erro();
        }
    }

    public void TipoEspecificador(){
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

    public void FunDeclaracao(){
        match(Token.TipoToken.APARENTESES);
        Params();
        match(Token.TipoToken.FPARENTESES);
        CompostoDecl();
    }

    public void Params(){
        if(ehProximo(Token.TipoToken.VOID)){
            match(Token.TipoToken.VOID);
        } else {
            ParamLista();
        }
    }

    public void ParamLista(){
        Param();
        if(ehProximo(Token.TipoToken.DELIM)){  //colocar virgula no automato
            match(Token.TipoToken.DELIM); 
            ParamLista();
        }
    }

    public void Param(){
        TipoEspecificador();
        Ident();
        Param2();
    }

    public void Param2(){
        if(ehProximo(Token.TipoToken.ACOLCHETES)){
            match(Token.TipoToken.ACOLCHETES);
            match(Token.Tipo.FCOLCHETES);
        }
    }

    public void CompostoDecl(){
        match(Token.TipoToken.ACHAVES);
        LocalDeclaracoes();
        ComandoLista();
        match(Token.TipoToken.FCHAVES);
    }

    public void LocalDeclaracoes(){
        if((EhProximo(Token.TipoToken.INT)) || (EhProximo(Token.TipoToken.FLOAT)) || (EhProximo(Token.TipoToken.CHAR)) || (EhProximo(Token.TipoToken.VOID)) || (EhProximo(Token.TipoToken.STRUCT))){
            Declaracao();
            LocalDeclaracoes();
        }
    }
    
}