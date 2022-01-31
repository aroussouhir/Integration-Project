/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.IService;

import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author arous souhir
 */
public interface ISujet<T> {
    
    void ajouter(T t) throws SQLException;
    void ajouterA(T t) throws SQLException;
    boolean delete(T t) throws SQLException;
    boolean update(T t) throws SQLException;
    void show(T t) throws SQLException;
    
    T rechercher(int id) throws SQLException;
    
    List<T> readAll() throws SQLException;
    List<T> afficher() throws SQLException;
    List<T> demandes() throws SQLException;
    
    void valider(T t) throws SQLException;
    void rejeter(T t) throws SQLException;
    
    void rejeter2(T t) throws SQLException;
    void Valider2(T t) throws SQLException;
    
    boolean exist(T t) throws SQLException;
    
    ObservableList<T> triNbJ() throws SQLException;
    ObservableList<T> sujetsUser(String nomUser) throws SQLException;
    
    int idSujet (String topic) throws SQLException;
    
    ObservableList<T> readAll2() throws SQLException;
    ObservableList<T> afficher2() throws SQLException;
    ObservableList<T> demandes2() throws SQLException;
    
    ObservableList<T> search(String topic) throws SQLException;
    
}
