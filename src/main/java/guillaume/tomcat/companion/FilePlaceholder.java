
package guillaume.tomcat.companion;


public class FilePlaceholder {

    private String path;

    public FilePlaceholder() {
        super();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FilePlaceholder forPath(String path) {
        this.path = path;
        return this;
    }

}
