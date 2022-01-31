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
import com.mycompany.myapp.entities.Likes;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author arous souhir
 */
public class ServiceLike {
    
 /*    public ArrayList<Likes> likes;
    
    public static ServiceLike instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceLike() {
         req = new ConnectionRequest();
    }

    public static ServiceLike getInstance() {
        if (instance == null) {
            instance = new ServiceLike();
        }
        return instance;
    }

    public boolean addLike(Likes t) {
        String url = Statics.BASE_URL + "/like/" + t.getTopic() + "/" + t.getQuestion();
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
    }

    public ArrayList<Likes> parseLikes(String jsonText){
        ArrayList<Likes> likes2=new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String,Object> likesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)likesListJson.get("root");
            for(Map<String,Object> obj : list){
                Likes t = new Likes();
                float id = Float.parseFloat(obj.get("id").toString());
                Map<String,Object> date = (Map<String,Object>) obj.get("createdAt");
                float dateF = Float.parseFloat(date.get("timestamp").toString());
                t.setId((int)id);
                t.setQuestion(obj.get("question").toString());
                t.setTopic(obj.get("topic").toString());
                t.setCreatedAt((long) dateF);
                t.setNomUser(obj.get("nomUser").toString());
                
                 for ( Categorie c : ServiceCategorie.getInstance().getAllCategories())
                {
                    if(c.getId() == ((int)Float.parseFloat(obj.get("idC").toString())) )
                    {
                        t.setIdC(c);
                    }
                }               

                likes2.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return likes2;
    }
    
    public ArrayList<Like> getAllLikes(){
        String url = Statics.BASE_URL+"/like/allM";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                likes = parseLikes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return likes;
    }
    */
    
}
