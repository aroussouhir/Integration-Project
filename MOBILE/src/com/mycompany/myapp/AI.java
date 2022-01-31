/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Codename One through http://www.codenameone.com/ if you 
 * need additional information or have any questions.
 */

package com.mycompany.myapp;

import com.codename1.util.StringUtil;
import java.util.List;

/**
 * Placed most of the logic here to avoid crowding the main file with nonsense 
 * 
 * @author Shai Almog
 */
public class AI {
    private static AI instance = new AI();
    private static final String[] BAD_WORDS = {"fuck", "sex","stupid"};
    
    private static final String[] WORDS = {"contact", "email","facebook", "telephone", "site" };
    
    private static final String CANT_ABIDE_SUCH_LANGUAGE = "Ce vocabulaire n'est pas toléré.";
    
    private static final String WHY_ARE_YOU_CONCERNED = "Pour plus d'informations vous pouvez consulter notre site Web www.OuiBike.tn ";

    private static final String TOO_LITTLE_DATA_PLEASE_TELL_ME_MORE = "Pouvez-vous etre plus clair ?";
    
    public static String getResponse(String question) {
        return instance.getResponseToQuestion(question.toLowerCase());
    }

    private String getResponseToQuestion(String question) {
        if(has(question, BAD_WORDS)) {
            return CANT_ABIDE_SUCH_LANGUAGE;
        }
        
        if(has(question, WORDS)) {
            return "Pour nous contacter : \n"+"Site Web : www.OuiBike.tn \n"
                    +"Nomero de telephone : 71 802 811 \n"
                    +"Page Facebook : OuiBike \n"
                     +"Email : adresseouibike@gmail.com \n"
                    +"Nous vous attendons impatiemment !";
        }
        
        if(question.startsWith("merci")) {
            return "Merci a vous :)";
        }
        
        if(question.contains("ivraison"))
        {
            return "Nous ne faisons pas de livraison.";
        }
        
        if(question.startsWith("say ")) {
            return question.substring(4);
        }
        
        if(question.length() < 5) {
            return TOO_LITTLE_DATA_PLEASE_TELL_ME_MORE;
        }
        
        List<String> tokens = StringUtil.tokenize(question, " .,;\"':-?!-_");
        return WHY_ARE_YOU_CONCERNED ;
    }
    
    private boolean has(String question, String[] words) {
        for(String s : words) {
            if(question.indexOf(s) > -1) {
                return true;
            }
        }
        return false;
    }
}
