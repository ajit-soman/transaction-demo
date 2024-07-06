package com.transaction.demo.controller;

import com.transaction.demo.domain.Transaction;
import com.transaction.demo.enums.OperationsType;
import com.transaction.demo.service.AccountService;
import com.transaction.demo.service.TransactionService;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.TransactionSystemException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;

    ///////////////// performTransaction /////////////////////////////////////////////

    @Test
    public void testPerformTransactionWithIncorrectPayload() throws Exception {
        this.mockMvc.perform(post("/api/v1/transactions"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testPerformTransactionWithTransactionFailure() throws Exception {
        Mockito.doThrow(new TransactionSystemException("Failed to deduct amount")).when(transactionService).performTransaction(1L,2.0, OperationsType.CREDIT_VOUCHER);
        JSONObject payload = new JSONObject();
        payload.put("accountId", "1");
        payload.put("operationsType", "CREDIT_VOUCHER");
        payload.put("amount", "2.0");

        MvcResult result = this.mockMvc.perform(
                post("/api/v1/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload.toString())

                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        JSONObject content = new JSONObject(result.getResponse().getContentAsString());

        Assertions.assertEquals("Failed to deduct amount", content.get("message"));
    }

    @Test
    public void testPerformTransactionWithSuccessFlow() throws Exception {
        Transaction tx = new Transaction();
        tx.setTransactionId(1L);

        Mockito.when(transactionService.performTransaction(1L,2.0, OperationsType.CREDIT_VOUCHER)).thenReturn(tx);
        JSONObject payload = new JSONObject();
        payload.put("accountId", "1");
        payload.put("operationsType", "CREDIT_VOUCHER");
        payload.put("amount", "2.0");

        MvcResult result = this.mockMvc.perform(
                        post("/api/v1/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload.toString())

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        JSONObject content = new JSONObject(result.getResponse().getContentAsString());

        Assertions.assertEquals("Transaction created successfully", content.get("message"));
        Assertions.assertEquals(1, content.get("data"));
    }
}
