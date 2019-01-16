<%-- 
    Document   : genero
    Created on : 15/01/2019, 23:30:00
    Author     : Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="tela">
    <div class="loader"></div>
    <div id="dialogFrame">
        <p id="dialogMessage">aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</p>
        <button id="dialogButton">Entendi</button>    
    </div>
    <div class="painel">
        <div class="painel-form">
            <label>Nome: </label>
            <input id="nomeGenero" class="campo-nome" type="text"/><br>                                    
            <div style="text-align: right">
                <button onclick="adicionarGenero()">Salvar</button>
            </div>            
        </div>    
    </div>    
    <div class="painel">
        <table id="tabelaGenero" class="tabela">       
            <tr>
                <th>Nome</th>
                <th class="row-actions">Ações</th>
            </tr>
            <c:forEach items="${listaGeneros}" var="genero">
                <tr class="conteudo">
                    <td>${genero.nome}</td>                    
                    <td class="row-actions"><button>Modificar</button><button>Excluir</button></td>
                </tr>
            </c:forEach>
        </table>        
    </div>    
</div>
