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
import com.mycompany.myapp.entities.Sujet;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author arous souhir
 */
public class ServiceSujet {
    
    public ArrayList<Sujet> sujets;
    
    public static ServiceSujet instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceSujet() {
         req = new ConnectionRequest();
    }

    public static ServiceSujet getInstance() {
        if (instance == null) {
            instance = new ServiceSujet();
        }
        return instance;
    }

    public boolean addSujet(Sujet t) {
        String url = Statics.BASE_URL + "/sujet/" + t.getTopic() + "/" + t.getQuestion();
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

    public ArrayList<Sujet> parseSujets(String jsonText){
        ArrayList<Sujet> sujets2=new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String,Object> sujetsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)sujetsListJson.get("root");
            for(Map<String,Object> obj : list){
                Sujet t = new Sujet();
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

                sujets2.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return sujets2;
    }
    
    public ArrayList<Sujet> getAllSujets(){
        String url = Statics.BASE_URL+"/sujet/allM";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                sujets = parseSujets(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return sujets;
    }
    
    public Sujet chercher(String topic ) {
                String url = Statics.BASE_URL+"/sujet/allM";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                sujets = parseSujets(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        Sujet s ;
        for (Iterator<Sujet> it = sujets.iterator(); it.hasNext();) {
            s = it.next();
            if(s.getTopic().equals(topic))
            return s;
        }
        return null ;
    }
    
}
