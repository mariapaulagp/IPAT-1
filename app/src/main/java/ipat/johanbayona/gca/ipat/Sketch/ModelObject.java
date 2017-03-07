package ipat.johanbayona.gca.ipat.Sketch;

import ipat.johanbayona.gca.ipat.R;

/**
 * Created by anupamchugh on 26/12/15.
 */
public enum ModelObject {

    //RED(R.string.red, R.layout.view_red),
    SkDraw(R.string.Sketch, R.layout.activity_sketchdraw),
    SkTable(R.string.Table, R.layout.activity_sketchtable);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
