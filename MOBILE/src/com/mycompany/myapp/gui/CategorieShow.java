/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.MultiButton;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Sujet;
import com.mycompany.myapp.services.ServiceSujet;

/**
 *
 * @author arous souhir
 */
public class CategorieShow extends Form{
    Form current;
    public CategorieShow(Form previous,Categorie c) {
        current=this;
        setTitle(c.getNom());
        setLayout(BoxLayout.y());

          Form f2 = new Form("Les sujets", new BorderLayout());
          Container list = new Container(BoxLayout.y());
          list.setScrollableY(true);
  
            for ( Sujet s : ServiceSujet.getInstance().getAllSujets())
            {
                if (s.getIdC().getId()== c.getId())
                {
                    MultiButton mb = new MultiButton(s.getTopic());
                    mb.setTextLine2("Voir");
                    mb.addActionListener(e-> new SujetShow(current, s).show());
                    list.add(mb);
                }
            }
  
  
  f2.add(CENTER, list);
  addAll(f2);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
}
