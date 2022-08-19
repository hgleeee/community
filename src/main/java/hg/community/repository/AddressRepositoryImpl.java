package hg.community.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hg.community.AddrCondition;
import hg.community.domain.Address;
import hg.community.domain.QAddress;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static hg.community.domain.QAddress.*;

@Repository
public class AddressRepositoryImpl implements AddressRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public AddressRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Address> findAddressByAddrCondition(AddrCondition addrCondition) {
        return queryFactory
                .selectFrom(address)
                .where(addrConditionFit(addrCondition))
                .orderBy(address.id.asc())
                .limit(10)
                .fetch();
    }

    BooleanBuilder addrConditionFit(AddrCondition addrCondition) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (StringUtils.hasText(addrCondition.getCityName())) {
            booleanBuilder.and(address.cityName.like(addrCondition.getCityName()));
        }
        if (StringUtils.hasText(addrCondition.getCityCountyName())) {
            booleanBuilder.and(address.cityCountyName.like(addrCondition.getCityCountyName()));
        }
        if (StringUtils.hasText(addrCondition.getTownName())) {
            booleanBuilder.and(address.townName.like(addrCondition.getTownName()));
        }
        if (StringUtils.hasText(addrCondition.getStreetName())) {
            booleanBuilder.and(address.streetName.like(addrCondition.getStreetName()));
        }
        if (StringUtils.hasText(addrCondition.getBuildingNum())) {
            booleanBuilder.and(address.buildingNum.eq(addrCondition.getBuildingNum()));
        }
        if (StringUtils.hasText(addrCondition.getBuildingSideNum())) {
            booleanBuilder.and(address.buildingSideNum.eq(addrCondition.getBuildingSideNum()));
        }
        return booleanBuilder;
    }
}
