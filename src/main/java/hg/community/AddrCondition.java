package hg.community;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddrCondition {

    private String cityName;
    private String cityCountyName;
    private String townName;
    private String streetName;
    private String buildingNum;
    private String buildingSideNum;
}
