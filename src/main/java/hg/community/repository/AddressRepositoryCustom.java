package hg.community.repository;

import hg.community.AddrCondition;
import hg.community.domain.Address;

import java.util.List;

public interface AddressRepositoryCustom {

    public List<Address> findAddressByAddrCondition(AddrCondition addrCondition);
}
