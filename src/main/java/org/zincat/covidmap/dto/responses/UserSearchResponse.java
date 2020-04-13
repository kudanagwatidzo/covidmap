package org.zincat.covidmap.dto.responses;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.zincat.covidmap.enums.ZincatRole;

import java.util.List;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class UserSearchResponse {
    @ApiModelProperty(position = 0)
    private int id;
    @ApiModelProperty(position = 1)
    private String username;
    @ApiModelProperty(position = 2)
    private List<ZincatRole> roles;
}
