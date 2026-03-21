import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

public class Router {

    private static final String[] WEB_ROOT_CANDIDATES = {"webroot", "../webroot", "./webroot"};

    public static byte[] handleRequest(String method, String path, Map<String, String> headers) {
        if (!"GET".equalsIgnoreCase(method)) {
            return textResponse("405 Method Not Allowed", "Method not allowed", "text/plain", 405);
        }

        if (path == null || path.isEmpty() || path.equals("/")) {
            path = "/index.html";
        }

        // prevent directory traversal attacks
        try {
            File rootDir = locateWebRoot();
            if (rootDir == null) {
                return textResponse("HTTP/1.1 500 Internal Server Error", "<h1>500 Web root not found</h1>", "text/html; charset=utf-8", 500);
            }

            File requested = new File(rootDir, path).getCanonicalFile();
            if (!requested.getPath().startsWith(rootDir.getPath()) || !requested.exists() || requested.isDirectory()) {
                return textResponse("HTTP/1.1 404 Not Found", "<h1>404 Not Found</h1>", "text/html; charset=utf-8", 404);
            }

            byte[] bodyBytes = Files.readAllBytes(requested.toPath());
            String contentType = contentTypeForPath(requested.getName());
            return buildResponse("HTTP/1.1 200 OK", bodyBytes, contentType);

        } catch (Exception e) {
            e.printStackTrace();
            return textResponse("HTTP/1.1 500 Internal Server Error", "<h1>500 Internal Server Error</h1>", "text/html; charset=utf-8", 500);
        }
    }

    private static File locateWebRoot() {
        for (String candidate : WEB_ROOT_CANDIDATES) {
            try {
                File dir = new File(candidate).getCanonicalFile();
                if (dir.exists() && dir.isDirectory()) {
                    return dir;
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private static byte[] buildResponse(String statusLine, byte[] bodyBytes, String contentType) {
        String headers = statusLine + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Content-Length: " + bodyBytes.length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";
        byte[] headerBytes = headers.getBytes(StandardCharsets.UTF_8);

        byte[] response = new byte[headerBytes.length + bodyBytes.length];
        System.arraycopy(headerBytes, 0, response, 0, headerBytes.length);
        System.arraycopy(bodyBytes, 0, response, headerBytes.length, bodyBytes.length);
        return response;
    }

    private static byte[] textResponse(String statusLine, String body, String contentType, int statusCode) {
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        return buildResponse(statusLine, bodyBytes, contentType);
    }

    private static String contentTypeForPath(String filename) {
        String lower = filename.toLowerCase();
        if (lower.endsWith(".html") || lower.endsWith(".htm")) {
            return "text/html; charset=utf-8";
        } else if (lower.endsWith(".css")) {
            return "text/css; charset=utf-8";
        } else if (lower.endsWith(".js")) {
            return "application/javascript; charset=utf-8";
        } else if (lower.endsWith(".png")) {
            return "image/png";
        } else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (lower.endsWith(".svg")) {
            return "image/svg+xml";
        } else if (lower.endsWith(".json")) {
            return "application/json; charset=utf-8";
        }
        return "application/octet-stream";
    }
}