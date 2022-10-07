package com.winterhold.service;

import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.author.InsertAuthorDTO;
import com.winterhold.dto.author.UpdateAuthorDTO;
import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;

import java.util.List;

public interface CustomerService {

    public List<CustomerGridDTO> getCustomerGrid(Integer page, String membershipNumber,String name);
    public Long getTotalPages(String membershipNumber,String name);

    public String saveCustomer(InsertCustomerDTO dto);
    public UpdateCustomerDTO getUpdateCustomer(String membershipNumber);

    public void updateCustomer(UpdateCustomerDTO dto);
    public void deleteCustomer(String membershipNumber);

    public CustomerGridDTO getDetail(String membershipNumber);

    public void extendExpireDate(String membershipNumber);

    public Boolean checkExistingMembershipNumber(String membershipNumber);
}
