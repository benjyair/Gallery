package io.benjyair.gallery.net;

import java.util.List;

public class Gallery {

    private int total;
    private boolean end;

    private List<Picture> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public List<Picture> getList() {
        return list;
    }

    public void setList(List<Picture> list) {
        this.list = list;
    }

    public static class Picture {

        private String title;
        private String thumb;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }
}
