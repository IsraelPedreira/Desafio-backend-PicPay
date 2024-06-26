package com.desafiopicpay.picpaysimplificado.services;

import com.desafiopicpay.picpaysimplificado.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExternalAPIsService {
    @Autowired
    RestTemplate restTemplate;
    public boolean authorizeTransfer(){
        String url = "https://util.devi.tools/api/v2/authorize";

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if(response.getBody().get("status").equals("success")){
            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            boolean authorization = (boolean) data.get("authorization");
            return authorization;
        }else{
            return false;
        }
    }

    public void sendNotification(String email, String message) throws Exception {
        NotificationDTO request = new NotificationDTO(email, message);
        ResponseEntity<String> response = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", request, String.class);
        if(!response.getStatusCode().equals(HttpStatus.NO_CONTENT)){
            throw new Exception("Could not send notification");
        }
    }

}
