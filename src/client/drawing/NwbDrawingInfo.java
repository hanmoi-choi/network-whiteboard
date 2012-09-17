package client.drawing;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 6:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbDrawingInfo implements Cloneable{

    private Point startPoint;
    private Point endPoint;
    private List<Point> pointList;



    private String text;

    public NwbDrawingInfo(){
        pointList = new ArrayList<Point>();
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
        pointList.add(point);
    }

    public List<Point> getPointList(){
        return this.pointList;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void clearInfo(){
        startPoint = null;
        endPoint = null;
        text = null;
        pointList.clear();
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        NwbDrawingInfo info = new NwbDrawingInfo();
        info.startPoint = this.startPoint;
        info.endPoint = this.endPoint;
        info.text = this.text;

        Iterator<Point> iterator = this.pointList.iterator();
        while(iterator.hasNext()){
            info.pointList.add(iterator.next());
        }

        return info;
    }


}
