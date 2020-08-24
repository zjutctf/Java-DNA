package ctf;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class Hello extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file = req.getParameter("file");
        if(file == null) {
            resp.getWriter().write("/hello?file=");
            return;
        }
        if (file.contains("flag")) {
            resp.getWriter().write("'flag' is not allowed");
            return;
        }
        resp.getWriter().write(getContent(file));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public static String getContent(String file) {
        try {
            byte[] b = Files.readAllBytes(Paths.get(file));
            return Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            e.printStackTrace();
            return Base64.getEncoder().encodeToString(e.toString().getBytes());
        }
    }
}
