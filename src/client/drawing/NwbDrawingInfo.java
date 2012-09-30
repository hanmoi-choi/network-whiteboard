package client.drawing;

import java.awt.*;
import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.util.List;

import static client.view.ui.comp.NwbCanvas.StrokeNFillMode;
import static client.view.ui.comp.NwbCanvas.StrokeNFillMode.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 6:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbDrawingInfo implements Cloneable, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3155855877520331685L;

    private Point canvasSize;
    private Color bgColor = Color.WHITE;
    private Color fgColor = Color.BLACK;

    private Point startPoint;
    private Point endPoint;
    private List<Point> erasorPointList;

    private List<Map<String, Point>> sketchPointList = new ArrayList<Map<String, Point>>();

    private String text;
    private Font font = new Font("Arial",Font.PLAIN, 12);
    private File imageFile;
    private boolean isImageStale;

    private StrokeNFillMode fillNStrokeMode;

    private int strokeSize = 1;


    public NwbDrawingInfo(){
        erasorPointList = new ArrayList<Point>();
        sketchPointList = new ArrayList<Map<String, Point>>();
    }
    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public void addPointToPointList(Point point) {
        erasorPointList.add(point);
    }

    public List<Point> getErasorPointList(){
        return this.erasorPointList;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return this.font;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public Color getFgColor() {
        return fgColor;
    }

    public void setFgColor(Color fgColor) {
        this.fgColor = fgColor;
    }

    public void clearInfo(){
        startPoint = null;
        endPoint = null;
        text = null;
        font = new Font("Arial",Font.PLAIN, 12);
        imageFile = null;
        canvasSize = null;
        bgColor = Color.WHITE;
        fgColor = Color.BLACK;
        strokeSize = 1;
        fillNStrokeMode = StrokeOnly;
        erasorPointList.clear();
        sketchPointList.clear();
    }

    public NwbDrawingInfo getClone(){
        NwbDrawingInfo info = null;
        try {
            info = (NwbDrawingInfo) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return info;
    }

    public File getBGImage(){
        isImageStale = false;
        return this.imageFile;
    }
    public void setBGImage(File imageFile) {
        this.imageFile = imageFile;
        isImageStale = true;
    }

    public boolean isImageStale() {
        return this.isImageStale;
    }

    public void setStrokeNFillMode(StrokeNFillMode fillNStroke) {
        this.fillNStrokeMode = fillNStroke;
    }
    public StrokeNFillMode getFillNStrokeMode() {
        return fillNStrokeMode;
    }

    public void setStrokeSize(int strokeSize) {
        this.strokeSize = strokeSize;
    }

    public int getStrokeSize() {
        return strokeSize;
    }

    public void addSketchPoints(Point startPoint, Point endPoint) {
        Map<String, Point> sketchPoints = new HashMap<String, Point>();
        sketchPoints.put("start", startPoint);
        sketchPoints.put("end", endPoint);

        sketchPointList.add(sketchPoints);
    }

    public List<Map<String, Point>> getSketchPointList(){
        return this.sketchPointList;
    }

    public Point getCanvasSize() {
        return canvasSize;
    }

    public void setCanvasSize(Point canvasSize) {
        this.canvasSize = canvasSize;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        NwbDrawingInfo info = new NwbDrawingInfo();
        info.startPoint = this.startPoint;
        info.endPoint = this.endPoint;
        info.text = this.text;
        info.font = this.font;
        info.imageFile = this.imageFile;
        info.bgColor = this.bgColor;
        info.fgColor = this.fgColor;
        info.isImageStale = true;
        info.strokeSize = this.strokeSize;
        info.fillNStrokeMode = this.fillNStrokeMode;
        info.canvasSize = this.canvasSize;

        Iterator<Point> erasorIterator = this.erasorPointList.iterator();
        while(erasorIterator.hasNext()){
            info.erasorPointList.add(erasorIterator.next());
        }

        Iterator<Map<String, Point>> sketchIterator = this.sketchPointList.iterator();
        while(sketchIterator.hasNext()){
            info.sketchPointList.add(sketchIterator.next());
        }

        return info;
    }
}
