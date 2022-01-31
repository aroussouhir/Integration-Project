/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Sujet;
import com.mycompany.myapp.services.ServiceCategorie;
import com.mycompany.myapp.services.ServiceSujet;

/**
 *
 * @author arous souhir
 */
public class AllSujets extends Form{

    Form current;
    public AllSujets(Form previous) {
        current=this;
        setLayout(BoxLayout.y());
 //       setTitle("Tous les sujets");
        
 /*       SpanLabel sp = new SpanLabel();
        sp.setText(ServiceSujet.getInstance().getAllSujets().toString());
        add(sp);
*/
          Form f2 = new Form("Tous les sujets", new BorderLayout());
          Container list = new Container(BoxLayout.y());
          list.setScrollableY(true);
  
            for ( Sujet c : ServiceSujet.getInstance().getAllSujets())
            {
                //list.add(c.toString());
                MultiButton mb = new MultiButton(c.getTopic());
                mb.setTextLine2("Voir");
                mb.addActionListener(e-> new SujetShow(current, c).show());
                list.add(mb);
            }
  
  
  f2.add(CENTER, list);
  addAll(f2);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
    
}
