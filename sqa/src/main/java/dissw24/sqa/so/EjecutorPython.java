package dissw24.sqa.so;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//latest version (creo)rellenar lo de los pasos 1 a 10, está en foto 14/03/2024

public class EjecutorPython {

	public void ejecuta(String fileName) {
		//1)
		//2)
		//3)
		//4)
		//5)
		//6)
		//7)
		//8)
		//9)
		//10)
		
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
				}
			}
		} catch (IOException | InterruptedException e){
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Algo pasa");
		}
	}
}
