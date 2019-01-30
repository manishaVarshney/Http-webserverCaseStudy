package httpResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manisha.v on 30/01/19.
 */

public class ResponseStatus {

    /**
     * Successful 2xx
     */
    public static final int OK = 200;

    /**
     * Client Error 4xx
     */
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int NOT_FOUND = 404;

    /**
     * Server Error 5xx
     */
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;

    public static Map<Integer, String> reasons;

    static {
        Map<Integer, String> reasonCodes = new HashMap();
        reasonCodes.put(OK, "OK");
        reasonCodes.put(BAD_REQUEST, "Bad Request");
        reasonCodes.put(UNAUTHORIZED, "Unauthorized");
        reasonCodes.put(NOT_FOUND, "File Not Found");
        reasonCodes.put(INTERNAL_SERVER_ERROR, "Internal Server Error");
        reasonCodes.put(NOT_IMPLEMENTED, "Not Implemented");
        reasons = Collections.unmodifiableMap(reasonCodes);
    }
}
