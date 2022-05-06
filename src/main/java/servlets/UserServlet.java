package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.UserDao;
import dao.UserDaoImpl;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {

    private UserDao userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<User> users = userDao.getAllUsers();
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(users);
            resp.setStatus(200);
            resp.getWriter().print(json);
        } catch (IOException e) {
            resp.setStatus(500);
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            User payload = mapper.readValue(req.getInputStream(), User.class);
            userDao.insert(payload);
            resp.setStatus(203);
            resp.getWriter().print("User added!");
        } catch (IOException e) {
            resp.setStatus(500);
            resp.getWriter().print("Something went wrong when adding the user.");
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            User payload = mapper.readValue(req.getInputStream(), User.class);
            userDao.update(payload);

            resp.setStatus(203);
            resp.getWriter().print("User updated!");

        } catch (IOException e) {
            resp.setStatus(500);
            resp.getWriter().print("Something went wrong when updating the user.");
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("Usao");

        try {

        // get the id from the request parameter:
            System.out.println("Usao");
        int idToDelete = Integer.parseInt(req.getParameter("id"));
        System.out.println(idToDelete);
        // call the service:
            //
        boolean success = userDao.delete(idToDelete);

        // check if deletion was successful and send the appropriate response:
            if(success) {
                resp.setStatus(200);
                resp.getWriter().print("User deleted!");
            }
        } catch (IOException e) {
            resp.setStatus(500);
            resp.getWriter().print("Something went wrong when deleting the user.");
            System.out.println(e.getLocalizedMessage());
        }

    }
}