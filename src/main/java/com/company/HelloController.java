package com.company;

import io.appsfly.core.AppsflyException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;

import io.appsfly.core.AppInstance;
import io.appsfly.core.Callback;
import io.appsfly.util.json.JSONObject;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.*;

@RestController
public class HelloController {
    AppInstance.AFConfig config = new AppInstance.AFConfig("https://microapps.appsfly.io", "2756868445626273", "3a547fb1-f5c5-4185-af50-363fb1c3cf64");

    @RequestMapping(value="/", method = RequestMethod.POST)
    public String index(HttpServletRequest payload) throws IOException {
        byte[] bytes = IOUtils.toByteArray(payload.getInputStream());
        String data = new String(bytes);
        System.out.println(data);
        JSONObject jsonObject = new JSONObject(data);
        jsonObject.optJSONObject("user_details").put("bookingRef", "123987");

        System.out.println(jsonObject);

        AppInstance travelProvider = new AppInstance(config, "io.appsfly.dcb-bank-micro-service-demo");
        try {
            String response = travelProvider.execSync("confirm_booking", jsonObject, "generic").toString();
            System.out.println(response);
            return response;
        } catch (AppsflyException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
