/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.phdev.srs.daos;

import com.br.phdev.srs.exceptions.DAOException;
import com.br.phdev.srs.utils.UrlAttribute;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.util.List;

/**
 *
 * @author Paulo Henrique Gonçalves Bacelar <henrique.phgb@gmail.com>
 */
public class DataDAO {

    private final HttpURLConnection connection;

    private DataDAO(HttpURLConnection connection) {
        this.connection = connection;
        try {
            this.connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
        }
    }

    public DataDAO(HttpURLConnection connection, String requestMethod) {
        this.connection = connection;
        try {
            this.connection.setRequestMethod(requestMethod);
        } catch (ProtocolException e) {
        }
    }

    private byte[] getBytesFromFile(File file) throws IOException {
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return bytes;
    }    

    public void sendAttributes(List<UrlAttribute> urlAttributeList) throws DAOException {
        if (!this.connection.getDoOutput()) {
            this.connection.setDoOutput(true);
        }
        if (!urlAttributeList.isEmpty()) {
            OutputStream os = null;
            BufferedWriter bfw = null;
            try {
                os = connection.getOutputStream();
                bfw = new BufferedWriter(new OutputStreamWriter(os, "ISO-8859-1"));
                StringBuilder parameters = new StringBuilder();
                for (UrlAttribute attr : urlAttributeList) {
                    parameters.append(attr.getName());
                    parameters.append("=");
                    parameters.append(attr.getValue());
                    parameters.append("&");
                }
                parameters.deleteCharAt(parameters.length() - 1);
                bfw.write(parameters.toString());                
                bfw.flush();
                bfw.close();
                os.close();
            } catch (IOException e) {
                throw new DAOException("Falha ao enviar os dados", e, 200);
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                    if (bfw != null) {
                        bfw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendJSON(String json) throws DAOException {
        if (!this.connection.getDoOutput()) {
            this.connection.setDoOutput(true);
        }
        OutputStream os = null;
        BufferedWriter bfw = null;
        try {
            this.connection.setRequestProperty("Content-type", "application/json");
            os = this.connection.getOutputStream();
            bfw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bfw.write(json);
            bfw.flush();
            bfw.close();
            os.close();
        } catch (IOException e) {
            throw new DAOException("Falha ao enviar os dados", e, 200);
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (bfw != null) {
                    bfw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String retrieveString() throws DAOException {
        StringBuilder content = null;
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new InputStreamReader(this.connection.getInputStream(), "UTF-8"));
            String inputLine;
            content = new StringBuilder();
            while ((inputLine = bfr.readLine()) != null) {
                content.append(inputLine);
            }
        } catch (IOException e) {
            throw new DAOException("Falha ao enviar os dados", e, 200);
        } finally {
            if (bfr != null) {
                try {
                    bfr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content != null ? content.toString() : null;
    }
    
    public byte[] retrieveByteArray(int length) throws DAOException {
        byte[] finalBuffer = new byte[length];
        try {
            InputStream in = connection.getInputStream();
            int totalDataReaded = 0;
            int dataRead = 0;
            while (totalDataReaded < finalBuffer.length) {
                dataRead = in.read(finalBuffer, totalDataReaded, finalBuffer.length - totalDataReaded);
                totalDataReaded += dataRead;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalBuffer;
    }   

    public static int getResponseCode(HttpURLConnection con) {
        if (con == null) {
            return -1;
        }
        try {
            return con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
