package pt.iscte_iul.socialsports;

/**
 * Created by cserrao on 19/07/15.
 */
public class Filter {
    private String filterid;
    private String filtername;
    private String filterdescription;
    private String filterdetails;
    private String filterstartdate;
    private String filterenddate;

    public Filter(String filterid, String filtername, String filterdescription, String filterdetails, String filterstartdate, String filterenddate) {
        super();

        this.filterid = filterid;
        this.filtername = filtername;
        this.filterdescription = filterdescription;
        this.filterdetails = filterdetails;
        this.filterstartdate = filterstartdate;
        this.filterenddate = filterenddate;
    }

    public String getFilterid() {
        return filterid;
    }

    public void setFilterid(String filterid) {
        this.filterid = filterid;
    }

    public String getFiltername() {
        return filtername;
    }

    public void setFiltername(String filtername) {
        this.filtername = filtername;
    }

    public String getFilterdescription() {
        return filterdescription;
    }

    public void setFilterdescription(String filterdescription) {
        this.filterdescription = filterdescription;
    }

    public String getFilterdetails() {
        return filterdetails;
    }

    public void setFilterdetails(String filterdetails) {
        this.filterdetails = filterdetails;
    }

    public String getFilterstartdate() {
        return filterstartdate;
    }

    public void setFilterstartdate(String filterstartdate) {
        this.filterstartdate = filterstartdate;
    }

    public String getFilterenddate() {
        return filterenddate;
    }

    public void setFilterenddate(String filterenddate) {
        this.filterenddate = filterenddate;
    }
}
