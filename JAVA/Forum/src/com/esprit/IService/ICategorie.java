/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.IService;

import com.esprit.Entite.Sujet;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author arous souhir
 */
public interface ICategorie<T> {
    
    void ajouter(T t) throws SQLException;
    boolean delete(T t) throws SQLException;
    boolean update(T t) throws SQLException;
    List<T> readAll() throws SQLException;
    List<Sujet> readSujets (int idC) throws SQLException;
    
    T rechercher(int id) throws SQLException;
    void show(T t) throws SQLException;
    
    boolean exist(T t) throws SQLException;
    
    ObservableList<T> readAll2() throws SQLException;
    ObservableList<String> readAllNew() throws SQLException;
    ObservableList<Sujet> readSujets2 (int idC) throws SQLException;
    int idCateg(String nomCateg) throws SQLException;
    
    
}
