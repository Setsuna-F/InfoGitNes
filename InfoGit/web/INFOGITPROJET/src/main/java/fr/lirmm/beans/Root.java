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
public class Root {
    public String sample;
    public String microfmeasure;
    public String macrofmeasure;
    public String microprecision;
    public String macroprecision;
    public String microrecall;
    public String macrorecall;
    
    public Root(){}
    
    public Root(String s, String mif, String maf, String mip, String map, String mir, String mar){
        sample = s;
        microfmeasure = mif;
        macrofmeasure = maf;
        microprecision = mip;
        macroprecision = map;
        microrecall = mir;
        macrorecall = mar;
    }
   
    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }
    
    public String getMicrofmeasure() {
        return microfmeasure;
    }

    public void setMicrofmeasure(String microfmeasure) {
        this.microfmeasure = microfmeasure;
    }

    public String getMacrofmeasure() {
        return macrofmeasure;
    }

    public void setMacrofmeasure(String macrofmeasure) {
        this.macrofmeasure = macrofmeasure;
    }

    public String getMicroprecision() {
        return microprecision;
    }

    public void setMicroprecision(String microprecision) {
        this.microprecision = microprecision;
    }

    public String getMacroprecision() {
        return macroprecision;
    }

    public void setMacroprecision(String macroprecision) {
        this.macroprecision = macroprecision;
    }

    public String getMicrorecall() {
        return microrecall;
    }

    public void setMicrorecall(String microrecall) {
        this.microrecall = microrecall;
    }

    public String getMacrorecall() {
        return macrorecall;
    }

    public void setMacrorecall(String macrorecall) {
        this.macrorecall = macrorecall;
    } 
}
