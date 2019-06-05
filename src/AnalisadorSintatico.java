import java.util.List;
import java.util.ArrayList;

public class AnalisadorSintatico {
    private List<Token> tokens;
    private int indexToken;
    boolean erro;

    public AnalisadorSintatico(AnalisadorLexico lexico){
        tokens = lexico.getTokens();
        indexToken = 0;
        erro = false;
    }

    public void executarAnaliseSintatica(){
        Programa();
        match(Token.TipoToken.FIM);
        if(!erro){
            System.out.println("Analise sintatica realizada com sucesso");
        }
    }

    private Token getTokenAtual(){
        if(indexToken >= tokens.size()){
            indexToken = tokens.size()-1;
        }
        return tokens.get(indexToken);
    }

    private void Erro(){
        Token token = getTokenAtual();
        if(token.getTipoToken() != Token.TipoToken.FIM){
            System.out.println("Erro sintatico na linha " + token.getNumLinha() + " coluna " + token.getNumColuna() + ", encontrou " + token.getTipoToken());
        }
        erro = true;
    }

    private void match(Token.TipoToken esperado){
        Token atual = getTokenAtual();
        if(atual.getTipoToken() == esperado){
                ++indexToken;
        } else {
            System.out.println("Tipo de token nao esperado na linha " + atual.getNumLinha() + " coluna " + atual.getNumColuna() + " esperava " + esperado);
            erro = true;
            boolean acabou = false;
            while((atual.getTipoToken() != esperado)&&(!acabou)){
                if(!(atual.getTipoToken() == Token.TipoToken.FIM)){
                    ++indexToken;
                    atual = getTokenAtual();
                } else {
                    acabou = true;
                }
            }
            ++indexToken;
        }
    }
    //funcao ehProximo, recebe o token e quantos a frente vai dar uma espiada
    private boolean ehProximo(Token.TipoToken tipo, int i) {
        int indice = indexToken+(i-1);
        if(indice < tokens.size()){
            Token token = tokens.get(indice);
            if (token.getTipoToken() == tipo) {
                return true;
            }
        }
        return false;
    }

    private boolean ehProximo(Token.TipoToken tipo) {
        if(indexToken < tokens.size()){
            Token token = tokens.get(indexToken);
            if (token.getTipoToken() == tipo) {
                return true;
            }
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
        if((ehProximo(Token.TipoToken.INT)) || (ehProximo(Token.TipoToken.FLOAT)) || (ehProximo(Token.TipoToken.CHAR)) || (ehProximo(Token.TipoToken.VOID)) || (ehProximo(Token.TipoToken.STRUCT))){
            DeclaracaoLista();
        } else if(!ehProximo(Token.TipoToken.FIM)){
            Erro();
        }
    }

    // 3. <declaracao> ::= <tipo-especificador> <ident> <declaracao-aux> ## 1 regras
    private void Declaracao(){
        TipoEspecificador();
        Ident();
        DeclaracaoAux();
    }

    private void Declaracao2(){
        TipoEspecificador();
        Ident();
        match(Token.TipoToken.DELIM);
    }

    // 3.01 <declaracao-aux> ::= <var-declaracao-aux> | <fun-declaracao>
    private void DeclaracaoAux(){
        if((ehProximo(Token.TipoToken.DELIM))   || (ehProximo(Token.TipoToken.ACOLCHETES))){
            VarDeclaracaoAux();
        } else if(ehProximo(Token.TipoToken.APARENTESES)){
            FunDeclaracao();
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
        if((ehProximo(Token.TipoToken.INT)) || (ehProximo(Token.TipoToken.FLOAT)) || (ehProximo(Token.TipoToken.CHAR)) || (ehProximo(Token.TipoToken.VOID)) || (ehProximo(Token.TipoToken.STRUCT))){
            VarDeclaracao();
            VarDeclaracaoLoop();
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

    // 7. <fun-declaracao> ::= ( <params> ) <composto-decl> ## 1 regra
    private void FunDeclaracao(){
        match(Token.TipoToken.APARENTESES);
        Params();
        match(Token.TipoToken.FPARENTESES);
        CompostoDecl();
    }

    // 8. <params> ::= <param-lista> | void ## 2 regras
    private void Params(){
        if((ehProximo(Token.TipoToken.INT)) || (ehProximo(Token.TipoToken.FLOAT)) || (ehProximo(Token.TipoToken.CHAR)) || (ehProximo(Token.TipoToken.VOID)) || (ehProximo(Token.TipoToken.STRUCT))){
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
    private void ExpressaoDecl() {
        if (ehProximo(Token.TipoToken.DELIM)) {
            match(Token.TipoToken.DELIM);
        } else {
            Expressao();
            match(Token.TipoToken.DELIM);
        }
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


    //20
    private void Expressao(){
        if((ehProximo(Token.TipoToken.ID) || ehProximo(Token.TipoToken.ACOLCHETES)) && (ehProximo(Token.TipoToken.ACOLCHETES) || ehProximo(Token.TipoToken.ATRIB, 2))){
            Var();
            match(Token.TipoToken.ATRIB);
            Expressao();
        } else {
            ExpressaoSimples();
        }
    }

    //21
    private void Var(){
        if(ehProximo(Token.TipoToken.ID) && ehProximo(Token.TipoToken.ACOLCHETES, 2)){
            Ident();
            match(Token.TipoToken.ACOLCHETES);
            Expressao();
            match(Token.TipoToken.FCOLCHETES);
        } else if(ehProximo(Token.TipoToken.ACOLCHETES)) {
            match(Token.TipoToken.ACOLCHETES);
            Expressao();
            match(Token.TipoToken.FCOLCHETES);
        } else if(ehProximo(Token.TipoToken.ID)){
            Ident();
        } else if(!ehProximo(Token.TipoToken.FIM)){
            Erro();
        }
    }

    // 25.01 <expressao-soma-aux> ::= {<soma> <termo>} ##1 regra




    // 21. <var-aux> ::= #vazio# | <abre-colchete> <expressao> <fecha-colchete> {<abre-colchete> <expressao> <fecha-colchete>} ## 2 regras
    private void VarAux(){
        if(ehProximo(Token.TipoToken.ACOLCHETES)){
            match(Token.TipoToken.ACOLCHETES);
            Expressao();
            match(Token.TipoToken.FCOLCHETES);
            VarAux();
        }
    }

    // 22. <expressao-simples-aux> ::= <relacrm ional> <expressao-soma> | #vazio# ## 2 regras
    private void ExpressaoSimplesAux(){
        if(ehProximo(Token.TipoToken.OPCOMP)){
            match(Token.TipoToken.OPCOMP);
            ExpressaoSoma();
        }
    }

    // 22.1 <expressao-simples> ::= <expressao-soma> <expressao-soma-aux>
    private void ExpressaoSimples(){
        ExpressaoSoma();
        ExpressaoSimplesAux();
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
        } else if(ehProximo(Token.TipoToken.CHAR)){
            match(Token.TipoToken.CHAR);
        } else if(ehProximo(Token.TipoToken.VOID)){
            match(Token.TipoToken.VOID);
        } else if(ehProximo(Token.TipoToken.STRUCT)){
            match(Token.TipoToken.STRUCT);
            Ident();
            match(Token.TipoToken.ACHAVES);
            AtributosDeclaracao();
            match(Token.TipoToken.FCHAVES);
        } else if(!ehProximo(Token.TipoToken.FIM)){
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
        } else if(!ehProximo(Token.TipoToken.FIM)){
            Erro();
        }
    }


    // 9. <param-lista> ::= <param> {, <param>} ## 1 regra
    private void ParamLista(){
        Param();
        if(ehProximo(Token.TipoToken.VIRGULA)){  
            match(Token.TipoToken.VIRGULA);
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
        if((ehProximo(Token.TipoToken.INT)) || (ehProximo(Token.TipoToken.FLOAT)) || (ehProximo(Token.TipoToken.CHAR)) || (ehProximo(Token.TipoToken.VOID)) || (ehProximo(Token.TipoToken.STRUCT))){            
            Declaracao2();
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

    private void Fator(){
        if(ehProximo(Token.TipoToken.APARENTESES)){
            match(Token.TipoToken.APARENTESES);
            Expressao();
            match(Token.TipoToken.FPARENTESES);
        } else if(ehProximo(Token.TipoToken.NUM)){
            match(Token.TipoToken.NUM);
        } else if(ehProximo(Token.TipoToken.NUMFLOAT)){
            match(Token.TipoToken.NUMFLOAT);
        } else if (ehProximo(Token.TipoToken.CAR)){
            match(Token.TipoToken.CAR);
        } else if(ehProximo(Token.TipoToken.ID) && ehProximo(Token.TipoToken.APARENTESES, 2)){
            Ativacao();
        } else {
            Var();
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
        if((ehProximo(Token.TipoToken.ID)) || (ehProximo(Token.TipoToken.APARENTESES)) || (ehProximo(Token.TipoToken.NUM)) || (ehProximo(Token.TipoToken.NUMFLOAT) || (ehProximo(Token.TipoToken.CAR)))){
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




}