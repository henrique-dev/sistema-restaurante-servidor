<%-- 
    Document   : login
    Created on : 14/01/2019, 20:12:16
    Author     : Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Entrar</title>
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>       
    </head>
    <body>
        <form method="POST" action="autenticar">
            <label>Usuário</label>
            <input id="nome" type="text" name="nomeUsuario"><br>
            <label>Senha</label>
            <input id="senha" type="password" name="senhaUsuario"><br>        
            <input type="submit" value="Entrar">
        </form>        
    </body>
</html>
