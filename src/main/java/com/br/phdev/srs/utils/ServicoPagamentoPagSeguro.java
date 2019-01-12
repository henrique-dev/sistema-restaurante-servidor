/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.utils;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.common.domain.ShippingType;
import br.com.uol.pagseguro.api.common.domain.builder.AddressBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.CreditCardBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.DocumentBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.HolderBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.InstallmentBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentItemBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PhoneBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.SenderBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.ShippingBuilder;
import br.com.uol.pagseguro.api.common.domain.enums.Currency;
import br.com.uol.pagseguro.api.common.domain.enums.DocumentType;
import br.com.uol.pagseguro.api.common.domain.enums.State;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.transaction.register.DirectPaymentRegistrationBuilder;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;
import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.exceptions.PaymentException;
import com.br.phdev.srs.http.HttpClient;
import com.br.phdev.srs.http.HttpConnection;
import com.br.phdev.srs.models.ExecutarPagamento;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class ServicoPagamentoPagSeguro {

    private final String email = "henrique.phgb@gmail.com";
    private final String token = "ECE837D33EE94BFFAF0364B256ACFC8C";

    public String criarTokenPagamento() {
        String tokenSessao = null;
        HttpURLConnection conexao = null;
        try {
            conexao = new HttpConnection().getConnection(
                    "https://ws.sandbox.pagseguro.uol.com.br/v2/sessions/?email=" + email + "&token=" + token);
            HttpClient cliente = new HttpClient(conexao, "POST");
            String resposta = cliente.retrieveString();
            tokenSessao = new XMLUtils().getFirstElement("id", resposta);
            ObjectMapper mapeador = new ObjectMapper();
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            if (conexao != null) {
                conexao.disconnect();
            }
        }
        return tokenSessao;
    }

    public String executarPagamento(ExecutarPagamento ep) throws PaymentException {
        System.out.println("EXECUTANDO PAGAMENTO");
        try {
            final PagSeguro pagSeguro = PagSeguro
                    .instance(new SimpleLoggerFactory(), new JSEHttpClient(),
                            Credential.sellerCredential(email, token), PagSeguroEnv.SANDBOX);
            
            System.out.println(ep.getConfirmaPedido().getEnderecos().get(0).getCep());
            System.out.println(ep.getConfirmaPedido().getEnderecos().get(0).getCidade());
            System.out.println(ep.getConfirmaPedido().getEnderecos().get(0).getComplemento());
            System.out.println(ep.getConfirmaPedido().getEnderecos().get(0).getBairro());
            System.out.println(ep.getConfirmaPedido().getEnderecos().get(0).getNumero());
            System.out.println(ep.getConfirmaPedido().getEnderecos().get(0).getLogradouro());
            System.out.println(ep.getCliente().getCodigoAreaTelefone());
            System.out.println(ep.getCliente().getTelefoneSemCodigoArea());            
            System.out.println(ep.getCliente().getNome());
            System.out.println(ep.getCliente().getCpf());
            System.out.println(ep.getConfirmaPedido().getPrecoTotal());
            System.out.println(ep.getCpf());
            System.out.println(ep.getNome());
            System.out.println(ep.getData());
            System.out.println(ep.getTokenCartao());
            System.out.println(ep.getHashCliente());            
            

            AddressBuilder endereco = new AddressBuilder()
                    .withPostalCode(ep.getConfirmaPedido().getEnderecos().get(0).getCep())
                    .withCountry("BRA")
                    .withState(State.AP)
                    .withCity(ep.getConfirmaPedido().getEnderecos().get(0).getCidade())
                    .withComplement(ep.getConfirmaPedido().getEnderecos().get(0).getComplemento())
                    .withDistrict(ep.getConfirmaPedido().getEnderecos().get(0).getBairro())
                    .withNumber(ep.getConfirmaPedido().getEnderecos().get(0).getNumero())
                    .withStreet(ep.getConfirmaPedido().getEnderecos().get(0).getLogradouro());                        

            PhoneBuilder telefone = new PhoneBuilder()
                    .withAreaCode(ep.getCliente().getCodigoAreaTelefone())
                    .withNumber(ep.getCliente().getTelefoneSemCodigoArea());

            TransactionDetail creditCardTransaction
                    = pagSeguro.transactions().register(new DirectPaymentRegistrationBuilder()
                            .withPaymentMode("default")
                            .withCurrency(Currency.BRL)
                            .withExtraAmount(new BigDecimal(0))
                            .withNotificationURL("http://35.202.51.59/mrfood/pagamentos/notificar3")
                            .addItem(new PaymentItemBuilder()
                                    .withId("1")
                                    .withDescription("Alimentícios")
                                    .withAmount(new BigDecimal(String.valueOf(ep.getConfirmaPedido().getPrecoTotal())))
                                    .withQuantity(1)
                                    .withWeight(500))
                            .withReference("mrfood_pagamento")
                            .withSender(new SenderBuilder()
                                    .withEmail("henrique.phgb@sandbox.pagseguro.com.br")
                                    .withName(ep.getCliente().getNome())
                                    .withCPF(ep.getCliente().getCpf())
                                    .withIp("127.0.0.0")
                                    .withHash(ep.getHashCliente())
                                    .withPhone(telefone))
                            .withShipping(new ShippingBuilder()
                                    .withType(ShippingType.Type.USER_CHOISE)
                                    .withCost(BigDecimal.ZERO)
                                    .withAddress(endereco)
                            )
                    ).withCreditCard(new CreditCardBuilder()
                            .withBillingAddress(endereco)
                            .withInstallment(new InstallmentBuilder()
                                    .withQuantity(1)
                                    .withValue(new BigDecimal(String.valueOf(ep.getConfirmaPedido().getPrecoTotal())))
                            )
                            .withHolder(new HolderBuilder()
                                    .addDocument(new DocumentBuilder()
                                            .withType(DocumentType.CPF)
                                            .withValue(ep.getCpf())
                                    )
                                    .withName(ep.getNome())
                                    .withBithDate(new SimpleDateFormat("dd/MM/yyyy").parse(ep.getData()))
                                    .withPhone(new PhoneBuilder()
                                            .withAreaCode(ep.getCliente().getCodigoAreaTelefone())
                                            .withNumber(ep.getCliente().getTelefoneSemCodigoArea()))
                            )
                            .withToken(ep.getTokenCartao())
                    );            
            System.out.println(creditCardTransaction);
            return creditCardTransaction.getCode();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PaymentException(e);
        }        
    }

}
