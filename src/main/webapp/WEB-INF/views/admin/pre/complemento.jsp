<%-- 
    Document   : complemento
    Created on : 14/01/2019, 15:41:15
    Author     : Paulo Henrique Gon�alves Bacelar <henrique.phgb@gmail.com>
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="tela">
    <div class="loader"></div>
    <div id="dialogFrame">
        <p id="dialogMessage"></p>
        <button id="dialogButton">Entendi</button>    
    </div>
    <div class="painel">
        <div class="painel-form">
            <label>Nome: </label>
            <input id="nomeComplemento" class="campo-nome" type="text"/><br>
            
            <label>Pre�o: </label>
            <input id="precoComplemento" class="campo-valor" type="text"/><br>
            
            <label>Arquivo de imagem: </label>
            <input id="arquivoComplemento" class="campo-arquivo" type="file"/><br>
            
            <div style="text-align: right">
                <button onclick="adicionarComplemento()">Salvar</button>
            </div>            
        </div>    
    </div>    
    <div class="painel">
        <table id="tabelaComplemento" class="tabela">       
            <tr>
                <th>Nome</th>
                <th>Pre�o</th>                    
                <th class="row-actions">A��es</th>
            </tr>
            <c:forEach items="${listaComplementos}" var="complemento">
                <tr class="conteudo">
                    <td>${complemento.nome}</td>
                    <td>${complemento.preco}</td>
                    <td class="row-actions"><button>Modificar</button><button>Excluir</button></td>
                </tr>
            </c:forEach>
        </table>        
    </div>    
</div>