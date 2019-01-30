package httpResponse;

import helpers.Logger;

import java.io.*;

/**
 * Created by manisha.v on 30/01/19.
 */
public class SuccessfulHttpResponse extends HttpResponse {

    public SuccessfulHttpResponse() {
        super();
        this.statusCode = ResponseStatus.OK;
    }

    public void sendResponse(OutputStream dataOut, PrintWriter printWriter, File file, String requestFileName) throws IOException {
        write(dataOut,printWriter,file,requestFileName);
        Logger.info(this.getClass().getSimpleName(),"File " + requestFileName + " returned");
    }

}
