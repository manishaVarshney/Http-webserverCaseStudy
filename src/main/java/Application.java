import helpers.Constants;
import helpers.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * Created by manisha.v on 27/01/19.
 */
public class Application {
    private static final int PORT = 8090;
    private static ThreadPoolExecutor threadPoolExecutor;
    private static final String LOGGING_TAG = "src.main.java.Application.java";

    public static void main(String[] args) {

        try {
            ServerSocket serverConnect = new ServerSocket(PORT);
            threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
            Logger.info(LOGGING_TAG,"Server started.\nListening for connections on port : " + PORT + " ...\n");

            // The port will keep listening until user halts the server execution
            while (true) {
                String pathName = args[0];
                JavaHTTPServer myServer = new JavaHTTPServer(serverConnect.accept(), pathName.concat(Constants.RESOURCE_DIRECTORY));
                Logger.info(LOGGING_TAG,"Connecton opened. (" + new Date() + ")");
                threadPoolExecutor.execute(myServer);
            }
        } catch (IOException e) {
            Logger.error(LOGGING_TAG,"Server Connection error : " + e.getMessage());
        }
    }
}
