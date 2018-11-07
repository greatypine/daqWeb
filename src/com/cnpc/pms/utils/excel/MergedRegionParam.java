package com.cnpc.pms.utils.excel;

/**
 * @Function: Excel文件中合并单元格标题
 * @Auther: chenchuang
 * @Date: 2018/10/30 18:19
 */
public class MergedRegionParam {
    private String start;//起始标题
    private String end;//结束标题

    private int i;//根据起始标题确定的起始坐标
    private int j;//根据结束标题确定的结束坐标
    private String name;//合并后的标题名

    public MergedRegionParam(String start, String end, String name){
        this.start = start;
        this.end = end;
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
}
