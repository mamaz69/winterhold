package com.winterhold.service;

import com.winterhold.dao.CustomerRepository;
import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.author.UpdateAuthorDTO;
import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;
import com.winterhold.entity.Author;
import com.winterhold.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImplementation implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private final Integer ROWS_IN_PAGE = 10;

    @Override
    public List<CustomerGridDTO> getCustomerGrid(Integer page, String membershipNumber, String name) {
        Pageable pagination = PageRequest.of(page - 1, ROWS_IN_PAGE, Sort.by("id"));
        List<CustomerGridDTO> grid = customerRepository.findAll(membershipNumber, name, pagination);
        return grid;
    }

    @Override
    public Long getTotalPages(String membershipNumber, String name) {
        double totalData = (double) customerRepository.count(membershipNumber, name);
        long totalPage = (long) (Math.ceil(totalData / (double) ROWS_IN_PAGE));
        return totalPage;
    }

    @Override
    public String saveCustomer(InsertCustomerDTO dto) {
        Customer entity = new Customer(dto.getMembershipNumber(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getGender(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getMembershipExpireDate());
        Customer newCustomer = customerRepository.save(entity);
        return newCustomer.getMembershipNumber();
    }

    @Override
    public UpdateCustomerDTO getUpdateCustomer(String membershipNumber) {
        Optional<Customer> nullableEntity = customerRepository.findById(membershipNumber);
        Customer entity = nullableEntity.get();
        UpdateCustomerDTO dto = new UpdateCustomerDTO(entity.getMembershipNumber(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate(),
                entity.getGender(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getMembershipExpireDate());
        return dto;
    }

    @Override
    public void updateCustomer(UpdateCustomerDTO dto) {
        Customer entity = new Customer(dto.getMembershipNumber(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getGender(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getMembershipExpireDate());
        customerRepository.save(entity);
    }

    @Override
    public void deleteCustomer(String membershipNumber) {
        customerRepository.deleteById(membershipNumber);
    }

    @Override
    public CustomerGridDTO getDetail(String membershipNumber) {
        Customer entity = customerRepository.findById(membershipNumber).get();
        String fullName = String.format("%s %s", entity.getFirstName(), entity.getLastName());
        CustomerGridDTO dto = new CustomerGridDTO(entity.getMembershipNumber(),
                fullName,
                entity.getBirthDate(),
                entity.getGender(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getMembershipExpireDate());
        return dto;
    }

    @Override
    public void extendExpireDate(String membershipNumber) {
        Customer entity = customerRepository.findById(membershipNumber).get();
        LocalDate expireDate = entity.getMembershipExpireDate();
        entity.setMembershipExpireDate(expireDate.plusYears(2));
        customerRepository.save(entity);
    }

    @Override
    public Boolean checkExistingMembershipNumber(String membershipNumber) {
        Long sameMembershipNumber = customerRepository.countMembership(membershipNumber);
        if (sameMembershipNumber>0){
            return true;
        }
        return false;
    }
}
