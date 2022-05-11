package com.mk.muradbank.controller;

import com.mk.muradbank.dto.customer.CreateCustomerRequest;
import com.mk.muradbank.dto.customer.CustomerDto;
import com.mk.muradbank.dto.customer.UpdateCustomerRequest;
import com.mk.muradbank.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CreateCustomerRequest customerRequest) {

        //202 okey bna postmappinden döüş nesnesi sağlar
        //Dönüş nesnemi nereden alacağım?
        //customerService den createcustomerı çağırıp customerrequest objesini  vererek
        //createCustomer bana CustomerDto döner
        return ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable String id) {
        return ResponseEntity.ok(customerService.getCustomerDtoById(id));


    }

    //deleteCustomer bir değer dönmediği için return ResponseEntity.ok().build(); olur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();

    }

    //uptade için PutMapping kullanılır
    @PutMapping("/{id}")
    //güncellenmiş customerDto dönecek
    //@PathVariable v   @RequestBody alır neden uptade de   @RequestBody alır?
    //  @RequestBody-->>yeni customer verilerini almam için json ile almam gerek. bunuda
    //  jsonu nesneye çevirmek için de @RequestBody kullanıp aldım
    public ResponseEntity<CustomerDto> uptadeCustomerDto(@PathVariable String id,
                                                         @RequestBody UpdateCustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.uptadeCustomer(id,customerRequest));



    }


}
//@RequestBody -->>v1/customer postmappingi gör0.2ü0düğünde birtane json alıcak. jsonu otomatik olarak createCustomerRequest objesine çevirir
//@PathVariable -->>url den gelen parametriyi  bir değere atayabilmemiz için