package Pruebas;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class Whatsapp {
  // Reemplaza los siguientes datos con tu información
  private static final String ID_CLIENTE = ""; // TU ID DE CLIENTE
  private static final String CLIENT_SECRET = ""; //TU CLAVE SECRETA DE CLIENTE WHATSAPP
  private static final String URL_GATEWAY = ""; // TU HOST QUE HARA COMO GATEWAY

  public static void main(String[] args) throws Exception {
    String numero = "000000";  // Número telefónico del destinatario, junto con su código de pais
    String mensaje = "Hola"; // Mensaje deseado
    //LLAMADA AL MÉTODO ENCARGADO DE ENVIAR EL MENSAJE
    envio(numero, mensaje);
  }
  
  public static void envio(String numero, String mensaje) {

    String payload="{ Número: " + numero + " | Mensaje: " + mensaje;

    try {
	    URL url = new URL(URL_GATEWAY);
	    HttpURLConnection conectividad= (HttpURLConnection) url.openConnection();
	    HttpURLConnection conex = conectividad;
	    Post(conex);
	    OutputStream output = conex.getOutputStream();
	    output.write(payload.getBytes());
	    output.flush();
	    output.close();
	
	    int CodigoEstado = conex.getResponseCode();
	    System.out.println("Respuesta del Gateway: \n");
	    System.out.println("Codigo de estado: " + CodigoEstado);
	    InputStreamReader Escritura;
	    if(CodigoEstado==200) {
	    	Escritura=new InputStreamReader(conex.getInputStream());
	    }else {
	    	Escritura=new InputStreamReader(conex.getErrorStream());
	    }
	    BufferedReader br = new BufferedReader(Escritura);
	    String outputS;
	    while ((outputS = br.readLine()) != null) {
	        System.out.println(outputS);
	    }
	    conex.disconnect();
    }catch(Exception e) {
      	System.out.println("No se pudo enviar el mensaje");
    }
  }
  public static void Post(HttpURLConnection conex) throws ProtocolException {
	  conex.setDoOutput(true);
	  conex.setRequestMethod("POST");
	  conex.setRequestProperty("ID", ID_CLIENTE);
	  conex.setRequestProperty("CLIENT_SECRET", CLIENT_SECRET);
	  conex.setRequestProperty("Content-Type", "application/json");
  }

}
