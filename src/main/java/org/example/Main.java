package org.example;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.servlet.http.HttpServletRequest;
import org.example.service.VirtualThreadEmber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

//@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
        //new VirtualThreadEmber().impl();
    }

}
@RestController
@ResponseBody
class NewJDKController{

    private final ObservationRegistry observationRegistry;

    public NewJDKController(ObservationRegistry observationRegistry) {
        this.observationRegistry = observationRegistry;
    }

    @GetMapping("/factorial/{num}")
    public BigInteger factorial(@PathVariable("num") int num){
        long t1 = System.currentTimeMillis();
        BigInteger res = calcFactorial(BigInteger.valueOf(num));
        System.out.println("Time elapsed: "+(System.currentTimeMillis()-t1)+"ms");
        return Observation.createNotStarted("factorial",observationRegistry)
                .observe(()->res);
        //return calcFactorial(num);
    }
    @GetMapping("/getCall")
    public String getCall(){
        try {
            HashMap<String, String> parameters = new HashMap<>();
            URL url = new URL("https://www.wikihow.com/Save-Money");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);

            DataOutputStream dis = new DataOutputStream(con.getOutputStream());
            dis.writeBytes(ParameterStringBuilder.getParameterString(parameters));
            dis.flush();
            dis.close();
            return dis.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return "All done";
    }

    private String printFact(int num){
        StringBuilder str = new StringBuilder();
        return str.toString();
    }

    private BigInteger calcFactorial(BigInteger num) {
        if(num.intValue()<1)
            throw new BadNumberException("Number is negative");
        //int res = 0;
        if(num.intValue()==1){
            return BigInteger.ONE;
        }else{
            //System.out.println(num);
            return num.multiply(calcFactorial(num.subtract(BigInteger.ONE)));
        }

    }
}
class BadNumberException extends RuntimeException{
    public BadNumberException(String msg){
        super(msg);
    }
}

@ControllerAdvice
class controllerAdvice{
    @ExceptionHandler
    ProblemDetail handle(BadNumberException exception, HttpServletRequest request){
        request.getHeaderNames().asIterator()
                .forEachRemaining(System.out::println);
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
        pd.setDetail(exception.getLocalizedMessage());
        return pd;
    }
}
class ParameterStringBuilder{
    public static String getParameterString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder res = new StringBuilder();
        for(Map.Entry<String, String> entry:params.entrySet()){
            res.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            res.append("=");
            res.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            res.append("&");
        }
        String resString = res.toString();
        return resString.length() > 0
                ? resString.substring(0, resString.length()-1)
                : resString;

    }
}