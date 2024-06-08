package dissw24.sqa.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import dissw24.sqa.model.Hamiltoniano;
import dissw24.sqa.services.CommonService;

@Component
public abstract class CommonController {

	@Autowired
	private CommonService commonService;
	
	protected boolean validarToken(String token) throws IOException {
		boolean valido = this.commonService.validarToken(token);
		if (valido) {
			this.crearCarpeta(token);
		}
		return valido;
	}
	
	private void crearCarpeta(String token) {
		new File(this.getName(token)).mkdir();
	}

	private String getName(String token) {
		String userPath = System.getProperty("user.home");
		if (!userPath.endsWith(File.separator)) {
			userPath = userPath + File.separator;
		}
		userPath = userPath + "practicaDisoft" + File.separator;
		return userPath + token + File.separator;
	}
	
	public String save(String token, Serializable s) throws FileNotFoundException, IOException {
		String fileName = this.getName(token) + "hamiltoniano.txt";
		try(FileOutputStream fos = new FileOutputStream(fileName)) {
			try(ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				oos.writeObject(s);
			}
		}
		return fileName;
	}

	protected void saveCodigo(String token, String codigo) throws FileNotFoundException, IOException {
		String fileName = this.getName(token) + "codigo.py";
		try(FileOutputStream fos = new FileOutputStream(fileName)) {
			fos.write(codigo.getBytes());
		}
	}
	
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
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "El sistema de control de credenciales no está disponible");
		}
		return token;
	}
}
