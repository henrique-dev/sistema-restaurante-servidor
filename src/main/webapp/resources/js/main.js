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

function autenticar(nomeUusario, senhaUsuario) {
    var usuario = nomeUusario.value;
    var senha = senhaUsuario.value;    
    $.post("autenticar", {usuario : usuario, senha : senha}, function (data) {
        console.log(data);
    });
}