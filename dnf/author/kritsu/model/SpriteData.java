package dnf.author.kritsu.model;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("serial")
public class SpriteData implements Serializable {
    private int colorBits;
    private int targetIndex ;
    private int compressMode;
    private byte[] data;
    private int width;
    private int height;
    private int length;
    private int x;
    private int y;
    private int frameWidth;
    private int frameHeight;
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width=width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height=height;
    }
    public int getColorBits() {
        return colorBits;
    }
    public void setColorBits(int colorBits) {
        this.colorBits=colorBits;
    }
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data=data;
    }
    public int getCompressMode() {
        return compressMode;
    }
    public void setCompressMode(int compressMode) {
        this.compressMode=compressMode;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length=length;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x=x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y=y;
    }
    public int getFrameWidth() {
        return frameWidth;
    }
    public void setFrameWidth(int frameWidth) {
        this.frameWidth=frameWidth;
    }
    public int getFrameHeight() {
        return frameHeight;
    }
    public void setFrameHeight(int frameHeight) {
        this.frameHeight=frameHeight;
    }
    public int getTargetIndex() {
        return targetIndex;
    }
    public void setTargetIndex(int targetIndex) {
        this.targetIndex=targetIndex;
    }
    @Override
    public String toString() {
        return "SpriteData{" +
                "colorBits=" + colorBits +
                ", targetIndex=" + targetIndex +
                ", compressMode=" + compressMode +
                ", data=" + Arrays.toString(data) +
                ", width=" + width +
                ", height=" + height +
                ", length=" + length +
                ", x=" + x +
                ", y=" + y +
                ", frameWidth=" + frameWidth +
                ", frameHeight=" + frameHeight +
                '}';
    }
}