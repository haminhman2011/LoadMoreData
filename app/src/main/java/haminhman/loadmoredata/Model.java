package haminhman.loadmoredata;

import java.io.Serializable;

/**
 * Created by haminhman on 8/8/2017.
 */

public class Model implements Serializable {
    public String id;
    public String name;

    public Model(){}

    public Model(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
