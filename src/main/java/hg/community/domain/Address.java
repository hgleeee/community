package hg.community.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@Builder
public class Address {

    @Id
    @Column(name = "건물관리번호")
    private Long id;

    @Column(name = "시도명")
    private String cityName;

    @Column(name = "시군구명")
    private String cityCountyName;

    @Column(name = "법정읍면동명")
    private String townName;

    @Column(name = "도로명")
    private String streetName;

    @Column(name = "건물본번")
    private String buildingNum;

    @Column(name = "건물부번")
    private String buildingSideNum;

    public Address() {

    }
}
