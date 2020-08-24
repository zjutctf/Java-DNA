package ctf;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.IDN;
import java.net.URL;

public class Url extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = req.getParameter("url");
        if (url.contains("evil.ca")) {
            return;
        }
        URL url1 = new URL(IDN.toASCII(url));
        if (url1.getHost().equals("evil.ca")) {
            resp.getWriter().write(Hello.getContent("/Temp/flag"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
