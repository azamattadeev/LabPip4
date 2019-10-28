package Lab4Service.services;

import Lab4Service.configs.AuthenficationConfig;
import Lab4Service.dataaccess.UserDAO;
import Lab4Service.entities.User;
import Lab4Service.security.Secured;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Secured
@Path("/logout")
public class LogoutService {
    @EJB
    UserDAO userDAO;

    @POST
    public Response logout(@Context HttpHeaders headers){
        String authorizationHeader =
                headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader
                .substring(AuthenficationConfig.AUTHENTICATION_SCHEME.length()).trim();
        User user = userDAO.findByToken(token);
        if (user != null) {
            userDAO.updateUserToken(user, null);
            return Response.status(Response.Status.NO_CONTENT).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}