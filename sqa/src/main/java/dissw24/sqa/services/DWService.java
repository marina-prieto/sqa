package dissw24.sqa.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class DWService {

    public String generarCodigo(int[][] matriz) throws Exception {
        String template = this.read("dwavetemplate.txt");

        StringBuilder sb = new StringBuilder();
        sb.append("x = [");
        for (int i = 0; i < matriz.length; i++) {
            sb.append("\"x").append(i).append("\", ");
        }
        sb.append("]\n");

        for (int i = 0; i < matriz.length; i++) {
            for (int j = i; j < matriz[i].length; j++) {
                if (matriz[i][j] != 0) {
                    sb.append("Q[(\"x").append(i).append("\", \"x").append(j).append("\")] = ")
                        .append(matriz[i][j]).append("\n");
                }
            }
        }

        String initialPart = template.substring(0, template.indexOf("#RULES#"));
        String finalPart = template.substring(template.indexOf("#RULES#") + "#RULES#".length());

        return initialPart + sb.toString() + finalPart;
    }

    public String read(String fileName) throws FileNotFoundException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream fis = classLoader.getResourceAsStream(fileName)) {
            byte[] b = new byte[fis.available()];
            fis.read(b);
            return new String(b);
        }
    }
}
