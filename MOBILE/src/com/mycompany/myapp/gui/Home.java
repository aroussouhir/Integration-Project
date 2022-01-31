/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.mycompany.myapp.AI;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.services.ServiceCategorie;



/**
 *
 * @author arous souhir
 */
public class Home extends Form{
Form current;
String userName="Souhir";
private Image userPicture,userPicture2;
private Resources theme;
    public Home() {
        
        theme = UIManager.initFirstTheme("/theme");
        userPicture = theme.getImage("user2.png");
        userPicture2 = theme.getImage("robot.png");
        
        current=this;
        setTitle("Forum");
        setLayout(BoxLayout.y());
        
       // add(new Label("Choose an option"));
        Button btnAddSujet = new Button("Ajouter un sujet");
        Button btnListSujets = new Button("Tous les sujets");
        
        btnAddSujet.addActionListener(e-> new AjoutSujet(current).show());
        btnListSujets.addActionListener(e-> new AllSujets(current).show());
       
        
 /*       Form hi = new Form("Box Y Layout", new BoxLayout(BoxLayout.Y_AXIS));
hi.add(new Label("First")).
    add(new Label("Second")).
    add(new Label("Third")).
    add(new Label("Fourth")).
    add(new Label("Fifth"));

 addAll(btnAddSujet, btnListSujets, hi);*/
 
 
        SpanLabel sp = new SpanLabel();
        sp.setText(ServiceCategorie.getInstance().getAllCategories().toString());
        //add(sp);*
        
  /*      UIBuilder u = new UIBuilder();
        
        Container c = u.createContainer(theme, "home");
        Form f = (Form) u.findByName("home", c);
        Label label = (Label) u.findByName("Label", c);
        List categories = (List) u.findByName("List", c);
        categories.addActionListener(e-> new AjoutSujet(current).show());
        //f.show();
        addAll(btnAddSujet, btnListSujets, f);
        
        Form f1 = new Form(new FlowLayout(CENTER, CENTER));
        f1.add(new Label("Forum"));
   */     
  
  Form f2 = new Form("Categories", new BorderLayout());
  Container list = new Container(BoxLayout.y());
  list.setScrollableY(true);
  
  for ( Categorie c : ServiceCategorie.getInstance().getAllCategories())
  {
      //list.add(c.toString());
      MultiButton mb = new MultiButton(c.toString());
      mb.addActionListener(e-> new CategorieShow(current, c).show());
      list.add(mb);
  }
  
  
  f2.add(CENTER, list);
  addAll(btnAddSujet, btnListSujets, f2);
  
  
        Toolbar tb = this.getToolbar();
        tb.addMaterialCommandToSideMenu("Forum", FontImage.MATERIAL_FORUM, e-> 
                new AjoutSujet(current).show());
         tb.addMaterialCommandToSideMenu("ChatBot", FontImage.MATERIAL_CHAT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                 showSbaitso();
            }
        });
            
            
    
    
    }
    
     private DataChangedListener createSearchListener(final TextField searchField, final Container discussion, final Button ask) {
        return (type, index) -> {
            String t = searchField.getText();
            int count = discussion.getComponentCount();
            if (t.length() == 0) {
                ask.setEnabled(true);
                for (Component c : discussion) {
                    c.setHidden(false);
                    c.setVisible(true);
                }
                animateChanage(discussion);
                return;
            }
            t = t.toLowerCase();
            ask.setEnabled(false);
            for (Component c : discussion) {
                SpanLabel tt = (SpanLabel) c;
                if (tt.getText().toLowerCase().indexOf(t) < 0) {
                    tt.setHidden(true);
                    tt.setVisible(false);
                } else {
                    tt.setHidden(false);
                    tt.setVisible(true);
                }
            }
            animateChanage(discussion);
        };
    }

    private boolean animateLock;

    void animateChanage(Container discussion) {
        if (!animateLock) {
            animateLock = true;
            discussion.animateLayoutAndWait(300);
            animateLock = false;
        }
    }

    void showSbaitso() {
        Form sb = new Form(new BorderLayout());
        
        
        sb.setFormBottomPaddingEditingMode(true);
        Toolbar t = sb.getToolbar();
        t.addCommandToRightBar(">", null, (evts) -> {
            current.showBack();
        });

        final TextField searchField = new TextField("", "Recherche ...", 20, TextField.ANY);
        t.setTitleComponent(searchField);
        final TextField ask = new TextField("", "Question?", 20, TextField.ANY);
        Button askButton = new Button("Envoyer");
        final Container discussion = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        sb.add(BorderLayout.SOUTH, BorderLayout.center(ask).
                add(BorderLayout.EAST, askButton)).
                add(BorderLayout.CENTER, discussion);

        discussion.setScrollableY(true);

        sb.show();
        Display.getInstance().callSerially(() -> {
            String w = "Bonjour " + userName + ", B-BOT est la pour vous aider !\n"
                    + "Vous avez une question a propos de OuiBike ?"
                    + "Achats, Livraison, Remboursement, Reclamation, Moyens de nous contacter, Informations sur nos platforms ...";

            say(discussion, w, false);

        });
        searchField.addDataChangeListener(createSearchListener(searchField, discussion, askButton));
        ActionListener askEvent = (e) -> {
            String t1 = ask.getText();
            if (t1.length() > 0) {
                ask.setText("");
                say(discussion, t1, true);
                answer(t1, discussion);
            }
        };
        ask.setDoneListener(askEvent);
        askButton.addActionListener(askEvent);
    }

    void answer(String question, Container dest) {
        String resp = AI.getResponse(question);
        say(dest, resp, false);

    }

    void say(Container destination, String text, boolean question) {
        SpanLabel t = new SpanLabel(text);
        t.setWidth(destination.getWidth());
        t.setX(0);
        t.setHeight(t.getPreferredH());

        if (question) {
            t.setY(Display.getInstance().getDisplayHeight());
            t.setTextUIID("BubbleUser");
            t.setIconPosition(BorderLayout.EAST);
            t.setIcon(userPicture);
            t.setTextBlockAlign(Component.RIGHT);
        } else {
            t.setY(0);
            t.setTextUIID("BubbleSbaitso");
            t.setIconPosition(BorderLayout.WEST);
            t.setIcon(userPicture2);
            t.setTextBlockAlign(Component.LEFT);
        }
        destination.add(t);
        destination.animateLayoutAndWait(400);
        destination.scrollComponentToVisible(t);
    }


        
        
        
    }
    

