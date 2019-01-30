import helpers.Constants;
import helpers.Logger;
import httpRequest.HttpRequestMethod;
import httpResponse.ClientErrorHttpResponse;
import httpResponse.HttpResponse;
import httpResponse.ServerErrorHttpResponse;
import httpResponse.SuccessfulHttpResponse;
import lombok.NoArgsConstructor;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

@NoArgsConstructor
public class JavaHTTPServer implements Runnable {

    private File fileLocation;
    private Socket connect;

    public JavaHTTPServer(Socket socket, String pathName) {
        connect = socket;
        this.fileLocation = new File(pathName);
    }

    public void run() {
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        BufferedOutputStream bufferedOutputStream = null;
        String fileRequested = null;

        try {

            connect.setKeepAlive(true);
            bufferedReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            printWriter = new PrintWriter(connect.getOutputStream());

            bufferedOutputStream = new BufferedOutputStream(connect.getOutputStream());
            String input = bufferedReader.readLine();
            if (input == null) return;

            StringTokenizer tokenizedLine = new StringTokenizer(input);
            String method = tokenizedLine.nextToken().toUpperCase();
            fileRequested = tokenizedLine.nextToken().toLowerCase();

            if (!method.equals(HttpRequestMethod.GET.name()) && !method.equals(HttpRequestMethod.HEAD.name())) {
                Logger.info(this.getClass().getSimpleName(), "501 Not Implemented : " + method + " method.");
                methodNotImplemented(bufferedOutputStream,printWriter,fileRequested);

            } else {
                fileFound(fileRequested,method,bufferedOutputStream,printWriter);
            }
        } catch (FileNotFoundException fnfe) {
            fileNotFound(bufferedOutputStream,printWriter,fileRequested);
        } catch (IOException ioe) {
            Logger.error(this.getClass().getSimpleName(), "Server error : " + ioe);
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (printWriter != null) printWriter.close();
                if (bufferedOutputStream != null) bufferedOutputStream.close();
                connect.close();
            } catch (IOException e) {
                Logger.error(this.getClass().getSimpleName(), "Error closing stream: " + e.getMessage());
            }
        }

    }

    private void fileNotFound(BufferedOutputStream bufferedOutputStream, PrintWriter printWriter, String fileRequested) {
        try {
            File file = new File(fileLocation, Constants.FILE_NOT_FOUND);
            HttpResponse httpResponse = new ClientErrorHttpResponse();
            httpResponse.sendResponse(bufferedOutputStream, printWriter, file, fileRequested);
        } catch (IOException ioe) {
            Logger.error(this.getClass().getSimpleName(), "Error with file not found exception : " + ioe.getMessage());
        }
    }

    private void methodNotImplemented(BufferedOutputStream bufferedOutputStream, PrintWriter printWriter, String fileRequested) throws IOException {
        File file = new File(fileLocation, Constants.METHOD_NOT_SUPPORTED);
        HttpResponse httpResponse = new ServerErrorHttpResponse();
        httpResponse.sendResponse(bufferedOutputStream, printWriter, file, fileRequested);
    }

    private void fileFound(String fileRequested, String method, BufferedOutputStream bufferedOutputStream,
                           PrintWriter printWriter) throws IOException {
        HttpResponse httpResponse = new SuccessfulHttpResponse();
        if (fileRequested.endsWith("/")) {
            fileRequested += Constants.DEFAULT_FILE;
        }

        File file = new File(fileLocation, fileRequested);
        if (method.equals(HttpRequestMethod.GET.name())) {
            httpResponse.sendResponse(bufferedOutputStream, printWriter, file, fileRequested);
        }

    }
}