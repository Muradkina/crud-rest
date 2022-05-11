package com.mk.muradbank.service;

import com.mk.muradbank.dto.customer.CreateCustomerRequest;
import com.mk.muradbank.dto.customer.CustomerDto;
import com.mk.muradbank.dto.customer.CustomerDtoConverter;
import com.mk.muradbank.dto.customer.UpdateCustomerRequest;
import com.mk.muradbank.model.Customer;
import com.mk.muradbank.model.City;
import com.mk.muradbank.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    //request customere çevrildi
    public CustomerDto createCustomer(CreateCustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setId(customerRequest.getId());
        customer.setAd(customerRequest.getAd());
        customer.setDogumYili(customerRequest.getDogumYili());

        //CustomerRequestin getSehirini getir-> onunda değerini getir
        customer.setCity(City.valueOf(customerRequest.getCity().name()));
        customer.setAdres(customerRequest.getAdres());
        customerRepository.save(customer);

        return customerDtoConverter.convert(customer);
    }

    public List<CustomerDto> getAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer customer : customerList) {
            customerDtoList.add(customerDtoConverter.convert(customer));
        }

        return customerDtoList;
    }


    public CustomerDto getCustomerDtoById(String id) {
        Optional<Customer> customerOptinal = customerRepository.findById(id);

        //optionalın içerisindeki değeri dışarı çıkartmak için map()i kullandım.
        //customerDtoConverteri lambda fonksiyonu ile çağırdım
        //eğer customerOptinal.map(customerDtoConverter::convert) bş ise orElse gir  ve yeni bi customerDto getir
        //böylelikle programım hata alıp patlamaktan kurtuldu
        return customerOptinal.map(customerDtoConverter::convert).orElse(new CustomerDto());
    }

    public void deleteCustomer(String id) {
        //deleteCustomerde repositorye git id yi ver ve delete etmesini söyle
        customerRepository.deleteById(id);

    }
    //service kısmında genelde CustomerDto döndüm. veritabanı modelim olan customer dönmedim

    public CustomerDto uptadeCustomer(String id, UpdateCustomerRequest customerRequest) {
        //1->veri tabanında ki findById ile customerı bul
        Optional<Customer> customerOptional = customerRepository.findById(id);
        //2->customerRequest ile gelen değişkenle bu customeri güncelle
        //2a-> Optional<Customer> içini açcustomerRequestte ki değerler ile güncelle
        //ifPresent  eğer metotdun içerisinde değer varsa o işlemi yap. yoksa atla
        customerOptional.ifPresent(customer -> {
            customer.setAd(customerRequest.getAd());
            customer.setDogumYili(customerRequest.getDogumYili());
            //enumTipini almak için City.valueOf kullandım
            customer.setCity(City.valueOf(customerRequest.getCity().name()));
            customer.setAdres(customerRequest.getAdres());
            //3->save et
            customerRepository.save(customer);

        });


        return customerOptional.map(customerDtoConverter::convert).orElse(new CustomerDto());
    }

    // protected yapmamın sebebi sadece servis paketleri erişebilsin. yanıAccountservice erişebilir
    protected Customer getCustomerById(String id) {
        return customerRepository.findById(id).orElse(new Customer());


    }
}