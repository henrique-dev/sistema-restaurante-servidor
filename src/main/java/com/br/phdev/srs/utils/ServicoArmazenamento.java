/* Copyright (C) HeadRed, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Paulo Henrique Gona�alves Bacelar <henrique.phgb@gmail.com>, Agosto 2018
 */
package com.br.phdev.srs.utils;

import com.br.phdev.srs.exceptions.StorageException;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar
 */
public class ServicoArmazenamento {
    
    private static final String MASTER_PATH = "/home/henrique/Documentos/Teste/";       
    
    /*
    public com.br.phdev.sistemarestaurante.utils.Arquivo store(MultipartFile multipartFile, int type, User user) throws StorageException {
        if (type < 0 && type > 20)
            throw new StorageException("Falha ao armazenar o arquivo. O tipo não é valido");        
        com.br.phdev.sistemarestaurante.utils.Arquivo fileSaved;
        try {
            
            byte[] bytes = multipartFile.getBytes();
            String path = user.getClass().getSimpleName()+ "/" + user.getId() + "/" + type;
            Arquivo file = new Arquivo(MASTER_PATH + path);
            if (!file.exists()) {                
                file.mkdirs();
            }           
            Date currentDate = Calendar.getInstance().getTime();
            String dateParsed = new SimpleDateFormat("dd-MM-yyyy-hh-mm-s-ms").format(currentDate);
            path = path + "/" + multipartFile.getOriginalFilename() + "-" + dateParsed;
            file = new Arquivo(MASTER_PATH + path);            
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(bytes);
                fos.flush();
            }
            fileSaved = new com.br.phdev.sistemarestaurante.utils.Arquivo();
            fileSaved.setFileName(multipartFile.getOriginalFilename());
            fileSaved.setFilePath(path);
            fileSaved.setType(type);
            fileSaved.setFileUploadDate(new Timestamp(currentDate.getTime()));
            fileSaved.setFileLength((int)file.length());            
        } catch (IOException e) {
            throw new StorageException("Falha ao gravar arquivo no disco", e);
        }
        return fileSaved;
    }*/       
    
    public void delete(Arquivo fileToDelete) throws SecurityException {
        File file = new File(MASTER_PATH + fileToDelete.getCaminho());
        file.delete();
    }

    public byte[] carregar(Arquivo fileSaved) {
        File file = new File(MASTER_PATH + fileSaved.getCaminho());
        byte[] bytes = new byte[(int)file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
    
}
