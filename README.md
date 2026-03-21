# Simple Java HTTP Server

A minimal multithreaded HTTP file server in Java. Serves static files from a `webroot/` directory and handles basic GET requests with content-type inference.

## Features

- Listens on port `8080` by default
- Multithreaded: each client request is handled in its own `Thread`
- Serves `index.html` for root `/`
- Safely resolves paths (`getCanonicalFile`) to prevent directory traversal
- Includes built-in 404/500/405 error responses
- Content type detection for HTML, CSS, JS, JSON, PNG, JPG, SVG

## Repository Structure

- `Main.java` - entry point; starts `Server` on port 8080
- `Server.java` - accepts socket connections and dispatches `ClientHandler`
- `ClientHandler.java` - parses request, reads headers, delegates to `Router`
- `Router.java` - resolves requested file, streams response bytes with HTTP headers
- `webroot/` - sample static site content (`index.html`, `styles.css`, `app.js`)

## Running the Server

### Requirements

- Java 17+ (or Java 11+)
- `javac` and `java` on PATH

### Compile

```bash
javac *.java
```

### Run

```bash
java Main
```

You should see:

```
Server started on port 8080
```

Then open http://localhost:8080 in a browser.

## Behavior

- `GET /` → serves `webroot/index.html`
- `GET /styles.css` → serves `webroot/styles.css`
- `GET /app.js` → serves `webroot/app.js`
- non-GET methods → `405 Method Not Allowed`
- missing files or traversal attempts → `404 Not Found`
- missing `webroot/` → `500 Web root not found`

## Customization

- Change port in `Main.java` by passing a new value to `new Server(port)`
- Add new static files under `webroot/`
- Extend `Router.contentTypeForPath()` for additional MIME types (e.g., `.txt`, `.woff`)

## Notes

This project is an educational sample and is not intended for production use. For production, use a robust web server framework with proper HTTP/1.1+ handling, keep-alive, chunked transfer encoding, request parsing, security headers, and connection pooling.
