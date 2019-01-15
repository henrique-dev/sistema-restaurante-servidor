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
    </head>
    <body>
        <label>Usuário</label>
        <input id="nome" type="text"><br>
        <label>Senha</label>
        <input id="senha" type="password"><br>        
        <button onclick="autenticar()"></button>
    </body>
</html>
