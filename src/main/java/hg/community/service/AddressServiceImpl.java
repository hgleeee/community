package hg.community.service;

import hg.community.AddrCondition;
import hg.community.domain.Address;
import hg.community.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public List<Address> findAddressByAddrCondition(AddrCondition addrCondition) {
        return addressRepository.findAddressByAddrCondition(addrCondition);
    }
}
