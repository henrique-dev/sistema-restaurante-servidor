<%-- 
    Document   : item
    Created on : 16/01/2019, 10:34:59
    Author     : Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="tela">
    <div class="loader"></div>
    <div id="dialogFrame">
        <p id="dialogMessage"></p>
        <button id="dialogButton">Entendi</button>    
    </div>
    <div class="painel-left">
        <div class="painel-form">
            <div>
                <label>Nome: </label>
                <input id="nomeGenero" class="campo-nome" type="text"/><br>
            </div>            
            <div>
                <label>Preço: </label>
                <input id="nomeGenero" class="campo-valor" type="text"/><br>
            </div>            
            <div>
                <label>Descricao: </label>
                <textarea id="nomeGenero" class="campo-texto" rows="2" cols="75" maxlength="150"></textarea><br>
            </div>                        
        </div>
    </div>        
    <div class="painel-left" style="text-align: center; height: 180px">
        <div style="display: inline-block; width: calc(100% / 2.5 - 1rem); height: 100%; background: #f3f3f3; overflow-y: hidden;">
            <label style="width: 24%; display: inline-block;">Nome</label>
            <label style="width: 24%; display: inline-block;">Preço</label>
            <label style="width: 24%; display: inline-block;">Max</label>
            <label style="width: 24%; display: inline-block;">Ordem</label>
            <div style="overflow: auto; height: 100%; width: 100%; display: inline-block;">
                <table id="tabelaVariacao" class="tabela" style="height: auto; width: 100%; table-layout: fixed;">
                    <tbody style="width: 100%; height: auto;">                       
                        <tr class="conteudo">                            
                        </tr>
                    </tbody>
                </table>            
            </div>            
        </div>
        <div style="display: inline-block; width: calc(100% / 5 - 1rem); height: 100%; background: #f3f3f3; overflow-y: hidden; margin: auto">
            <button onclick="adicionarVariacao()" style="width: 100%">Adicionar variação</button>
            <button style="width: 100%">Remover variação</button>
            <button style="width: 100%">Adicionar grupo</button>
            <button style="width: 100%">Remover grupo</button>
        </div>
        <div style="display: inline-block; width: calc(100% / 2.5 - 1rem); height: 100%; background: #f3f3f3; overflow-y: hidden;">
            <label style="width: 24%; display: inline-block;">Id grupo</label>
            <label style="width: 24%; display: inline-block;">Max</label>
            <label style="width: 24%; display: inline-block;">Qtd variações</label>            
            <div style="overflow: auto; height: 100%; width: 100%; display: inline-block;">
                <table id="tabelaGrupoVariacao" class="tabela" style="height: auto; width: 100%; table-layout: fixed;">
                    <tbody style="width: 100%; height: auto;">                       
                        <tr class="conteudo">                            
                        </tr>
                    </tbody>
                </table>            
            </div>            
        </div>
    </div>
    <div class="painel-right">
        <table id="tabelaItem" class="tabela">       
            <tr>
                <th>Nome</th>
                <th class="row-actions">Ações</th>
            </tr>
            <c:forEach items="${listaItens}" var="item">
                <tr class="conteudo">
                    <td>${item.nome}</td>                    
                    <td class="row-actions"><button>Modificar</button><button>Excluir</button></td>
                </tr>
            </c:forEach>
        </table>        
    </div>    
</div>