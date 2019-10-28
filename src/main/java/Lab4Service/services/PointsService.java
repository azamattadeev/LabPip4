package Lab4Service.services;

import Lab4Service.configs.AuthenficationConfig;
import Lab4Service.dataaccess.PointDAO;
import Lab4Service.dataaccess.UserDAO;
import Lab4Service.entities.Point;
import Lab4Service.entities.User;
import Lab4Service.security.Secured;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Secured
@Path("points")
public class PointsService {
    @EJB
    private UserDAO userDAO;
    @EJB
    private PointDAO pointDAO;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPoints(@Valid @NotNull(message = "Point is null") Point[] points,
                                @Context HttpHeaders headers){
        String authorizationHeader =
                headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader
                .substring(AuthenficationConfig.AUTHENTICATION_SCHEME.length()).trim();
        for(Point point: points) {
            check(point);
            point.setOwner(userDAO.findByToken(token));
            pointDAO.createPoint(point);
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Lab4Service.json.Point> getPoints(@Context HttpHeaders headers){
        String authorizationHeader =
                headers.getHeaderString(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader
                .substring(AuthenficationConfig.AUTHENTICATION_SCHEME.length()).trim();
        User user = userDAO.findByToken(token);
        List<Point> points = pointDAO.findAllByOwner(user);
        if (points==null) return new ArrayList<>();
        return Lab4Service.json.Point.getJsonPoints(points);
    }

    private void check(Point point){
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();

        if (x >= 0 && y >= 0 && x*x + y*y <= r*r) point.setInArea(true);
        if (x >= 0 && y <= 0 && y >= (x-r)/2) point.setInArea(true);
        if (x <= 0 && y <= 0 && y > -r && x > -r) point.setInArea(true);
    }

}
