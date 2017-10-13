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
    AppInstance.AFConfig config = new AppInstance.AFConfig("https://microapps.appsfly.io", "1389191907067771", "0122899b-13ef-4770-9bb3-d7b8d6c72cd6");

    @RequestMapping(value="/", method = RequestMethod.POST)
    public String index(HttpServletRequest payload) throws IOException {
        System.out.println("Test");
        System.out.println(payload);
        byte[] bytes = IOUtils.toByteArray(payload.getInputStream());
        String data = new String(bytes);
        System.out.println(data);
        JSONObject jsonObject = new JSONObject(data);
        jsonObject.optJSONObject("user_details").put("bookingRef", "123987");

        System.out.println(jsonObject);

        AppInstance travelProvider = new AppInstance(config, "com.cleartrip.ms-test-cta-server-transaction");
        try {
            return (travelProvider.execSync("confirm_booking", jsonObject, "generic")).toString();
        } catch (AppsflyException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
