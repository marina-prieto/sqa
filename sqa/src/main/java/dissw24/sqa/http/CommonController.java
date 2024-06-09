package dissw24.sqa.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import dissw24.sqa.services.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public abstract class CommonController {

    @Autowired
    private CommonService commonService;

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    //Validación de tokens de usuario
    protected boolean validarToken(String token) throws IOException {
        boolean valido = this.commonService.validarToken(token);
        if (valido) {
            this.crearCarpeta(token);
        }
        return valido;
    }

    //Crear carpeta del token de sesión
    private void crearCarpeta(String token) {
        File dir = new File(this.getName(token));
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    //Getname del archivo, con path, token, nombre...
    protected String getName(String token) {
        String userPath = System.getProperty("user.home");
        if (!userPath.endsWith(File.separator)) {
            userPath = userPath + File.separator;
        }
        userPath = userPath + "practicaDisoft" + File.separator;
        return userPath + token + File.separator;
    }

    public String save(String token, Serializable s) throws FileNotFoundException, IOException {
        String fileName = this.getName(token) + "hamiltoniano.txt";
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(s);
            }
        }
        return fileName;
    }

    public String save(String token, String content) throws FileNotFoundException, IOException {
        String fileName = this.getName(token) + "hamiltoniano.txt";
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(content.getBytes());
        }
        return fileName;
    }

    protected String saveCodigo(String token, String codigo) throws FileNotFoundException, IOException {
        String fileName = this.getName(token) + "dwavefile.py";
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(codigo.getBytes());
        }
        return fileName;
    }

    //Validar peticiones del frontend con token
    public String validarPeticion(HttpServletRequest req) {
        String token = req.getHeader("token");
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token nula");
        }
        try {
            if (!validarToken(token)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token inválida");
            }
        } catch (IOException e) {
            logger.error("Error al validar el token", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "El sistema de control de credenciales no está disponible");
        }
        return token;
    }
}
