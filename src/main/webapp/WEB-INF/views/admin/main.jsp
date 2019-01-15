<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css"/>
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>       
    </head>    
    <body>
        <div class="navbar">
            <button class="collapsible">Venda</button>
            <div class="content">            
                <ul>
                    <li><a onclick="cadastrarComplemento()">Cadastrar complemento</a></li>
                    <li><a>Cadastrar gênero</a></li>
                    <li><a>Cadastrar item</a></li>                
                </ul>
            </div>                    
        </div>        
        <div id="tela" style="display: block; float: none; height: auto; padding-left: 10rem;"></div>
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
