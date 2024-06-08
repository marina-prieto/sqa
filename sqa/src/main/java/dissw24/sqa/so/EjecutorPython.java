package dissw24.sqa.so;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EjecutorPython {

	public String ejecuta(String fileName) {
		//1) abrir la consola en el directorio en el que queremos ejecutar el proceso
		//2) establecer el PATH y las variables de entorno
		//3) construimos el comando que queremos ejecutar
		//4) redireccionar la salida estandar de la consola a un archivo
		//5) redireccionar la salidad de error de la consola a un archivo
		//6) lanzar el proceso
		//7) esperamos a que termine
		//8) leer el archivo de salida y el archivo de error
		//9) si no hay error, devolver a quien corresponda el resultado de la salida
		//10) si si hay error, devolver a quien corresponda el mensaje de error para que haga llegar al front una excepcion
		
		fileName = "/Users/thala/practicaDisoft/pruebas/codigo.py";
		ProcessBuilder pb = new ProcessBuilder("python", fileName);
		
		String carpeta = fileName.substring(0, fileName.length()-9);
		String salida = carpeta + "salida.txt";
		String errores = carpeta + "errores.txt";
		
		pb.directory(new File(carpeta));
		pb.redirectOutput(new File(salida));
		pb.redirectError(new File(errores));
		
		try {
			Process process = pb.start();
			process.waitFor();
			if (new File(errores).length() > 0) {
				throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Algo pasa");
			} else {
				String respuesta;
				try (FileInputStream fis = new FileInputStream(new File(salida))) {
					byte[] bytes = new byte[fis.available()];
					fis.read(bytes);
					respuesta = new String(bytes);
					return respuesta;
				}
			}
		} catch (IOException | InterruptedException e){
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Algo pasa");
		}
	}
}
