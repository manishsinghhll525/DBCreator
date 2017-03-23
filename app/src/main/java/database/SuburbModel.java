package database;

/**
 * Created by techelogy2 on 26/10/16.
 */

public class SuburbModel {
    private String id;
    private String post_code;
    private String suburbs;
    private String country_id;
    private String state_id;
    private String delete_status;
    private String lat;
    private String lng;
    private String country_name;
    private String display_title;
    private String insertionTime;

    public String getInsertionTime() {
        return insertionTime;
    }

    public void setInsertionTime(String insertionTime) {
        this.insertionTime = insertionTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getSuburbs() {
        return suburbs;
    }

    public void setSuburbs(String suburbs) {
        this.suburbs = suburbs;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(String delete_status) {
        this.delete_status = delete_status;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getDisplay_title() {
        return display_title;
    }

    public void setDisplay_title(String display_title) {
        this.display_title = display_title;
    }

    @Override
    public String toString() {
        return display_title;
    }


    @Override
    public boolean equals(Object object) {
        //  System.out.println("inside equals method = " + ((QualificationSubModel)object).id);
        boolean result = false;

        if (object == null) {
            result = false;

        } else {


            SuburbModel subModel = (SuburbModel) object;
            //  System.out.println("inside else method = " +this.id+"==model id=="+subModel.id);
            if (this.id.equals(subModel.id)) {
                result = true;
            }
        }


        return result;
    }

}
