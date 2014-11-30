package com.noa.connect.hook;


import com.dcore.request.DataPeticion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

/**
 *
 * @author user
 */
public abstract class AbstractConnectionHttp extends HookAdapter {

    private DataPeticion dataPeticion;
    private URL url;
    private String data;
    private boolean verbose = false;

    public DataPeticion getDataPeticion() {
        return dataPeticion;
    }

    public void setDataPeticion(DataPeticion dataPeticion) {
        this.dataPeticion = dataPeticion;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    @Override
    public void start() {
        try {
            URLConnection con = url.openConnection();

            //añadimos los datos de la cabecera para una peticion normal
            for (int i = 0; i < dataPeticion.getHeader().getListHeader().size(); i++) {
                con.setRequestProperty(dataPeticion.getHeader().getListHeader().get(i).getKey(), dataPeticion.getHeader().getListHeader().get(i).getValue());
            }
            //Permito el envío de mensajes hacia ese servidor
            con.setDoOutput(true);

            /*
             Creo un stream de salida para el envío de estos datos
             usando la conexión URL
             */
            OutputStreamWriter wr
                    = new OutputStreamWriter(con.getOutputStream());

            //Escribo en el stream de salida ...osea se van los datos
            //  String data = outS.toString();
            wr.write(data);

            //Libero el stream de salida una vez enviado todo
            wr.flush();

            /*
             Creo un stream de entrada, mediante el cual
             veré que respondió el servidor de acuerdo a los datos
             que le envié, de igual manera usando la conexión URL
             */
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));

            /*
             Leo todo el contenido recibido hasta que sea nulo
             esto se hace linea por linea y se guarda en una variable,
             por eso se usa el ciclo while.
             */
            String linea;

            while ((linea = in.readLine()) != null) {
                if (verbose) {
                    System.out.println(linea);
                }
            }
            //PruebaOlimpica.canSend = false;
        } catch (BindException be) {
            System.err.println("Error de Conexion! " + be.getMessage());
        } catch (SocketException se) {
            System.err.println("Error Abriendo Socked! " + se.getMessage());
        } catch (UnknownHostException ue) {
            System.err.println("No se Enuentra el Host! " + ue.getMessage());
        } catch (IOException ex) {
            System.err.println("Error de I/O " + ex.getMessage());
        }
    }
}
