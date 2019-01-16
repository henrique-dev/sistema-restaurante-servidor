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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css"/>
        <title>Entrar</title>        
        <style>                
            .caixa-login {
                display: block;
                border-radius: 25px;                
                background: #ffebcc;
                margin: auto;         
                opacity: 0.95;
                position: relative;
                top: calc(50% - 175px);
                width: 500px;
                height: 350px;
                text-align: center;
            }            

            img {
                position: fixed;
                width: 100%;
                height: 100%;
                filter: blur(5px);
                opacity: 0.8;
            }

            form {
                position: relative;
                top: calc(50% - 55px);
            }

            label {
                display: inline-block;
                width: 25%;
                height: 30px;
            }

            input {
                display: inline-block;
                width: 65%;
                height: 30px;
            }

            button {
                margin-top: 1rem;
                margin-left: 5px;
                margin-right: 5px;
                display: inline-block;
                background: #ff9c33;
                border-bottom-color: #000;
                font-size: 18px;
                width: 40%;
                height: 40px;
            }
        </style>
    </head>    
    <body>
        <img src="${pageContext.request.contextPath}/resources/img/wp.jpg"/>
        <div class="caixa-login">
            <form id="formLogin"method="POST" action="autenticar">
                <label>Usuário</label>
                <input id="nome" type="text" name="nomeUsuario"><br>
                <label>Senha</label>
                <input id="senha" type="password" name="senhaUsuario"><br>        
                <button form="formLogin">Entrar</button>
                <button>Esqueci minha senha</button>
            </form>        
        </div>        
    </body>
</html>
