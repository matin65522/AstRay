package com.rayvarz.rayasset3.model;

public class RecAstItem {
    private String AstImg;
    private String AstTitle;
    private String AstBarcode;
    private String AstLocation;
    private int AstClassify;

    public RecAstItem(String astImg, String astTitle, String astBarcode, String astLocation, int astClassify) {
        AstImg = astImg;
        AstTitle = astTitle;
        AstBarcode = astBarcode;
        AstLocation = astLocation;
        AstClassify = astClassify;
    }

    public int getAstClassify() {return AstClassify;}

    public void setAstClassify(int astClassify) {AstClassify = astClassify;}

    public String getAstImg() {
        return AstImg;
    }

    public void setAstImg(String astImg) {
        AstImg = astImg;
    }

    public String getAstTitle() {
        return AstTitle;
    }

    public void setAstTitle(String astTitle) {
        AstTitle = astTitle;
    }

    public String getAstBarcode() {
        return AstBarcode;
    }

    public void setAstBarcode(String astBarcode) {
        AstBarcode = astBarcode;
    }

    public String getAstLocation() {
        return AstLocation;
    }

    public void setAstLocation(String astLocation) {
        AstLocation = astLocation;
    }
}
