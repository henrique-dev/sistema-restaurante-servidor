/* 
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
function trocarTela(opcao) {    
    $("#main-0").css({"background-color": "#f1f1f1", "color": "black"});
    $("#main-1").css({"background-color": "#f1f1f1", "color": "black"});
    $("#main-2").css({"background-color": "#f1f1f1", "color": "black"});
    $("#main-3").css({"background-color": "#f1f1f1", "color": "black"});
    $("#main-4").css({"background-color": "#f1f1f1", "color": "black"});
    switch (opcao) {
        case 0:
            $("#main-" + opcao).css({"background-color": "#555", "color": "white"});
            $.get("complementos", {}, function (data) {
                $("#tela").html(data);
            });
            break;
        case 1:
            $("#main-" + opcao).css({"background-color": "#555", "color": "white"});
            $.get("generos", {}, function (data) {
                $("#tela").html(data);
            });
        case 2:
            break;
    }
}

function mostrarDialogo(mensagem) {    
    document.getElementById("dialogMessage").innerHTML = mensagem;
    $("#tela").css("background-color", "rgba(0,0,0,0.4)");
    $("#dialogFrame").css("visibility", "visible");   
    $("#dialogButton").one("onClick", null, function () {
        console.log("OK");
    });    
}

function adicionarComplemento() {
    var nome = document.getElementById("nomeComplemento").value;
    var preco = document.getElementById("precoComplemento").value;
    var arquivo = document.getElementById("arquivoComplemento").files[0];
    if (nome === null || nome.length === 0) {
        mostrarDialogo("Insira um nome válido");
        return ;
    }
    
    $(".tela").css("opacity", "0.3");
    $(".loader").css("visibility", "visible");
    var formData = new FormData();
    formData.append("arquivo", arquivo);
    var reqSalvarImagem = new XMLHttpRequest();
    reqSalvarImagem.open("POST", "salvar-imagem");
    reqSalvarImagem.onreadystatechange = function () {
        if (reqSalvarImagem.readyState === XMLHttpRequest.DONE && reqSalvarImagem.status === 200) {
            var resposta = JSON.parse(reqSalvarImagem.response);
            var complemento = JSON.stringify(
                    {
                        nome: nome,
                        preco: preco,
                        foto: {
                            id: parseInt(resposta.descricao)
                        }
                    }
            );
            var request = new XMLHttpRequest();
            request.open("POST", "cadastrar-complemento");
            request.setRequestHeader("Content-Type", "application/json");
            request.onreadystatechange = function () {
                if (request.readyState === XMLHttpRequest.DONE && request.status === 200) {
                    trocarTela(0);
                    $(".tela").css("opacity", "1");
                    $(".loader").css("visibility", "hidden");
                }
            };
            request.send(complemento);
        }
    };
    reqSalvarImagem.send(formData);
}