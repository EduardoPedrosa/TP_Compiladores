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
        Token token = getTokenAtual();
        System.out.println("Erro sintatico na linha " + token.getNumLinha() + " coluna " + token.getNumColuna() + " " + token);
    }

    private void match(Token.TipoToken esperado){
        Token atual = getTokenAtual();
        if(atual.getTipoToken() == esperado){
            indexToken++;
        } else {
            System.out.println("Tipo de token nao esperado na linha " + atual.getNumLinha() + " coluna " + atual.getNumColuna());
        }
    }

    private boolean ehProximo(Token.TipoToken tipo){
        Token token = tokens.get(indexToken);
        if(token.getTipoToken() == tipo){
            return true;
        }
        return false;
    }

    // <programa> ::= <declaração-lista>
    private void Programa(){
        DeclaracaoLista();
    }

    // <declaração-lista> ::= <declaração> {<declaração>}
    private void DeclaracaoLista(){
        Declaracao();
        if((ehProximo(Token.TipoToken.INT)) || (ehProximo(Token.TipoToken.FLOAT)) || (ehProximo(Token.TipoToken.CAR)) || (ehProximo(Token.TipoToken.VOID)) || (ehProximo(Token.TipoToken.STRUCT))){
            DeclaracaoLista();
        } else if(!ehProximo(Token.TipoToken.FIM)){
            Erro();
        }
    }

    // 4.1 <var-declaracao> ::= <tipo-especificador> <ident> <var-declaracao-aux> ## 1 regra
    private void VarDeclaracao(){
        TipoEspecificador();
        Ident();
        VarDeclaracaoAux();
    }

    // 6. <atributos-declaracao> ::= <var-declaracao> <var-declaracao-loop>  ## 1 regra
    private void AtributosDeclaracao(){
        VarDeclaracao();
        VarDeclaracaoLoop();
    }

    // 6.1 <var-declaracao-loop> ::= {<var-declaracao>}  ## 1 regra
    private void VarDeclaracaoLoop(){
        if((ehProximo(Token.TipoToken.INT)) || (ehProximo(Token.TipoToken.FLOAT)) || (ehProximo(Token.TipoToken.CAR)) || (ehProximo(Token.TipoToken.VOID)) || (ehProximo(Token.TipoToken.STRUCT))){
            VarDeclaracao();
            VarDeclaracaoLoop();
        } else if(!ehProximo(Token.TipoToken.FIM)){
            System.out.println("aqui");
            Erro();
        }
    }

    // 7. <fun-declaracao> ::= <tipo-especificador> <ident> ( <params> ) <composto-decl> ## 1 regra
    private void FunDeclaracao(){
        TipoEspecificador();
        Ident();
        match(Token.TipoToken.APARENTESES);
        Params();
        match(Token.TipoToken.FPARENTESES);
        CompostoDecl();
    }

    // 9. <param-lista> ::= <param> {, <param>} ## 1 regra
    private void ParamLista(){
        Param();
        if(ehProximo(Token.TipoToken.VIRGULA)){  
            match(Token.TipoToken.DELIM); 
            ParamLista();
        }
    }

    // 11. <composto-decl> ::= <abre-chave> <local-declaracoes> <comando-lista> <fecha-chave> ## 1 regra
    private void CompostoDecl(){
        match(Token.TipoToken.ACHAVES);
        LocalDeclaracoes();
        ComandoLista();
        match(Token.TipoToken.FCHAVES);
    }

    // 10.1 <param> ::= <tipo-especificador> <ident> <param-aux> ## 1 regra
    private void Param(){
        TipoEspecificador();
        Ident();
        ParamAux();
    }

    // 12. <local-declaracoes> ::= {<var-declaracao>} ## 1 regra
    private void LocalDeclaracoes(){
        if((ehProximo(Token.TipoToken.INT)) || (ehProximo(Token.TipoToken.FLOAT)) || (ehProximo(Token.TipoToken.CAR)) || (ehProximo(Token.TipoToken.VOID)) || (ehProximo(Token.TipoToken.STRUCT))){
            Declaracao();
            LocalDeclaracoes();
        }
    }

    // 13. <comando-lista> ::= { <comando> } ## 1 regra
    private void ComandoLista(){
        if((ehProximo(Token.TipoToken.ID)) || (ehProximo(Token.TipoToken.APARENTESES)) || (ehProximo(Token.TipoToken.OPAR)) || (ehProximo(Token.TipoToken.NUM)) || (ehProximo(Token.TipoToken.NUMFLOAT)) || (ehProximo(Token.TipoToken.ACHAVES)) || (ehProximo(Token.TipoToken.IF)) || (ehProximo(Token.TipoToken.WHILE)) || (ehProximo(Token.TipoToken.RETURN))){
            Comando();
            ComandoLista();
        }
    }

    // 16.1 <selecao-decl> ::= if ( <expressao> ) <comando> <selecao-decl-aux> ## 1 regra
    private void SelecaoDecl(){
        match(Token.TipoToken.IF);
        match(Token.TipoToken.APARENTESES);
        Expressao();
        match(Token.TipoToken.FPARENTESES);
        Comando();
        SelecaoDeclAux();
    }

    // 18. <iteracao-decl> ::= while ( <expressao> ) <comando> ## 1 regra
    private void IteracaoDecl(){
        match(Token.TipoToken.WHILE);
        match(Token.TipoToken.APARENTESES);
        Expressao();
        match(Token.TipoToken.FPARENTESES);
        Comando();
    }

    // 21.1 <var> ::= <ident> <var-aux> ## 1 regra
    private void Var(){
        Ident();
        VarAux();
    }

    // 22.1 <expressao-simples> ::= <expressao-soma> <expressao-soma-aux> ## 1 regra   
    private void ExpressaoSimples(){
        ExpressaoSoma();
        ExpressaoSomaAux(); 
    }

    // 25.01 <expressao-soma-aux> ::= {<soma> <termo>} ##1 regra
    private void ExpressaoSomaAux(){
        if(ehProximo(Token.TipoToken.OPAR)){
            Soma();
            Termo();
            ExpressaoSomaAux();
        }
    }

    // 25. <expressao-soma> ::= <termo> <expressao-soma-loop> ## 1 regra
    private void ExpressaoSoma(){
        Termo();
        ExpressaoSomaLoop();
    }

    // 25.1 <expressao-soma-loop> ::= {<soma> <termo>} ## 1 regra
    private void ExpressaoSomaLoop(){
        if(ehProximo(Token.TipoToken.OPAR)){
            Soma();
            Termo();
            ExpressaoSomaLoop();
        }
    }

    // 27. <termo> ::= <fator> <termo-loop> ## 1 regra
    private void Termo(){
        Fator();
        TermoLoop();
    }

    // 27.1 <termo-loop> ::= {<mult> <fator>} ## 1 regra
    private void TermoLoop(){
        if(ehProximo(Token.TipoToken.OPMULT)){
            Mult();
            Fator();
            TermoLoop();
        }
    }

    // 30. <ativacao> ::= <ident> ( <args> ) ## 1 regra
    private void Ativacao(){
        Ident();
        match(Token.TipoToken.APARENTESES);
        Args();
        match(Token.TipoToken.FPARENTESES);
    }

    // 31. <args> ::= [<arg-lista>] ## 1 regra
    private void Args(){
        if((ehProximo(Token.TipoToken.ID)) || (ehProximo(Token.TipoToken.APARENTESES)) || (ehProximo(Token.TipoToken.NUM)) || (ehProximo(Token.TipoToken.NUMFLOAT))){
            ArgLista();
        }
    }

    // 32. <arg-lista> ::= <expressao> <arg-lista-loop> ## 1 regra
    private void ArgLista(){
        Expressao();
        ArgListaLoop();
    }

    // 32.1 <arg-lista-loop> ::= {, <expressao>} ## 1 regra
    private void ArgListaLoop(){
        if(ehProximo(Token.TipoToken.VIRGULA)){
            match(Token.TipoToken.VIRGULA);
            Expressao();
            ArgListaLoop();
        }
    }

    // 33. <num> ::= [+ | -] <dıgito> {<dıgito>} [. <dıgito> {<dıgito>}] [E [+ | -] <dıgito> {<dıgito>}] ## 1 regra
    private void Num(){
        match(Token.TipoToken.NUMFLOAT);
    }

    // 34. <num-int> ::= <dıgito> {<dıgito>} ## 1 regra
    private void NumInt(){
        match(Token.TipoToken.NUM);
    }

    // 36. <ident> ::= <letra> {<letra> | <dıgito>} ## 1 regra
    private void Ident(){
        match(Token.TipoToken.ID);
    }

    // 3. <declaracao> ::= <tipo-especificador> <ident> <declaracao-aux> ## 1 regras
    private void Declaracao(){
        TipoEspecificador();
        Ident();
        DeclaracaoAux();
    }

    // 4.01 <declaracao-aux> ::= <var-declaracao-aux> | <fun-declaracao-aux>
    private void DeclaracaoAux(){
        if((ehProximo(Token.TipoToken.DELIM)) || (ehProximo(Token.TipoToken.ACOLCHETES))){
            VarDeclaracaoAux();
        } else if(ehProximo(Token.TipoToken.APARENTESES)){
            FunDeclaracaoAux();
        } else {
            Erro();
        }
    }

    // 4. <var-declaracao-aux> ::= ; | <abre-colchete> <num-int> <fecha-colchete>  <var-declaracao-aux-loop>; ## 2 regras
    private void VarDeclaracaoAux(){
        if(ehProximo(Token.TipoToken.DELIM)){
            match(Token.TipoToken.DELIM);
        } else if(ehProximo(Token.TipoToken.ACOLCHETES)){
            match(Token.TipoToken.ACOLCHETES);
            NumInt();
            match(Token.TipoToken.FCOLCHETES);
            VarDeclaracaoAuxLoop();
        } else {
            Erro();
        }
    }

    // 4.2 <var-declaracao-aux-loop> ::= {<abre-colchete> <num-int> <fecha-colchete>}
    private void VarDeclaracaoAuxLoop(){
        if(ehProximo(Token.TipoToken.ACOLCHETES)){
            match(Token.TipoToken.ACOLCHETES);
            NumInt();
            match(Token.TipoToken.FCOLCHETES);
            VarDeclaracaoAuxLoop();
        }
    }

    // 7. <fun-declaracao-aux> ::= ( <params> ) <composto-decl> ## 1 regra
    private void FunDeclaracaoAux(){
        match(Token.TipoToken.APARENTESES);
        Params();
        match(Token.TipoToken.FPARENTESES);
        CompostoDecl();
    }

    // 8. <params> ::= <param-lista> | void ## 2 regras
    private void Params(){
        if((ehProximo(Token.TipoToken.INT)) || (ehProximo(Token.TipoToken.FLOAT)) || (ehProximo(Token.TipoToken.CAR)) || (ehProximo(Token.TipoToken.VOID)) || (ehProximo(Token.TipoToken.STRUCT))){
            ParamLista();
        }
    }

    // 10. <param-aux> ::= #vazio# | <abre-colchete> <fecha-colchete> ## 2 regras
    private void ParamAux(){
        if(ehProximo(Token.TipoToken.ACOLCHETES)){
            match(Token.TipoToken.ACOLCHETES);
            match(Token.TipoToken.FCOLCHETES);
        }
    }

    // 15. <expressao-decl> ::= <expressao> ; | ; ## 2 regras
    private void ExpressaoDecl(){
        Expressao();   //verificar necessidade do ponto e virgula
    }

    // 16. <selecao-decl-aux> ::= #vazio# | else <comando> ## 2 regras
    private void SelecaoDeclAux(){
        if(ehProximo(Token.TipoToken.ELSE)){
            match(Token.TipoToken.ELSE);
            Comando();
        }
    }

    // 19. <retorno-decl> ::= return <retorno-decl-aux>  ## 1 regra
    private void RetornoDecl(){
        match(Token.TipoToken.RETURN);
        RetornoDeclAux();
    }

    // 19.1 <retorno-decl-aux ::= ; | <expressao> ; ## 2 regras
    private void RetornoDeclAux(){
        if(ehProximo(Token.TipoToken.DELIM)){
            match(Token.TipoToken.DELIM);
        } else {
            Expressao();
            match(Token.TipoToken.DELIM);
        }
    }

    // 20. <expressao> ::= <ident> <expressao-2> | | <expressao-simples-2>
    private void Expressao(){ //erro
        if(ehProximo(Token.TipoToken.ID)){
            Ident();
            Expressao2();
        } else if((ehProximo(Token.TipoToken.APARENTESES)) || (ehProximo(Token.TipoToken.NUM)) || (ehProximo(Token.TipoToken.NUMFLOAT))){
            ExpressaoSimples();
        } else {
            Erro();
        }
    }

    // 20.1 <expressao-2> ::= <var-2> = <expressao> 
    private void Expressao2(){
        if((ehProximo(Token.TipoToken.APARENTESES)) || (ehProximo(Token.TipoToken.NUM)) || (ehProximo(Token.TipoToken.APARENTESES)) ){
            ExpressaoSimples2();
        } else {
            Var2();
            match(Token.TipoToken.ATRIB);
            Expressao();
        }   
        
    }

    // 20.2 <var-2> ::= <var-aux>
    private void Var2(){
        VarAux();
    }

    // 20.3 <expressao-simples-2> ::= <expressao-soma-2> <expressao-soma-aux>
    private void ExpressaoSimples2(){
        ExpressaoSoma2();
        ExpressaoSomaAux();
    }

    // 20.4 <expressao-soma-2> ::= <termo2><expressao-soma-loop>
    private void ExpressaoSoma2(){
        Termo2();
        ExpressaoSomaLoop();
    }

    // 20.5 <termo2> ::= <fator2><termo-loop>
    private void Termo2(){
        Fator2();
        TermoLoop();
    }

    // 20.6 <fator2> ::= ( <expressao> ) | <num> | <num-int> ## 4 regras
    private void Fator2(){
        if(ehProximo(Token.TipoToken.APARENTESES)){
            match(Token.TipoToken.APARENTESES);
            Expressao();
            match(Token.TipoToken.FPARENTESES);
        } else if (ehProximo(Token.TipoToken.NUMFLOAT)){
            Num();
        } else if (ehProximo(Token.TipoToken.NUM)){
            NumInt();
        } else {
            Erro();
        }
    }

    // 21. <var-aux> ::= #vazio# | <abre-colchete> <expressao> <fecha-colchete> {<abre-colchete> <expressao> <fecha-colchete>} ## 2 regras
    private void VarAux(){
        if(ehProximo(Token.TipoToken.ACOLCHETES)){
            match(Token.TipoToken.ACOLCHETES);
            Expressao();
            match(Token.TipoToken.FCOLCHETES);
            VarAux();
        }
    }

    // 22. <expressao-simples-aux> ::= <relacional> <expressao-soma> | #vazio# ## 2 regras
    private void ExpressaoSimplesAux(){
        if(ehProximo(Token.TipoToken.OPCOMP)){
            match(Token.TipoToken.OPCOMP);
            ExpressaoSoma();
        }
    }

    // 26. <soma> ::= + | - ## 2 regras
    private void Soma(){
        match(Token.TipoToken.OPAR);
    }    

    // s28. <mult> ::= * | / ## 2 regras
    private void Mult(){
        match(Token.TipoToken.OPMULT);
    }

    // 5. <tipo-especificador> ::= int | float | char | void | struct <ident> <abre-chave> <atributos-declaracao> <fecha-chave> ## 5 regras
    private void TipoEspecificador(){
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
            AtributosDeclaracao();
            match(Token.TipoToken.FCHAVES);
        } else {
            Erro();
        }
    }

    // 14. <comando> ::= <expressao-decl> | <composto-decl> | <selecao-decl> | <iteracao-decl> | <retorno-decl> ## 5 regras
    private void Comando(){
        if((ehProximo(Token.TipoToken.APARENTESES)) || (ehProximo(Token.TipoToken.ID)) || (ehProximo(Token.TipoToken.NUM)) || (ehProximo(Token.TipoToken.NUMFLOAT))){
            ExpressaoDecl();
        } else if(ehProximo(Token.TipoToken.ACHAVES)){
            CompostoDecl();
        } else if(ehProximo(Token.TipoToken.IF)){
            SelecaoDecl();
        } else if (ehProximo(Token.TipoToken.WHILE)){
            IteracaoDecl();
        } else if (ehProximo(Token.TipoToken.RETURN)){
            RetornoDecl();
        } else {
            Erro();
        }
    }

    // 30.1 <ativacao-aux> ::= ( <args> ) ## 1 regra
    private void AtivacaoAux(){
        match(Token.TipoToken.APARENTESES);
        Args();
        match(Token.TipoToken.FPARENTESES);
    }

    // 29. <fator> ::= ( <expressao> ) | <ident> <fator-aux> | <num> | <num-int> ## 5 regras
    private void Fator(){
        if(ehProximo(Token.TipoToken.APARENTESES)){
            match(Token.TipoToken.APARENTESES);
            Expressao();
            match(Token.TipoToken.FPARENTESES);
        } else if(ehProximo(Token.TipoToken.ID)){
            Ident();
            FatorAux();
        } else if (ehProximo(Token.TipoToken.NUMFLOAT)){
            Num();
        } else if (ehProximo(Token.TipoToken.NUM)){
            NumInt();
        } else {
            Erro();
        }
    }

    // 29.1 <fator-aux> ::= <var-aux> | <ativacao-aux>
    private void FatorAux(){
        if(ehProximo(Token.TipoToken.ACOLCHETES)){
            VarAux();
        } else if (ehProximo(Token.TipoToken.APARENTESES)){
            AtivacaoAux();
        }
    }
}