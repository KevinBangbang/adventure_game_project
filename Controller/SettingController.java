package Controller;

import com.sun.speech.freetts.Voice;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;

public class SettingController {
    int volume = 50;
    int brighness = 50;
    boolean normal = true;
    ToggleButton c1;
    ToggleButton c2;
    ArrayList<Pane> panes = new ArrayList<>();
    ArrayList<MediaPlayer> medias = new ArrayList<>();
    ColorAdjust colorAdjust;
    public SettingController()
    {
        this.colorAdjust = new ColorAdjust();
    }

    public int getVolume()
    {
        return this.volume;
    }
    public int getBrighness()
    {
        return this.brighness;
    }
    public void setVolume(int v){
        this.volume = v;
    }

    public void setBrightness(int v){
        this.brighness = v;

    }
    public void updateBrightness()
    {
        this.colorAdjust.setBrightness((double)(this.brighness-50)/50);
        for (Pane p:this.panes)
        {
            p.setEffect(this.colorAdjust);
        }
    }
    public void updateVolume()
    {
        for (MediaPlayer m: this.medias)
        {
            m.setVolume((double)this.volume/100);
        }
    }
    public void changeNormal()
    {
        if(!this.normal)
        {
            this.normal = true;
            this.c1.setSelected(this.normal);
            this.c2.setSelected(!this.normal);

        }
        else {
            this.c1.setSelected(true);
        }
    }
    public void changeAccess()
    {
        if(this.normal)
        {
            c2.setSelected(this.normal);
            c1.setSelected(!this.normal);
            this.normal = false;
        }
        else {
            c2.setSelected(true);
        }
    }
    public boolean getMode()
    {
        return this.normal;
    }
    public void setToggleButton(ToggleButton t1, ToggleButton t2)
    {
        this.c1 = t1;
        this.c2 = t2;
    }
    public void addPane(Pane pane)
    {
        this.panes.add(pane);
    }

    public void addMedia(MediaPlayer mediaPlayer)
    {
        this.medias.add(mediaPlayer);
    }

}
