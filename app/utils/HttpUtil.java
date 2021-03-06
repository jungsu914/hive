package utils;

import play.api.http.MediaRange;
import play.mvc.Http;
import scala.Option;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    public static String getFirstValueFromQuery(Map<String, String[]> query, String name) {
        String[] values = query.get(name);

        if (values != null && values.length > 0) {
           return values[0];
        } else {
            return null;
        }
    }

    public static String encodeContentDisposition(String filename)
            throws UnsupportedEncodingException {
        // Encode the filename with RFC 2231; IE 8 or less, and Safari 5 or less
        // are not supported. See http://greenbytes.de/tech/tc2231/
        filename = filename.replaceAll("[:\\x5c\\/{?]", "_");
        filename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        filename = "filename*=UTF-8''" + filename;
        return filename;
    }

    public static String getPreferType(Http.Request request, String ... types) {
        // acceptedTypes is sorted by preference.
        for(MediaRange range : request.acceptedTypes()) {
            for(String type : types) {
                if (range.accepts(type)) {
                    return type;
                }
            }
        }
        return null;
    }
}
