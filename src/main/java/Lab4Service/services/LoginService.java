package Lab4Service.services;

import Lab4Service.datawrappers.Credentials;
import Lab4Service.dataaccess.UserDAO;
import Lab4Service.entities.User;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Path("/login")
public class LoginService {
    @EJB
    private UserDAO userDAO;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(Credentials credentials){
        String login = credentials.getLogin();
        String password = credentials.getPassword();
        Gson gson=new Gson();
        if(login == null || password == null || login.equals("") || password.equals("")){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {

            String token = authenticate(login, password);
            return Response.ok("{\"token\":\""+token+"\"}").build();
        }catch (SecurityException ex){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }catch (Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String authenticate(String login, String password) throws SecurityException, NoSuchAlgorithmException {
        User user = userDAO.findByLogin(login);
        if (user == null) throw new SecurityException("Invalid login or password");
        byte[] bytes = MessageDigest.getInstance("SHA-256").digest(password.getBytes(StandardCharsets.UTF_8));
        for(int i = 0; i < bytes.length; i++){
            if(bytes[i] == 0) bytes[i]++;
        }
        if (user.getPasswordHash().equals(new String(bytes))) {
            String token = UUID.randomUUID().toString();
            userDAO.updateUserToken(user, token);
            return token;
        }else {
            throw new SecurityException("Invalid login or password");
        }
    }
}
