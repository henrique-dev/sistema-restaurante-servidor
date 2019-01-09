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
        <title>JSP Page</title>
        !-- Load the required checkout.js script -->
        <script src="https://www.paypalobjects.com/api/checkout.js" data-version-4></script>

        <!-- Load the required Braintree components. -->
        <script src="https://js.braintreegateway.com/web/3.39.0/js/client.min.js"></script>
        <script src="https://js.braintreegateway.com/web/3.39.0/js/paypal-checkout.min.js"></script>        
    <div id="paypal-button"></div>
    <script>
        paypal.Button.render({
            braintree: braintree,
            client: {
                sandbox: '${token}'
            },
            env: 'sandbox', // Or 'sandbox'
            commit: true, // This will add the transaction amount to the PayPal button

            payment: function (data, actions) {
                console.log(data);
                return actions.braintree.create({
                    flow: 'checkout', // Required
                    amount: 10.00, // Required
                    currency: 'BRL', // Required                   
                });
            },

            onAuthorize: function (data, actions) {
                console.log(data);
                return actions.request.post('http://localhost:8084/mrfood/pagamentos/set-pagamento', {                    
                    intent: data.intent,
                    orderID: data.orderID,
                    payerID: data.payerID,
                    paymentID: data.paymentID,                    
                    paymentToken: data.paymentToken,
                    nonce: data.nonce
                });
            },
        }, '#paypal-button');
    </script>
</head>
<body>

</body>
</html>
