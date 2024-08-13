package io.unufolio.restful;

/**
 * @author Unufolio unufolio@gmail.com
 */
public class PaginationParameters {

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int DEFAULT_PAGE_NUM = 1;

    private int pageSize = DEFAULT_PAGE_SIZE;
    private long pageNum = DEFAULT_PAGE_NUM;
    private String cursor;

    public PaginationParameters() {}

    public PaginationParameters(int pageSize, long pageNum, String cursor) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.cursor = cursor;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize >= 1) {
            this.pageSize = pageSize;
        }
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        if (pageNum >= 1) {
            this.pageNum = pageNum;
        }
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public PaginationParameters limitPageSize(int maxPageSize) {
        this.pageSize = Math.min(pageSize, maxPageSize);
        return this;
    }

    public PaginationParameters limitPageNum(int maxPageNum) {
        this.pageNum = Math.min(pageSize, maxPageNum);
        return this;
    }

    @Override
    public String toString() {
        return "PaginationParameters{" +
                "pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}
