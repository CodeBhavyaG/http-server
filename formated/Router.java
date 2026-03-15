import java.nio.charset.StandardCharsets;

public class Router {

    public static String handleRequest(String method, String path, java.util.Map<String, String> headers) {
        if (!"GET".equalsIgnoreCase(method)) {
            return textResponse("405 Method Not Allowed", "Method not allowed", "text/plain", 405);
        }

        if (path == null || path.isEmpty()) {
            path = "/";
        }

        if (path.equals("/")) {
            return htmlResponse("<h1>Home Page</h1>");
        }

        if (path.equals("/about")) {
            return htmlResponse("<h1>About Page</h1>");
        }

        if (path.equals("/hello")) {
            return htmlResponse("<h1>Hello from HTTP Server</h1>");
        }

        return htmlResponse("<h1>404 Not Found</h1>", 404);
    }

    private static String htmlResponse(String body) {
        return htmlResponse(body, 200);
    }

    private static String htmlResponse(String body, int statusCode) {
        return textResponse(statusLine(statusCode), body, "text/html; charset=utf-8", statusCode);
    }

    private static String textResponse(String statusLine, String body, String contentType, int statusCode) {
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        return statusLine + "\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Content-Length: " + bodyBytes.length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                body;
    }

    private static String statusLine(int statusCode) {
        if (statusCode == 200) {
            return "HTTP/1.1 200 OK";
        } else if (statusCode == 404) {
            return "HTTP/1.1 404 Not Found";
        } else if (statusCode == 405) {
            return "HTTP/1.1 405 Method Not Allowed";
        }
        return "HTTP/1.1 500 Internal Server Error";
    }
}