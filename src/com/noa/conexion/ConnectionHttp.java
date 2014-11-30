package com.noa.conexion;

import com.noa.connect.hook.AbstractConnectionHttp;
import com.dcore.request.DataPeticion;
import com.dcore.request.Opcion;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class ConnectionHttp extends AbstractConnectionHttp {

    ArrayList<Opcion> arrayHeaders;

    public ConnectionHttp(DataPeticion data, String url, ArrayList<Opcion> arrayHeader) {
        try {
            setDataPeticion(data);
            setUrl(new URL(url));
            arrayHeaders = arrayHeader;
        } catch (MalformedURLException ex) {
            System.err.println("No es unaUrl Valida! " + ex.getMessage());
        }
    }

    @Override
    public void beforeDo() {
        addDataToSend();
    }

    protected void addDataToSend() {
        String data = "";
        if (arrayHeaders != null) {
            try {
                for (Opcion opc : arrayHeaders) {
                    data += URLEncoder.encode(opc.getKey(), "UTF-8") + "="
                            + URLEncoder.encode(opc.getValue(), "UTF-8") + "&";
                }
            } catch (UnsupportedEncodingException ex) {
                System.err.println("Error de Codificacion: " + ex.getMessage());
            }
        }
        setData(data);
    }
}
