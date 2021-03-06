package nld.ede.runconnect.backend.service.filters.firewall;

import nld.ede.runconnect.backend.service.tokens.TokenHashMap;

import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import java.util.List;

public class FirewallUsers implements IFirewall {
    @Override
    public void rules(ContainerRequestContext requestContext, List<PathSegment> pathSegments, MultivaluedMap<String, String> parameters) {
        String token = parameters.getFirst("token");
        TokenHashMap tokenHashMap = TokenHashMap.getInstance();

        //Path("/")
        //registerUser
        if (requestContext.getMethod().equals("POST") && pathSegments.size() <= 2) {
            return;
        }

        // @Path("/{follower-id}/followee-activities")
        // Get followee activities.
        if (pathSegments.get(2).toString().equals("followee-activities") && tokenHashMap.doesExist(token)) {
            return;
        }

        // Path("/find/{search-value}")
        // searchForUser
        if (pathSegments.get(1).toString().equals("find") && tokenHashMap.doesExist(token)) {
            return;
        }

        // Path("/{user-id}")
        // Get user based on ID.
        if (pathSegments.get(1).toString().equals("get-by-id") && requestContext.getMethod().equals("GET") && tokenHashMap.doesExist(token)) {
            return;
        }

        // Path("/{follower-id}/is-following/{followee-id}")
        // Is following
        if (pathSegments.get(2).toString().equals("is-following") && requestContext.getMethod().equals("GET") && tokenHashMap.doesExist(token)) {
            return;
        }

        // Path("/{follower-id}/unfollows/{followee-id}")
        // Unfollow
        if (pathSegments.get(2).toString().equals("follows") && requestContext.getMethod().equals("DELETE") && tokenHashMap.doesExist(token)) {
            return;
        }

        // Path("/{follower-id}/follows/{followee-id}")
        // Follow
        if (pathSegments.get(2).toString().equals("follows") && requestContext.getMethod().equals("POST") && tokenHashMap.doesExist(token)) {
            return;
        }

        requestContext.abortWith(Response.status(Response.Status.NOT_FOUND).build());
    }
}
