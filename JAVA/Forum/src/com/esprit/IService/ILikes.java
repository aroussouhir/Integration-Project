/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.IService;
import com.esprit.Entite.Commentaire;
import com.esprit.Entite.Sujet;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author arous souhir
 */
public interface ILikes<T> {
    
    void ajouter(T t) throws SQLException;
    boolean delete(T t) throws SQLException;
    
    ObservableList<Sujet> userLikesSujet(String nomUser) throws SQLException;
    
    T rechercher(String nomUser, int idS)throws SQLException;
    
}
