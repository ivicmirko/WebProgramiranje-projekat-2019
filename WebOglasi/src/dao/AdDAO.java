package dao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Ad;
import beans.Category;

public class AdDAO {

private HashMap<Integer, Ad> ads=new HashMap<>();
	
	
	public HashMap<Integer, Ad> getAds() {
		return ads;
	}

	public void setAds(HashMap<Integer, Ad> ads) {
		this.ads = ads;
	}

	public AdDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public AdDAO(String contextPath) {
		loadAds(contextPath);
	}
	
	public void loadAds(String contextPath) {
		
		try {
			File file=new File(contextPath+ "/jsonFiles/ads.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			Ad[] adsArray=objectMapper.readValue(file, Ad[].class);
			
			for(Ad ad:adsArray) {
				ads.put(ad.getId(), ad);
			}
			System.out.println("Vozila "+ads.size());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
	}
	
	public void saveAds(String contextPath) {
		try {
			//File file=new File(contextPath+"/jsonFiles/vozila.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			ArrayList<Ad> adsArray=new ArrayList<>();
			
			for(Ad ad: ads.values()) {
				adsArray.add(ad);
			}
			

			objectMapper.writeValue(new File(contextPath+"/jsonFiles/ads.json"), adsArray);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public Ad addAd(Ad newAd, String contextPath) {
				
		int id=generateNewId();
		newAd.setRemoved(false);
		newAd.setRecensions(new ArrayList<>());
		newAd.setId(id);
		newAd.setLikes(0);
		newAd.setDislikes(0);
		newAd.setFavourite(0);
		newAd.setActive(true);
		newAd.setStatus("aktivan");
		newAd.setReported(false);
		newAd.setCommented(false);
		newAd.setRated(false);
		newAd.setSellerCommented(false);
		newAd.setSellerRated(false);
		newAd.setRecensions(new ArrayList<>());
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		String date=formatter.format(new Date());
		newAd.setDate(date);
		ads.put(newAd.getId(), newAd);
		
		saveAds(contextPath);
		return newAd;
		
	}
	
	public void editAd(Ad ad, String contextPath) {
		
		ads.replace(ad.getId(), ad);
		saveAds(contextPath);
	}
	
	public void removeAd(int adId, String contextPath) {
		Ad ad=ads.get(adId);
		ad.setRemoved(true);
		ad.setActive(false);
		ads.put(adId,ad);
		saveAds(contextPath);
	}
	
	public Collection<Ad> getAllAds(){
		ArrayList<Ad> retVal=new ArrayList<>();
		for(Ad a:ads.values()) {
			if(!a.isRemoved()) {
				retVal.add(a);
			}
		}
		Collections.reverse(retVal);
		return retVal;
	}
	
	public List<Ad> getActiveAds(){
		ArrayList<Ad> retVal=new ArrayList<>();
		for(Ad a:ads.values()) {
			if(!a.isRemoved() && a.getStatus().equals("aktivan") && a.isActive()) {
				retVal.add(a);
			}
		}
		Collections.reverse(retVal);
		return retVal;
	}
	
	public Ad addToFavourite(int id, String contextPath) {
		
		Ad ad=ads.get(id);
		int fav=ad.getFavourite()+1;
		ad.setFavourite(fav);
		ads.replace(ad.getId(), ad);
		saveAds(contextPath);
		
		return ad;
	}
	
	public List<Ad> getAdsByIds(List<Integer> adsIds){
		List<Ad> retVal=new ArrayList<>();
		
		for(Ad ad:ads.values()) {
			for(int k:adsIds) {
				if(ad.getId()==k && ad.isActive() && !ad.isRemoved()) {
					retVal.add(ad);
				}
			}
		}
		Collections.reverse(retVal);
		return retVal;
	}
	
	public List<Ad> getMyPublishedAds(List<Integer> adsIds){
		List<Ad> retVal=new ArrayList<>();
		for(Ad a:ads.values()) {
			for(int k:adsIds) {
				if(a.getId()==k && !a.isRemoved() && a.getStatus().equals("aktivan")) {
					retVal.add(a);
				}
			}
		}
		Collections.reverse(retVal);
		return retVal;
	}
	
	public List<Ad> getMyInRealizationAds(List<Integer> adsIds){
		List<Ad> retVal=new ArrayList<>();
		for(Ad a:ads.values()) {
			for(int k:adsIds) {
				if(a.getId()==k && !a.isRemoved() && a.getStatus().equals("u realizaciji")) {
					retVal.add(a);
				}
			}
		}
		Collections.reverse(retVal);
		return retVal;
	}
	
	public List<Ad> getMyRealizedAds(List<Integer> adsIds){
		List<Ad> retVal=new ArrayList<>();
		for(Ad a:ads.values()) {
			for(int k:adsIds) {
				if(a.getId()==k && !a.isRemoved() && a.getStatus().equals("dostavljeno")) {
					retVal.add(a);
				}
			}
		}
		Collections.reverse(retVal);
		return retVal;
	}
	
	public List<Ad> getMyOrderedAds(List<Integer> adsIds){
		List<Ad> retVal=new ArrayList<>();
		for(Ad a:ads.values()) {
			for(int k:adsIds) {
				if(a.getId()==k && !a.isRemoved() && a.getStatus().equals("u realizaciji")) {
					retVal.add(a);
				}
			}
		}
		Collections.reverse(retVal);
		return retVal;
	}
	
	public List<Ad> getMyDeliveredAds(List<Integer> adsIds){
		List<Ad> retVal=new ArrayList<>();
		for(Ad a:ads.values()) {
			for(int k:adsIds) {
				if(a.getId()==k && !a.isRemoved() && a.getStatus().equals("dostavljeno")) {
					retVal.add(a);
				}
			}
		}
		Collections.reverse(retVal);
		return retVal;
	}
	
	
	
	
	public int generateNewId() {
		int id=1;
		for(Ad a:ads.values()) {
			if(a.getId()==id) {
				id++;
			}
		}
		return id;
	}
}
