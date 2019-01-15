/* 
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
function cadastrarComplemento() {
    $.get("complementos", {}, function (data) {
        $("#tela").html(data);
    });
}

function autenticar() {
    var usuario = document.querySelector("#nome").value;
    var senha = document.querySelector("#senha").value;    
    $.post("autenticar", {nomeUsuario : usuario, senhaUsuario : senha}, function (request) {
        console.log(request);
    });    
}