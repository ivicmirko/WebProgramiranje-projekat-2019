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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Ad;
import beans.Category;
import dao.AdDAO;
import dao.CategoryDAO;


@Path("/categories")
public class CategoryService {
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("categoriesDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("categoriesDAO", new CategoryDAO(contextPath));
		}
		
		if(ctx.getAttribute("adsDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("adsDAO", new AdDAO(contextPath));
		}
	}
	
	@POST
	@Path("/addCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCategory(Category cat) {
		
		CategoryDAO categories=(CategoryDAO)ctx.getAttribute("categoriesDAO");
		String contextPath=ctx.getRealPath("");
		Category category=categories.addCategory(cat, contextPath);
		if(category==null) {
			return Response.status(400).build();
		}
		return Response.status(200).build();
	}
	
	
	@PUT
	@Path("/editCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editCategory(Category cat,@Context HttpServletRequest request) {
		CategoryDAO vozila=(CategoryDAO)ctx.getAttribute("categoriesDAO");
		String contextPath=ctx.getRealPath("");
		vozila.updateCategory(cat, contextPath);
		return Response.status(200).build();
	}
	
	@GET
	@Path("/removeCategory/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int removeCategory(@PathParam("id") int id) {
		CategoryDAO categories=(CategoryDAO)ctx.getAttribute("categoriesDAO");
		String contextPath=ctx.getRealPath("");
		categories.removeCategory(id, contextPath);

		return 0;
	}
	
	@GET
	@Path("/getAllCategories")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Category> loading() {
		CategoryDAO categories=(CategoryDAO)ctx.getAttribute("categoriesDAO");
		return categories.getAllCategories();
	}
	
	@GET
	@Path("/categoryAds/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ad> getAdsByCategory(@PathParam("id") int catId) {
		System.out.println("pogodio");
		CategoryDAO categories=(CategoryDAO)ctx.getAttribute("categoriesDAO");
		AdDAO ads=(AdDAO)ctx.getAttribute("adsDAO");
		List<Integer> adsOfCat=categories.getCategoryAds(catId);
		
		return ads.getAdsByIds(adsOfCat);
	}
	

}
