package dissw24.sqa.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dissw24.sqa.model.Hamiltoniano;
import dissw24.sqa.services.CommonService;

@Component
public abstract class CommonController {

	@Autowired
	private CommonService commonService;
	
	protected boolean validar(String token) throws IOException {
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
	
	public void save(String token, Hamiltoniano h) throws FileNotFoundException, IOException {
		String fileName = this.getName(token) + "hamiltoniano.txt";
		try(FileOutputStream fos = new FileOutputStream(fileName)) {
			//el hola deberia ser realmente el hamiltoniano
			fos.write("Hola".getBytes());
		}
	}

	protected abstract void saveCodigo(String token, String codigo);
}
