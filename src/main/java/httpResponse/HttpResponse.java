package httpResponse;

import helpers.Constants;

import java.io.*;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by manisha.v on 30/01/19.
 */
public abstract class HttpResponse {
    protected String protocol = Constants.HTTP_PROTOCOL;

    protected int statusCode;
    protected HashMap<String, String> headers;


    public HttpResponse() {
        this.headers = new HashMap();
    }

    abstract public void sendResponse(OutputStream out, PrintWriter printWriter, File file, String requestFileName) throws IOException;


    protected void write(OutputStream dataOut, PrintWriter printWriter, File file, String requestFileName) throws IOException {
        int fileLength = (int) file.length();
        byte[] fileData = readFileData(file);

        printWriter.println(getResponseLine());
        printWriter.println("Server: Java HTTP Server : 1.0");
        printWriter.println("Date: " + new Date());
        printWriter.println("Content-type: " + getContentType(requestFileName));
        printWriter.println("Content-length: " + fileLength);
        printWriter.println("Connection: Keep-Alive");
        printWriter.println();
        printWriter.flush();

        dataOut.write(fileData, 0, fileLength);
        dataOut.flush();
    }

    protected String getResponseLine() {
        return String.format("%s %s %s", protocol, String.valueOf(statusCode), ResponseStatus.reasons.getOrDefault(statusCode, "Not Specified"));
    }

    protected byte[] readFileData(File file) throws IOException {
        int fileLength = (int) file.length();
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }

        return fileData;
    }

    protected String getContentType(String fileRequested) {
        if (fileRequested.endsWith(Constants.HTM_EXTENSION)  ||  fileRequested.endsWith(Constants.HTML_EXTENSION))
            return Constants.TEXT_HTML;
        else
            return Constants.TEXT_PLAIN;
    }

}
