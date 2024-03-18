/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbs.compilerproject.parser;

/**
 *
 * @author Ben
 */
public class Param {
    
    private String type;
    private Expression id;
    private boolean brackets;
    
    public Param(String s, Expression e, boolean k){
        
        type = s;
        id = e;
        brackets = k;
    }
}
