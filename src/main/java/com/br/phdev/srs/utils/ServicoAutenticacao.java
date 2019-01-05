/*
 * Copyright (C) Paulo Henrique Goncalves Bacelar, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gonacalves Bacelar <henrique.phgb@gmail.com>, Dezembro 2018
 */
package com.br.phdev.srs.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class ServicoAutenticacao {

    private final static String key = "ZXDas7966mby@dan";

    public String gerarToken(String keypass) {
        StringBuilder tokenHex = new StringBuilder();
        try {
            SecretKey key;
            byte[] mensagemEncriptada;
            key = new SecretKeySpec(this.key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            mensagemEncriptada = cipher.doFinal(keypass.getBytes());
            for (byte b : mensagemEncriptada) {
                tokenHex.append(String.format("%02X", 0xFF & b));
            }
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException
                | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return tokenHex.toString();
    }

    public String gerarHashSenha(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
        byte textoDigerido[] = algoritmo.digest(senha.getBytes("UTF-8"));
        StringBuilder tokenHex = new StringBuilder();
        for (byte b : textoDigerido) {
            tokenHex.append(String.format("%02X", 0xFF & b));
        }
        return tokenHex.toString();
    }

}
