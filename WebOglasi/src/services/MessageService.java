package services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.java.swing.plaf.motif.MotifEditorPaneUI;

import beans.Ad;
import beans.Category;
import beans.Message;
import beans.User;
import dao.AdDAO;
import dao.CategoryDAO;
import dao.MessageDAO;
import dao.UserDAO;

@Path("/messages")
public class MessageService {

	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void intit() {
		
		if(ctx.getAttribute("messagesDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("messagesDAO", new MessageDAO(contextPath));
		}
			
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
		
	}
	
	@POST
	@Path("/newMessage/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newMessage(@PathParam("id") int userId,Message msg, @Context HttpServletRequest request) {
		User user=(User)request.getSession().getAttribute("loggedUser");
		MessageDAO messages=(MessageDAO)ctx.getAttribute("messagesDAO");
		UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
		String contextPath=ctx.getRealPath("");
		msg.setReceiverName(users.getUsers().get(userId).getUsername());
		msg.setSenderName(user.getUsername());
		messages.addMessage(msg, contextPath);
		
		return  Response.status(200).build();
	}
	
	@PUT
	@Path("/removeMessage/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeMessage(@PathParam("id") int msgId, @Context HttpServletRequest request) {
		User user=(User)request.getSession().getAttribute("loggedUser");
		MessageDAO messages=(MessageDAO)ctx.getAttribute("messagesDAO");
		String contextPath=ctx.getRealPath("");
		
		messages.removeMessage(msgId,user, contextPath);
		
		return  Response.status(200).build();
	}
	
	@PUT
	@Path("/editMessage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editMessage(Message msg, @Context HttpServletRequest request) {
		User user=(User)request.getSession().getAttribute("loggedUser");
		MessageDAO messages=(MessageDAO)ctx.getAttribute("messagesDAO");
		String contextPath=ctx.getRealPath("");
		messages.updateMessage(msg, contextPath);
		
		return  Response.status(200).build();
	}
	
	@GET
	@Path("/getMySentMessages")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getSentMessages(@Context HttpServletRequest request){
		User user=(User)request.getSession().getAttribute("loggedUser");
		MessageDAO messages=(MessageDAO)ctx.getAttribute("messagesDAO");
		
		List<Message> msgs=messages.getMySentMesssages(user.getUsername());

		return msgs;
	}
	
	@GET
	@Path("/getMyInboxMessages")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getInboxMessages(@Context HttpServletRequest request){
		User user=(User)request.getSession().getAttribute("loggedUser");
		MessageDAO messages=(MessageDAO)ctx.getAttribute("messagesDAO");
		
		List<Message> msgs=messages.getMyInboxMesssages(user.getUsername());

		return msgs;
	}
	
	@POST
	@Path("/responseMessage")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message responseMessage(Message msg,@Context HttpServletRequest request) {
		User user=(User)request.getSession().getAttribute("loggedUser");
		MessageDAO messages=(MessageDAO)ctx.getAttribute("messagesDAO");
		UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
		String contextPath=ctx.getRealPath("");
		msg.setSenderName(user.getUsername());
		messages.addMessage(msg, contextPath);
		
		return msg;
	}
	
}
