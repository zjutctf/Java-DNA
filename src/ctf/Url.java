package ctf;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.IDN;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Url extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = req.getParameter("url");
        if (url.contains("evil.ca")) {
            resp.getWriter().write("host is not allowed");
            return;
        }
        URL url1 = new URL(IDN.toASCII(url));

        if (url1.getHost().equals("evil.ca")) {
            String[] path = url.split("/");
            String file = new String(Base64.getDecoder().decode(path[path.length - 1]));
            resp.getWriter().write(new String(Files.readAllBytes(Paths.get(file))));
        }else{
            resp.getWriter().write("host is not allowed");
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}