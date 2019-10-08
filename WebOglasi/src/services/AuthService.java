package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.User;
import dao.UserDAO;


@Path("/auth")
public class AuthService {
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
		// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
	
		if(ctx.getAttribute("usersDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("usersDAO", new UserDAO(contextPath));
		}
	}
	
	@GET
	@Path("/getLogged")
	@Produces(MediaType.APPLICATION_JSON)
	public User getLogged(@Context HttpServletRequest request) {
		return (User)request.getSession().getAttribute("loggedUser");
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(User user, @Context HttpServletRequest request) {
		UserDAO usersDAO=(UserDAO)ctx.getAttribute("usersDAO");
		
		User u=usersDAO.checkLogin(user.getUsername(), user.getPassword());
		if(u!=null) {
			request.getSession().setAttribute("loggedUser", u);

			return u;
		}
		
		return null;
	}
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
		return Response.status(200).build();
	}
	
	@GET
	@Path("/getAllUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllUsers(@Context HttpServletRequest request) {
		User user=(User)request.getSession().getAttribute("loggedUser");
		UserDAO users=(UserDAO) ctx.getAttribute("usersDAO");
		return users.getAllUsers(user);
	}
	
	@GET
	@Path("/getUsersForMessage")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllActiveUsers(@Context HttpServletRequest request) {
		UserDAO users=(UserDAO) ctx.getAttribute("usersDAO");
		User user=(User) request.getSession().getAttribute("loggedUser");
		if(user.getRole().equals("kupac")) {
			return users.getAllSellers();
		}else if(user.getRole().equals("prodavac")) {
			return users.getAllAdmins();
		}else {
			return users.getAllUsers(user);
		}
	}

	
	@POST
	@Path("/signUp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registration(User u, @Context HttpServletRequest request) {
		if(u==null) {
			
		}
		System.out.println(u.getSurname()+" "+u.getName()+" "+u.getPhone());
		System.out.println("bbb"+u.getCity()+" "+u.getRole()+" "+u.getEmail());
		UserDAO users=(UserDAO) ctx.getAttribute("usersDAO");
		boolean postojiVec=users.checkUserName(u.getUsername());
		System.out.println("muuuuu"+postojiVec);
		if(postojiVec==true) {
			return Response.status(400).build();
		}
		
		String contextPath=ctx.getRealPath("");
		users.addUser(u, contextPath);
		
		return Response.status(200).build();
	}
	
	@POST
	@Path("/changeRole")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeRole(User u, @Context HttpServletRequest request) {
		UserDAO users=(UserDAO) ctx.getAttribute("usersDAO");
		User user=(User) request.getSession().getAttribute("loggedUser");
		String contextPath=ctx.getRealPath("");
		if(user==null) {
			return Response.status(401).build();
		}
		
		if(user.getId()==u.getId()) {
			return Response.status(401).build();
		}
		users.editUser(u, contextPath);
		
		return Response.status(200).build();
		
	}
	
	@GET
	@Path("/unmarkUser/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response unmarkUser(@PathParam("id") int id, @Context HttpServletRequest request) {
		UserDAO users=(UserDAO) ctx.getAttribute("usersDAO");
		String contextPath=ctx.getRealPath("");
		User user=users.unmarkUser(id, contextPath);
		if(user==null) {
			return Response.status(401).build();
		}
		
		return Response.status(200).build();

	}
	
	@POST
	@Path("/findUser")
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
