package co.techsylvania.rolocolor.model;

/**
 * Created by tavi on 22/05/16.
 */
public class MainMenuItem {
    public static final int ItemTypeNone = 0;
    public static final int ItemTypeColorCorrection = 1;
    public static final int ItemTypeWorldEnhance = 2;

    private String itemName;
    private int itemDrawable, itemType;
    public MainMenuItem(int itemType, String itemName, int drawableResource) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.itemDrawable = drawableResource;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemDrawable() {
        return itemDrawable;
    }

    public void setItemDrawable(int itemDrawable) {
        this.itemDrawable = itemDrawable;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
