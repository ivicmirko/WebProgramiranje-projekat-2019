package services;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

import beans.Ad;
import beans.Category;
import beans.Message;
import beans.User;
import dao.AdDAO;
import dao.CategoryDAO;
import dao.MessageDAO;
import dao.RecensionDAO;
import dao.UserDAO;

@Path("/ads")
public class AdService {
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
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
		
		if(ctx.getAttribute("recensionsDAO") == null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("recensionsDAO", new RecensionDAO(contextPath));
		}
		
		
	}
	
	@POST
	@Path("/addAd/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Ad addAd(Ad newAd, @PathParam("id") int catId, @Context HttpServletRequest request) {
		User user=(User)request.getSession().getAttribute("loggedUser");
		AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
		CategoryDAO categories=(CategoryDAO)ctx.getAttribute("categoriesDAO");
		UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");

		
		if(!user.getActive()) {
			return null;
		}
		
		newAd.setPublisher(user.getUsername());
		String contextPath=ctx.getRealPath("");
		Ad ad=ads.addAd(newAd, contextPath);
		if(ad==null) {
			return null;
		}
		
		users.addPublishedAd(contextPath, ad.getId(), user.getId());
		categories.addAdToCategory(contextPath, ad.getId(), catId);
		
		return ad;
	}
	
	@GET
	@Path("/getAd/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Ad getAdById(@PathParam("id") int id){
		AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
		Ad ad=ads.getAds().get(id);
		return ad;
	}
	
	@PUT
	@Path("/editAd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Ad editCategory(Ad ad,@Context HttpServletRequest request) {
		CategoryDAO cats=(CategoryDAO)ctx.getAttribute("categoriesDAO");
		AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
		User user=(User)request.getSession().getAttribute("loggedUser");
		String contextPath=ctx.getRealPath("");
		UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
		MessageDAO messages=(MessageDAO)ctx.getAttribute("messagesDAO");
		
		Ad temp=ads.getAds().get(ad.getId());
		if(user.getRole().equals("prodavac") && (ad.getStatus().equals("u realizaciji") || ad.getStatus().equals("dostavljeno"))){
			return null;
		}
		ad.setPublisher(temp.getPublisher());
		ad.setDate(temp.getDate());
		ad.setStatus(temp.getStatus());
		ad.setReported(temp.isReported());
		ad.setRated(temp.isRated());
		ad.setCommented(temp.isCommented());
		ad.setRecensions(temp.getRecensions());
		ad.setSellerCommented(temp.isSellerCommented());
		ad.setSellerRated(temp.isSellerRated());
		String buyer="";
		ads.editAd(ad, contextPath);
		for(User u:users.getUsers().values()) {
			
			for(int a1:u.getOrderedAds()) {
				if(a1==ad.getId()) {
					buyer=u.getUsername();
				}
			}
			
			for(int a1:u.getDeliveredAds()) {
				if(a1==ad.getId()) {
					buyer=u.getUsername();

				}
			}
		}
		
		
		if(user.getRole().equals("admin")) {
			if(ad.getStatus().equals("aktivan")) {
				messages.editActiveAdByAdmin(ad, user, contextPath);
			}else {
				messages.editActiveAdByAdmin(ad, user, contextPath);
				messages.editUnActiveAdByAdmin(ad, user, contextPath, buyer);		}
		}
		
		ads.saveAds(contextPath);
		users.saveUsers(contextPath);
		cats.saveCategories(contextPath);
		return ad;
	}
	
	

	
	@GET
	@Path("/getAllAds")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ad> getAllAd(@Context HttpServletRequest request) {
		AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
		User u=(User)request.getSession().getAttribute("loggedUser");
		if(!u.getRole().equals("admin")) {
			return new ArrayList<Ad>();
		}
		return ads.getAllAds();
	}
	//brise ad
	@GET
	@Path("/test/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Ad test(@Context HttpServletRequest request,@PathParam("id")int id) {
		
		System.out.println("pogodio delete ad");
		MessageDAO messages=(MessageDAO) ctx.getAttribute("messagesDAO");
		CategoryDAO cats=(CategoryDAO)ctx.getAttribute("categoriesDAO");
		AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
		User user=(User)request.getSession().getAttribute("loggedUser");
		String contextPath=ctx.getRealPath("");
		UserDAO users=(UserDAO)ctx.getAttribute("usersDAO");
		
		Ad ad=ads.getAds().get(id);
		if(user.getRole().equals("prodavac") && (ad.getStatus().equals("u realizaciji") || ad.getStatus().equals("dostavljeno"))){
			return null;
		}
		
		String buyer="";
		ads.removeAd(id, contextPath);
		for(User u:users.getUsers().values()) {
			
			for(int a1:u.getOrderedAds()) {
				if(a1==ad.getId()) {
					buyer=u.getUsername();
				}
			}
		
			for(int a1:u.getDeliveredAds()) {
				if(a1==ad.getId()) {
					
					buyer=u.getUsername();
				}
			}

		}
		
		if(user.getRole().equals("prodavac")) {
			messages.removeAdBySeller(ad, user, contextPath);
		}else if(user.getRole().equals("admin")) {
			if(ad.getStatus().equals("aktivan")) {
				messages.removeActiveAdByAdmin(ad, user, contextPath);
			}else {
				messages.removeActiveAdByAdmin(ad, user, contextPath);
				messages.removeUnActiveAdByAdmin(ad, user, contextPath, buyer);
			}
		}

		ads.saveAds(contextPath);
		users.saveUsers(contextPath);
		cats.saveCategories(contextPath);

		return ad;
		//return Response.status(200).build();
	}
	
	@GET
	@Path("/getTopTen")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ad> getTopTen(){
		AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
		List<Ad> topTen=new ArrayList<>();
		List<Ad> allAds=ads.getActiveAds();
		Collections.sort(allAds, new Comparator<Ad>(){
				@Override
			   public int compare(Ad a1, Ad a2) {
			      return Integer.compare(a2.getFavourite(), a1.getFavourite());
			   }
			});
		int top=10;
		if(allAds.size()==0) {
			return new ArrayList<>();
		}else if(allAds.size()<10) {
			top=allAds.size();
		}
		
		for(int i=0; i<top; i++) {
			topTen.add(allAds.get(i));
		}
		return topTen;
	}

}
