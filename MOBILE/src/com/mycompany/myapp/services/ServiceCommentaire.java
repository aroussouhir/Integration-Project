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
import com.mycompany.myapp.entities.Commentaire;
import com.mycompany.myapp.entities.Sujet;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author arous souhir
 */
public class ServiceCommentaire {
    
        
    public ArrayList<Commentaire> commentaires;
    
    public static ServiceCommentaire instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceCommentaire() {
         req = new ConnectionRequest();
    }

    public static ServiceCommentaire getInstance() {
        if (instance == null) {
            instance = new ServiceCommentaire();
        }
        return instance;
    }

    public boolean addCommentaire(Commentaire t) {
        String url = Statics.BASE_URL + "/commentaire/" + t.getContenu() + "/" + t.getNomUser()+ "/" + t.getSujet_id().getId();
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

    public ArrayList<Commentaire> parseCommentaires(String jsonText, Sujet ids){
        ArrayList<Commentaire> commentaires2=new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String,Object> commentairesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)commentairesListJson.get("root");
            for(Map<String,Object> obj : list){
                Commentaire t = new Commentaire();
                float id = Float.parseFloat(obj.get("id").toString());
                Map<String,Object> date = (Map<String,Object>) obj.get("createdAt");
                float dateF = Float.parseFloat(date.get("timestamp").toString());
                t.setId((int)id);
                t.setContenu(obj.get("contenu").toString());
                t.setNomUser(obj.get("nomUser").toString());
                t.setCreatedAt((long) dateF);

                if(ids.getId() == ((int)Float.parseFloat(obj.get("sujet").toString())) )
                    {
                        t.setSujet_id(ids);
                        commentaires2.add(t);
                    }
                //commentaires2.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return commentaires2;
    }
    
 /*   public ArrayList<Commentaire> getAllCommentaires(){
        String url = Statics.BASE_URL+"/commentaire/allM";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                commentaires = parseCommentaires(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return commentaires;
    }*/
    
        public ArrayList<Commentaire> sujetCommentaires(Sujet s){
        String url = Statics.BASE_URL+"/commentaire/allM";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                commentaires = parseCommentaires(new String(req.getResponseData()), s);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return commentaires;
    }
}
