package ipat.johanbayona.gca.ipat.DataModels;

/**
 * Created by JohanBayona on 20/10/2016.
 */

public class Medida {

    private int index;
    private int icon;
    private float x;
    private float y;
    private String evidencia;
    private String description;

    public Medida() {

    }

    public int getIndex() {
        return this.index;
    }
    public final void setIndex(int index) { this.index = index; }

    public int getIcon(int icon) { return this.icon; }
    public final void setIcon(int icon) { this.icon = icon; }

    public float getX() {
        return this.x;
    }
    public final void setX(float x) { this.x = x; }

    public float getY() {
        return this.y;
    }
    public final void setY(float y) { this.y = y; }

    public String getEvidencia() {
        return this.evidencia;
    }
    public final void setEvidencia(String evidencia) { this.evidencia = evidencia; }

    public String getDescripcion() {
        return description;
    }
    public final void setDescripcion(String description) { this.description = description; }
}
