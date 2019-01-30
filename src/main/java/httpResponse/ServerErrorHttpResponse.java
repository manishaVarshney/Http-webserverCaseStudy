package httpResponse;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by manisha.v on 30/01/19.
 */
public class ServerErrorHttpResponse extends HttpResponse {
    public ServerErrorHttpResponse() {
        super();
        this.statusCode = ResponseStatus.NOT_IMPLEMENTED;
    }

    public void sendResponse(OutputStream dataOut, PrintWriter printWriter, File file, String requestFileName) throws IOException {
        write(dataOut,printWriter,file,requestFileName);
    }
}
