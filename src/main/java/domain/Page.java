package domain;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

public class Page {
    @ApiParam(required=false,defaultValue = "1")
    private int page = 1;
    @ApiParam(required = false,defaultValue = "10")
    private int range = 10;

    public int getPage(){return page;}

    public void setPage(int page){
        if(page<1)
            page =1;
        this.page= page;
    }

    public int getRange(){return range;}

    public void setRange(int range){
        if(range<10)
            range = 10;
        this.range = range;
    }
}
