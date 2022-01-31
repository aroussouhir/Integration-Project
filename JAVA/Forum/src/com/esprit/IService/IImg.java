/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.IService;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author arous souhir
 */
public interface IImg<T> {
    
    void ajouter(T t) throws SQLException;
    boolean delete(T t) throws SQLException;
    List<T> imgCom(int com_id) throws SQLException;
    List<T> imgSujet (int sujet_id) throws SQLException;

    String imSujet1 (int sujet_id) throws SQLException;
    String imSujet2 (int sujet_id) throws SQLException;
    String imSujet3 (int sujet_id) throws SQLException;
    
    int idimSujet1 (int sujet_id) throws SQLException;
    int idimSujet2 (int sujet_id) throws SQLException;
    int idimSujet3 (int sujet_id) throws SQLException;
    
    T rechercher(int id) throws SQLException; 
    
    T rechercher2 (int sujet_id) throws SQLException ;
    
    
    void delete2(int sujet_id) throws SQLException ;
   
    void show(T t) throws SQLException;
    
}
