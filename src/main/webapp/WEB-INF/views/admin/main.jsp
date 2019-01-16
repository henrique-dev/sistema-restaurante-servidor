<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Principal</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css"/>
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>       
    </head>    
    <body>
        <div style="display: inline;">
            <div class="sidenav">
                <button class="collapsible">Venda</button>
                <div class="content">            
                    <ul>
                        <li id="main-3"><a>Pedidos</a></li>                        
                    </ul>
                </div>                    
                <button class="collapsible">Administração</button>
                <div class="content">            
                    <ul>
                        <li id="main-0"><a onclick="trocarTela(0)">Cadastrar complemento</a></li>
                        <li id="main-1"><a onclick="trocarTela(1)">Cadastrar gênero</a></li>
                        <li id="main-2"><a onclick="trocarTela(2)">Cadastrar item</a></li>                
                    </ul>
                </div>                    
            </div>        
            <div id="tela"></div>
        </div>        
        <script>
            var coll = document.getElementsByClassName("collapsible");
            var i;
            for (i = 0; i < coll.length; i++) {
                coll[i].addEventListener("click", function () {
                    this.classList.toggle("active");
                    var content = this.nextElementSibling;
                    if (content.style.maxHeight) {
                        content.style.maxHeight = null;
                    } else {
                        content.style.maxHeight = content.scrollHeight + "px";
                    }
                });
            }
        </script>
    </body>    
</html>
