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
import com.mycompany.myapp.entities.Img;
import com.mycompany.myapp.entities.Img;
import com.mycompany.myapp.entities.Sujet;
import static com.mycompany.myapp.services.ServiceSujet.instance;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author arous souhir
 */
public class ServiceImg {
    
    public ArrayList<Img> imgs;
    
        public static ServiceImg instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceImg() {
         req = new ConnectionRequest();
    }
    
    public static ServiceImg getInstance() {
        if (instance == null) {
            instance = new ServiceImg();
        }
        return instance;
    }
    
        public boolean addImg(String ref, int sujet_id) {
        String url = Statics.BASE_URL + "/IMG/" + ref + "/" + sujet_id;
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
        
        
        public ArrayList<Img> parseImgs(String jsonText){
        ArrayList<Img> imgs2=new ArrayList<>();
        try {
            JSONParser j = new JSONParser();
            Map<String,Object> imgsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)imgsListJson.get("root");
            for(Map<String,Object> obj : list){
                Img t = new Img();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setRef(obj.get("ref").toString());
                t.setSujet_id((int)obj.get("sujet_id"));
                imgs2.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return imgs2;
    }
    
    public ArrayList<Img> getAllImgs(){
        String url = Statics.BASE_URL+"/IMG/allM";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                imgs = parseImgs(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return imgs;
    }
    
}
