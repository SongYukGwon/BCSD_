package domain;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

public class FindPagenation extends Page{
    @ApiParam(required = true)
    private String keyword;
    @ApiParam(required = true, defaultValue = "1")
    private int type;

    public void setKeyword(String keyword){this.keyword = keyword;}

    public String getKeyword(){return keyword;}

    public void setType(int type){this.type = type;}

    public int getType(){return type;}
}
