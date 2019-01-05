<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <style>
            img {
                display: block;
                margin-left: auto;
                margin-right: auto;
            }
            div {
               align-items: center; 
               text-align: center;
            }
        </style>
    </head>
    <body>
        <div>
            <h4>
                Seu pagamento está sendo processado<br>
                Por favor aguarde
            </h4>
            <img src="${pageContext.request.contextPath}/resources/img/loading.gif" class="center" height="150%">            
        </div>
    </body>
</html>
