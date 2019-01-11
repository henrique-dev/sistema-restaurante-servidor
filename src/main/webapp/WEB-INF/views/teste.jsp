<%-- 
    Document   : teste
    Created on : 08/01/2019, 12:46:50
    Author     : Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <div id="paypal-button"></div>
    <script type="text/javascript" src="https://stc.sandbox.pagseguro.uol.com.br/pagseguro/api/v2/checkout/pagseguro.directpayment.js"></script>
    <script>
        function getToken() {
            PagSeguroDirectPayment.setSessionId('${tokenSessao}');
            PagSeguroDirectPayment.onSenderHashReady(function (response) {
                if (response.status == 'error') {
                    console.log(response.message);
                    return false;
                }
                var hash = response.senderHash; //Hash estará disponível nesta variável.
                console.log(hash);
            });
            var param = {
                cardNumber: '5090910933556102',
                brand: 'elo',
                cvv: '668',
                expirationMonth: '05',
                expirationYear: '2021',
                success: function (response) {
                    console.log(response);
                },
                error: function (response) {
                    console.log(response);
                },
                complete: function (response) {
                    
                }
            };
            PagSeguroDirectPayment.createCardToken(param);
        }
    </script>    
</head>
<body>
    <button onclick="getToken()">Pagar</button>    
</body>
</html>
