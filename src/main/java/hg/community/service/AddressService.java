package hg.community.service;

import hg.community.AddrCondition;
import hg.community.domain.Address;

import java.util.List;

public interface AddressService {

    public List<Address> findAddressByAddrCondition(AddrCondition addrCondition);

}
