package image;

public abstract class SimpleMask {

    public static enum Mode {
        WRAP, ZERO, REPLICATE
    };

    protected SimpleImage simpleImage = null;
    private boolean modify = true;

    public void modifyOriginal(boolean modify) {
        this.modify = modify;
    }

    public boolean willModifyOriginal() {
        return modify;
    }

    public void apply(SimpleImage simpleImage) {
        if (willModifyOriginal() == false) {
            this.simpleImage = simpleImage.copy();
        }
        else {
            this.simpleImage = simpleImage;
        }
    }

    public SimpleImage getSimpleImage() {
        return simpleImage;
    }

}