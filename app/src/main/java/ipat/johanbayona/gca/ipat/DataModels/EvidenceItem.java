package ipat.johanbayona.gca.ipat.DataModels;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by JohanBayona on 20/10/2016.
 */

public class EvidenceItem {

    private Drawable icon;
    private String name;
    private String type;
    private String eviNumber;

    public EvidenceItem( Drawable icon, String name, String type, String eviNumber) {
        this.icon = icon;
        this.name = name;
        this.type = type;
        this.eviNumber = eviNumber;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEviNumber() {
        return eviNumber;
    }

    public void setEviNumber(String evidence) {
        this.eviNumber = evidence;
    }

}
