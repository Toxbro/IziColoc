package com.uqac.frenchies.izicoloc.activities.menus_semaine;

/**
 * Created by Dylan on 01/12/2016.
 */

public class Menu
{
    private String jour;
    private String midi;
    private String soir;

    public Menu(String j, String m, String s)
    {
        this.jour = j;
        this.midi = m;
        this.soir = s;
    }

    public String getJour() {return this.jour;}
    public void setJour(String newJour) {this.jour = newJour;}

    public String getMidi() {return this.midi;}
    public void setMidi(String newMidi) {this.midi = newMidi;}

    public String getSoir() {return this.soir;}
    public void setSoir(String newSoir) {this.soir = newSoir;}
}
