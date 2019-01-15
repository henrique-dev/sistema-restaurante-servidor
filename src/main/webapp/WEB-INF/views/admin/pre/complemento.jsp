<%-- 
    Document   : complemento
    Created on : 14/01/2019, 15:41:15
    Author     : Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="tela-complementos">
    <form>
        <label>Nome: </label>
        <input type="text"/><br>
        <label>Preço: </label>
        <input type="number"/><br>
        <label>Arquivo de imagem: </label>
        <input type="file"/><br>
    </form>    
    <div>
        <table id="tabela-complementos">       
            <tr>
                <th>Nome</th>
                <th>Preço</th>                    
            </tr>
            <c:forEach items="${listaComplementos}" var="complemento">
                <tr>
                    <th>${complemento.nome}</th>
                    <th>${complemento.preco}</th>
                </tr>
            </c:forEach>
        </table>        
    </div>    
</div>