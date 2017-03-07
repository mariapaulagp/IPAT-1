package ipat.johanbayona.gca.ipat.DataModels;

import android.graphics.PointF;
import android.widget.ImageView;

import ipat.johanbayona.gca.ipat.DataIpat;

/**
 * Created by Gabriel on 26/01/2017.
 */
public class ObjetPosition
{

    DataIpat.EvidenceCategory type;
    int index;
    float Scale;
    float Rotation;
    PointF Position;
    int flip;
    Boolean Remove;
    ImageView ImageCreate;

    public ImageView getImageCreate() {
        return ImageCreate;
    }

    public void setImageCreate(ImageView imageCreate) {
        ImageCreate = imageCreate;
    }



    public ObjetPosition(float scale, float rotation, PointF position, int flip) {
        Scale = scale;
        Rotation = rotation;
        Position = position;
        this.flip = flip;
    }

    public Boolean getRemove() {
        return Remove;
    }

    public void setRemove(Boolean remove) {
        Remove = remove;
    }



    public DataIpat.EvidenceCategory getType() {
        return type;
    }

    public void setType(DataIpat.EvidenceCategory type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public float getScale() {
        return Scale;
    }

    public void setScale(float scale) {
        Scale = scale;
    }

    public float getRotation() {
        return Rotation;
    }

    public void setRotation(float rotation) {
        Rotation = rotation;
    }

    public PointF getPosition() {
        return Position;
    }

    public void setPosition(PointF position) {
        Position = position;
    }

    public int getFlip() {
        return flip;
    }

    public void setFlip(int flip) {
        this.flip = flip;
    }
}
