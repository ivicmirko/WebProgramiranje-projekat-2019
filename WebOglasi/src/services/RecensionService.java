package services;

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

import beans.Ad;
import beans.Category;
import beans.Recension;
import beans.User;
import dao.AdDAO;
import dao.CategoryDAO;
import dao.MessageDAO;
import dao.RecensionDAO;
import dao.UserDAO;

@Path("/recensions")
public class RecensionService {
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("recensionsDAO") == null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("recensionsDAO", new RecensionDAO(contextPath));
		}
		
		if(ctx.getAttribute("adsDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("adsDAO", new AdDAO(contextPath));
		}
		
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
	}
	
	@POST
	@Path("/newRecension")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Recension newRecension(Recension rec,@Context HttpServletRequest request) {
		User user=(User)request.getSession().getAttribute("loggedUser");
		AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
		UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
		RecensionDAO recs=(RecensionDAO)ctx.getAttribute("recensionsDAO");
		MessageDAO messages=(MessageDAO) ctx.getAttribute("messagesDAO");
		CategoryDAO cats=(CategoryDAO)ctx.getAttribute("categoriesDAO");
		String contextPath=ctx.getRealPath("");
		
		rec.setAdName(ads.getAds().get(rec.getAdId()).getName());
		rec.setAuthor(user.getUsername());
		for(User u:users.getUsers().values()) {
			if(u.getUsername().equals(ads.getAds().get(rec.getAdId()).getPublisher())) {
				rec.setSellerId(u.getId());
			}
		}
		rec.setAdRecension(true);
		rec.setMessage("Recenzija oglasa");
		recs.addRecension(rec, contextPath);
		

		
		Ad ad=ads.getAds().get(rec.getAdId());
		ad.setCommented(true);
		ads.getAds().replace(ad.getId(), ad);
		ads.saveAds(contextPath);

		messages.addRecensionMessage(ad, user.getUsername(), contextPath);
		ads.saveAds(contextPath);
		users.saveUsers(contextPath);
		cats.saveCategories(contextPath);
		
		users.saveUsers(contextPath);
		
		return rec;
	}
	
	@POST
	@Path("/newSellerRecension")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Recension newSellerRecension(Recension rec,@Context HttpServletRequest request) {
		User user=(User)request.getSession().getAttribute("loggedUser");
		AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
		UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
		RecensionDAO recs=(RecensionDAO)ctx.getAttribute("recensionsDAO");
		MessageDAO messages=(MessageDAO) ctx.getAttribute("messagesDAO");
		CategoryDAO cats=(CategoryDAO)ctx.getAttribute("categoriesDAO");
		String contextPath=ctx.getRealPath("");
		
		rec.setAdName(ads.getAds().get(rec.getAdId()).getName());
		rec.setAuthor(user.getUsername());
		for(User u:users.getUsers().values()) {
			if(u.getUsername().equals(ads.getAds().get(rec.getAdId()).getPublisher())) {
				rec.setSellerId(u.getId());
			}
		}
		rec.setAdRecension(false);
		rec.setMessage("Recenzija prodavca");
		
		rec.setAdName(ads.getAds().get(rec.getAdId()).getName());
		rec.setAuthor(user.getUsername());
		for(User u:users.getUsers().values()) {
			if(u.getUsername().equals(ads.getAds().get(rec.getAdId()).getPublisher())) {
				rec.setSellerId(u.getId());
			}
		}
		recs.addRecension(rec, contextPath);
		

		
		Ad ad=ads.getAds().get(rec.getAdId());
		ad.setSellerCommented(true);
		ads.getAds().replace(ad.getId(), ad);
		ads.saveAds(contextPath);

		messages.addRecensionMessage(ad, user.getUsername(), contextPath);
		ads.saveAds(contextPath);
		users.saveUsers(contextPath);
		cats.saveCategories(contextPath);
		
		users.saveUsers(contextPath);
		
		return rec;
		
	}
	
	@GET
	@Path("/getRecension/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Recension getRecension(@PathParam("id")int id) {
		RecensionDAO recs=(RecensionDAO)ctx.getAttribute("recensionsDAO");
		
		return recs.getRecensions().get(id);
	}
	
	@POST
	@Path("/editRecension")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Recension editRecension(Recension rec) {
		AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
		UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
		MessageDAO messages=(MessageDAO) ctx.getAttribute("messagesDAO");
		RecensionDAO recs=(RecensionDAO)ctx.getAttribute("recensionsDAO");
		String contextPath=ctx.getRealPath("");
		System.out.println("usao u editRex");
		Recension r=recs.getRecensions().get(rec.getId());
		int adId=r.getAdId();
		Ad ad=ads.getAds().get(adId);
		
		rec.setAdId(adId);
		rec=recs.updateRecension(rec, contextPath);
		messages.ediRecensionMessage(ad, rec.getAuthor(), contextPath);
		
		
		return rec;
	}
	
	@GET
	@Path("/removeRecension/{id}")
	public Response removeRecension(@PathParam("id")int id) {
		RecensionDAO recs=(RecensionDAO)ctx.getAttribute("recensionsDAO");
		String contextPath=ctx.getRealPath("");
		
		recs.removeRecension(id, contextPath);
		return Response.status(200).build();
	}
	
	
	@GET
	@Path("/getSellerRecensions")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Recension> getSellerRecenison(@Context HttpServletRequest request){
		User user=(User)request.getSession().getAttribute("loggedUser");
		RecensionDAO recs=(RecensionDAO)ctx.getAttribute("recensionsDAO");

		return recs.getSellerRecensions(user.getId());
	}
	
	@GET
	@Path("/getUserRecensions")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Recension> getUserRecension(@Context HttpServletRequest request){
		User user=(User)request.getSession().getAttribute("loggedUser");
		RecensionDAO recs=(RecensionDAO)ctx.getAttribute("recensionsDAO");
		System.out.println("bio"+recs.getMyRecensions(user.getUsername()).size());
		return recs.getMyRecensions(user.getUsername());
	}
	
	@GET
	@Path("/getAdRecensions/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Recension> getAdRecension(@PathParam("id")int id){
		RecensionDAO recs=(RecensionDAO)ctx.getAttribute("recensionsDAO");
		
		return recs.getAdRecensions(id);
	}
	
	@GET
	@Path("/likeAd/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void likeAd(@PathParam("id") int id) {
		MessageDAO messages=(MessageDAO) ctx.getAttribute("messagesDAO");
		CategoryDAO cats=(CategoryDAO)ctx.getAttribute("categoriesDAO");
		AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
		String contextPath=ctx.getRealPath("");
		UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
		
		Ad ad=ads.getAds().get(id);
		ad.setRated(true);
		int l=ad.getLikes()+1;
		ad.setLikes(l);
		ads.getAds().replace(ad.getId(), ad);
		ads.saveAds(contextPath);

		
		User user=null;
		
		for(User u:users.getUsers().values()) {
			if(u.getUsername().equals(ad.getPublisher())) {
				user=u;
			}
		}
		if(user==null) {
			return;
		}
		
		ads.saveAds(contextPath);
		users.saveUsers(contextPath);
		cats.saveCategories(contextPath);
		
		users.saveUsers(contextPath);
	}
	
	
	@GET
	@Path("/dislikeAd/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void dislikeAd(@PathParam("id") int id) {
		MessageDAO messages=(MessageDAO) ctx.getAttribute("messagesDAO");
		CategoryDAO cats=(CategoryDAO)ctx.getAttribute("categoriesDAO");
		AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
		String contextPath=ctx.getRealPath("");
		UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
		
		Ad ad=ads.getAds().get(id);
		ad.setRated(true);
		int l=ad.getDislikes()+1;
		ad.setDislikes(l);
		ads.getAds().replace(ad.getId(), ad);
		ads.saveAds(contextPath);

		
		User user=null;
		
		for(User u:users.getUsers().values()) {
			if(u.getUsername().equals(ad.getPublisher())) {
				user=u;
			}
		}
		if(user==null) {
			return;
		}
		
		
		ads.saveAds(contextPath);
		users.saveUsers(contextPath);
		cats.saveCategories(contextPath);
		
		users.saveUsers(contextPath);
	}
	
	@GET
	@Path("/likeSeller/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void likeSeller(@PathParam("id")int id) {
		MessageDAO messages=(MessageDAO) ctx.getAttribute("messagesDAO");
		CategoryDAO cats=(CategoryDAO)ctx.getAttribute("categoriesDAO");
		AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
		String contextPath=ctx.getRealPath("");
		UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
		
		Ad ad=ads.getAds().get(id);
		ad.setSellerRated(true);
		ads.getAds().replace(ad.getId(), ad);
		ads.saveAds(contextPath);

		
		User user=null;
		
		for(User u:users.getUsers().values()) {
			if(u.getUsername().equals(ad.getPublisher())) {
				user=u;
			}
		}
		if(user==null) {
			return;
		}
		int l=user.getLikes()+1;
		user.setLikes(l);
		users.getUsers().replace(user.getId(), user);
		
		
		
		
		messages.saveMessages(contextPath);
		ads.saveAds(contextPath);
		users.saveUsers(contextPath);
		cats.saveCategories(contextPath);
		
		users.saveUsers(contextPath);
	}
	
	@GET
	@Path("/dislikeSeller/{id}")
	public void dislikeSeller(@PathParam("id")int id) {
		MessageDAO messages=(MessageDAO) ctx.getAttribute("messagesDAO");
		CategoryDAO cats=(CategoryDAO)ctx.getAttribute("categoriesDAO");
		AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
		String contextPath=ctx.getRealPath("");
		UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
		
		Ad ad=ads.getAds().get(id);
		ad.setSellerRated(true);
		ads.getAds().replace(ad.getId(), ad);
		ads.saveAds(contextPath);

		
		User user=null;
		
		for(User u:users.getUsers().values()) {
			if(u.getUsername().equals(ad.getPublisher())) {
				user=u;
			}
		}
		if(user==null) {
			return;
		}
		int l=user.getLikes()+1;
		user.setDislikes(l);
		users.getUsers().replace(user.getId(), user);
		
		
		messages.saveMessages(contextPath);
		ads.saveAds(contextPath);
		users.saveUsers(contextPath);
		cats.saveCategories(contextPath);
		
		users.saveUsers(contextPath);
	}
	
	

}
