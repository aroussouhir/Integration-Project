/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author arous souhir
 */
public class ServiceCategorie {
    
     public ArrayList<Categorie> categories;
    
    public static ServiceCategorie instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceCategorie() {
         req = new ConnectionRequest();
    }

    public static ServiceCategorie getInstance() {
        if (instance == null) {
            instance = new ServiceCategorie();
        }
        return instance;
    }

 /*   public boolean addCategorie(Categorie t) {
        String url = Statics.BASE_URL + "/categorie/" + t.getNom() ;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }*/

    public ArrayList<Categorie> parseCategories(String jsonText){
        ArrayList<Categorie> categories2=new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String,Object> categoriesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)categoriesListJson.get("root");
            for(Map<String,Object> obj : list){
                Categorie t = new Categorie();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setNom(obj.get("nom").toString());
             //   t.setTopic(obj.get("topic").toString());
                categories2.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return categories2;
    }
    
    public ArrayList<Categorie> getAllCategories(){
        String url = Statics.BASE_URL+"/categorie/allM";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                categories = parseCategories(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return categories;
    }
    
    /*    public Categorie parseCategorie(String jsonText, int id1){
        Categorie categorie2 = new Categorie();
        try {
            JSONParser j = new JSONParser();
            Map<String,Object> categoriesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)categoriesListJson.get("root");
            for(Map<String,Object> obj : list){
                Categorie t = new Categorie();
                float id = Float.parseFloat(obj.get("id").toString());
                if( (int)id == id1)
                {
                    t.setId((int)id);
                    t.setNom(obj.get("nom").toString());
                    categorie2 = t ;
                }
            }
            
            
        } catch (IOException ex) {
            
        }
        return categorie2;
    }
    
    public Categorie findCategorie(int id){
        Categorie categorie = new Categorie();
        String url = Statics.BASE_URL+"/categorie/find/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                categorie = parseCategorie(new String(req.getResponseData()), id);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return categorie;
    }*/
    
   /*         String url = Statics.BASE_URL + "/categorie/" + t.getNom() ;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;*/
    
}
