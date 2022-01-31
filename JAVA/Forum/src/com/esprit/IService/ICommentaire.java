/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.IService;

import java.util.List;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author arous souhir
 */
public interface ICommentaire<T> {
    
    void ajouter(T t) throws SQLException;
    boolean delete(T t) throws SQLException;
    boolean update(T t) throws SQLException;
    void show(T t) throws SQLException;
    
    T rechercher(int id) throws SQLException;

    List<T> readCommentaires (int sujet_id) throws SQLException;
    
  //  List<T> triNbJ(int sujet_id) throws SQLException;
  //  List<T> commentairesUser(String nomUser) throws SQLException;
    
    ObservableList<T> readCommentaires2 (int sujet_id) throws SQLException;
    
     boolean exist(T t) throws SQLException;
     
    ObservableList<T> triNbJ(int sujet_id) throws SQLException;
    ObservableList<T> commentairesUser(String nomUser) throws SQLException;
     
}
