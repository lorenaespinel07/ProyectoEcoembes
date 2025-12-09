package es.deusto.sd.ecoembes.external;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Component;

import es.deusto.sd.ecoembes.entity.InfoPlanta;
import es.deusto.sd.ecoembes.entity.PlantaReciclaje;

@Component
public class ContSocketGateway implements IPlantaGateway{
	
	private String serverIP;
	private int serverPort;
	private static String DELIMITAR = "#";
	private PlantaReciclaje plantaReciclaje= new PlantaReciclaje("ContSocket Ltd.", 4);
	public ContSocketGateway() {
		this.serverIP = "127.0.0.1";
		this.serverPort = 9500;
	}
	/*
	@Override
	public Optional<ArrayList<InfoPlanta>> getInfosPlanta() {
		ContSocketGateway contSocketGateway = new ContSocketGateway();
		String respuesta = contSocketGateway.mandarMensaje("GET INFO");
		SimpleDateFormat formato = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		if (respuesta != null && !respuesta.isEmpty()) {
			String[] partes = respuesta.split("\n");
			ArrayList<InfoPlanta> infosPlanta = new ArrayList<>();
			for (String parte : partes) {
				String[] atributos = parte.split(DELIMITAR);
				double cantidadReciclada = Double.parseDouble(atributos[1]);
				try {
					Date fechaParseada = formato.parse(atributos[2]);
					InfoPlanta infoPlanta = new InfoPlanta(plantaReciclaje, cantidadReciclada, fechaParseada);
					infosPlanta.add(infoPlanta);
					//System.out.println("Fecha parseada: " + fechaParseada);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return Optional.of(infosPlanta);
		}
		return Optional.empty();
	}
	*/
	
	public String mandarMensaje(String mensaje){
		String resultado = null;
		try (Socket socket = new Socket(serverIP, serverPort);
				// Streams to send and receive information are created from the Socket
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

			// Send request (one String) to the server
			out.writeUTF(mensaje);
			System.out.println(" -Mandando data a: '" + socket.getInetAddress().getHostAddress() + ":"
					+ socket.getPort() + "' -> '" + mensaje + "'");

			// Read response (one String) from the server
			resultado = in.readUTF();
			System.out.println(" - Recibiendo data de: '" + socket.getInetAddress().getHostAddress() + ":"
					+ socket.getPort() + "' -> '" + resultado + "'");
			System.out.println(resultado);
			socket.close();
		} catch (UnknownHostException e) {
			System.err.println(" # Trans. SocketClient: Socket error: " + e.getMessage());
		} catch (EOFException e) {
			System.err.println(" # Trans. SocketClient: EOF error: " + e.getMessage());
		} catch (IOException e) {
			System.err.println(" # Trans. SocketClient: IO error: " + e.getMessage());
		}
		return resultado;
	}

//	@Override
//	public Optional<InfoPlanta> getInfoPlantaPorFecha(Date fecha) {
//		String fechaStr = fecha.toString();
//		System.out.println("Fecha a buscar: " + fechaStr);
//		ContSocketGateway contSocketGateway = new ContSocketGateway();
//		String respuesta = contSocketGateway.mandarMensaje("GET INFO"+DELIMITAR + fechaStr);
//		System.out.println("Respuesta recibida: " + respuesta);
//		SimpleDateFormat formato = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
//		if (respuesta != null && !respuesta.isEmpty()) {
//			String[] partes = respuesta.split("\n");
//			InfoPlanta infoPlanta = null;
//			//Modelo de datos #id#capacidadActual#fechaActu\n
//			for (String parte : partes) {
//				String[] atributos = parte.split(DELIMITAR);
//				double cantidadReciclada = Double.parseDouble(atributos[2]);
//				try {
//					Date fechaParseada = formato.parse(atributos[3]);
//					infoPlanta = new InfoPlanta(plantaReciclaje, cantidadReciclada, fechaParseada);
//					System.out.println("Fecha parseada: " + fechaParseada);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			return Optional.of(infoPlanta);
//		}
//
//		return Optional.empty();
//	}

    @Override
    public double getCapacidadPlanta(String fecha) {
        return 0;
    }

    @Override
	public Optional<String> enviarAsignacionPlanta(int numeroContenedores, int cantidadEnvases) {
		ContSocketGateway contSocketGateway = new ContSocketGateway();
		String respuesta = contSocketGateway.mandarMensaje("ASIGNAR"+DELIMITAR+ numeroContenedores + DELIMITAR + cantidadEnvases);
		if (respuesta != null && !respuesta.isEmpty()) {
			return Optional.of(respuesta);
		}
		return Optional.empty();
	}
	
}
