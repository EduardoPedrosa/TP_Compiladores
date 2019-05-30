import java.util.List;
import java.util.ArrayList;

public class AnalisadorSintatico {
    private List<Token> tokens;
    private int indexToken;

    public AnalisadorSintatico(AnalisadorLexico lexico){
        tokens = lexico.getTokens();
        indexToken = 0;
    }

    public void executarAnaliseSintatica(){
        Programa();
    }

    private Token getTokenAtual(){
        return tokens.get(indexToken);
    }

    private void Erro(){
        System.out.println("Erro sintatico");
    }

    private void match(Token.TipoToken esperado){
        Token atual = getTokenAtual();
        if(atual.getTipoToken() == esperado){
            indexToken++;
        } else {
            System.out.println("Tipo de token nao esperado");
        }
    }

    private boolean ehProximo(Token.TipoToken tipo){
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
        if((ehProximo(Token.TipoToken.INT)) || (ehProximo(Token.TipoToken.FLOAT)) || (ehProximo(Token.TipoToken.CAR)) || (ehProximo(Token.TipoToken.VOID)) || (ehProximo(Token.TipoToken.STRUCT))){
            DeclaracaoLista();
        }
    }

    private void VarDeclaracao(){
        TipoEspecificador();
        Ident();
        VarDeclaracaoAux();
    }

    private void AtributosDeclaracao(){
        VarDeclaracao();
        VarDeclaracaoLoop();
    }

    private void VarDeclaracaoLoop(){
        if((ehProximo(Token.TipoToken.INT)) || (ehProximo(Token.TipoToken.FLOAT)) || (ehProximo(Token.TipoToken.CAR)) || (ehProximo(Token.TipoToken.VOID)) || (ehProximo(Token.TipoToken.STRUCT))){
            VarDeclaracao();
            VarDeclaracaoLoop();
        }
    }

    private void FunDeclaracao(){
        TipoEspecificador();
        Ident();
        match(Token.TipoToken.APARENTESES);
        Params();
        match(Token.TipoToken.FPARENTESES);
        CompostoDecl();
    }

    private void ParamLista(){
        Param();
        if(ehProximo(Token.TipoToken.VIRGULA)){  
            match(Token.TipoToken.DELIM); 
            ParamLista();
        }
    }

    private void CompostoDecl(){
        match(Token.TipoToken.ACHAVES);
        LocalDeclaracoes();
        ComandoLista();
        match(Token.TipoToken.FCHAVES);
    }

    private void Param(){
        TipoEspecificador();
        Ident();
        Param2();
    }

    private void LocalDeclaracoes(){
        if((ehProximo(Token.TipoToken.INT)) || (ehProximo(Token.TipoToken.FLOAT)) || (ehProximo(Token.TipoToken.CAR)) || (ehProximo(Token.TipoToken.VOID)) || (ehProximo(Token.TipoToken.STRUCT))){
            Declaracao();
            LocalDeclaracoes();
        }
    }

    private void ComandoLista(){
        if((ehProximo(Token.TipoToken.ID)) || (ehProximo(Token.TipoToken.APARENTESES)) || (ehProximo(Token.TipoToken.OPAR)) || (ehProximo(Token.TipoToken.NUM)) || (ehProximo(Token.TipoToken.NUMFLOAT)) || (ehProximo(Token.TipoToken.ACHAVES)) || (ehProximo(Token.TipoToken.IF)) || (ehProximo(Token.TipoToken.WHILE)) || (ehProximo(Token.TipoToken.RETURN))){
            Comando();
            ComandoLista();
        }
    }

    private void SelecaoDecl(){
        match(Token.TipoToken.IF);
        match(Token.TipoToken.APARENTESES);
        Expressao();
        match(Token.TipoToken.FPARENTESES);
        Comando();
        selecaoDeclAux();
    }

    private void IteracaoDecl(){
        match(Token.TipoToken.WHILE);
        match(Token.TipoToken.APARENTESES);
        Expressao();
        match(Token.TipoToken.FPARENTESES);
        Comando();
    }

    private void Var(){
        Ident();
        VarAux();
    }

    private void ExpressaoSimples(){
        ExpressaoSoma();
        ExpressaoSomaAux();
    }

    private void ExpressaoSoma(){
        Termo();
        ExpressaoSomaLoop();
    }

    private void ExpressaoSomaLoop(){
        if(ehProximo(Token.TipoToken.OPAR)){
            Soma();
            Termo();
            ExpressaoSomaLoop();
        }
    }

    private void Termo(){
        Fator();
        TermoLoop();
    }

    private void TermoLoop(){
        if(ehProximo(Token.TipoToken.OPMULT)){
            Mult();
            Fator();
            TermoLoop();
        }
    }

    private void Ativacao(){
        Ident();
        match(Token.TipoToken.APARENTESES);
        Args();
        match(Token.TipoToken.FPARENTESES);
    }

    private void Args(){
        if((ehProximo(Token.TipoToken.ID)) || (ehProximo(Token.TipoToken.APARENTESES)) || (ehProximo(Token.TipoToken.NUM)) || (ehProximo(Token.TipoToken.NUMFLOAT))){
            ArgLista();
        }
    }

    private void ArgLista(){
        Expressao();
        ArgListaLoop();
    }

    private void ArgListaLoop(){
        if(ehProximo(Token.TipoToken.VIRGULA)){
            match(Token.TipoToken.VIRGULA);
            Expressao();
            ArgListaLoop();
        }
    }

    private void Num(){
        match(Token.TipoToken.NUMFLOAT);
    }

    private void NumInt(){
        match(Token.TipoToken.NUM);
    }

    private void Ident(){
        match(Token.TipoToken.ID);
    }

    /*private void Declaracao(){
        TipoEspecificador();
        Ident();
        Declaracao2();
    }*/

    /*private void Declaracao2(){
        if(ehProximo(Token.TipoToken.DELIM)){
            VarDeclaracao();
        } else if((ehProximo(Token.TipoToken.APARENTESES) || ehProximo(Token.TipoToken.ACOLCHETES))){
            FunDeclaracao();
        } else {
            Erro();
        }
    }*/

    /*private void VarDeclaracao(){
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
    }*/

    /*private void TipoEspecificador(){
        if(ehProximo(Token.TipoToken.INT)){
            match(Token.TipoToken.INT);
        } else if(ehProximo(Token.TipoToken.FLOAT)){
            match(Token.TipoToken.FLOAT);
        } else if(ehProximo(Token.TipoToken.CAR)){
            match(Token.TipoToken.CAR);
        } else if(ehProximo(Token.TipoToken.VOID)){
            match(Token.TipoToken.VOID);
        } else if(ehProximo(Token.TipoToken.STRUCT)){
            match(Token.TipoToken.STRUCT);
            Ident();
            match(Token.TipoToken.ACHAVES);
            DeclaracaoLista();
            match(Token.TipoToken.FCHAVES);
        } else {
            Erro();
        }
    }

    
    private void Params(){
        if(ehProximo(Token.TipoToken.VOID)){
            match(Token.TipoToken.VOID);
        } else {
            ParamLista();
        }
    }

    

    

    private void Param2(){
        if(ehProximo(Token.TipoToken.ACOLCHETES)){
            match(Token.TipoToken.ACOLCHETES);
            match(Token.TipoToken.FCOLCHETES);
        }
    }*/

    
    
}