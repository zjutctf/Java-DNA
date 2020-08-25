package ctf;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Hello extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file = req.getParameter("file");
        if (file == null) {
            resp.getWriter().write("/hello?file=");
            return;
        }
        if (file.contains("flag")) {
            resp.getWriter().write("'flag' is not allowed");
            return;
        }
        if (file.contains("passwd")) {
            resp.getWriter().write("'passwd' is not allowed");
            return;
        }
        String path = this.getServletContext().getRealPath(file.split("\\?")[0]);
        OutputStream os = resp.getOutputStream();
        os.write(Files.readAllBytes(Paths.get(path)));
        os.flush();
        os.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
