package com.transaction.demo.controller;


import com.transaction.demo.domain.Account;
import com.transaction.demo.service.AccountService;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.TransactionSystemException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;

    ///////////////// createAccount /////////////////////////////////////////////
    @Test
    public void testCreateAccountWithNoDocumentNumber() throws Exception {
        this.mockMvc.perform(post("/api/v1/accounts"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateAccountWithDuplicateDocumentNumber() throws Exception {
        Mockito.doThrow(new DataIntegrityViolationException("Duplicate document number")).when(accountService).saveAccount(123L);
        JSONObject payload = new JSONObject();
        payload.put("documentNumber", "123");

        this.mockMvc.perform(
                        post("/api/v1/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload.toString())
                )
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateAccountWithGenericException() throws Exception {
        Mockito.doThrow(new TransactionSystemException("failed to save account")).when(accountService).saveAccount(123L);
        JSONObject payload = new JSONObject();
        payload.put("documentNumber", "123");

        this.mockMvc.perform(
                        post("/api/v1/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload.toString())
                )
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateAccountWithSuccessFlow() throws Exception {
        Mockito.when(accountService.saveAccount(123L)).thenReturn(1L);
        JSONObject payload = new JSONObject();
        payload.put("documentNumber", "123");

        MvcResult result = this.mockMvc.perform(
                        post("/api/v1/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload.toString())
                )
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

        JSONObject content = new JSONObject(result.getResponse().getContentAsString());

        Assertions.assertEquals(1, content.get("data"));
        Assertions.assertEquals("Account created successfully", content.get("message"));
    }

    ///////////////// getAccount /////////////////////////////////////////////

    @Test
    public void testGetAccountWithIncorrectAccountId() throws Exception {

        MvcResult result = this.mockMvc.perform(
                        get("/api/v1/accounts/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()).andExpect(status().isNotFound())
                .andReturn();

        JSONObject content = new JSONObject(result.getResponse().getContentAsString());
        Assertions.assertEquals("Account not found", content.get("message"));

    }

    @Test
    public void testGetAccountWithCorrectAccountId() throws Exception{
        Account account = new Account();
        account.setAccountId(1L);
        account.setDocumentNumber(123L);
        account.setBalance(2.00);
        Mockito.when(accountService.getAccount(1L)).thenReturn(account);

        MvcResult result = this.mockMvc.perform(
                        get("/api/v1/accounts/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()).andExpect(status().isOk())
                .andReturn();

        JSONObject content = new JSONObject(result.getResponse().getContentAsString());
        Assertions.assertEquals("Account details", content.get("message"));

        JSONObject accountJson = content.getJSONObject("data");
        Assertions.assertEquals(1, accountJson.get("accountId"));
        Assertions.assertEquals(123, accountJson.get("documentNumber"));
        Assertions.assertEquals(2.0, accountJson.get("balance"));
    }


}
