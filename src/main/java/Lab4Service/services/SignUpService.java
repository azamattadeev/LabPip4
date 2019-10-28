package Lab4Service.services;

import Lab4Service.datawrappers.Credentials;
import Lab4Service.dataaccess.UserDAO;
import Lab4Service.entities.User;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

@Path("/signup")
public class SignUpService {
    @EJB
    private UserDAO userDAO;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signup(Credentials credentials){
        String login = credentials.getLogin();
        String password = credentials.getPassword();
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256").digest(password.getBytes(StandardCharsets.UTF_8));
            for(int i = 0; i < bytes.length; i++){
                if(bytes[i] == 0) bytes[i]++;
            }
            String passwordHash = new String(bytes);
            String token = UUID.randomUUID().toString();
            User user = new User(login, passwordHash, token);
            userDAO.createUser(user);
            return Response.ok("{\"token\":\""+token+"\"}").build();
        }catch (Exception ex){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
