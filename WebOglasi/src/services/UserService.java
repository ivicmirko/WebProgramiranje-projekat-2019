package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import beans.Message;
import beans.User;
import dao.AdDAO;
import dao.CategoryDAO;
import dao.MessageDAO;
import dao.UserDAO;

@Path("/users")
public class UserService {

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
	

	
		@GET
		@Path("/userOfAd/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public User getUserOfAd(@PathParam("id") int adId) {
			UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
			AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
			Ad ad=ads.getAds().get(adId);
			
			if(ad==null) {
				return null;
			}
			
			
			for(User u:users.getUsers().values()) {
				if(u.getUsername().equals(ad.getPublisher())) {
					return u;
				}
			}
			return null;
		}
		
		@GET
		@Path("/getMyActiveAds")
		@Produces(MediaType.APPLICATION_JSON)
		public List<Ad> getMyActiveAds(@Context HttpServletRequest request){
			UserDAO users=(UserDAO) ctx.getAttribute("usersDAO");
			User u=(User) request.getSession().getAttribute("loggedUser");
			AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
			List<Integer> adsIds=users.getActiveAds(u.getId());
			return ads.getMyPublishedAds(adsIds);
		}
		
		@GET
		@Path("/getMyInRealizationAds")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public List<Ad> getMyInRealizationAds(@Context HttpServletRequest request){
			UserDAO users=(UserDAO) ctx.getAttribute("usersDAO");
			User u=(User) request.getSession().getAttribute("loggedUser");
			AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");

			return ads.getMyInRealizationAds(u.getPublishedAds());
		}
		
		@GET
		@Path("/getMyRealizedAds")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public List<Ad> getMyRealizedAds(@Context HttpServletRequest request){
			UserDAO users=(UserDAO) ctx.getAttribute("usersDAO");
			User u=(User) request.getSession().getAttribute("loggedUser");
			AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");

			return ads.getMyRealizedAds(u.getRealizedAds());
		}
		
		@GET
		@Path("/getMyOrderedAds")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public List<Ad> getMyOrderdAds(@Context HttpServletRequest request){
			UserDAO users=(UserDAO) ctx.getAttribute("usersDAO");
			User u=(User) request.getSession().getAttribute("loggedUser");
			AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");

			return ads.getMyOrderedAds(u.getOrderedAds());
		}
		
		@GET
		@Path("/getMyDeliveredAds")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public List<Ad> getMyDeliveredAds(@Context HttpServletRequest request){
			UserDAO users=(UserDAO) ctx.getAttribute("usersDAO");
			User u=(User) request.getSession().getAttribute("loggedUser");
			AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");

			return ads.getMyDeliveredAds(u.getDeliveredAds());
		}
		
		@GET
		@Path("/getMyFavouriteAds")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public List<Ad> getMyFavouriteAds(@Context HttpServletRequest request){
			User u=(User) request.getSession().getAttribute("loggedUser");
			AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
			
			return ads.getAdsByIds(u.getFavouriteAds());
		}
		
		@GET
		@Path("/getAllAds")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public List<Ad> getAllAds(@Context HttpServletRequest request){
			UserDAO users=(UserDAO) ctx.getAttribute("usersDAO");
			User u=(User) request.getSession().getAttribute("loggedUser");
			AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
			return ads.getActiveAds();
		}
		
		@GET
		@Path("/addToFavourites/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response addToFavourite(@PathParam("id") int id, @Context HttpServletRequest request) {
			User u=(User) request.getSession().getAttribute("loggedUser");
			AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
			UserDAO users=(UserDAO) ctx.getAttribute("usersDAO");

			String contextPath=ctx.getRealPath("");
			Ad ad=ads.addToFavourite(id, contextPath);
			u.getFavouriteAds().add(ad.getId());
			users.getUsers().replace(u.getId(), u);
			
			
			ads.saveAds(contextPath);
			users.saveUsers(contextPath);			
			users.saveUsers(contextPath);
			return Response.status(200).build();
		}
		
		@GET
		@Path("/orderAd/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public Ad orderAd(@PathParam("id") int id,@Context HttpServletRequest request) {
			
			CategoryDAO cats=(CategoryDAO)ctx.getAttribute("categoriesDAO");
			AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
			User user=(User)request.getSession().getAttribute("loggedUser");
			String contextPath=ctx.getRealPath("");
			UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
			Ad ad=ads.getAds().get(id);
			
			if(!user.getRole().equals("kupac")){
				return null;
			}
			
			//promeni status oglasa
			ad.setStatus("u realizaciji");
			
			//dodaj u listu porucenih kod kupca
			user.getOrderedAds().add(ad.getId());
			ads.getAds().replace(ad.getId(), ad);
			ads.saveAds(contextPath);
			
			users.saveUsers(contextPath);
			cats.saveCategories(contextPath);
			
			return ad;
		}
		
		@GET
		@Path("/realizeAd/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public Ad realizeAd(@PathParam("id")int id, @Context HttpServletRequest request) {
			CategoryDAO cats=(CategoryDAO)ctx.getAttribute("categoriesDAO");
			AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
			User user=(User)request.getSession().getAttribute("loggedUser");
			String contextPath=ctx.getRealPath("");
			UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
			Ad ad=ads.getAds().get(id);
			
			if(!user.getRole().equals("kupac")){
				return null;
			}
			
			
			ad.setStatus("dostavljeno");
			
			//dodaj oglase u dostavljene kod korsinika i obrisi iz porucenih
			user.getDeliveredAds().add(ad.getId());
			ads.getAds().replace(ad.getId(), ad);
		
			for(int i=0;i<user.getOrderedAds().size();i++) {
				if(user.getOrderedAds().get(i)==ad.getId()) {
					user.getOrderedAds().remove(i);
					break;
				}
			}
			
			//nadji prodavca oglasa
			User publisher=users.findByUsername(ad.getPublisher());
			if(publisher==null) {
				return null;
			}
			
			//dodaj u realizovane i obrisi iz publikovanih
			publisher.getRealizedAds().add(ad.getId());
			
			for(int i=0;i<publisher.getPublishedAds().size();i++) {
				if(publisher.getPublishedAds().get(i)==ad.getId()) {
					publisher.getPublishedAds().remove(i);
					break;
				}
			}
			      
			Message msg=new Message();
			msg.setAdName(ad.getName());
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm");
			String date=formatter.format(new Date());
			msg.setDate(date);
			msg.setReceiverName(ad.getPublisher());
			msg.setSenderName(user.getUsername());
			msg.setReceiverRemoved(false);
			msg.setSenderRemoved(false);
			msg.setTitle("Obaveštenje o realizaciji oglasa");
			msg.setText("Oglas id="+ad.getId()+" naziva="+ad.getName()+" je realizovan od strane korinsika "+user.getUsername());
			MessageDAO messages=(MessageDAO) ctx.getAttribute("messagesDAO");
			messages.addMessage(msg, contextPath);
			
			messages.saveMessages(contextPath);
			ads.saveAds(contextPath);
			users.saveUsers(contextPath);
			cats.saveCategories(contextPath);
			
			return ad;
		}
		
		@GET
		@Path("/reportSeller/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public void reportSeller(@PathParam("id") int id ) {
			MessageDAO messages=(MessageDAO) ctx.getAttribute("messagesDAO");
			CategoryDAO cats=(CategoryDAO)ctx.getAttribute("categoriesDAO");
			AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
			String contextPath=ctx.getRealPath("");
			UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
			
			Ad ad=ads.getAds().get(id);
			ad.setReported(true);
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
			int reports=user.getReports()+1;
			user.setReports(reports);
			if(user.getReports()>3) {
				user.setActive(false);
			}
			users.getUsers().replace(user.getId(), user);
			
			messages.reportMessage(ad, user, contextPath);
			
			messages.saveMessages(contextPath);
			ads.saveAds(contextPath);
			users.saveUsers(contextPath);
			cats.saveCategories(contextPath);
			
			users.saveUsers(contextPath);
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
