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
            console.log("CARREGANDO COMPLEMENTOS");
            $("#main-" + opcao).css({"background-color": "#555", "color": "white"});
            $.get("complementos", {}, function (data) {
                $("#tela").html(data);
            });
            break;
        case 1:
            console.log("CARREGANDO GENEROS");
            $("#main-" + opcao).css({"background-color": "#555", "color": "white"});
            $.get("generos", {}, function (data) {
                $("#tela").html(data);
            });
            break;
        case 2:
            console.log("CARREGANDO ITENS");
            $("#main-" + opcao).css({"background-color": "#555", "color": "white"});
            $.get("itens", {}, function (data) {
                $("#tela").html(data);
            });
            break;
    }
}

function mostrarDialogo(mensagem) {
    document.getElementById("dialogMessage").innerHTML = mensagem;
    $("#dialogWindow").css("visibility", "visible");
    $("#dialogFrame").css("visibility", "visible");
    $("#dialogButton").click(function () {
        $("#dialogWindow").css("visibility", "hidden");
        $("#dialogFrame").css("visibility", "hidden");
    });
}

function adicionarVariacao() {
    var tabela = document.getElementById("tabelaVariacao");
    var quantidadeLinhas = document.getElementById("tabelaVariacao").getElementsByTagName("tr").length;    
    var linha = tabela.insertRow(quantidadeLinhas);
    linha.classList.add("conteudo");    
    var cNome = linha.insertCell(0);    
    var cPreco = linha.insertCell(1);
    var cMax = linha.insertCell(2);
    var cOrdem = linha.insertCell(3);

    cNome.innerHTML = "Teste";    
    cNome.style.textAlign = "center";
    cPreco.innerHTML = "2.50";    
    cPreco.style.textAlign = "center";
    cMax.innerHTML = "Teste";    
    cMax.style.textAlign = "center";
    cOrdem.innerHTML = "Teste";    
    cOrdem.style.textAlign = "center";
}

function adicionarComplemento() {
    var nome = document.getElementById("nomeComplemento").value;
    var preco = document.getElementById("precoComplemento").value;
    var arquivo = document.getElementById("arquivoComplemento").files[0];
    if (nome === null || nome.length === 0) {
        mostrarDialogo("Insira um nome valido para o complemento");
        return;
    }
    if (preco === null || preco.length === 0) {
        mostrarDialogo("Insira um preco valido para o complemento");
        return;
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