package Lab4Service.services;

import Lab4Service.configs.AuthenficationConfig;
import Lab4Service.dataaccess.UserDAO;
import Lab4Service.entities.User;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthService {
    @EJB
    private UserDAO userDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response isAuthorized(@Context HttpHeaders headers){
        String authorizationHeader =
                headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader
                .substring(AuthenficationConfig.AUTHENTICATION_SCHEME.length()).trim();
        return Response.ok("{\"auth\":\""+(userDAO.findByToken(token) != null)+"\"}").build();
    }

}
