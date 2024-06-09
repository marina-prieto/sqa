package dissw24.sqa.so;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EjecutorPython {

    public String ejecuta(String fileName) {
        ProcessBuilder pb = new ProcessBuilder("python", fileName);
        
        String carpeta = new File(fileName).getParent();
        String salida = carpeta + File.separator + "salida.txt";
        String errores = carpeta + File.separator + "errores.txt";
        
        pb.directory(new File(carpeta));
        pb.redirectOutput(new File(salida));
        pb.redirectError(new File(errores));
        
        try {
            Process process = pb.start();
            process.waitFor();
            if (new File(errores).length() > 0) {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error ejecutando el código Python");
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
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error ejecutando el código Python");
        }
    }
}
