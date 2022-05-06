package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PingServlet  extends HttpServlet {
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
        resp.setStatus(200);
        resp.getWriter().print("Pong");
        } catch (IOException e) {
        resp.setStatus(500);
        System.out.println(e.getLocalizedMessage());
        }
}
}
