package dissw24.sqa.services;

import java.io.IOException;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class CommonService {

    private final OkHttpClient client;

    public CommonService() {
        this.client = new OkHttpClient();
    }

    public boolean validarToken(String token) throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:9000/users/validarToken?token=" + token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return (response.code() == 200);
        }
    }
}
