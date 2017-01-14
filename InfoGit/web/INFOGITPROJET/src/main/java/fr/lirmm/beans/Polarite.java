/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.lirmm.beans;

/**
 *
 * @author azaz1
 */
public class Polarite {
    public String classe;
    public String sample;
    public String fmeasure;
    public String precision;
    public String recall;
    
    public Polarite(){}
    
    public Polarite(String c, String s, String f,  String p,  String r){
        classe = c;
        sample = s;
        fmeasure = f;
        precision = p;
        recall = r;
    }
    
    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }
    
    public String getFmeasure() {
        return fmeasure;
    }

    public void setFmeasure(String fmeasure) {
        this.fmeasure = fmeasure;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getRecall() {
        return recall;
    }

    public void setRecall(String recall) {
        this.recall = recall;
    }
    
}
