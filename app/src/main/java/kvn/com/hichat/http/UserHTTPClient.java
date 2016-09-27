package kvn.com.hichat.http;

import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.spothero.volley.JacksonRequest;
import com.spothero.volley.JacksonRequestListener;

import java.io.IOException;

import kvn.com.hichat.ApplicationController;
import kvn.com.hichat.entity.User;

/**
 * Created by sevo on 21.09.2016.
 */

public class UserHTTPClient {
    private static User user = null;

    public static User getUser(){
        final String URL = "http://192.168.6.59:8080/user/registration";

        final StringRequest req = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            user =  objectMapper.readValue(response.getBytes(),User.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Response is: " + user);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });
        ApplicationController.getInstance().addToRequestQueue(req);
        return  user;
    }

    public static User get(){
        final String URL = "http://192.168.6.59:8080/user/registration";
        JacksonRequest j =  new JacksonRequest<User>(Request.Method.GET,
                URL,
                new JacksonRequestListener<User>() {
                    @Override
                    public void onResponse(User response, int statusCode, VolleyError error) {
                        System.out.println(response);
                    }

                    @Override
                    public JavaType getReturnType() {
                        return SimpleType.construct(User.class);
                    }
                });

        ApplicationController.getInstance().addToRequestQueue(j);
        return null;
    }

    public static void registerUser(final User user) throws ParseError {
        final String URL = "http://192.168.6.59:8080/user/registration";


        Jackson2Request r = new Jackson2Request(User.class, Request.Method.POST, URL, user, new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("no");
            }
        });

        ApplicationController.getInstance().addToRequestQueue(r);
    }




}
