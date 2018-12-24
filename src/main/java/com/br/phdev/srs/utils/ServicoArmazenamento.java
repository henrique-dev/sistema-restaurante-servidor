/* Copyright (C) HeadRed, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gona�alves Bacelar <henrique.phgb@gmail.com>, Agosto 2018
 */
package com.br.phdev.srs.utils;

import com.br.phdev.srs.exceptions.StorageException;
import com.br.phdev.srs.models.Complemento;
import com.br.phdev.srs.models.Foto;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar
 */
public class ServicoArmazenamento {
    
    private static final String MASTER_PATH = "/home/henrique/Documentos/Teste/";       
    
    
    public void salvar(MultipartFile arquivo, long id) throws StorageException {                
        try {
            byte[] bytes = arquivo.getBytes();            
            File file = new File(MASTER_PATH);
            if (!file.exists()) {                
                file.mkdirs();
            }           
            //Date currentDate = Calendar.getInstance().getTime();
            //String dateParsed = new SimpleDateFormat("dd-MM-yyyy-hh-mm-s-ms").format(currentDate);            
            file = new File(MASTER_PATH + id);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(bytes);
                fos.flush();
            }            
        } catch (IOException e) {
            throw new StorageException("Falha ao gravar arquivo no disco", e);
        }        
    }
    
    public void excluir(long path) throws SecurityException {
        File file = new File(MASTER_PATH + path);
        file.delete();
    }
    
    public void excluir(List<Complemento> complementos) throws SecurityException {
        for (Complemento complemento : complementos) {
            excluir(complemento.getFoto().getId());
        }        
    }

    public byte[] carregar(Foto foto) {
        File file = new File(MASTER_PATH + foto.getId());
        byte[] bytes = new byte[(int)file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
    
}
