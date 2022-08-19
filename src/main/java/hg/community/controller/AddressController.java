package hg.community.controller;

import hg.community.AddrCondition;
import hg.community.domain.Address;
import hg.community.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/address")
    public List<Address> getAddressList(@ModelAttribute AddrCondition addrCondition) {
        return addressService.findAddressByAddrCondition(addrCondition);
    }
}
