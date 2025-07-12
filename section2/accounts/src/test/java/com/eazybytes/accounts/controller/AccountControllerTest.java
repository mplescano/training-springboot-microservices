package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.audit.AuditAwareImpl;
import com.eazybytes.accounts.dto.CustomerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountsController.class, includeFilters = {
        @ComponentScan.Filter(Service.class), @ComponentScan.Filter(Repository.class)
})
@AutoConfigureDataJpa
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Import({AuditAwareImpl.class})
public class AccountControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testCreateAccount() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("name 1");
        customerDto.setEmail("email@email.com");
        customerDto.setMobileNumber("9856525521");
        MvcResult mvcResult1 = this.mvc.perform(MockMvcRequestBuilders.post("/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(customerDto))
        ).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        MvcResult mvcResult2 = this.mvc.perform(MockMvcRequestBuilders.get("/api/fetch").param("mobileNumber", customerDto.getMobileNumber()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        //mvcResult2.getResponse().
    }

}
