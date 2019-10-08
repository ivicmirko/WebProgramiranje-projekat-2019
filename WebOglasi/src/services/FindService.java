package services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.User;
import dao.AdDAO;
import dao.CategoryDAO;
import dao.UserDAO;

@Path("/find")
public class FindService {

	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("usersDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("usersDAO", new UserDAO(contextPath));
		}

		if(ctx.getAttribute("categoriesDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("categoriesDAO", new CategoryDAO(contextPath));
		}
		
		if(ctx.getAttribute("adsDAO")==null) {
			String contexxtPath=ctx.getRealPath("");
			ctx.setAttribute("adsDAO", new AdDAO(contexxtPath));
		}
		
		if(ctx.getAttribute("messagesDAO")==null) {
			String contexxtPath=ctx.getRealPath("");
			ctx.setAttribute("messagesDAO", new AdDAO(contexxtPath));
		}
	}
	
	@POST
	@Path("/user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> findUser(User user){
		UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
		List<User> retVal=new ArrayList<>();
		
		String city=user.getCity().toLowerCase();
		String username=user.getUsername().toLowerCase();
		
		for(User u:users.getUsers().values()) {
			if(u.getCity().toLowerCase().equals(city)) {
				retVal.add(u);
			}
		}
		
		for(User u:users.getUsers().values()) {
			if(u.getUsername().toLowerCase().equals(username) && !retVal.contains(u)) {
				retVal.add(u);
			}
		}
		
		return retVal;
	}
}
