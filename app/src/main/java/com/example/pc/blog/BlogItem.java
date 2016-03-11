package com.example.pc.blog;

/**
 * Created by pc on 7/6/2015.
 */
public class BlogItem{
    private int _id;
    private String _ImagePath;
    private String _BlogTitle;
    private String _BlogSentence;

    public BlogItem(String BlogTitle , String BlogSentence, String ImagePath) {
        this._BlogTitle = BlogTitle; this._BlogSentence = BlogSentence; this._ImagePath=ImagePath;
    }
    public BlogItem() {

    }

    public String get_BlogTitle() {
        return _BlogTitle;
    }

    public void set_BlogTitle(String _BlogTitle) {
        this._BlogTitle = _BlogTitle;
    }

    public String get_ImagePath() {
        return _ImagePath;
    }

    public void set_ImagePath(String _ImagePath) {
        this._ImagePath = _ImagePath;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_BlogSentence() {
        return _BlogSentence;
    }

    public void set_BlogSentence(String _BlogSentence) {
        this._BlogSentence= _BlogSentence;
    }
}
